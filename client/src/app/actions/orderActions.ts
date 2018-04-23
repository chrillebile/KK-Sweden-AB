import { Dispatch } from 'redux';
import { RootState } from 'app/reducers';
import axios from 'axios';
import { APIConfig } from 'app/config';


export namespace OrderActions {
  export enum Type {
    GET_ORDERS_REQUEST = 'GET_ORDERS_REQUEST',
    GET_ORDERS_SUCCESS = 'GET_ORDERS_SUCCESS',
    GET_ORDERS_FAILURE = 'GET_ORDERS_FAILURE',
  }

  /**
   * Retrieve all orders.
   * @returns {(dispatch: Dispatch<RootState>) => void}
   */
  export function getOrders() {
    return (dispatch: Dispatch<RootState>) => {
      dispatch(getOrdersRequest());
      axios.get(APIConfig.url + '/orders').then((response) => {
        dispatch(getOrdersSuccess(response.data));
      })
        .catch((error) => {
          console.log('API failure: retrieving orders');
          console.log(error);
        });
    };
  }

  /**
   * Returns an action that retrieving list of orders succeeded.
   * @param {object | null} payload The order list retrieved.
   * @returns {{type: OrderActions.Type; payload: object | null}}
   */
  function getOrdersSuccess(payload: object | null) {
    return {
      type: Type.GET_ORDERS_SUCCESS,
      payload: payload
    };
  }

  /**
   * Returns an action that retrieving a list of all pallets has been requested.
   * @returns {{type: OrderActions.Type}}
   */
  function getOrdersRequest() {
    return {
      type: Type.GET_ORDERS_REQUEST
    };
  }
}

export type OrderActions = Omit<typeof OrderActions, 'Type'>;