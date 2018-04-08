import { combineReducers, Reducer } from 'redux';
import { RootState } from './state';
import { routerReducer, RouterState } from 'react-router-redux';
import { palletReducer } from 'app/reducers/pallets';

export { RootState, RouterState };

export const rootReducer: Reducer<RootState> = combineReducers<RootState>({
  pallets: palletReducer,
  router: routerReducer
});
