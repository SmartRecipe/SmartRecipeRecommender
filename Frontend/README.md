# Smart Recipe Recommender Frontend

A simple single page responsive application developed using React for Smart Recipe Recommender.

## Tech Spec

* React 16+
* React Redux 
* React Router v4
* React Material
* Json Server (For Dev Build Only)

## API Requirements

The frontend application expects the backend to support CRUD operations on different data. 

### User APIs

Collection of users 

```json
{ 
    "users": [
        { 
            "_type": "user", 
            "id": "", 
            "name": "", 
            "email": "", 
            "password": "", 
            "salt": "", 
            "createdAt": "", 
            "updatedAt": "" 
        }
    ]
}
```

Sign Up API

```bash
POST /signup

Parameters : 
{
    "user": {
        "email": "",
        "password": "",
    }
}
```

Sign In API

```bash
POST /signin

Parameters : 
{
    "user": {
        "email": "",
        "password": "",
    }
}
```

### Ingredient APIs

Collection of ingredients

```json
{ 
    "ingredients": [
        { 
            "_type": "ingredient", 
            "id": "", 
            "name": "", 
            "qty": "", 
            "unit": "", 
            "createdAt": "", 
            "updatedAt": "" 
        }
    ]
}
```

Get list of ingredients

```bash
GET /ingredients

Headers: 
{
    X-Access-Token: "<ACCESS_TOKEN>",
}
```

Add new ingredient

```bash
POST /ingredients

Parameters : 
{
    "id": "<OPTIONAL>",
    "name": "",
    "qty": "",
    "unit": "",
}

Headers: 
{
    X-Access-Token: "<ACCESS_TOKEN>",
}
```

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
REACT_APP_API_BASE_URL='http://localhost:3001/' yarn start
```

#### Production Build

To run application in production mode, replace mock API with your API. 

```bash
cd SmartRecipeRecommender/Frontend
REACT_APP_API_BASE_URL=<BACKEND_API_URL_HERE> yarn prod-start
```