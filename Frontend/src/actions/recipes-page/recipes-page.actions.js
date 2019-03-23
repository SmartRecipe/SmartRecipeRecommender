import { apiProxy } from '../../utils/api-proxy.service';
import { apiConstants } from '../../utils/app.constants';
import { actionsRecipes } from '../../utils/app.constants';

/**
 * Adding new recipe to the redux store
 * @param  {Object} recipe    Recipe to add
 * @return 
 */
export function addRecipe(recipe) {
  function get(recipes) {
    return { type: actionsRecipes.get, recipes };
  }

  return (dispatch) => {
    if (recipe.id) {
      apiProxy.put(`${apiConstants.base_url}${apiConstants.recipes}${recipe.id}`, recipe, '123')
      .then((response) => {
        return apiProxy.get(`${apiConstants.base_url}${apiConstants.recipes}`, '123');
      })
      .then((response) => {
        console.log(response);
        dispatch(get(response));      
      })
      .catch((e) => { // eslint-disable-line
        console.log('error getting ingredients', e);
      })
    } else {
      apiProxy.post(`${apiConstants.base_url}${apiConstants.recipes}`, recipe, '123')
      .then((response) => {
        return apiProxy.get(`${apiConstants.base_url}${apiConstants.recipes}`, '123');
      })
      .then((response) => {
        dispatch(get(response));      
      })
      .catch((e) => { // eslint-disable-line
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
  function get(recipes) {
    return { type: actionsRecipes.get, recipes };
  }

  return (dispatch) => {
    apiProxy.get(`${apiConstants.base_url}${apiConstants.recipes}`, '123')
    .then((response) => {
      console.log('recipes : ', response);
      dispatch(get(response));
    })
    .catch((e) => { // eslint-disable-line
      console.log('error getting recipes', e);
    })
  };
}
