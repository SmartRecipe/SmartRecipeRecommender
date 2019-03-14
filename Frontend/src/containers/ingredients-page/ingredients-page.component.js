import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import Fab from '@material-ui/core/Fab';
import React, { Component } from 'react';
import Grid from '@material-ui/core/Grid';
import { withStyles } from '@material-ui/core/styles';
import AddIcon from '@material-ui/icons/Add';

import { addIngredient } from '../../actions/ingredients-page/ingredients-page.actions';

import IngredientCardComponent from '../../components/cards/ingredient-card.component';
import AddIngredientDialog from '../../components/dialogs/add-ingredient-dialog.component';

const GRID_ROW_SIZE = 5;

/**
 * Main container for ingredients page
 */
class IngredientsPageContainer extends Component {
  constructor(props){
    super(props);

    this.state = {
      ingredient: {
        name: '',
        qty: '',
        unit: '',
      },
      showDialog: false,
    }

    this.onDialogClosed = this.onDialogClosed.bind(this);
    this.onDialogSubmit = this.onDialogSubmit.bind(this);
    this.onDialogFormChange = this.onDialogFormChange.bind(this);
    this.onAddButtonClicked = this.onAddButtonClicked.bind(this);
    this.onIngredientUnitChange = this.onIngredientUnitChange.bind(this);
    this.onEditIngredientButtonClicked = this.onEditIngredientButtonClicked.bind(this);
  }

  onAddButtonClicked() {
    this.setState({
      showDialog: true,
    });
  }

  onIngredientDeleted() {
    
  }

  onDialogClosed() {
    this.setState({
      showDialog: false,
    });
  }

  onDialogSubmit(e, ingredient) {
    this.onDialogClosed();

    this.props.addIngredient(ingredient);

  }

  onDialogFormChange(e) {
    e.preventDefault();

    const { target } = e;

    switch (target.id) {
      case 'title':
        this.setState({
          ingredient: {
            ...this.state.ingredient,
            name: target.value,
          }
        });
        break;
      case 'qty':
        this.setState({
          ingredient: {
            ...this.state.ingredient,
            qty: target.value,
          }
        });
        break;
    }
  }

  onIngredientUnitChange(e) {
    const { target } = e;

    const { value } = target;

    this.setState({
      ingredient: {
        ...this.state.ingredient,
        unit: value,
      }
    });
  }

  onEditIngredientButtonClicked(id) {
    const ingredient = this.props.allIngredients.filter((ingredient) => ingredient.id == id);

    console.log();

    this.setState({
      ...this.state,
      showDialog: true,
      ingredient: ingredient[0],
    });
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
                id={currentIngredient.id}
                key={currentIngredient.id}
                title={currentIngredient.name}
                qty={currentIngredient.qty}
                unit={currentIngredient.unit}
                onEditButtonClicked={this.onEditIngredientButtonClicked}
              />
            </Grid>);
        }
      }
      rows.push(<Grid key={"ing_row_" + i} container spacing={8}>{cols}</Grid>);
    }

    return rows;
  }

  render() {
    const { classes, allIngredients } = this.props;

    const { ingredient, showDialog } = this.state;

    const ingredientsGrid = this.getIngredientsGrid(allIngredients);

    return (
        <div className="card-deck-container">
          <Fab onClick={this.onAddButtonClicked} color="primary" aria-label="Add" className={classes.fab}>
            <AddIcon />
          </Fab>
          {
            <AddIngredientDialog 
              open={showDialog} 
              onClose={this.onDialogClosed} 
              onSubmit={this.onDialogSubmit} 
              onFormChange={this.onDialogFormChange}
              onUnitChange={this.onIngredientUnitChange}
              ingredient={ingredient}/>
          }
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
  allIngredients: state.ingredientsReducer.ingredients,
  currentRoute: state.navigationReducer.currentRoute,
});


const mapDispatchToProps = dispatch => ({
  addIngredient: (ingredient) => dispatch(addIngredient(ingredient)),
});

export default connect(mapStateToProps, mapDispatchToProps)(withStyles(styles)(IngredientsPageContainer));