import { combineReducers, Reducer } from 'redux';
import { RootState } from './state';
import { routerReducer, RouterState } from 'react-router-redux';
import { palletReducer } from 'app/reducers/pallets';
import { recipeReducer } from 'app/reducers/recipes';
import { orderReducer } from 'app/reducers/orders';

export { RootState, RouterState };

export const rootReducer: Reducer<RootState> = combineReducers<RootState>({
  pallets: palletReducer,
  recipes: recipeReducer,
  orders: orderReducer,
  router: routerReducer
});
