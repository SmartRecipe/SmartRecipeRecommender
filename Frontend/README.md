# Smart Recipe Recommender Frontend

A simple single page responsive application developed using React for Smart Recipe Recommender.

## Tech Spec

* React 16+
* React Redux 
* React Router v4
* React Bootstrap

## API Requirements

### User Sign Up API

New users create their accounts using this API. Requires backend server to accepet following arguments.

* email : String
* password : String

Sign Up results in creating a new account and signing in the new user at the same time.

### User Sign In API

Existing users sign in to the application using this API. Requires backend server to accepet following arguments.

* email : String
* password  : String

The server should return the status of the request along with a unique user id and a session id when Sign In succeeds.

### Ingredient API

Ingredient is the most basic data in the application. Most logic revolves around a list of ingredients of a user. Each ingredient comprises of following fields.

* id : String
* name : String
* qty : String
* unit : String

### Recipe API

A recipe is made of following fields.

* name : String
* short_description : String
* description : String
* ingredients : List(Ingredient)

## Installation & Usage

### Build Requirements

Make sure you have these packages installed in order to build the project

* npm
* yarn

#### Development Build

To run the frontend application in development mode, use following commands 

```bash
cd SmartRecipeRecommender/Frontend
yarn start
```

#### Production Build

In progress...