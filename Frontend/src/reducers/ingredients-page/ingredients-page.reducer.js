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
    case actionsIngredients.add:
      return {
        ...state,
        ingredients: [
            ...state.ingredients,
            action.ingredient,
        ],
      };

    default:
      return state;
  }
}
