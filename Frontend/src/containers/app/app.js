import React, { Component } from 'react';
import { Route, Router } from 'react-router-dom';
import { Navbar, Nav, Form, FormControl, Button  } from 'react-bootstrap';

import '@trendmicro/react-sidenav/dist/react-sidenav.css';

import { history, menuItemProps } from '../../utils/app.constants';

import RecipesPageContainer from '../recipes-page/recipes-page.component';
import IngredientsPageContainer from '../ingredients-page/ingredients-page.component';

/**
 * Entry point for frontend app
 * Uses react router to change between links 
 * Uses Navigation Bar component to allow moving to different pages
 */
class App extends Component {
  render() {
    return (
        <div>
            <Navbar bg="primary" variant="dark" expand="lg" collapseOnSelect sticky="top">
                <Navbar.Brand href="/">Smart Recipe Recommender</Navbar.Brand>
                <Navbar.Toggle aria-controls="basic-navbar-nav" />
                <Navbar.Collapse id="basic-navbar-nav">
                    <Nav className="mr-auto">
                        <Nav.Link href={menuItemProps.recipesMenu.route}>{menuItemProps.recipesMenu.title}</Nav.Link>
                        <Nav.Link href={menuItemProps.ingredientsMenu.route}>{menuItemProps.ingredientsMenu.title}</Nav.Link>
                    </Nav>
                    <Form inline>
                        <FormControl type="text" placeholder="Search" className=" mr-sm-2" />
                        <Button type="submit">Submit</Button>
                    </Form>
                </Navbar.Collapse>
            </Navbar>
            <div className="sample"/>
            <Router history={history}>
                <div>
                    <Route path={menuItemProps.recipesMenu.route} component={props => <RecipesPageContainer/>}/> 
                    <Route path={menuItemProps.ingredientsMenu.route} component={props => <IngredientsPageContainer/>}/> 
                </div>
            </Router>
        </div>
    );
  }
}

export default App;