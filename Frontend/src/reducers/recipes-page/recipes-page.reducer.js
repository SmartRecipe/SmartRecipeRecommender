import initialState from './initial.state';
import { actionsRecipes } from '../../utils/app.constants';

/**
 * Reducer responsible for handling actions performed on Recipes page
 * @param  {Object} state  Initial State to start 
 * @param  {Object} action Which action to perform
 * @return {Object}        New State 
 */
export default function recipesReducer(state = initialState, action = {}) {

 switch (action.type) {
    case actionsRecipes.add:
      const { recipes } = state;
      console.log('add reducer called');

      let found = false;

      for (var i = 0; i < recipes.length; i++) {
        if (recipes[i].id === action.recipe.id) {
          recipes[i] = action.recipe;
          found = true;
        }
      }

      if (!found) {
        recipes.push(action.recipe);
      }

      return {
        ...state,
        recipes,
      };

    case actionsRecipes.get:
      return {
        ...state,
        recipes: action.recipes,
      };

    default:
      return state;
  }
}
