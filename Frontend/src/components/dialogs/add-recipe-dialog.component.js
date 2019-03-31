import React, { Component } from 'react';
import Button from '@material-ui/core/Button';
import Dialog from '@material-ui/core/Dialog';
import DialogTitle from '@material-ui/core/DialogTitle';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';

import EditRecipeFormComponent from '../forms/edit-recipe-form.component';

/**
 * Dialog which contains 'Edit Recipe' form
 */
class AddRecipeDialog extends Component {
  constructor(props) {
    super(props);

    this.state = {
      recipe: {
        name: '',
        short_description: '',
        description: '',
        ingredients: '',
      },
    }

    this.onFormSubmit = this.onFormSubmit.bind(this);
  }

  /**
   * Handles onSubmit event of the recipe form
   * Uses a callback function from the parent component 
   * @param  {Object} e               Event
   * @param  {Object} recipe      Recipe to edit / add
   * @param  {Function} nextFunction  Callback from parent component
   * @return 
   */
  onFormSubmit(e, recipe, nextFunction) {
    // callback from parent component
    nextFunction(e, recipe);
  }

  render() {
    const { 
      open, 
      recipe,
      onClose,
      onSubmit,
      onFormChange,
      onIngredientAdded,
      onIngredientDeleted,
    } = this.props;

    return (
      <Dialog open={open} aria-labelledby="form-dialog-title">
        <DialogTitle id="form-dialog-title">Recipe</DialogTitle>
        <DialogContent>
          <EditRecipeFormComponent 
            recipe={recipe} 
            onFormChange={onFormChange} 
            onIngredientAdded={onIngredientAdded} 
            onIngredientDeleted={onIngredientDeleted}
          />
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