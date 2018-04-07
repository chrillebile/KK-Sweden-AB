import { Dispatch } from 'redux';
import { RootState } from 'app/reducers';
import axios from 'axios';
import { APIConfig } from 'app/config';


export namespace PalletActions {
  export enum Type {
    GET_PALLETS_REQUEST = 'GET_PALLETS_REQUEST',
    GET_PALLETS_SUCCESS = 'GET_PALLETS_SUCCESS',
    GET_PALLETS_FAILURE = 'GET_PALLETS_FAILURE',
  }

  export function getPallets() {
    return (dispatch: Dispatch<RootState>) => {
      dispatch(getPalletsRequest());
      axios.get(APIConfig.url + '/pallets').then((response) => {
        dispatch(getPalletsSuccess(response.data));
      })
        .catch((error) => {
          console.log('API failure');
          console.log(error);
        });
    };
  }

  /**
   * Action to get one pallet with a specific id.
   * @param {number} id The id of the pallet to search.
   * @returns {any}
   */
  export function getPalletById(id: number): any {
    return (dispatch: Dispatch<RootState>) => {
      dispatch(getPalletsRequest());
      axios.get(APIConfig.url + '/pallets/' + id).then((response) => {
        // When requesting one resource, response.data is not an array. Make it an array so that it can be parsed.
        let res = {
          data: [
            response.data.data
          ]
        };
        dispatch(getPalletsSuccess(res));
      })
        .catch((error) => {
          console.log('API failure when retrieving specific pallet');
          console.log(error);
        });
    };
  }

  function getPalletsSuccess(payload: object | null) {
    return {
      type: Type.GET_PALLETS_SUCCESS,
      payload: payload
    };
  }

  /**
   * Returns an action that retrieving a list of all pallets has been requested.
   * @returns {{type: PalletActions.Type}}
   */
  function getPalletsRequest() {
    return {
      type: Type.GET_PALLETS_REQUEST
    };
  }
}

export type PalletActions = Omit<typeof PalletActions, 'Type'>;
