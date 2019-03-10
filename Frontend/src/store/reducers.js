import { combineReducers } from 'redux';
import { Router } from 'react-router-dom';

import authReducer from '../reducers/auth/auth.reducer';

const rootReducer = combineReducers({
  authReducer,
});

export default rootReducer;
