import uuidv1 from 'uuid/v1';

import { actionsIngredients } from '../../utils/app.constants';

/**
 * Add new ingredient to Redux store
 * @param  {Object} ingredient  Ingredient to add
 * @return 
 */
export function addIngredient(ingredient={}) {
  function add(ingredient) {
    let id = uuidv1();

    if (ingredient.id) {
      id = ingredient.id;
    }

    ingredient = {
      ...ingredient,
      id: id,
    }

    return { type: actionsIngredients.add, ingredient };
  }

  return (dispatch) => {
    dispatch(add(ingredient));
  };
}
