| Master | Dev |
|:--:|:--:|
| [![Build Status](https://travis-ci.org/SmartRecipe/SmartRecipeRecommender.svg?branch=master)](https://travis-ci.org/SmartRecipe/SmartRecipeRecommender) | [![Build Status](https://travis-ci.org/SmartRecipe/SmartRecipeRecommender.svg?branch=dev)](https://travis-ci.org/SmartRecipe/SmartRecipeRecommender) |
| [![Coverage Status](https://coveralls.io/repos/github/SmartRecipe/SmartRecipeRecommender/badge.svg?branch=master)](https://coveralls.io/github/SmartRecipe/SmartRecipeRecommender?branch=master) | [![Coverage Status](https://coveralls.io/repos/github/SmartRecipe/SmartRecipeRecommender/badge.svg?branch=dev)](https://coveralls.io/github/SmartRecipe/SmartRecipeRecommender?branch=dev) |
| [![Maintainability](https://api.codeclimate.com/v1/badges/858b3039e89fc3d954c2/maintainability)](https://codeclimate.com/github/SmartRecipe/SmartRecipeRecommender/maintainability) |  |

# Smart Recipe Recommender

A backend application for Smart Recipe Recommender.

## Requirements

* Java 8+

## Build

The project requires a MongoDB database. Use following Shell script to setup ENV vars for the project. 

```bash
export SSH_KEY=$(cat <PATH_TO_YOUR_KEY>)
export DB_NAME='<MONGO_DB_NAME>'
export DB_ADDRESS='<MONGO_DB_ADDRESS>'
export DB_PORT='<MONGO_DB_PORT>'
```

Please note that since the project is deployed on Heroku, there is no way to store a SSH key on deployment environment. Instead, the ENV variable SSH_KEY contains the content of your private key to connect to the database.

 