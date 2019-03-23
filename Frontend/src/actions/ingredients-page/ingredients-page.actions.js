import uuidv1 from 'uuid/v1';

import { apiProxy } from '../../utils/api-proxy.service';
import { apiConstants } from '../../utils/app.constants';
import { actionsIngredients } from '../../utils/app.constants';

/**
 * Add new ingredient to Redux store
 * @param  {Object} ingredient  Ingredient to add
 * @return 
 */
export function addIngredient(ingredient={}) {
  function get(ingredients) {
    return { type: actionsIngredients.get, ingredients };
  }

  return (dispatch) => {
    let id = uuidv1();

    if (ingredient.id) {
      id = ingredient.id;
    }

    ingredient = {
      ...ingredient,
      id: id,
    }

    apiProxy.post(`${apiConstants.baseUrl}${apiConstants.ingredients}`, ingredient, '123')
    .then((response) => {
      return apiProxy.get(`${apiConstants.baseUrl}${apiConstants.ingredients}`, '123');
    })
    .then((response) => {
      console.log(response);
      dispatch(get(response));      
    })
    .catch((e) => { // eslint-disable-line
      console.log('error getting ingredients', e);
    })
  };
}

/**
 * Get list of ingredients
 * @return {[Object]} List of ingredients
 */
export function getIngredients() {
  function get(ingredients) {
    return { type: actionsIngredients.get, ingredients };
  }

  return (dispatch) => {
    apiProxy.get(`${apiConstants.baseUrl}${apiConstants.ingredients}`, '123')
    .then((response) => {
      console.log(response);
      dispatch(get(response));
    })
    .catch((e) => { // eslint-disable-line
      console.log('error getting ingredients', e);
    })
  };
}
