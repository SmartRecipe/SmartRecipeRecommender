import React from 'react';
import PropTypes from 'prop-types';
import classNames from 'classnames';
import Icon from '@material-ui/core/Icon';
import List from '@material-ui/core/List';
import Drawer from '@material-ui/core/Drawer';
import AppBar from '@material-ui/core/AppBar';
import red from '@material-ui/core/colors/red';
import MenuIcon from '@material-ui/icons/Menu';
import Toolbar from '@material-ui/core/Toolbar';
import Divider from '@material-ui/core/Divider';
import ListItem from '@material-ui/core/ListItem';
import SearchIcon from '@material-ui/icons/Search';
import InputBase from '@material-ui/core/InputBase';
import { withStyles } from '@material-ui/core/styles';
import IconButton from '@material-ui/core/IconButton';
import CssBaseline from '@material-ui/core/CssBaseline';
import ListItemText from '@material-ui/core/ListItemText';
import ChevronLeftIcon from '@material-ui/icons/ChevronLeft';
import ChevronRightIcon from '@material-ui/icons/ChevronRight';
import { fade } from '@material-ui/core/styles/colorManipulator';


import { menuItemProps } from '../../utils/app.constants';

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

            <div className={classes.grow} />
            {
              !open && 
              <div className={classes.search}>
                <div className={classes.searchIcon}>
                  <SearchIcon />
                </div>
                <InputBase
                  placeholder="Search…"
                  classes={{
                    root: classes.inputRoot,
                    input: classes.inputInput,
                  }}
                />
              </div>
            }
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
            <ListItem button key={menuItemProps.recipesMenu.title} onClick={() => onMenuItemClicked(menuItemProps.recipesMenu.route, menuItemProps.recipesMenu.title)}>
                <Icon className={classNames(classes.icon, 'fa fa-utensils')} color="action"/>
                <ListItemText primary={menuItemProps.recipesMenu.title}/>
            </ListItem>
            <ListItem button key={menuItemProps.ingredientsMenu.title} onClick={() => onMenuItemClicked(menuItemProps.ingredientsMenu.route, menuItemProps.ingredientsMenu.title)}>
                <Icon className={classNames(classes.icon, 'fa fa-carrot')} color="action"/>
                <ListItemText primary={menuItemProps.ingredientsMenu.title}/>
            </ListItem>
            <ListItem button key='Logout' onClick={() => onSignOutClicked()}>
                <Icon className={classNames(classes.icon, 'fa fa-sign-out-alt')} color="action"/>
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
  search: {
    position: 'relative',
    borderRadius: theme.shape.borderRadius,
    backgroundColor: fade(theme.palette.common.white, 0.15),
    '&:hover': {
      backgroundColor: fade(theme.palette.common.white, 0.25),
    },
    marginLeft: 0,
    marginRight: 10,
    width: '80%',
    [theme.breakpoints.up('sm')]: {
      marginLeft: theme.spacing.unit,
      width: 'auto',
    },
  },
  grow: {
    flexGrow: 1,
  },
  searchIcon: {
    width: theme.spacing.unit * 9,
    height: '100%',
    position: 'absolute',
    pointerEvents: 'none',
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
  },
  inputRoot: {
    color: 'inherit',
    width: '100%',
  },
  inputInput: {
    paddingTop: theme.spacing.unit,
    paddingRight: theme.spacing.unit,
    paddingBottom: theme.spacing.unit,
    paddingLeft: theme.spacing.unit * 10,
    transition: theme.transitions.create('width'),
    width: '100%',
    [theme.breakpoints.up('sm')]: {
      width: 120,
      '&:focus': {
        width: 200,
      },
    },
  },
  icon: {
    // margin: theme.spacing.unit * 2,
  },
  iconHover: {
    // margin: theme.spacing.unit * 2,
    '&:hover': {
      color: red[800],
    },
  },
});


MenuComponent.propTypes = {
  classes: PropTypes.object.isRequired,
  theme: PropTypes.object.isRequired,
};

export default withStyles(styles, { withTheme: true })(MenuComponent);
