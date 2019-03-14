import uuidv1 from 'uuid/v1';

import { actionsRecipes } from '../../utils/app.constants';

/**
 * Adding new recipe to the redux store
 * @param  {Object} recipe    Recipe to add
 * @return 
 */
export function addRecipe(recipe) {
  function add(recipe) {
    recipe = {
      ...recipe,
      id: uuidv1(),
    }

    return { type: actionsRecipes.add, recipe: recipe };
  }

  return (dispatch) => {
    dispatch(add(recipe));
  };
}