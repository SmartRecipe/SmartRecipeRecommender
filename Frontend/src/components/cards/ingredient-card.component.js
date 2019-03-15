import PropTypes from 'prop-types';
import React, { Component } from 'react';
import Card from '@material-ui/core/Card';
import Avatar from '@material-ui/core/Avatar';
import { withStyles } from '@material-ui/core/styles';
import IconButton from '@material-ui/core/IconButton';
import CardHeader from '@material-ui/core/CardHeader';
import CardActions from '@material-ui/core/CardActions';

import {
  red, blue,
  pink, cyan,
  teal, lime,
  green, brown,
  orange, purple,
  yellow, lightGreen,
  lightBlue, deepPurple, amber,
} from '@material-ui/core/colors';

import EditIcon from '@material-ui/icons/Edit';
import DeleteIcon from '@material-ui/icons/Delete';

const avatarColors = [
  red[500], blue[500], pink[500], cyan[500], lightBlue[900],
  teal[500], lime[900], green[500], orange[500], purple[500],
  brown[500], deepPurple[500], yellow[900], lightGreen[900], amber[500]
];

/**
 * Generic card component to show an ingredient
 */
class IngredientCardComponent extends Component {
  constructor(props) {
    super(props);

    this.state = {
      avatarColor: red[500],
    }

    this.avatarColor = red[500];
  }

  componentDidMount() {
    this.setState({
      avatarColor: avatarColors[Math.floor(Math.random()*avatarColors.length)],
    });
  }

  render(){
    const {
      classes,
      ingredient,
      onEditButtonClicked,
    } = this.props;

    const {
      id,
      name: title,
      qty, 
      unit,
    } = ingredient;

    const description = `${qty} ${unit}`;

    let avatar = 'R';

    if (title) {
      avatar = title[0];
    }

    return (
      <Card className={classes.card}>
        <CardHeader
          avatar={
            <Avatar aria-label="Ingredient" style={{ backgroundColor: this.state.avatarColor }}>
              {avatar}
            </Avatar>
          }
          title={ title }
          subheader={ description }
        />

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
    width: '100%',  
    maxWidth: '100%',
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

