import React, { Component } from 'react';
import Card from 'react-bootstrap/Card';

/**
 * Generic card component to show recipe information
 */
class RecipeCardComponent extends Component {
  render() {
    const {
      text,
      title,
    } = this.props;

    return (
      <Card bg="light" border="secondary">
        <Card.Body>
          <Card.Title>{title}</Card.Title>
          <Card.Text>
            {text}
          </Card.Text>
          <Card.Link>Another Link</Card.Link>
        </Card.Body>
      </Card>
    );
  }
}

export default RecipeCardComponent;