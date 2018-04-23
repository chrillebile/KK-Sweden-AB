import { Dispatch } from 'redux';
import { RootState } from 'app/reducers';
import axios from 'axios';
import { APIConfig } from 'app/config';
import { toast } from 'react-toastify';


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
      axios.get(APIConfig.url + '/recipes').then((response) => {
        dispatch(getRecipesSuccess(response.data));
      })
        .catch((error) => {
          APIFailureHandler(error.response.data);
          console.log('API failure: retrieving recipes');
        });
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

  function APIFailureHandler(errorObject: object | any) {
    toast(errorObject.message, {
      type: 'error'
    });
  }
}

export type RecipeActions = Omit<typeof RecipeActions, 'Type'>;
