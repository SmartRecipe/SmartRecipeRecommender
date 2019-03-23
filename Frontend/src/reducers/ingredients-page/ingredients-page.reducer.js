import initialState from './initial.state';
import { actionsIngredients } from '../../utils/app.constants';

/**
 * Reducer responsible for handling user authentication related information flow
 * @param  {Object} state  Initial State to start 
 * @param  {Object} action Which action to perform
 * @return {Object}        New State 
 */
export default function ingredientsReducer(state = initialState, action = {}) {
  switch (action.type) {
    // Add new ingredient
    case actionsIngredients.add:
      let found = false;
      const { ingredients } = state;
      for (var i = 0; i < ingredients.length; i++) {
        if (ingredients[i].id === action.ingredient.id) {
          ingredients[i] = action.ingredient;
          found = true;
        }
      }
      if (!found) {
        ingredients.push(action.ingredient);
      }
      return {
        ...state,
        ingredients,
      };
    // Get all ingredients
    case actionsIngredients.get:
      return {
        ...state,
        ingredients: action.ingredients,
      };

    default:
      return state;
  }
}
