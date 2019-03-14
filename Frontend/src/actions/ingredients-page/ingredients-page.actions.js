import uuidv1 from 'uuid/v1';

import { actionsIngredients } from '../../utils/app.constants';

/**
 * Add new ingredient to Redux store
 * @param  {Object} ingredient  Ingredient to add
 * @return 
 */
export function addIngredient(ingredient={}) {
  function add() {

    const ingredient = {
        id: uuidv1(),
        name: 'ingredient 1',
        qty: 10,
        unit: 'gms',
    };

    return { type: actionsIngredients.add, ingredient };
  }

  return (dispatch) => {
    dispatch(add());
  };
}
