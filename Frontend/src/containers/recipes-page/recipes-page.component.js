import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import Fab from '@material-ui/core/Fab';
import React, { Component } from 'react';
import Grid from '@material-ui/core/Grid';
import { withStyles } from '@material-ui/core/styles';

import AddIcon from '@material-ui/icons/Add';

import { addRecipe } from '../../actions/recipes-page/recipes-page.actions';

import RecipeCardComponent from '../../components/cards/recipe-card.component';
import AddRecipeDialog from '../../components/dialogs/add-recipe-dialog.component';

// Row size of recipe grid
const GRID_ROW_SIZE = 4;

/**
 * Main Container for recipes page. 
 */
class RecipesPageContainer extends Component {
  constructor(props) {
    super(props);

    this.state = {
      showDialog: false,
      recipe: {
        name: '',
        short_description: '',
        description: '',
        ingredients: [],
      },
    };

    this.onDialogClosed = this.onDialogClosed.bind(this);
    this.onDialogSubmit = this.onDialogSubmit.bind(this);
    this.onDialogFormChange = this.onDialogFormChange.bind(this);
    this.onAddButtonClicked = this.onAddButtonClicked.bind(this);
    this.onFormIngredientAdded = this.onFormIngredientAdded.bind(this);
    this.onFormIngredientDeleted = this.onFormIngredientDeleted.bind(this);
    this.onRecipeEditButtonClicked = this.onRecipeEditButtonClicked.bind(this);
    this.onRecipeDeleteButtonClicked = this.onRecipeDeleteButtonClicked.bind(this);
  }

  /**
   * When 'add new recipe' button is clicked
   * @return
   */
  onAddButtonClicked() {
    this.setState({
      showDialog: true,
    });
  } 

  /**
   * When new recipe dialog is closed
   * @return
   */
  onDialogClosed() {
    this.setState({
      showDialog: false,
    });
  }

  /**
   * Handles subimitting recipe form
   * @param  {Object} e      Event
   * @param  {Object} recipe New recipe to add
   * @return
   */
  onDialogSubmit(e, recipe) {
    this.onDialogClosed();

    this.props.addRecipe(recipe);
  }

  /**
   * Handles changes on new 'recipe' form
   * @param  {Object} e Event
   * @return
   */
  onDialogFormChange(e) {
    e.preventDefault();

    const { target } = e;

    switch (target.id) {
      case 'title':
        this.setState({
          recipe: {
            ...this.state.recipe,
            name: target.value,
          }
        });
        break;
      case 'short_description':
        this.setState({
          recipe: {
            ...this.state.recipe,
            short_description: target.value,
          }
        });
        break;
      case 'description':
        this.setState({
          recipe: {
            ...this.state.recipe,
            description: target.value,
          }
        });
        break;
      default:
        break;
    }
  }

  /**
   * Handles new ingredient added in a recipe form
   * @param  {Object} e event
   */
  onFormIngredientAdded(e) {
    const { 
      ingredients: currentIngredients,
    } = this.state.recipe;

    const {
      allIngredients
    } = this.props;

    const id = e.target.value;

    for (var i = 0; i < currentIngredients.length; i++) {
      if (currentIngredients[i].id === id) {
        return
      }
    }

    this.setState(state => {
      const ingredients = [...state.recipe.ingredients];

      let chipToAdd = {
        id: id,
      };

      for (var i = 0; i < allIngredients.length; i++) {
        if (allIngredients[i].id === id) {
          chipToAdd = allIngredients[i];
        }
      }

      ingredients.push(chipToAdd);
      return {
        recipe: {
          ...state.recipe,
          ingredients,
        } 
      };
    });
  }

  /**
   * Handles event when ingredient is deleted from a recipe
   * @param  {Object} data Ingredient data
   */
  onFormIngredientDeleted(data) {
    this.setState(state => {
      const ingredients = [...state.recipe.ingredients];
      const chipToDelete = ingredients.indexOf(data);
      ingredients.splice(chipToDelete, 1);
      return { 
        ...state,
        recipe: {
          ...state.recipe,
          ingredients, 
        }
      };
    });
  }

  /**
   * Handles click on 'Edit' button on 'recipe' card
   * @param  {Object} id Id of the recipe clicked
   */
  onRecipeEditButtonClicked(id) {
    const recipe = this.props.recipes.filter((recipe) => recipe.id === id);

    this.setState({
      showDialog: true,
      recipe: recipe[0],
    });
  }

  onRecipeDeleteButtonClicked(id) {

  }

  /**
   * Generates a grid of specified size out of 
   * all recipe components available in the store.
   * @param  {Array} recipes List of recipes
   */
  getRecipesGrid(recipes) {
    const totalRecipes = recipes.length;

    const totalRows = totalRecipes / GRID_ROW_SIZE;

    let rows = []

    for (var i = 0; i < totalRows; i++) {
      const start = i*GRID_ROW_SIZE;
      let end = (i+1)*GRID_ROW_SIZE;
      end = (totalRecipes < end) ? totalRecipes : end; 
      let cols = []
      for (var j = start; j < end; j++) {
        const currentRecipe = recipes[j];
        if ((currentRecipe !== undefined) && (currentRecipe !== null)) {
          const { id, name, short_description, description, ingredients } = currentRecipe;
          cols.push(
            <Grid key={"recipe_item_" + j} item sm>
              <RecipeCardComponent
                id={id}
                key={id}
                title={name}
                description={description}
                ingredients={ingredients}
                shortDescription={short_description}
                onEditButtonClicked={this.onRecipeEditButtonClicked}
                onDeleteButtonClicked={this.onRecipeDeleteButtonClicked}
              />
            </Grid>);
        }
      }
      rows.push(<Grid key={"ing_row_" + i} container spacing={8}>{cols}</Grid>);
    }

    return rows;
  }

  render() {
    const { showDialog, recipe } = this.state;

    const { classes, recipes } = this.props;

    let recipesGrid = []

    if (recipes !== undefined && recipes !== null) recipesGrid = this.getRecipesGrid(recipes);

    return (
        <div className="card-deck-container">
          <Fab color="primary" aria-label="Add" className={classes.fab} onClick={this.onAddButtonClicked}>
            <AddIcon />
          </Fab>
          {
            <AddRecipeDialog 
              open={showDialog} 
              onClose={this.onDialogClosed} 
              onSubmit={this.onDialogSubmit} 
              onFormChange={this.onDialogFormChange}
              onIngredientAdded={this.onFormIngredientAdded}
              onIngredientDeleted={this.onFormIngredientDeleted}
              recipe={recipe}/>
          }
          {
            recipesGrid
          }
        </div>
    );
  }
}

const styles = theme => ({
  root: {
    flexGrow: 1,
  },
  paper: {
    padding: theme.spacing.unit * 2,
    textAlign: 'center',
    color: theme.palette.text.secondary,
  },
  fab: {
    margin: 24,
    position: 'fixed',
    bottom: 0,
    right: 0,
  },
});

RecipesPageContainer.propTypes = {
  classes: PropTypes.object.isRequired,
};

const mapStateToProps = state => ({
  recipes: state.recipesReducer.recipes,
  currentRoute: state.navigationReducer.currentRoute,
  allIngredients: state.ingredientsReducer.ingredients,
});


const mapDispatchToProps = dispatch => ({
  addRecipe: (recipe) => dispatch(addRecipe(recipe)),
});

export default connect(mapStateToProps, mapDispatchToProps)(withStyles(styles)(RecipesPageContainer));

