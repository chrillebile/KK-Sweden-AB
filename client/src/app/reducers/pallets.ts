import { PalletActions } from 'app/actions';

const initialState = {
  palletItems: []
};

export function palletReducer(state = initialState, action: any) {
  switch (action.type) {
    case PalletActions.Type.GET_PALLETS_REQUEST:
      return state;
    case PalletActions.Type.GET_PALLETS_SUCCESS:
      return Object.assign({}, state, {
        palletItems: action.payload.data
      });
    default:
      return state;
  }
}
