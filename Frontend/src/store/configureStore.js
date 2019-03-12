import thunk from 'redux-thunk';
import { createStore as createReduxStore, applyMiddleware } from 'redux';

import storage from 'redux-persist/lib/storage';
import { persistReducer } from 'redux-persist';

import rootReducer from './reducers';

const persistConfig = {
  key: 'root',
  storage,
};

const persistedReducer = persistReducer(persistConfig, rootReducer);


const configureStore = (initialState = {}) => {
  const middleware = [thunk];
  const store = createReduxStore(
    persistedReducer,
    initialState,
    applyMiddleware(...middleware),
  );

  return store;
};

export default configureStore;