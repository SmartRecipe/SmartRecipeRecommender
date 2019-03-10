import React, { Component } from 'react';
import { CardDeck } from 'react-bootstrap';

import RecipeCardComponent from '../../components/cards/recipe-card.component'

/**
 * Container for recipes page
 */
class RecipesPageContainer extends Component {
  render() {
    return (
        <div>
            <CardDeck className="card-deck-container">
                <RecipeCardComponent title="Sample Recipe 1"/>
                <RecipeCardComponent title="Sample Recipe 2"/>
            </CardDeck>
        </div>
    );
  }
}

export default RecipesPageContainer;