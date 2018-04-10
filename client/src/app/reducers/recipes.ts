import { RecipeActions } from 'app/actions/recipeActions';

const initialState = {
  recipeItems: []
};

export function recipeReducer(state = initialState, action: any) {
  switch (action.type) {
    case RecipeActions.Type.GET_RECIPES_REQUEST:
      return state;
    case RecipeActions.Type.GET_RECIPES_SUCCESS:
      return Object.assign({}, state, {
        recipeItems: action.payload.data
      });
    default:
      return state;
  }
}
