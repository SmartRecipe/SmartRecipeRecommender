[![Build Status](https://travis-ci.org/SmartRecipe/SmartRecipeRecommender.svg?branch=master)](https://travis-ci.org/SmartRecipe/SmartRecipeRecommender)
[![Coverage Status](https://coveralls.io/repos/github/SmartRecipe/SmartRecipeRecommender/badge.svg?branch=master)](https://coveralls.io/github/SmartRecipe/SmartRecipeRecommender?branch=master)
[![Maintainability](https://api.codeclimate.com/v1/badges/858b3039e89fc3d954c2/maintainability)](https://codeclimate.com/github/SmartRecipe/SmartRecipeRecommender/maintainability)

# Smart Recipe Recommender

The backend application for Smart Recipe Recommender.

## Requirements

* Java 8+

## Build

This project requires a MongoDB server for the backend. Here is an example Bash script which will help you set up the ENV vars. Please note that since the project is deployed on Heroku, there is no way to store a SSH key on deployment environment. Therefore, you need to store the contents of your private key in SSH_KEY variable instead of a path to the key. 

```bash
export SSH_KEY=$(cat <PATH_TO_YOUR_KEY>)
export DB_NAME='<MONGO_DB_NAME>'
export DB_ADDRESS='<MONGO_DB_ADDRESS>'
export DB_PORT='<MONGO_DB_PORT>'
```

 