import React, { Component } from 'react';
import Button from '@material-ui/core/Button';
import Dialog from '@material-ui/core/Dialog';
import { FacebookShareButton } from 'react-share';
import DialogTitle from '@material-ui/core/DialogTitle';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';

/**
 * Generic card component to show recipe information
 */
class SocialShareDialogComponent extends Component {
  render(){
    const { 
      open, 
      recipe,
      onClose, 
    } = this.props;
    
    return (
      <Dialog open={open} aria-labelledby="form-dialog-title">
        <DialogTitle id="form-dialog-title">Share Via</DialogTitle>
        <DialogContent>
            <FacebookShareButton url='https://google.com'/>
        </DialogContent>
        <DialogActions>
          <Button onClick={onClose} color="secondary">
            Cancel
          </Button>
        </DialogActions>
      </Dialog>
    );
  }
}

export default SocialShareDialogComponent;

