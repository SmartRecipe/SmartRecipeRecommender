# Smart Recipe Recommender Frontend

A simple single page responsive application developed using React for Smart Recipe Recommender.

## Tech Spec

* React 16+
* React Redux 
* React Router v4
* React Material

## API Requirements

The frontend application expects the backend to support CRUD operations on different data. 

### User Sign Up API

New users create their accounts using this API. Requires backend server to accepet following arguments.

* email : String
* password : String

Sign Up results in creating a new account and signing in the new user at the same time.

On successful sign up, a new user with following attributes will be created in the database.

* id : String (Unique user id)
* email : String
* password : String
* salt : String (A unique salt for encryption)

### User Sign In API

Existing users sign in to the application using this API. Requires backend server to accepet following arguments.

* email : String
* password  : String

The server should return the status of the request along with a unique user id and a session id when Sign In succeeds.

### Ingredient API

Most of the business logic of the application revolves around a list of ingredients of a user. Each ingredient comprises of following fields.

* id : String
* name : String
* qty : String
* unit : String
* userId : String (Associates the ingredient with a user)

### Recipe API

A recipe is made of following fields.

* name : String
* short_description : String
* description : String
* ingredients : List(Ingredient)
* userId : String (Associates the recipe with a user)

## Installation & Usage

### Build Requirements

Make sure you have these packages installed in order to build the project

* npm
* yarn

#### Development Build

Development version uses a mock backend API launched on port 3001. Make sure that the port is available. To run application in dev mode, run following commands

```bash
cd SmartRecipeRecommender/Frontend
API_BASE_URL='http://localhost:3001/' yarn dev-start
```

#### Production Build

To run application in production mode, replace mock API with your API. 

```bash
cd SmartRecipeRecommender/Frontend
API_BASE_URL=<BACKEND_API_URL_HERE> yarn start
```