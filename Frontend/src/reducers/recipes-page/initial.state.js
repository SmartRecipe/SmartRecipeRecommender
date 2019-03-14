/**
 * List of recipes saved in the state.
 * Each recipe looks like this - 
 * {
 *     id : '',
 *     name : '',
 *     short_description : '',
 *     description : '',
 * }
 */
import { sampleRecipe } from '../../utils/app.constants';

export default {
    recipes: [
        {
            id: 1,
            name: sampleRecipe.name,
            short_description: sampleRecipe.shortDescription,
            description: sampleRecipe.description,
            ingredients: [],
        },
        {
            id: 2,
            name: sampleRecipe.name,
            short_description: sampleRecipe.shortDescription,
            description: sampleRecipe.description,
            ingredients: [],
        },
        {
            id: 3,
            name: sampleRecipe.name,
            short_description: sampleRecipe.shortDescription,
            description: sampleRecipe.description,
            ingredients: [],
        },
        {
            id: 4,
            name: sampleRecipe.name,
            short_description: sampleRecipe.shortDescription,
            description: sampleRecipe.description,
            ingredients: [],
        },
        {
            id: 5,
            name: sampleRecipe.name,
            short_description: sampleRecipe.shortDescription,
            description: sampleRecipe.description,
            ingredients: [],
        },
    ],
};
