import { apiProxy } from '../../utils/api-proxy.service';
import { apiConstants } from '../../utils/app.constants';
import { actionsSignIn } from '../../utils/app.constants';
import { history, menuItemProps } from '../../utils/app.constants';

/**
 * Creates new user
 * @param  {String} email    
 * @param  {String} password 
 * @return {Object} User        
 */
export function signUp(email, password) {
  /**
   * @param {Boolean}
   */
  function setSignInPending(isSignInPending) {
    return { type: actionsSignIn.pending, isSignInPending};
  }

  /**
   * @param {Boolean}
   */
  function setSignInSuccess(isSignInSuccess, user) {
    return { type: actionsSignIn.success, isSignInSuccess, user };
  }

  /**
   * @param {Boolean}
   */
  function setSignInFailed(isSignInFailed) {
    return { type: actionsSignIn.failed, isSignInFailed};
  }

  return (dispatch) => {
    const user = {
      email,
      password
    };

    dispatch(setSignInPending(true));
    apiProxy.post(`${apiConstants.baseUrl}${apiConstants.users}`, user, '123')
      .then((response) => {
        dispatch(setSignInSuccess(true, response));
      })
      .catch((e) => { // eslint-disable-line
        dispatch(setSignInFailed(true));
        console.log('error getting the user', e);
      }
    );
  }
}

/**
 * Sign in new user
 * @param  {object} Email
 * @param  {object} Password
 * @return
 */
export function signIn(email, password) {
  /**
   * @param {Boolean}
   */
  function setSignInPending(isSignInPending) {
    return { type: actionsSignIn.pending, isSignInPending};
  }

  /**
   * @param {Boolean}
   */
  function setSignInSuccess(isSignInSuccess, user) {
    return { type: actionsSignIn.success, isSignInSuccess, user};
  }

  /**
   * @param {Boolean}
   */
  function setSignInFailed(isSignInFailed) {
    return { type: actionsSignIn.failed, isSignInFailed};
  }

  return (dispatch) => {
    const user = {
      email,
      password
    }; 

    dispatch(setSignInPending(true));
    dispatch(setSignInSuccess(true, user));
    history.push(menuItemProps.recipesMenu.route);
  };
}


/**
 * Signs out user
 * @return
 */
export function signOut(email, password) {
  /**
   * @param {Boolean}
   */
  function setSignOut(isSignInSuccess) {
    return { type: actionsSignIn.signout };
  }

  return (dispatch) => {
    dispatch(setSignOut());
  };
}