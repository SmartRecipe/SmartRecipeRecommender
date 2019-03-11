import initialState from './initial.state';
import { actionsSignIn } from '../../utils/app.constants';

/**
 * Reducer responsible for handling user authentication related information flow
 * @param  {Object} state  Initial State to start 
 * @param  {Object} action Which action to perform
 * @return {Object}        New State 
 */
export default function authReducer(state = initialState, action = {}) {
  switch (action.type) {
    case actionsSignIn.pending:
      return {
        ...state,
        isSignInFailed: false,
        isSignInSuccess: false,
        isSignInPending: action.isSignInPending,
      };

    case actionsSignIn.success:
      return {
        ...state,
        isSignInFailed: false,
        isSignInPending: false,
        isSignInSuccess: action.isSignInSuccess,
      };

    case actionsSignIn.failed:
      return {
        ...state,
        isSignInPending: false,
        isSignInSuccess: false,
        isSignInFailed: action.isSignInFailed,
      };

    case actionsSignIn.signout:
      return {
        ...state,
        isSignInFailed: false,
        isSignInPending: false,
        isSignInSuccess: false,
      };

    default:
      return state;
  }
}
