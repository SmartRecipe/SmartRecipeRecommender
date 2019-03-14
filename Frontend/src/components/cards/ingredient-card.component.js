import PropTypes from 'prop-types';
import React, { Component } from 'react';
import Card from '@material-ui/core/Card';
import Button from '@material-ui/core/Button';
import { withStyles } from '@material-ui/core/styles';
import IconButton from '@material-ui/core/IconButton';
import Typography from '@material-ui/core/Typography';
import CardHeader from '@material-ui/core/CardHeader';
import CardActions from '@material-ui/core/CardActions';
import CardContent from '@material-ui/core/CardContent';


import EditIcon from '@material-ui/icons/Edit';
import ShareIcon from '@material-ui/icons/Share';
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
          <Typography variant="h6" component="h2">
            {title}        
          </Typography>
          <Typography component="p">
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
    minWidth: 120,
    margin: 10,
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

