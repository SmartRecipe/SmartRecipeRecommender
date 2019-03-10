import React, { Component } from 'react';

import RecipeCardComponent from '../../components/cards/recipe-card.component'

class RecipesPageContainer extends Component {
  render() {
    return (
        <div>
            <RecipeCardComponent />
        </div>
    );
  }
}

export default RecipesPageContainer;