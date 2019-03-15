import React from 'react';
import PropTypes from 'prop-types';
import Card from '@material-ui/core/Card';
import Avatar from '@material-ui/core/Avatar';
import Divider from '@material-ui/core/Divider';
import { withStyles } from '@material-ui/core/styles';
import IconButton from '@material-ui/core/IconButton';
import CardHeader from '@material-ui/core/CardHeader';
import Typography from '@material-ui/core/Typography';
import CardContent from '@material-ui/core/CardContent';
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
import ShareIcon from '@material-ui/icons/Share';
import DeleteIcon from '@material-ui/icons/Delete';
import FavoriteIcon from '@material-ui/icons/Favorite';
import VisibilityIcon from '@material-ui/icons/Visibility';

import IngredientChipsComponent from '../chips/ingredient-chips.component';

const avatarColors = [
  red[500], blue[500], pink[500], cyan[500], lightBlue[500],
  teal[500], lime[500], green[500], orange[500], purple[500],
  brown[500], deepPurple[500], yellow[500], lightGreen[500], amber[500]
];

/**
 * Generic Card component to show recipe information
 */
class RecipeCardComponent extends React.Component {
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

  render() {
    const { 
      recipe,
      classes, 
      onCardViewed,
      onEditButtonClicked,
      onDeleteButtonClicked,
    } = this.props;

    const {
      id,
      name: title,
      ingredients,
      short_description : shortDescription,
    } = recipe;

    let avatar = 'R';

    if (title) {
      avatar = title[0];
    }

    const showIngredientChips = (ingredients.length === 0) ? false : true;
    
    return (
      <Card className={classes.card}>
        {/* Card Top Header */}
        <CardHeader
          avatar={
            <Avatar aria-label="Recipe" style={{ backgroundColor: this.state.avatarColor }}>
              {avatar}
            </Avatar>
          }
          title={ title }
          subheader="September 14, 2016"
        />

        {/* Short Description */}
        <CardContent>
          <Typography component="p">
            { shortDescription } 
          </Typography>
          <Divider className={classes.divider}/>
          {
            showIngredientChips 
            ? <IngredientChipsComponent ingredients={ingredients}/> : 
            <Typography component="body1">No ingredients...</Typography>
          }
        </CardContent>

        {/* Card Footer */}
        <CardActions className={classes.actions} disableActionSpacing>
          <IconButton aria-label="View" onClick={() => onCardViewed(id)}>
            <VisibilityIcon />
          </IconButton>
          <IconButton aria-label="Add to favorites">
            <FavoriteIcon />
          </IconButton>
          <IconButton aria-label="Edit" onClick={() => onEditButtonClicked(id)}>
            <EditIcon />
          </IconButton>
          <IconButton aria-label="Delete" onClick={() => onDeleteButtonClicked(id)}>
            <DeleteIcon />
          </IconButton>
          <IconButton aria-label="Share">
            <ShareIcon />
          </IconButton>
        </CardActions>
      </Card>
    );
  }
}

const styles = theme => ({
  card: {
    margin: 5,
    width: '100%',
    maxWidth: '100%',
    // minHeight: '100%',
  },
  media: {
    height: 0,
    paddingTop: '56.25%', // 16:9
  },
  divider: {
    marginTop: 15,
    marginBottom: 15,
  },
  actions: {
    display: 'flex',
  },
  expand: {
    transform: 'rotate(0deg)',
    marginLeft: 'auto',
    transition: theme.transitions.create('transform', {
      duration: theme.transitions.duration.shortest,
    }),
  },
  expandOpen: {
    transform: 'rotate(180deg)',
  },
});

RecipeCardComponent.propTypes = {
  classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(RecipeCardComponent);
