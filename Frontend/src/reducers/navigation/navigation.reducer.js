import initialState from './initial.state';
import { actionsNavigation } from '../../utils/app.constants';

/**
 * Reducer responsible for handling user authentication related information flow
 * @param  {Object} state  Initial State to start 
 * @param  {Object} action Which action to perform
 * @return {Object}        New State 
 */
export default function navigationReducer(state = initialState, action = {}) {
  switch (action.type) {
    case actionsNavigation.navigateTo:
      return {
        ...state,
        currentRoute: action.route,
      };

    default:
      return state;
  }
}
