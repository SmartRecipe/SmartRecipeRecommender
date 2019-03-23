import PropTypes from 'prop-types';
import React, { Component } from 'react';
import Input from '@material-ui/core/Input';
import Paper from '@material-ui/core/Paper';
import Button from '@material-ui/core/Button';
import InputLabel from '@material-ui/core/InputLabel';
import Typography from '@material-ui/core/Typography';
import FormControl from '@material-ui/core/FormControl';
import withStyles from '@material-ui/core/styles/withStyles';
import CircularProgress from '@material-ui/core/CircularProgress';

import { appConstants } from '../../utils/app.constants';

/**
 * Generic form component used as sign in / sign up form
 */
class UserSignOnComponent extends Component {
  
  constructor(props) {
    super(props);

    this.state = {
      password2: '',
    };

    this.onFormSubmit = this.onFormSubmit.bind(this);
    this.onPassword2Changed = this.onPassword2Changed.bind(this);
  }

  onFormSubmit(e, nextFunction) {
    const { password2 } = this.state;
    const { user, isSignUp } = this.props;
    const { password } = user;

    if ((password === password2) || !isSignUp) {
      nextFunction(e);
    } else {

    }
  }

  onPassword2Changed(e) {
    this.setState({
      password2: e.target.value,
    });
  }

  render() {
    const {
      user,
      classes,
      isSignUp, // trigger to change the form into a sign up component
      onSubmit, // submit function passed from parent component
      onFormChange,
      onToggleClicked, // helps toggle between sign in and sign up forms
      signInButtonText, 
      isSignInFailed,
      isSignInPending,  
      isSignInSuccess,
    } = this.props;

    const { email, password } = user; 

    const { password2 } = this.state;

    return (
      <div>
        <Paper className={classes.paper}>
          <Typography component="h1" variant="h5">
            {appConstants.appTitle}
          </Typography>
          <form name="signOn" className={classes.form} onSubmit={(e) => this.onFormSubmit(e, onSubmit)}>
            <FormControl margin="normal" required fullWidth>
              <InputLabel htmlFor="email">Email Address</InputLabel>
              <Input id="email" name="email" value={email} autoComplete="email" autoFocus onChange={onFormChange}/>
            </FormControl>
            <FormControl margin="normal" required fullWidth>
              <InputLabel htmlFor="password">Password</InputLabel>
              <Input name="password" type="password" value={password} id="password" autoComplete="current-password" onChange={onFormChange}/>
            </FormControl>
            {
              isSignUp &&
              <FormControl margin="normal" required fullWidth>
                <InputLabel htmlFor="password2">Confirm Password</InputLabel>
                <Input name="password2" type="password" value={password2} id="password" onChange={this.onPassword2Changed}/>
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
            {
              !isSignInPending &&
              <Button type="submit" name="signOnButton" fullWidth variant="contained" color="primary" className={classes.submit}>
                {signInButtonText}
              </Button>
            }
            {
              isSignInPending &&
              <CircularProgress className={classes.progress} />
            }   
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
  progress: {
    left: '50%',
    marginLeft: -20,
    position: 'relative',
  },
});

UserSignOnComponent.propTypes = {
  classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(UserSignOnComponent);
