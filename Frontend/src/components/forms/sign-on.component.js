import PropTypes from 'prop-types';
import React, { Component } from 'react';
import Input from '@material-ui/core/Input';
import Paper from '@material-ui/core/Paper';
import Button from '@material-ui/core/Button';
import InputLabel from '@material-ui/core/InputLabel';
import Typography from '@material-ui/core/Typography';
import CssBaseline from '@material-ui/core/CssBaseline';
import FormControl from '@material-ui/core/FormControl';
import withStyles from '@material-ui/core/styles/withStyles';

import { signIn } from '../../actions/auth/auth.actions';
import { history, menuItemProps, appConstants } from '../../utils/app.constants';

/**
 * Generic form component used as sign in / sign up form
 */
class UserSignOnComponent extends Component {
  render() {
    const { 
      classes,
      isSignUp, // trigger to change the form into a sign up component
      onSubmit, // submit function passed from parent component
      onToggleClicked, // helps toggle between sign in and sign up forms
      signInButtonText, 
    } = this.props;

    return (
      <div>
        <Paper className={classes.paper}>
          <Typography component="h1" variant="h5">
            {appConstants.appTitle}
          </Typography>
          <form className={classes.form} onSubmit={onSubmit}>
            <FormControl margin="normal" required fullWidth>
              <InputLabel htmlFor="email">Email Address</InputLabel>
              <Input id="email" name="email" autoComplete="email" autoFocus />
            </FormControl>
            <FormControl margin="normal" required fullWidth>
              <InputLabel htmlFor="password">Password</InputLabel>
              <Input name="password" type="password" id="password" autoComplete="current-password" />
            </FormControl>
            {
              isSignUp &&
              <FormControl margin="normal" required fullWidth>
                <InputLabel htmlFor="password">Confirm Password</InputLabel>
                <Input name="password" type="password" id="password" />
              </FormControl>
            }
            {
              isSignUp &&
              <Button fullWidth color="primary" onClick={onToggleClicked} className={classes.button}>Sign in instead ?</Button>
            }
            {
              !isSignUp &&
              <Button fullWidth color="primary" onClick={onToggleClicked} className={classes.button}>Don't have an account ?</Button>
            }
            <Button type="submit" fullWidth variant="contained" color="primary" className={classes.submit}>
              {signInButtonText}
            </Button>
          </form>
        </Paper>
      </div>
    );
  }
}

const styles = theme => ({
  paper: {
    marginTop: theme.spacing.unit * 8,
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
    padding: `${theme.spacing.unit * 2}px ${theme.spacing.unit * 3}px ${theme.spacing.unit * 3}px`,
  },
  avatar: {
    margin: theme.spacing.unit,
    backgroundColor: theme.palette.secondary.main,
  },
  form: {
    width: '100%', // Fix IE 11 issue.
    marginTop: theme.spacing.unit,
  },
  submit: {
    marginTop: theme.spacing.unit * 3,
  },
  text: {
    alignItems: 'center',
  },
  button: {
    // margin: theme.spacing.unit,
  },
});

UserSignOnComponent.propTypes = {
  classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(UserSignOnComponent);
