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

* email
* password

Sign Up results in creating a new account and signing in the new user at the same time.

### User Sign In API

Existing users sign in to the application using this API. Requires backend server to accepet following arguments.

* email
* password 

The server should return the status of the request along with a unique user id and a session id when Sign In succeeds.

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