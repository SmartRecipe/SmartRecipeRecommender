import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import Fab from '@material-ui/core/Fab';
import React, { Component } from 'react';
import Grid from '@material-ui/core/Grid';
import { withStyles } from '@material-ui/core/styles';
import AddIcon from '@material-ui/icons/Add';

import { addIngredient } from '../../actions/ingredients-page/ingredients-page.actions';

import IngredientCardComponent from '../../components/cards/ingredient-card.component';

const GRID_ROW_SIZE = 5;

/**
 * Main container for ingredients page
 */
class IngredientsPageContainer extends Component {
  constructor(props){
    super(props);

    this.onAddNewIngredient = this.onAddNewIngredient.bind(this);
  }

  onAddNewIngredient() {
    this.props.addIngredient();
  }

  onIngredientDeleted() {
    
  }

  getIngredientsGrid(ingredients) {
    const totalIngredients = ingredients.length;

    const totalRows = totalIngredients / GRID_ROW_SIZE;

    let rows = []

    for (var i = 0; i < totalRows; i++) {
      const start = i*GRID_ROW_SIZE;
      let end = (i+1)*GRID_ROW_SIZE;
      end = (totalIngredients < end) ? totalIngredients : end; 
      let cols = []
      for (var j = i*GRID_ROW_SIZE; j < end; j++) {
        const currentIngredient = ingredients[j];
        if ((currentIngredient !== undefined) && (currentIngredient !== null)) {
          cols.push(
            <Grid key={j} item xs>
              <IngredientCardComponent
                title={currentIngredient.name}
              />
            </Grid>);
        }
      }
      rows.push(<Grid key={"ing_row_" + i} container spacing={8}>{cols}</Grid>);
    }

    return rows;
  }

  render() {
    const { classes, ingredients } = this.props;

    const ingredientsGrid = this.getIngredientsGrid(ingredients);

    return (
        <div className="card-deck-container">
          <Fab onClick={this.onAddNewIngredient} color="primary" aria-label="Add" className={classes.fab}>
            <AddIcon />
          </Fab>
          <div>
            {
              ingredientsGrid
            }
          </div>
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
    margin: theme.spacing.unit,
    position: 'fixed',
    bottom: 0,
    right: 0,
    margin: 24,
  },
});

IngredientsPageContainer.propTypes = {
  classes: PropTypes.object.isRequired,
};

const mapStateToProps = state => ({
  ingredients: state.ingredientsReducer.ingredients,
  currentRoute: state.navigationReducer.currentRoute,
});


const mapDispatchToProps = dispatch => ({
  addIngredient: () => dispatch(addIngredient()),
});

export default connect(mapStateToProps, mapDispatchToProps)(withStyles(styles)(IngredientsPageContainer));