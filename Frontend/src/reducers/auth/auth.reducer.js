import initialState from './initial.state';

/**
 * Reducer which is reponsible to update state
 * @param {object} state - existing state information
 * @param {object} action - new state information
 */
export default function authReducer(state = initialState, action = {}) {
  switch (action.type) {
    default:
      return state;
  }
}
