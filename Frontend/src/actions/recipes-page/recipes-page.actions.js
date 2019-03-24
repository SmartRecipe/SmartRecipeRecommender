import { apiProxy } from '../../utils/api-proxy.service';
import { apiConstants } from '../../utils/app.constants';
import { actionsRecipes } from '../../utils/app.constants';

/**
 * Sets request as pending
 */
function setRequestPending() {
  return { type: actionsRecipes.pending };
}

/**
 * Sets request as failed
 */
function setRequestFailed() {
  return { type: actionsRecipes.failed };
}

/**
 * Populates redux store with list of recipes obtained from backend
 * @param  {[Object]} recipes List of recipes
 */
function get(recipes) {
  return { type: actionsRecipes.get, recipes };
}

/**
 * Adds new recipe to the redux store
 * @param  {Object} recipe    Recipe to add
 * @return 
 */
export function addRecipe(recipe) {
  return (dispatch) => {
    dispatch(setRequestPending());
    if (recipe.id) {
      apiProxy.put(`${apiConstants.baseUrl}${apiConstants.recipes}${recipe.id}`, recipe, '123')
      .then((response) => {
        return apiProxy.get(`${apiConstants.baseUrl}${apiConstants.recipes}`, '123');
      })
      .then((response) => {
        dispatch(get(response));      
      })
      .catch((e) => { // eslint-disable-line
        dispatch(setRequestFailed());
        console.log('error getting ingredients', e);
      })
    } else {
      apiProxy.post(`${apiConstants.baseUrl}${apiConstants.recipes}`, recipe, '123')
      .then((response) => {
        return apiProxy.get(`${apiConstants.baseUrl}${apiConstants.recipes}`, '123');
      })
      .then((response) => {
        dispatch(get(response));      
      })
      .catch((e) => { // eslint-disable-line
        dispatch(setRequestFailed());
        console.log('error getting ingredients', e);
      })
    };
  };
}

/**
 * Get list of recipes
 * @return {[Object]} List of recipes
 */
export function getRecipes() {
  return (dispatch) => {
    dispatch(setRequestPending());

    apiProxy.get(`${apiConstants.baseUrl}${apiConstants.recipes}`, '123')
    .then((response) => {
      dispatch(get(response));
    })
    .catch((e) => { // eslint-disable-line
      dispatch(setRequestFailed());
      console.log('error getting recipes', e);
    })
  };
}


/**
 * Delete a recipe
 * @param {Object} recipe 
 */
export function deleteRecipe(id) {
  return (dispatch) => {
    dispatch(setRequestPending());

    apiProxy.delete(`${apiConstants.baseUrl}${apiConstants.recipes}/${id}`, '123')
    .then((response) => {
      return apiProxy.get(`${apiConstants.baseUrl}${apiConstants.recipes}`, '123');
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