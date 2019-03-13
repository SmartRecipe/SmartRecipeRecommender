import uuidv1 from 'uuid/v1';

import { actionsRecipes } from '../../utils/app.constants';

/**
 * Populates state information when user navigates to another page
 * @param  {string} route    Route to navigate to
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

/*
    const recipe = {
        id: uuidv1(),
        name: "Shrimp and Chorizo Paella",

    }; 

    */