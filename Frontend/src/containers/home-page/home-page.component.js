import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import React, { Component } from 'react';
import withStyles from '@material-ui/core/styles/withStyles';

import { signIn, signUp } from '../../actions/auth/auth.actions';
import UserSignOnComponent from '../../components/forms/sign-on.component';

/**
 * Landing page component for unsigned users
 * Presents users with a login page
 */
class HomePageComponent extends Component {
  
  constructor(props) {
    super(props);

    this.state = {
      user: {
        email: '',
        password: '',
      },
      isSignUp: false,
    };

    this.onSignInClicked = this.onSignInClicked.bind(this);
    this.onSignOnFormChanged = this.onSignOnFormChanged.bind(this);
    this.onSignInOptionSelected = this.onSignInOptionSelected.bind(this);
    this.onSignUpOptionSelected = this.onSignUpOptionSelected.bind(this);
  }

  /**
   * When user clicks the sign in button
   * @param  {Object} e Event
   * @return {null}
   */
  onSignInClicked(e) {
    e.preventDefault();

    const { user, isSignUp } = this.state;

    const { email, password } = user;

    if (isSignUp) {
      this.props.signUp(email, password);
    } else {
      this.props.signIn(email, password);
    }
  }

  /**
   * When values in the Sign On forms change
   * @param  {Object} e Event
   */
  onSignOnFormChanged(e) {
    e.preventDefault();

    const { target } = e;

    switch (target.id) {
      case 'email':
        this.setState({
          ...this.state,
          user: {
            ...this.state.user,
            email: target.value,
          }
        });
        break;

      case 'password':
        this.setState({
          ...this.state,
          user: {
            ...this.state.user,
            password: target.value,
          }
        });
        break;

      default:
        break;
    }
  }

  /**
   * When user switches the form to 'Sign In' form
   */
  onSignInOptionSelected() {
    this.setState({
      isSignUp: false,
    });
  }

  /**
   * When user switches the form to 'Sign Up' form
   */
  onSignUpOptionSelected() {
    this.setState({
      isSignUp: true,
    });
  }

  render() {
    const { 
      classes, 
      isSignInFailed,
      isSignInPending,  
      isSignInSuccess,
    } = this.props;
    
    const { user, isSignUp } = this.state;

    return (
      <main className={classes.main}>
        {
          !isSignUp &&
          <UserSignOnComponent
            user={user}
            isSignUp={false}
            isSignInFailed={isSignInFailed}
            isSignInPending={isSignInPending}
            isSignInSuccess={isSignInSuccess}
            onSubmit={this.onSignInClicked}
            onFormChange={this.onSignOnFormChanged}
            onToggleClicked={this.onSignUpOptionSelected}
            signInButtonText="Sign In"
          />
        }

        {
          isSignUp &&
          <UserSignOnComponent
            user={user}
            isSignUp={true}
            onSubmit={this.onSignInClicked}
            isSignInFailed={isSignInFailed}
            isSignInPending={isSignInPending}
            isSignInSuccess={isSignInSuccess}
            onFormChange={this.onSignOnFormChanged}
            onToggleClicked={this.onSignInOptionSelected}
            signInButtonText="Create new account"
          />
        }
      </main>
    );
  }
}

const styles = theme => ({
  main: {
    width: 'auto',
    display: 'block', // Fix IE 11 issue.
    marginLeft: theme.spacing.unit * 3,
    marginRight: theme.spacing.unit * 3,
    [theme.breakpoints.up(400 + theme.spacing.unit * 3 * 2)]: {
      width: 400,
      marginLeft: 'auto',
      marginRight: 'auto',
    },
  },
});

HomePageComponent.propTypes = {
  classes: PropTypes.object.isRequired,
};

const mapStateToProps = state => ({
  user: state.authReducer.user,
  isSignInFailed: state.authReducer.isSignInFailed,
  isSignInPending: state.authReducer.isSignInPending,
  isSignInSuccess: state.authReducer.isSignInSuccess,
});

const mapDispatchToProps = dispatch => ({
  signIn: (email, password) => dispatch(signIn(email, password)),
  signUp: (email, password) => dispatch(signUp(email, password)),
});

export default connect(mapStateToProps, mapDispatchToProps)(withStyles(styles)(HomePageComponent));
