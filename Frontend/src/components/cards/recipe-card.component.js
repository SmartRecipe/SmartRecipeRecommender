import React, { Component } from 'react';
import Card from 'react-bootstrap/Card';

/**
 * Generic card component to show recipe information
 */
class RecipeCardComponent extends Component {
  render() {
    const {
      title,
    } = this.props;

    return (
      <Card>
        <Card.Body>
          <Card.Title>{title}</Card.Title>
          <Card.Text>
            Some quick example text to build on the card title and make up the bulk of
            the card's content.
          </Card.Text>
          <Card.Link href="#">Card Link</Card.Link>
          <Card.Link href="#">Another Link</Card.Link>
        </Card.Body>
      </Card>
    );
  }
}

export default RecipeCardComponent;