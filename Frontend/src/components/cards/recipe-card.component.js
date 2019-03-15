import React from 'react';
import PropTypes from 'prop-types';
import classnames from 'classnames';
import Card from '@material-ui/core/Card';
import Avatar from '@material-ui/core/Avatar';
import red from '@material-ui/core/colors/red';
import Divider from '@material-ui/core/Divider';
import Collapse from '@material-ui/core/Collapse';
import { withStyles } from '@material-ui/core/styles';
import IconButton from '@material-ui/core/IconButton';
import CardHeader from '@material-ui/core/CardHeader';
import Typography from '@material-ui/core/Typography';
import CardContent from '@material-ui/core/CardContent';
import CardActions from '@material-ui/core/CardActions';

import EditIcon from '@material-ui/icons/Edit';
import ShareIcon from '@material-ui/icons/Share';
import DeleteIcon from '@material-ui/icons/Delete';
import FavoriteIcon from '@material-ui/icons/Favorite';
import ExpandMoreIcon from '@material-ui/icons/ExpandMore';

import IngredientChipsComponent from '../chips/ingredient-chips.component.js'

/**
 * Generic Card component to show recipe information
 */
class RecipeCardComponent extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
      expanded: false,
    }

    this.onCardExpanded = this.onCardExpanded.bind(this);
  }

  onCardExpanded() {
    this.setState({
      expanded: !this.state.expanded,
    });
  }

  render() {
    const { 
      classes, 
      id,
      title,
      shortDescription,
      description,
      ingredients,
      onEditButtonClicked,
      onDeleteButtonClicked,
    } = this.props;

    const { 
      expanded,
    } = this.state;

    let avatar = 'R'
    if (title) {
      avatar = title[0];
    }
    

    return (
      <Card className={classes.card}>
        {/* Card Top Header */}
        <CardHeader
          avatar={
            <Avatar aria-label="Recipe" className={classes.avatar}>
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
        </CardContent>

        {/* Collapse Details */}
        <Collapse in={expanded} timeout="auto" unmountOnExit>
          <CardContent>
            <IngredientChipsComponent onDelete={undefined} ingredients={ingredients} />
            <Divider className={classes.divider}/>
            { description }
          </CardContent>
        </Collapse>

        {/* Card Footer */}
        <CardActions className={classes.actions} disableActionSpacing>
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
          <IconButton
            className={classnames(classes.expand, {
              [classes.expandOpen]: expanded,
            })}
            onClick={this.onCardExpanded}
            aria-expanded={expanded}
            aria-label="Show more"
          >
            <ExpandMoreIcon />
          </IconButton>
        </CardActions>
      </Card>
    );
  }
}

const styles = theme => ({
  card: {
    margin: 5,
    maxWidth: 400,
    minHeight: '100%',
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
  avatar: {
    backgroundColor: red[500],
  },
});

RecipeCardComponent.propTypes = {
  classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(RecipeCardComponent);
