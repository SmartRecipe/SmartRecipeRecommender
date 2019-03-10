import React, { Component } from 'react';
import SideNav, { NavItem, NavIcon, NavText } from '@trendmicro/react-sidenav';
import Container from 'react-bootstrap/Container'
import { Route, Router } from 'react-router-dom';

import '@trendmicro/react-sidenav/dist/react-sidenav.css';

import { history, menuItemProps } from '../../utils/app.constants';

import RecipesPageContainer from '../recipes-page/recipes-page.component';
import IngredientsPageContainer from '../ingredients-page/ingredients-page.component';

class App extends Component {
  render() {
    return (
        <Container>
            <Router history={history}>
                <Route render={({ location, history }) => (
                    <React.Fragment>
                        <SideNav
                            onSelect={(selected) => {
                                const to = selected;
                                if (location.pathname !== to) {
                                    history.push(to);
                                }
                                console.log('Triggered change');
                                console.log(location);
                            }}>
                            <SideNav.Toggle />
                            <SideNav.Nav defaultSelected={menuItemProps.recipesMenu.key}>
                                <NavItem eventKey={menuItemProps.recipesMenu.key}>
                                    <NavIcon>
                                        <i className="fa fa-fw fa-home" style={{ fontSize: '1.75em' }} />
                                    </NavIcon>
                                    <NavText>
                                        {menuItemProps.recipesMenu.title}
                                    </NavText>
                                </NavItem>
                                <NavItem eventKey={menuItemProps.ingredientsMenu.key}>
                                    <NavIcon>
                                        <i className="fa fa-fw fa-home" style={{ fontSize: '1.75em' }} />
                                    </NavIcon>
                                    <NavText>
                                        {menuItemProps.ingredientsMenu.title}
                                    </NavText>
                                </NavItem>
                            </SideNav.Nav>
                        </SideNav>
                        <header>
                            <nav className="navbar second-menu">
                            </nav>
                        </header>
                        <main>
                            <Route path="/" exact/>
                            <Route path={menuItemProps.recipesMenu.route} component={props => <RecipesPageContainer/>}/>
                            <Route path={menuItemProps.ingredientsMenu.route} component={props => <IngredientsPageContainer/>}/>
                        </main>
                    </React.Fragment>)}
                />
            </Router>
        </Container>
    );
  }
}

export default App;

