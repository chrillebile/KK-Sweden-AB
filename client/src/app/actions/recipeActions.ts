import { Dispatch } from 'redux';
import { RootState } from 'app/reducers';


export namespace RecipeActions {
  export enum Type {
    GET_RECIPES_REQUEST = 'GET_RECIPES_REQUEST',
    GET_RECIPES_SUCCESS = 'GET_RECIPES_SUCCESS',
    GET_RECIPES_FAILURE = 'GET_RECIPES_FAILURE',
  }

  /**
   * Retrieve all recipes.
   * @returns {(dispatch: Dispatch<RootState>) => void}
   */
  export function getRecipes() {
    return (dispatch: Dispatch<RootState>) => {
      dispatch(getRecipesRequest());
      dispatch(getRecipesSuccess({
        "data": [
          {
            "id": 1,
            "name": "Choklad Kakor"
          },
          {
            "id": 2,
            "name": "Vanilj Kakor"
          }
        ]
      }))
    };
  }

  /**
   * Returns an action that retrieving list of recipes succeeded.
   * @param {object | null} payload The recipe list retrieved.
   * @returns {{type: RecipeActions.Type; payload: object | null}}
   */
  function getRecipesSuccess(payload: object | null) {
    return {
      type: Type.GET_RECIPES_SUCCESS,
      payload: payload
    };
  }

  /**
   * Returns an action that retrieving a list of all pallets has been requested.
   * @returns {{type: PalletActions.Type}}
   */
  function getRecipesRequest() {
    return {
      type: Type.GET_RECIPES_REQUEST
    };
  }
}

export type RecipeActions = Omit<typeof RecipeActions, 'Type'>;
