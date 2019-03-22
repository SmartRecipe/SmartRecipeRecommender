import PropTypes from 'prop-types';
import React, { Component } from 'react';
import Input from '@material-ui/core/Input';
import Select from '@material-ui/core/Select';
import InputLabel from '@material-ui/core/InputLabel';
import FormControl from '@material-ui/core/FormControl';
import withStyles from '@material-ui/core/styles/withStyles';

import { ingredientUnits } from '../../utils/app.constants';

/**
 * Generic form component used as 'Edit ingredient' / 'Add new ingredient'
 */
class EditIngredientFormComponent extends Component {
  render() {
    const { 
      ingredient, // recipe 
      classes,
      onSubmit, // submit function passed from parent component
      onFormChange, // callback for handling updates in the form
      onUnitChange,
    } = this.props;

    let title = '', qty = '', unit = ''; 
    
    if (ingredient !== null && ingredient !== undefined && ingredient) {
      title = ingredient.name;
      qty = ingredient.qty; 
      unit = ingredient.unit;
    }
    
    return (
      <div>
        <form className={classes.form} onSubmit={onSubmit}>
          <FormControl margin="normal" required fullWidth>
            <InputLabel htmlFor="title">Title</InputLabel>
            <Input id="title" value={title} onChange={onFormChange} name="title" autoComplete="title" autoFocus />
          </FormControl>
          <FormControl margin="normal" required fullWidth>
            <InputLabel htmlFor="qty">Quantity</InputLabel>
            <Input name="qty" id="qty" value={qty} onChange={onFormChange} autoComplete="qty" />
          </FormControl>
          <FormControl margin="normal" required fullWidth>
            <InputLabel htmlFor="unit">Unit</InputLabel>
            <Select id="unit" onClick={onUnitChange} value={unit}>
              <option value=""> </option>
              {
                ingredientUnits.map(data => {
                  return (
                    <option key={data.id} value={data.val}>{data.val}</option>
                  );
                })
              }
            </Select>
          </FormControl>
        </form>
      </div>
    );
  }
}

const styles = theme => ({
  paper: {
    marginTop: theme.spacing.unit * 8,
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
    padding: `${theme.spacing.unit * 2}px ${theme.spacing.unit * 3}px ${theme.spacing.unit * 3}px`,
  },
  avatar: {
    margin: theme.spacing.unit,
    backgroundColor: theme.palette.secondary.main,
  },
  form: {
    width: '100%', // Fix IE 11 issue.
    marginTop: theme.spacing.unit,
  },
  submit: {
    marginTop: theme.spacing.unit * 3,
  },
  text: {
    alignItems: 'center',
  },
  button: {
    // margin: theme.spacing.unit,
  },
});

EditIngredientFormComponent.propTypes = {
  classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(EditIngredientFormComponent);
