import React, { Component } from 'react';
import SideNav from '@trendmicro/react-sidenav';
import { Route, Router } from 'react-router-dom';

import '@trendmicro/react-sidenav/dist/react-sidenav.css';

import MenuItem from '../../components/menu/menu-item.component';
import { history, menuItemProps } from '../../utils/app.constants';

class App extends Component {
  render() {
    return (
        <div className="App">
            <Router history={history}>
                <Route render={({ location, history }) => (
                    <React.Fragment>
                        <SideNav
                            onSelect={(selected) => {
                                const to = selected;
                                if (location.pathname !== to) {
                                    history.push(to);
                                }
                            }}>
                            <SideNav.Toggle />
                            <SideNav.Nav defaultSelected="home">
                                <MenuItem title={menuItemProps.recipesMenu.title}/>
                                <MenuItem title={menuItemProps.ingredientsMenu.title}/>
                            </SideNav.Nav>
                        </SideNav>
                        <main>
                            <Route path="/" exact/>
                            <Route path={menuItemProps.recipesMenu.route}/>
                            <Route path={menuItemProps.ingredientsMenu.route}/>
                        </main>
                    </React.Fragment>)}
                />
            </Router>
        </div>
    );
  }
}

export default App;

