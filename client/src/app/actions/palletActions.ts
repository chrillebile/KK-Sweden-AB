import { Dispatch } from 'redux';
import { RootState } from 'app/reducers';
import axios from 'axios';
import { APIConfig } from 'app/config';
import PalletModel from 'Models/PalletModel';


export namespace PalletActions {
  export enum Type {
    GET_PALLETS_REQUEST = 'GET_PALLETS_REQUEST',
    GET_PALLETS_SUCCESS = 'GET_PALLETS_SUCCESS',
    GET_PALLETS_FAILURE = 'GET_PALLETS_FAILURE',
    CREATE_PALLET_REQUEST = 'CREATE_PALLET_REQUEST',
    CREATE_PALLET_SUCCESS = 'CREATE_PALLET_SUCCESS',
    CREATE_PALLET_FAILURE = 'CREATE_PALLET_FAILURE'
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

  export function createPallet(pallet: PalletModel, recipeId: number, orderId: number) {
    return (dispatch: Dispatch<RootState>) => {
      if (pallet.deliveryTime && typeof pallet.deliveryTime) {
      }

      dispatch(createPalletRequest());
      axios.post(APIConfig.url + '/pallets', {
        data: {
          amount: pallet.amount,
          productionDate: pallet.productionDate,
          location: pallet.location,
          deliveryTime: pallet.deliveryTime,
          blocked: pallet.blocked,
          recipeId: recipeId,
          orderId: orderId
        }
      }).then((response) => {
        dispatch(createPalletSuccess());
      })
        .catch((error) => {
          console.log(error);
        });
    };
  }

  /**
   * Action to get pallets that have a blocked status.
   * @param {number} blockedPallets Whether to search for blocked or unblocked pallets.
   * @returns {any}
   */
  export function getPalletByBlockedStatus(blockedPallets: boolean): any {
    return (dispatch: Dispatch<RootState>) => {
      dispatch(getPalletsRequest());
      axios.get(APIConfig.url + '/pallets?isBlocked=' + blockedPallets).then((response) => {
        dispatch(getPalletsSuccess(response.data));
      })
        .catch((error) => {
          console.log('API failure when retrieving pallets based on blocked status');
          console.log(error);
        });
    };
  }

  /**
   * Action to get pallets that have been delivered to a customer.
   * @param customerId Id of the customer.
   * @returns {any}
   */
  export function getPalletsByCustomerId(customerId: number): any {
    return (dispatch: Dispatch<RootState>) => {
      dispatch(getPalletsRequest());
      axios.get(APIConfig.url + '/pallets?customerId=' + customerId).then((response) => {
        dispatch(getPalletsSuccess(response.data));
      })
        .catch((error) => {
          console.log('API failure when retrieving pallets based on customerId');
          console.log(error);
        });
    };
  }

  /**
   * Action to get pallets that have been produced between a timespan.
   * @param fromDate Date to search from. Must be in yyyy-MM-dd format.
   * @param toDate Date to search to. Must be in yyyy-MM-dd format.
   * @returns {any}
   */
  export function getPalletsByTimespan(fromDate: string, toDate: string): any {
    return (dispatch: Dispatch<RootState>) => {
      dispatch(getPalletsRequest());
      axios.get(APIConfig.url + '/pallets?startDate=' + fromDate + '&endDate=' + toDate).then((response) => {
        dispatch(getPalletsSuccess(response.data));
      })
        .catch((error) => {
          console.log('API failure when retrieving pallets based on timestamp');
          console.log(error);
        });
    };
  }

  /**
   * Action to get pallets that contain a specific product.
   * @param productId
   * @returns {any}
   */
  export function getPalletsByProductId(productId: number): any {
    return (dispatch: Dispatch<RootState>) => {
      dispatch(getPalletsRequest());
      axios.get(APIConfig.url + '/pallets?recipeId=' + productId).then((response) => {
        dispatch(getPalletsSuccess(response.data));
      })
        .catch((error) => {
          console.log('API failure when retrieving pallets based on product id');
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

  function createPalletRequest() {
    return {
      type: Type.CREATE_PALLET_REQUEST
    };
  }

  function createPalletSuccess() {
    return {
      type: Type.CREATE_PALLET_SUCCESS
    };
  }
}

export type PalletActions = Omit<typeof PalletActions, 'Type'>;
