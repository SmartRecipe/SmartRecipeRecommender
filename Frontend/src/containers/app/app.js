import React from 'react';
import PropTypes from 'prop-types';
import classNames from 'classnames';
import { connect } from 'react-redux';
import { Route, Router } from 'react-router-dom';

import { withStyles } from '@material-ui/core/styles';

import MenuComponent from '../../components/menu/menu.component';
import HomePageComponent from '../home-page/home-page.component';
import { history, menuItemProps } from '../../utils/app.constants';
import RecipesPageContainer from '../recipes-page/recipes-page.component';
import IngredientsPageContainer from '../ingredients-page/ingredients-page.component';

const drawerWidth = 240;

/**
 * Main entry point for the frontend application
 */
class App extends React.Component {
  
  /**
   * @param  {props}
   */
  constructor(props) {
    super(props);

    this.state = {
      open: false,
    };

    this.onDrawerOpen = this.onDrawerOpen.bind(this);
    this.onDrawerClosed = this.onDrawerClosed.bind(this);
    this.onMenuItemClicked = this.onMenuItemClicked.bind(this);
  }

  /**
   * When drawer is opened
   * @param  {object} e Event
   * @return {null}
   */
  onDrawerOpen(e) {
    this.setState({ open: true });
  }

  /**
   * When drawer is closed
   * @param  {object} e Event
   * @return {null}
   */
  onDrawerClosed(e) {
    this.setState({ open: false });
  }

  /**
   * Handles click on one of the menu items
   * @param  {string} link Link of the page to navigate to 
   * @return {null}      
   */
  onMenuItemClicked(link) {
    this.setState({ open: false });
    history.push(link)
  }

  render() {
    const { 
      classes, 
      isSignInFailed,
      isSignInSuccess,
      isSignInPending,
    } = this.props; 

    const { open } = this.state;

    return (
      <div className={classes.root}>
        {/* login page */}
        { !isSignInSuccess && <HomePageComponent/> }

        {/* navigation menu */}
        { 
          isSignInSuccess &&
          <MenuComponent
            open={this.state.open}
            onDrawerOpen={this.onDrawerOpen}
            onDrawerClosed={this.onDrawerClosed}
            onMenuItemClicked={this.onMenuItemClicked}
          />
        }

        
        {/* main content on the page */}
        {
          isSignInSuccess &&
          <main  className={classNames(classes.content, {[classes.contentShift]: open,})}>
              <Router history={history}>
                  <div>
                      <Route path={menuItemProps.recipesMenu.route} component={props => <RecipesPageContainer/>}/> 
                      <Route path={menuItemProps.ingredientsMenu.route} component={props => <IngredientsPageContainer/>}/> 
                  </div>
              </Router>
          </main>
        }
      </div>
    );
  }
}

const styles = theme => ({
  root: {
    display: 'flex',
  },
  content: {
    flexGrow: 1,
    padding: theme.spacing.unit * 3,
    transition: theme.transitions.create('margin', {
      easing: theme.transitions.easing.sharp,
      duration: theme.transitions.duration.leavingScreen,
    }),
    marginLeft: -drawerWidth,
  },
  contentShift: {
    transition: theme.transitions.create('margin', {
      easing: theme.transitions.easing.easeOut,
      duration: theme.transitions.duration.enteringScreen,
    }),
    marginLeft: 0,
  },
});


App.propTypes = {
  classes: PropTypes.object.isRequired,
  theme: PropTypes.object.isRequired,
};

const mapStateToProps = state => ({
  isSignInPending: state.authReducer.isSignInPending,
  isSignInSuccess: state.authReducer.isSignInSuccess,
  isSignInFailed: state.authReducer.isSignInFailed,
});


const mapDispatchToProps = dispatch => ({});


export default connect(mapStateToProps, mapDispatchToProps)(withStyles(styles, { withTheme: true })(App));



