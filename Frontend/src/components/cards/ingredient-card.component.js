import PropTypes from 'prop-types';
import React, { Component } from 'react';
import Card from '@material-ui/core/Card';
import { withStyles } from '@material-ui/core/styles';
import IconButton from '@material-ui/core/IconButton';
import Typography from '@material-ui/core/Typography';
import CardActions from '@material-ui/core/CardActions';
import CardContent from '@material-ui/core/CardContent';


import EditIcon from '@material-ui/icons/Edit';
import DeleteIcon from '@material-ui/icons/Delete';

/**
 * Generic card component to show an ingredient
 */
class IngredientCardComponent extends Component {
  render(){

    const {
      id,
      title, 
      classes,
      qty,
      unit,
      onEditButtonClicked,
    } = this.props;

    const description = `${qty} ${unit}`;
    
    return (
      <Card className={classes.card}>
        <CardContent>
          <Typography variant="title" component="p">
            {title}        
          </Typography>
          <Typography component="h5">
            {description}
          </Typography>
        </CardContent>

        <CardActions className={classes.actions} disableActionSpacing>
          <IconButton aria-label="Edit" onClick={() => onEditButtonClicked(id)}>
            <EditIcon />
          </IconButton>
          <IconButton aria-label="Delete">
            <DeleteIcon />
          </IconButton>
        </CardActions>
      </Card>
    );
  }
}

const styles = {
  card: {
    margin: 5,
    minWidth: 150,
    maxWidth: 150,
    minHeight: '100%',
  },
  bullet: {
    display: 'inline-block',
    margin: '0 2px',
    transform: 'scale(0.8)',
  },
  title: {
    fontSize: 14,
  },
  actions: {
    display: 'flex',
  },
  pos: {
    marginBottom: 12,
  },
};

IngredientCardComponent.propTypes = {
  classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(IngredientCardComponent);

