import PropTypes from 'prop-types';
import React, { Component } from 'react';
import Fab from '@material-ui/core/Fab';
import Grid from '@material-ui/core/Grid';
import { withStyles } from '@material-ui/core/styles';

import AddIcon from '@material-ui/icons/Add';

import IngredientCardComponent from '../../components/cards/ingredient-card.component';

/**
 * Main container for ingredients page
 */
class IngredientsPageContainer extends Component {
  constructor(props){
    super(props);

    this.onAddNewIngredient = this.onAddNewIngredient.bind(this);
  }

  onAddNewIngredient() {

  }

  render() {
    const { classes } = this.props;

    return (
        <div className="card-deck-container">
          <Fab onClick={this.onAddNewIngredient} color="primary" aria-label="Add" className={classes.fab}>
            <AddIcon />
          </Fab>
          <Grid container spacing={8}>
            <Grid item sm>
              <IngredientCardComponent/>
            </Grid>
            <Grid item sm>
              <IngredientCardComponent/>
            </Grid>
            <Grid item sm> 
              <IngredientCardComponent/>
            </Grid>
            <Grid item sm> 
              <IngredientCardComponent/>
            </Grid>
            <Grid item sm> 
              <IngredientCardComponent/>
            </Grid>
          </Grid>
          <Grid container spacing={8}>
            <Grid item sm>
              <IngredientCardComponent/>
            </Grid>
            <Grid item sm>
              <IngredientCardComponent/>
            </Grid>
            <Grid item sm> 
              <IngredientCardComponent/>
            </Grid>
            <Grid item sm> 
              <IngredientCardComponent/>
            </Grid>
            <Grid item sm> 
              <IngredientCardComponent/>
            </Grid>
          </Grid>
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

export default withStyles(styles)(IngredientsPageContainer);