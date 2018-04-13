import { OrderActions } from 'app/actions/orderActions';

const initialState = {
  orderItems: []
};

export function orderReducer(state = initialState, action: any) {
  switch (action.type) {
    case OrderActions.Type.GET_ORDERS_REQUEST:
      return state;
    case OrderActions.Type.GET_ORDERS_SUCCESS:
      return Object.assign({}, state, {
        orderItems: action.payload.data
      });
    default:
      return state;
  }
}
