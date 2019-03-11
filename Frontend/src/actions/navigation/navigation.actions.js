import { actionsNavigation } from '../../utils/app.constants';

/**
 * Populates state information when user navigates to another page
 * @param  {string} route    Route to navigate to
 * @return 
 */
export function navigateTo(route) {
  function navigateTo(route) {
    return { type: actionsNavigation.navigateTo, route: route };
  }

  return (dispatch) => {
    dispatch(navigateTo(route));
  };
}
