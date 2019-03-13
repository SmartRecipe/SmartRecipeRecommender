import { combineReducers } from 'redux';

import authReducer from '../reducers/auth/auth.reducer';
import navigationReducer from '../reducers/navigation/navigation.reducer';
import recipesReducer from '../reducers/recipes-page/recipes-page.reducer';
import ingredientsReducer from '../reducers/ingredients-page/ingredients-page.reducer';

/**
 * Combines all reducers into one big reducer
 * @type None
 */
const rootReducer = combineReducers({
  authReducer,
  recipesReducer,
  navigationReducer,
  ingredientsReducer,
});

export default rootReducer;
