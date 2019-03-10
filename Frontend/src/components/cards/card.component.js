import React, { Component } from 'react';
import { RecipeCard } from 'react-ui-cards';

import recipeimg from './recipe.jpg';

/**
 * Generic card component to show recipe information
 */
class RecipeCardComponent2 extends Component {
  render() {
    const {
      text,
      title,
    } = this.props;

    return (
        <RecipeCard
          href='https://github.com/nekonee'
          thumbnail='https://i.imgur.com/VkHTicg.jpg'
          title='Fluffy pancakes'
          time='0:30'
          servings='3-5'
          likeCallback={() => alert('You added Fluffy pancakes to favourites')}
        />
    );
  }
}

export default RecipeCardComponent2;
