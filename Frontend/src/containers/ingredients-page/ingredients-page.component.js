import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import Fab from '@material-ui/core/Fab';
import React, { Component } from 'react';
import Grid from '@material-ui/core/Grid';
import AddIcon from '@material-ui/icons/Add';
import { withStyles } from '@material-ui/core/styles';

import { addIngredient, getIngredients } from '../../actions/ingredients-page/ingredients-page.actions';

import IngredientCardComponent from '../../components/cards/ingredient-card.component';
import AddIngredientDialog from '../../components/dialogs/add-ingredient-dialog.component';

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
      default:
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
    const ingredient = this.props.allIngredients.filter((ingredient) => ingredient.id === id);

    this.setState({
      ...this.state,
      showDialog: true,
      ingredient: ingredient[0],
    });
  }


  getIngredientsGrid(ingredients) {
    let totalIngredients = 0;

    if (ingredients) {
      totalIngredients = ingredients.length;
    } 

    let ingredientItemComponents = [];

    for (var i = 0; i < totalIngredients; i++) {
      const currentIngredient = ingredients[i];
      if ((currentIngredient !== undefined) && (currentIngredient !== null)) {
        ingredientItemComponents.push(
          <Grid 
            item
            key={i} 
            container
            spacing={0}
            justify="center"
            alignItems="center"
            xs={6} sm={4} md={3} lg={2} xl={1}>
            <IngredientCardComponent
              key={currentIngredient.id}
              ingredient={currentIngredient}
              onEditButtonClicked={this.onEditIngredientButtonClicked}
            />
          </Grid>);
      }
    }

    return( 
      <Grid 
        key={"ing_row"} 
        container 
        spacing={8}>
          {ingredientItemComponents}
      </Grid>);
  }

  componentDidMount() {
    this.props.getIngredients();
  }

  render() {
    const { classes, allIngredients } = this.props;

    const { ingredient, showDialog } = this.state;

    const ingredientsGrid = this.getIngredientsGrid(allIngredients);

    return (
        <div className={classes.content}>
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
          {
            ingredientsGrid
          }
        </div>
    );
  }
}

const styles = theme => ({
  root: {
    flexGrow: 1,
  },
  content: {
    marginTop: '50px',
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

IngredientsPageContainer.propTypes = {
  classes: PropTypes.object.isRequired,
};

const mapStateToProps = state => ({
  allIngredients: state.ingredientsReducer.ingredients,
  currentRoute: state.navigationReducer.currentRoute,
});


const mapDispatchToProps = dispatch => ({
  addIngredient: (ingredient) => dispatch(addIngredient(ingredient)),
  getIngredients: () => dispatch(getIngredients()),
});

export default connect(mapStateToProps, mapDispatchToProps)(withStyles(styles)(IngredientsPageContainer));