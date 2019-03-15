import createHistory from 'history/createBrowserHistory'; // eslint-disable-line

export const history = createHistory();

export const appConstants = {
    appTitle: "Smart Recipe Recommender",
};

// constants related to in-memory ORM models 
export const ormConstants = {
    nameIngredientModel: 'Ingredient',
}

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

// constants related to ingredient actions
export const actionsIngredients = {
    add: "ACTION_ADD_INGREDIENT",
    delete: "ACTION_DELETE_INGREDIENT",
    update: "ACTION_UPDATE_INGREDIENT",
};

// constants related to recipe actions
export const actionsRecipes = {
    add: "ACTION_ADD_RECIPE",
    delete: "ACTION_DELETE_RECIPE",
    update: "ACTION_UPDATE_RECIPE",
};

// constants related to navigation reducer
// the information on the page changes based on which route user chose
export const actionsNavigation = {
    navigateTo: "ACTION_NAVIGATE_TO",
};

export const ingredientUnits = [
    { id: 1, val: 'lbs.' }, 
    { id: 2, val: 'oz.' }, 
    { id: 3, val: 'kg.' }, 
    { id: 4, val: 'gms.' },
    { id: 5, val: 'ct.' },
    { id: 6, val: 'pc.' },
];

/* eslint-disable */
export const sampleRecipe = {
    name: "Shrimp and Chorizo Paella", 
    description: "\
 \
Heat 1/2 cup of the broth in a pot until simmering, add saffron and set aside for 10 \
minutes. \
 \
 \
Heat oil in a (14- to 16-inch) paella pan or a large, deep skillet over medium-high \
heat. Add chicken, shrimp and chorizo, and cook, stirring occasionally until lightly \
browned, 6 to 8 minutes. Transfer shrimp to a large plate and set aside, leaving \
chicken and chorizo in the pan. Add pimentón, bay leaves, garlic, tomatoes, onion, \
salt and pepper, and cook, stirring often until thickened and fragrant, about 10 \
minutes. Add saffron broth and remaining 4 1/2 cups chicken broth; bring to a boil. \
 \
Add rice and stir very gently to distribute. Top with artichokes and peppers, and cook \
without stirring, until most of the liquid is absorbed, 15 to 18 minutes. Reduce heat \
to medium-low, add reserved shrimp and mussels, tucking them down into the rice, and \
cook again without stirring, until mussels have opened and rice is just tender, 5 to 7 \
minutes more. (Discard any mussels that don’t open.) \
 \
Set aside off of the heat to let rest for 10 minutes, and then serve.",
    shortDescription: "This impressive paella is a perfect party dish and a fun meal to cook together with your \
guests. Add 1 cup of frozen peas along with the mussels, if you like.",
    ingredients: [],
}
/* eslint-enable */