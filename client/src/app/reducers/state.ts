import { RouterState } from 'react-router-redux';
import PalletModel from 'Models/PalletModel';
import RecipeModel from 'Models/RecipeModel';

export interface RootState {
  pallets: RootState.PalletState,
  recipes: RootState.RecipeState
  router: RouterState
}

export interface PalletStateItems {
  palletItems: Array<PalletModel>
}

export interface RecipeStateItems {
  recipeItems: Array<RecipeModel>
}

export namespace RootState {
  export type PalletState = PalletStateItems;
  export type RecipeState = RecipeStateItems;
}
