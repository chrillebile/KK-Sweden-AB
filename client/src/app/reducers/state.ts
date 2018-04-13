import { RouterState } from 'react-router-redux';
import PalletModel from 'Models/PalletModel';
import RecipeModel from 'Models/RecipeModel';
import OrderModel from 'Models/OrderModel';

export interface RootState {
  pallets: RootState.PalletState,
  recipes: RootState.RecipeState,
  orders: RootState.OrderState
  router: RouterState
}

export interface PalletStateItems {
  palletItems: Array<PalletModel>
}

export interface RecipeStateItems {
  recipeItems: Array<RecipeModel>
}

export interface OrderStateItems {
  orderItems: Array<OrderModel>
}

export namespace RootState {
  export type PalletState = PalletStateItems;
  export type RecipeState = RecipeStateItems;
  export type OrderState = OrderStateItems;
}
