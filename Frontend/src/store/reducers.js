import { combineReducers } from 'redux';

import authReducer from '../reducers/auth/auth.reducer';

/**
 * Combines all reducers into one big reducer
 * @type None
 */
const rootReducer = combineReducers({
  authReducer,
});

export default rootReducer;
