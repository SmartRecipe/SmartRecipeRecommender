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
      return {
        ...state,
        recipes: [
            ...state.recipes,
            action.recipe,
        ],
      };

    default:
      return state;
  }
}
