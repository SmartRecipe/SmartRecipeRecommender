import createHistory from 'history/createBrowserHistory'; // eslint-disable-line

export const history = createHistory();

export const appConstants = {
    appTitle: "Smart Recipe Recommender",
};

// properties of items in the navigation menu
export const menuItemProps = {
    recipesMenu : {
        id: 1,
        key: "recipes",
        title: "Recipes",
        route: "/recipes",
    },
    ingredientsMenu: {
        id: 2,
        key: "ingredients", 
        title: "Ingredients",
        route: "/ingredients",
    },
}

// common constants related to API actions
export const statusRequests = {
    pending: "REQUEST_PENDING",
    success: "REQUEST_SUCCESS",
    fail: "REQUEST_FAIL",
};

// constants related to user sign in actions
export const actionsSignIn = {
    failed: "ACTION_SIGN_IN_FAILED",
    pending: "ACTION_SIGN_IN_PENDING",
    success: "ACTION_SIGN_IN_SUCCESS",
    signout: "ACTION_SIGN_OUT",
};
