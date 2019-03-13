import uuidv1 from 'uuid/v1';

import { actionsIngredients } from '../../utils/app.constants';

/**
 * Populates state information when user navigates to another page
 * @param  {string} route    Route to navigate to
 * @return 
 */
export function addIngredient() {
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
