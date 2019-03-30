import React, { Component } from 'react';
import Grid from '@material-ui/core/Grid';
import Button from '@material-ui/core/Button';
import Dialog from '@material-ui/core/Dialog';
import DialogTitle from '@material-ui/core/DialogTitle';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import { FacebookShareButton, TwitterShareButton, TwitterIcon, FacebookIcon } from 'react-share';

const shareTitle = 'Check out this amazing recipe!';

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
          <Grid container spacing={8}>
            <Grid item xs={6} sm={6} md={6} lg={6} xl={6} container>
              <FacebookShareButton title={shareTitle} url='https://google.com'>
                <FacebookIcon size={40} round />
              </FacebookShareButton>
            </Grid>
            <Grid item xs={6} sm={6} md={6} lg={6} xl={6} container>
              <TwitterShareButton title={shareTitle} url='https://google.com'>
                <TwitterIcon size={40} round />
              </TwitterShareButton>
            </Grid>
          </Grid>
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

