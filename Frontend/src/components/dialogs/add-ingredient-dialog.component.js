import { connect } from 'react-redux';
import React, { Component } from 'react';
import Button from '@material-ui/core/Button';
import Dialog from '@material-ui/core/Dialog';
import TextField from '@material-ui/core/TextField';
import DialogTitle from '@material-ui/core/DialogTitle';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';

import EditIngredientFormComponent from '../forms/edit-ingredient-form.component';

class AddIngredientDialog extends Component {
  constructor(props) {
    super(props);

    this.state = {
      recipe: {
        name: '',
        qty: 1,
        unit: 'lb',
      },
    }

    this.onFormSubmit = this.onFormSubmit.bind(this);
  }
  
  onFormSubmit(e, ingredient, nextFunction) {
    // callback from parent component
    nextFunction(e, ingredient);
  }

  render() {
    const { 
      open, 
      ingredient,
      onClose,
      onSubmit,
      onFormChange,
      onUnitChange,
    } = this.props;
  
    return (
      <Dialog open={open} aria-labelledby="form-dialog-title">
        <DialogTitle id="form-dialog-title">Ingredient</DialogTitle>
        <DialogContent>
          <EditIngredientFormComponent 
            ingredient={ingredient} 
            onFormChange={onFormChange}
            onUnitChange={onUnitChange}
          />
        </DialogContent>
        <DialogActions>
          <Button onClick={onClose} color="secondary">
            Cancel
          </Button>
          <Button onClick={(e) => this.onFormSubmit(e, ingredient, onSubmit)} color="primary">
            Save
          </Button>
        </DialogActions>
      </Dialog>
    );
  }
}

export default AddIngredientDialog;