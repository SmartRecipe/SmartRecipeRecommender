import React from 'react';
import PropTypes from 'prop-types';
import classnames from 'classnames';
import Card from '@material-ui/core/Card';
import Menu from '@material-ui/core/Menu';
import Avatar from '@material-ui/core/Avatar';
import red from '@material-ui/core/colors/red';
import MenuItem from '@material-ui/core/MenuItem';
import Collapse from '@material-ui/core/Collapse';
import CardMedia from '@material-ui/core/CardMedia';
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
import MoreVertIcon from '@material-ui/icons/MoreVert';
import ExpandMoreIcon from '@material-ui/icons/ExpandMore';

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
      avatar,
      date,
      title,
      shortDescription,
      description,
    } = this.props;

    const { 
      expanded,
    } = this.state;
    

    return (
      <Card className={classes.card}>
        {/* Card Top Header */}
        <CardHeader
          avatar={
            <Avatar aria-label="Recipe" className={classes.avatar}>
              R
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

        {/* Card Footer */}
        <CardActions className={classes.actions} disableActionSpacing>
          <IconButton aria-label="Add to favorites">
            <FavoriteIcon />
          </IconButton>
          <IconButton aria-label="Edit">
            <EditIcon />
          </IconButton>
          <IconButton aria-label="Delete">
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

        {/* Collapse Details */}
        <Collapse in={expanded} timeout="auto" unmountOnExit>
          <CardContent>
            { description }
          </CardContent>
        </Collapse>
      </Card>
    );
  }
}

const styles = theme => ({
  card: {
    maxWidth: 400,
    margin: 10,
  },
  media: {
    height: 0,
    paddingTop: '56.25%', // 16:9
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


/***
          onMenuOpened(e) {
    this.setState({ 
        anchorEl: e.currentTarget,
    });
  }

  onMenuClosed() {
    this.setState({ 
        anchorEl: null, 
    });
  }

  const options = [
  'Delete',
];

const ITEM_HEIGHT = 48;

        <Menu
          open={open}
          id="long-menu"
          anchorEl={anchorEl}
          onClose={this.onMenuClosed}
          PaperProps={{
            style: {
              maxHeight: ITEM_HEIGHT * 4.5,
              width: 200,
            },
          }}
        >
          {options.map(option => (
            <MenuItem key={option} selected={option === 'Pyxis'} onClick={this.onMenuClosed}>
              {option}
            </MenuItem>
          ))}
        </Menu>

          action={
            <IconButton aria-owns={open ? 'long-menu' : undefined} onClick={this.onMenuOpened}>
              <MoreVertIcon />
            </IconButton>
          }
***/