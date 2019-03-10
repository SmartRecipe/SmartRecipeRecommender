import { createStore as createReduxStore } from 'redux';

import rootReducer from './reducers';

const configureStore = (initialState = {}) => {
  const store = createReduxStore(
    rootReducer,
    initialState,
  );

  return store;
};

export default configureStore;