import createHistory from 'history/createBrowserHistory'; // eslint-disable-line

export const history = createHistory();

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