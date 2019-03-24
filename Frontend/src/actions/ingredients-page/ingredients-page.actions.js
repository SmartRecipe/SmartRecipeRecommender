import { apiProxy } from '../../utils/api-proxy.service';
import { apiConstants } from '../../utils/app.constants';
import { actionsIngredients, statusRequests } from '../../utils/app.constants';

/**
 * Sets request as pending
 */
function setRequestPending() {
  return { type: actionsIngredients.pending };
}

/**
 * Sets request as failed
 */
function setRequestFailed() {
  return { type: actionsIngredients.failed };
}

/**
 * Populates redux store with ingredients list
 * @param  {[Object]} ingredients List of ingredients
 */
function get(ingredients) {
  return { type: actionsIngredients.get, ingredients };
}

/**
 * Add new ingredient to Redux store
 * @param  {Object} ingredient  Ingredient to add
 * @return 
 */
export function addIngredient(ingredient={}) {
  return (dispatch) => {
    dispatch(setRequestPending());

    // if ingredient is edited, then PUT existing ingredient, else POST new ingredient
    if (ingredient.id) {
      apiProxy.put(`${apiConstants.baseUrl}${apiConstants.ingredients}${ingredient.id}`, ingredient, '123')
      .then((response) => {
        return apiProxy.get(`${apiConstants.baseUrl}${apiConstants.ingredients}`, '123');
      })
      .then((response) => {
        dispatch(get(response));      
      })
      .catch((e) => { // eslint-disable-line
        dispatch(setRequestFailed());
        console.log('Error getting ingredients', e);
      })
    } else {
      apiProxy.post(`${apiConstants.baseUrl}${apiConstants.ingredients}`, ingredient, '123')
      .then((response) => {
        return apiProxy.get(`${apiConstants.baseUrl}${apiConstants.ingredients}`, '123');
      })
      .then((response) => {
        dispatch(get(response));      
      })
      .catch((e) => { // eslint-disable-line
        dispatch(setRequestFailed());
        console.log('Error getting ingredients', e);
      })
    };
  };
}

/**
 * Get list of ingredients
 * @return {[Object]} List of ingredients
 */
export function getIngredients() {
  return (dispatch) => {
    dispatch(setRequestPending());

    apiProxy.get(`${apiConstants.baseUrl}${apiConstants.ingredients}`, '123')
    .then((response) => {
      dispatch(get(response));
    })
    .catch((e) => { // eslint-disable-line
      dispatch(setRequestFailed());
      console.log('Error getting ingredients', e);
    })
  };
}

/**
 * Delete an ingredient
 * @param {Object} ingredient 
 */
export function deleteIngredient(id) {
  return (dispatch) => {
    dispatch(setRequestPending());

    apiProxy.delete(`${apiConstants.baseUrl}${apiConstants.ingredients}/${id}`, '123')
    .then((response) => {
      return apiProxy.get(`${apiConstants.baseUrl}${apiConstants.ingredients}`, '123');
    })
    .then((response) => {
      dispatch(get(response));      
    })
    .catch((e) => { // eslint-disable-line
      dispatch(setRequestFailed());
      console.log('Error deleting ingredient', e);
    })
  };
}