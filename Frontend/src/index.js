import React from 'react';
import { render } from 'react-dom';
import { Provider } from 'react-redux';
import CssBaseline from "@material-ui/core/CssBaseline";
import indigo from '@material-ui/core/colors/indigo';
import { MuiThemeProvider, createMuiTheme } from '@material-ui/core/styles';

import App from './containers/app/app';
import configureStore from './store/configureStore';
import initialState from './reducers/auth/initial.state';

const store = configureStore(initialState);

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
        <MuiThemeProvider theme={theme}>
            <CssBaseline/>
            <App/>
        </MuiThemeProvider>
    </Provider>
  , document.getElementById('root')
);