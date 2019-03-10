import React from 'react';
import { render } from 'react-dom';
import { Provider } from 'react-redux';

import App from './containers/app/app';
import configureStore from './store/configureStore';
import initialState from './reducers/auth/initial.state';

const store = configureStore(initialState);

render(
    <Provider store={store}>
        <div>
            <App/>
        </div>
    </Provider>
  , document.getElementById('root')
);