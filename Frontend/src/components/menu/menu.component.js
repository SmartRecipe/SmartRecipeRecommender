import React from 'react';
import PropTypes from 'prop-types';
import classNames from 'classnames';
import List from '@material-ui/core/List';
import Drawer from '@material-ui/core/Drawer';
import AppBar from '@material-ui/core/AppBar';
import MenuIcon from '@material-ui/icons/Menu';
import Toolbar from '@material-ui/core/Toolbar';
import Divider from '@material-ui/core/Divider';
import ListItem from '@material-ui/core/ListItem';
import Typography from '@material-ui/core/Typography';
import { withStyles } from '@material-ui/core/styles';
import IconButton from '@material-ui/core/IconButton';
import CssBaseline from '@material-ui/core/CssBaseline';
import ListItemText from '@material-ui/core/ListItemText';
import ChevronLeftIcon from '@material-ui/icons/ChevronLeft';
import ChevronRightIcon from '@material-ui/icons/ChevronRight';

import { menuItemProps, appConstants } from '../../utils/app.constants';

const drawerWidth = 240;

/**
 * Navigation menu component
 */
class MenuComponent extends React.Component {
  render() {
    const { 
      open,
      title,
      theme,
      classes,
      onDrawerOpen,
      onDrawerClosed,
      onSignOutClicked,
      onMenuItemClicked, 
    } = this.props;

    return (
      <div>
        <CssBaseline />

        <AppBar position="fixed">
          <Toolbar disableGutters={!open}>
            <IconButton color="inherit" aria-label="Open drawer" onClick={onDrawerOpen} className={classNames(classes.menuButton, open && classes.hide)}>
              <MenuIcon/>
            </IconButton>
            <Typography variant="h6" color="inherit" noWrap>
              {title}
            </Typography>
          </Toolbar>
        </AppBar>
        
        {/* Drawer menu on top left side */}
        <Drawer variant="persistent" anchor="left" open={open} classes={{paper: classes.drawerPaper,}}>
          <div className={classes.drawerHeader}>
            <IconButton onClick={onDrawerClosed}>
              {theme.direction === 'ltr' ? <ChevronLeftIcon /> : <ChevronRightIcon />}
            </IconButton>
          </div>
          
          <Divider/>
          
          <List>
            <ListItem button key={menuItemProps.recipesMenu.title} onClick={() => onMenuItemClicked(menuItemProps.recipesMenu.route)}>
                <ListItemText primary={menuItemProps.recipesMenu.title}/>
            </ListItem>
            <ListItem button key={menuItemProps.ingredientsMenu.title} onClick={() => onMenuItemClicked(menuItemProps.ingredientsMenu.route)}>
                <ListItemText primary={menuItemProps.ingredientsMenu.title}/>
            </ListItem>
            <ListItem button key='Logout' onClick={() => onSignOutClicked()}>
                <ListItemText primary='Logout'/>
            </ListItem>
          </List>
        </Drawer>
      </div>
    );
  }
}

const styles = theme => ({
  appBar: {
    transition: theme.transitions.create(['margin', 'width'], {
      easing: theme.transitions.easing.sharp,
      duration: theme.transitions.duration.leavingScreen,
    }),
  },
  appBarShift: {
    width: `calc(100% - ${drawerWidth}px)`,
    marginLeft: drawerWidth,
    transition: theme.transitions.create(['margin', 'width'], {
      easing: theme.transitions.easing.easeOut,
      duration: theme.transitions.duration.enteringScreen,
    }),
  },
  menuButton: {
    marginLeft: 12,
    marginRight: 20,
  },
  hide: {
    display: 'none',
  },
  drawer: {
    width: drawerWidth,
    flexShrink: 0,
  },
  drawerPaper: {
    width: drawerWidth,
  },
  drawerHeader: {
    display: 'flex',
    alignItems: 'center',
    padding: '0 8px',
    ...theme.mixins.toolbar,
    justifyContent: 'flex-end',
  },
});


MenuComponent.propTypes = {
  classes: PropTypes.object.isRequired,
  theme: PropTypes.object.isRequired,
};

export default withStyles(styles, { withTheme: true })(MenuComponent);
