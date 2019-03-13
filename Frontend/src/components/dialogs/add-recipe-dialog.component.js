import React, { Component } from 'react';
import Button from '@material-ui/core/Button';
import TextField from '@material-ui/core/TextField';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import DialogTitle from '@material-ui/core/DialogTitle';

import EditRecipeFormComponent from '../forms/edit-recipe-form.component.js'

class AddRecipeDialog extends Component {
  constructor(props) {
    super(props);

    this.state = {
      recipe: {
        name: '',
        short_description: '',
        description: '',
        ingredients: [],
      },
    }

    this.onFormChange = this.onFormChange.bind(this);
    this.onFormSubmit = this.onFormSubmit.bind(this);
  }
  
  onFormSubmit(e, recipe, nextFunction) {
    this.setState({
      recipe: {
        name: '',
        short_description: '',
        description: '',
        ingredients: [],
      },
    });

    console.log(recipe);

    // callback from parent component
    nextFunction(e, recipe);
  }

  onFormChange(e) {
    e.preventDefault();

    const { target } = e;

    switch (target.id) {
      case 'title':
        this.setState({
          recipe: {
            ...this.state.recipe,
            name: target.value,
          }
        });
        break;
      case 'short_description':
        this.setState({
          recipe: {
            ...this.state.recipe,
            short_description: target.value,
          }
        });
        break;
      case 'description':
        this.setState({
          recipe: {
            ...this.state.recipe,
            description: target.value,
          }
        });
        break;
    }
  }

  componentDidMount() {
    const { recipe } = this.props;

    if (recipe !== undefined && recipe !== null && recipe) {
      this.setState({
        recipe: recipe,
      });
    }
  }

  render() {
    const { 
      open, 
      onClose,
      onSubmit,
    } = this.props;

    const { 
      recipe,
    } = this.state;

    return (
      <Dialog
        open={open}
        aria-labelledby="form-dialog-title"
      >
        <DialogTitle id="form-dialog-title">Recipe</DialogTitle>
        <DialogContent>
          <EditRecipeFormComponent recipe={recipe} onFormChange={this.onFormChange}/>
        </DialogContent>
        <DialogActions>
          <Button onClick={onClose} color="secondary">
            Cancel
          </Button>
          <Button onClick={(e) => this.onFormSubmit(e, recipe, onSubmit)} color="primary">
            Save
          </Button>
        </DialogActions>
      </Dialog>
    );
  }
}

export default AddRecipeDialog;