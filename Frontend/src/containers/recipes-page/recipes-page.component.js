import PropTypes from 'prop-types';
import Fab from '@material-ui/core/Fab';
import React, { Component } from 'react';
import Grid from '@material-ui/core/Grid';
import { withStyles } from '@material-ui/core/styles';

import AddIcon from '@material-ui/icons/Add';

import RecipeCardComponent from '../../components/cards/recipe-card.component';

/**
 * Container for recipes page
 */
class RecipesPageContainer extends Component {
  constructor(props) {
    super(props);

    this.onAddButtonClicked = this.onAddButtonClicked.bind(this);
  }

  onAddButtonClicked() {
    
  }

  render() {
    const { classes } = this.props;

    return (
        <div className="card-deck-container">
          <Fab color="primary" aria-label="Add" className={classes.fab}>
            <AddIcon />
          </Fab>
          <Grid container spacing={18}>
            <Grid item sm>
              <RecipeCardComponent/>
            </Grid>
            <Grid item sm>
              <RecipeCardComponent/>
            </Grid>
            <Grid item sm> 
              <RecipeCardComponent/>
            </Grid>
          </Grid>
          <Grid container spacing={18}>
            <Grid item sm>
              <RecipeCardComponent/>
            </Grid>
            <Grid item sm>
              <RecipeCardComponent/>
            </Grid>
            <Grid item sm> 
              <RecipeCardComponent/>
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

RecipesPageContainer.propTypes = {
  classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(RecipesPageContainer);


/**
          <Grid container spacing={12}>
            <Grid item sm>
              <RecipeCardComponent2 title="Sample Recipe 1" description="Sample Description 1"/>
            </Grid>
            <Grid item sm>
              <RecipeCardComponent2 title="Sample Recipe 2" description="Sample Description 1"/>
            </Grid>
            <Grid item sm> 
              <RecipeCardComponent2 title="Sample Recipe 2" description="Sample Description 1"/>
            </Grid>
          </Grid>
 */