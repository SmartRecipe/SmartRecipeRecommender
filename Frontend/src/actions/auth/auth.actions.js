import { actionsSignIn } from '../../utils/app.constants';

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
  function setSignInSuccess(isSignInSuccess) {
    return { type: actionsSignIn.success, isSignInSuccess};
  }

  /**
   * @param {Boolean}
   */
  function setSignInFailed(isSignInFailed) {
    return { type: actionsSignIn.failed, isSignInFailed};
  }

  return (dispatch) => {
    dispatch(setSignInPending(false));
    dispatch(setSignInFailed(false));
    dispatch(setSignInSuccess(true));
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