import PropTypes from 'prop-types';
import React, { Component } from 'react';
import Card from '@material-ui/core/Card';
import Menu from '@material-ui/core/Menu';
import MenuItem from '@material-ui/core/MenuItem';
import { withStyles } from '@material-ui/core/styles';
import IconButton from '@material-ui/core/IconButton';
import CardHeader from '@material-ui/core/CardHeader';
import Typography from '@material-ui/core/Typography';
import MoreVertIcon from '@material-ui/icons/MoreVert';
import CardContent from '@material-ui/core/CardContent';

// Upper right menu on ingredient card
const menuOptions = ['Edit', 'Delete'];

const MENU_ITEM_HEIGHT = 48;

/**
 * Generic card component to show an ingredient
 */
class IngredientCardComponent extends Component {
  constructor(props) {
    super(props);

    this.state = {
      anchorEl: null,
    }

    this.onMenuOpened = this.onMenuOpened.bind(this);
    this.onMenuClosed = this.onMenuClosed.bind(this);
  }
  
  /**
   * When Edit / Delete ingredient menu is opened
   */
  onMenuOpened(e) {
    this.setState({ anchorEl: e.currentTarget });
  }

  /**
   * When Edit / Delete ingredient menu is closed
   */
  onMenuClosed(option, id) {
    const { onEditButtonClicked, onDeleteButtonClicked } = this.props;

    switch(option) {
      case menuOptions[0]:
        onEditButtonClicked(id);
        break;
      case menuOptions[1]:
        onDeleteButtonClicked(id);
        break;
      default:
        break;
    }

    this.setState({ anchorEl: null });
  }

  render(){
    const {
      classes,
      ingredient,
    } = this.props;

    const {
      id,
      name: title,
      qty, 
      unit,
    } = ingredient;

    const {
      anchorEl,
    } = this.state;

    const description = `${qty} ${unit}`;

    const menuOpen = Boolean(anchorEl);

    const titleProps = {
      variant: 'subtitle1',
    }

    return (
      <Card className={classes.card}>
        <CardHeader
          title={ title }
          titleTypographyProps={titleProps}
          action={
            <IconButton onClick={this.onMenuOpened}>
              <MoreVertIcon />
            </IconButton>
          }
        />
        <Menu
          id="long-menu"
          open={menuOpen}
          anchorEl={anchorEl}
          onClose={this.onMenuClosed}
          PaperProps={{
            style: {
              maxHeight: MENU_ITEM_HEIGHT * 4.5,
              width: 200,
            },
          }}
        >
          {
            menuOptions.map(option => (
              <MenuItem key={option} selected={option === 'Edit'} onClick={() => this.onMenuClosed(option, id)}>
                {option}
              </MenuItem>
            ))
          }
        </Menu>
        <CardContent>
          <Typography component="p">
            { description }
          </Typography>
        </CardContent>
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

        // <CardActions className={classes.actions} disableActionSpacing>
        //   <IconButton aria-label="Edit" onClick={() => onEditButtonClicked(id)}>
        //     <EditIcon />
        //   </IconButton>
        //   <IconButton aria-label="Delete">
        //     <DeleteIcon />
        //   </IconButton>
        // </CardActions>
        // 

// import {
//   red, blue,
//   pink, cyan,
//   teal, lime,
//   green, brown,
//   orange, purple,
//   yellow, lightGreen,
//   lightBlue, deepPurple, amber,
// } from '@material-ui/core/colors';

// const avatarColors = [
//   red[500], blue[500], pink[500], cyan[500], lightBlue[900],
//   teal[500], lime[900], green[500], orange[500], purple[500],
//   brown[500], deepPurple[500], yellow[900], lightGreen[900], amber[500]
// ];