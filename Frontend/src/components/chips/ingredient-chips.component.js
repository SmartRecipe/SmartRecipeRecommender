import React from 'react';
import PropTypes from 'prop-types';
import { withStyles } from '@material-ui/core/styles';
import Chip from '@material-ui/core/Chip';
import Paper from '@material-ui/core/Paper';
import TagFacesIcon from '@material-ui/icons/TagFaces';

class IngredientChipsComponent extends React.Component {

  render() {
    const { classes, ingredients, handleDelete } = this.props;

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
                color={(handleDelete !== undefined) ? "primary" : undefined}
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