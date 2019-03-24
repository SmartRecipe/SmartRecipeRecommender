import { apiProxy } from '../../utils/api-proxy.service';
import { apiConstants } from '../../utils/app.constants';
import { actionsSignIn } from '../../utils/app.constants';
import { history, menuItemProps } from '../../utils/app.constants';

/**
 * Sets request as pending
 */
function setSignInPending() {
  return { type: actionsSignIn.pending };
}

/**
 * Sets request as succeded
 */
function setSignInSuccess(user) {
  return { type: actionsSignIn.success, user };
}

/**
 * Sets request as failed
 */
function setSignInFailed() {
  return { type: actionsSignIn.failed };
}

/**
 * Creates new user
 * @param  {String} email    
 * @param  {String} password 
 */
export function signUp(email, password) {
  return (dispatch) => {
    const user = {
      email,
      password
    };

    dispatch(setSignInPending());
    apiProxy.post(`${apiConstants.baseUrl}${apiConstants.users}`, user, '123')
      .then((response) => {
        dispatch(setSignInSuccess(response));
      })
      .catch((e) => { // eslint-disable-line
        dispatch(setSignInFailed());
        console.log('error getting the user', e);
      }
    );
  }
}

/**
 * Sign in existing user
 * @param  {object} Email
 * @param  {object} Password
 */
export function signIn(email, password) {
  return (dispatch) => {
    const user = {
      email,
      password
    }; 

    dispatch(setSignInPending());
    dispatch(setSignInSuccess(user));
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
  function setSignOut() {
    return { type: actionsSignIn.signout };
  }

  return (dispatch) => {
    dispatch(setSignOut());
  };
}