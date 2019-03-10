import React, { Component } from 'react';
import { ListGroup, CardDeck } from 'react-bootstrap';

import RecipeCardComponent from '../../components/cards/recipe-card.component'

/**
 * Container for recipes page
 */
class RecipesPageContainer extends Component {
  render() {
    return (
        <CardDeck>
            <RecipeCardComponent title="Sample Recipe 1"/>
            <RecipeCardComponent title="Sample Recipe 2"/>
        </CardDeck>
    );
  }
}

export default RecipesPageContainer;