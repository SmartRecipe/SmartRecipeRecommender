import initialState from './initial.state';

/**
 * Reducer responsible for handling user authentication related information flow
 * @param  {Object} state  Initial State to start 
 * @param  {Object} action Which action to perform
 * @return {Object}        New State 
 */
export default function authReducer(state = initialState, action = {}) {
  switch (action.type) {
    default:
      return state;
  }
}
