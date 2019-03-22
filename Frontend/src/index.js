import React from 'react';
import { render } from 'react-dom';
import { Provider } from 'react-redux';
import indigo from '@material-ui/core/colors/indigo';
import CssBaseline from "@material-ui/core/CssBaseline";
import { MuiThemeProvider, createMuiTheme } from '@material-ui/core/styles';

import { persistStore } from 'redux-persist';
import { PersistGate } from 'redux-persist/integration/react'

import App from './containers/app/app';
import configureStore from './store/configureStore';
import initialState from './reducers/auth/initial.state';


const store = configureStore(initialState);

const persistor = persistStore(store);

const theme = createMuiTheme({
  palette: {
    type: 'light',
    primary: indigo,
    background: {
      default: '#dfdfdf',
    },
  },
});

render(
    <Provider store={store}>
      <PersistGate loading={null} persistor={persistor}>
        <MuiThemeProvider theme={theme}>
            <CssBaseline/>
            <App/>
        </MuiThemeProvider>
      </PersistGate>
    </Provider>
  , document.getElementById('root')
);