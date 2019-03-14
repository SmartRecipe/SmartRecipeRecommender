import React from 'react';
import PropTypes from 'prop-types';
import Chip from '@material-ui/core/Chip';
import { withStyles } from '@material-ui/core/styles';

/**
 * Chip component to show ingredients associated with a recipe as a list of items
 */
class IngredientChipsComponent extends React.Component {

  render() {
    const { classes, ingredients, handleDelete } = this.props;

    console.log('Received new ingredients', ingredients);

    return ( 
      <div>
        {
          ingredients.map(data => {
            return (
              <Chip
                key={data.id}
                label={data.name}
                onDelete={(handleDelete !== undefined) ? () => handleDelete(data) : undefined}
                className={classes.chip}
                color={(handleDelete !== undefined) ? "primary" : "primary"}
                variant={(handleDelete !== undefined) ? undefined : "outlined"}
              />
            );
          })
        }
      </div>
    );
  }
}

IngredientChipsComponent.propTypes = {
  classes: PropTypes.object.isRequired,
};

const styles = theme => ({
  root: {
    display: 'flex',
    justifyContent: 'center',
    flexWrap: 'wrap',
    padding: theme.spacing.unit / 2,
  },
  chip: {
    margin: theme.spacing.unit / 2,
  },
});

export default withStyles(styles)(IngredientChipsComponent);