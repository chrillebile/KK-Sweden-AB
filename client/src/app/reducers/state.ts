import { RouterState } from 'react-router-redux';
import PalletModel from 'Models/PalletModel';

export interface RootState {
  pallets: RootState.PalletState,
  router: RouterState
}

export interface PalletStateItems {
  palletItems: Array<PalletModel>
}

export namespace RootState {
  export type PalletState = PalletStateItems;
}
