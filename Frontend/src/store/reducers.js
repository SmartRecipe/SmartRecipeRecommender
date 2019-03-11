import { combineReducers } from 'redux';

import authReducer from '../reducers/auth/auth.reducer';
import navigationReducer from '../reducers/navigation/navigation.reducer';

/**
 * Combines all reducers into one big reducer
 * @type None
 */
const rootReducer = combineReducers({
  authReducer,
  navigationReducer,
});

export default rootReducer;
