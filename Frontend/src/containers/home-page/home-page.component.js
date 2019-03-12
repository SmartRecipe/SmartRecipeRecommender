import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import React, { Component } from 'react';
import withStyles from '@material-ui/core/styles/withStyles';

import { signIn } from '../../actions/auth/auth.actions';
import { history, menuItemProps } from '../../utils/app.constants';
import UserSignOnComponent from '../../components/forms/sign-on.component';

/**
 * Landing page component for unsigned users
 * Presents users with a login page
 */
class HomePageComponent extends Component {
  
  constructor(props) {
    super(props);

    this.state = {
      isSignUp: false,
    };

    this.onSignInClicked = this.onSignInClicked.bind(this);
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

    this.props.signIn(null, null);

    console.log('calling sign in');

    history.push(menuItemProps.recipesMenu.route);
  }

  onSignInOptionSelected() {
    this.setState({
      isSignUp: false,
    });
  }

  onSignUpOptionSelected() {
    this.setState({
      isSignUp: true,
    });
  }

  render() {
    const { isSignUp } = this.state;
    const { classes } = this.props;

    return (
      <main className={classes.main}>
        {
          !isSignUp &&
          <UserSignOnComponent
            isSignUp={false}
            onSubmit={this.onSignInClicked}
            onToggleClicked={this.onSignUpOptionSelected}
            signInButtonText="Sign In"
          />
        }

        {
          isSignUp &&
          <UserSignOnComponent
            isSignUp={true}
            onSubmit={this.onSignInClicked}
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

});

const mapDispatchToProps = dispatch => ({
  signIn: (email, password) => dispatch(signIn(email, password)),
});

export default connect(mapStateToProps, mapDispatchToProps)(withStyles(styles)(HomePageComponent));
