import 'whatwg-fetch';

import { appConstants } from './app.constants';

const promise = require('es6-promise');

/**
 * Returns headers for API requests
 * @param  {string} accessToken User's access token
 * @return {Object}             Headers
 */
function getHeaders(accessToken) {
    const headers = new Headers();

    headers.append('Content-Type', 'application/json');
    
    if (accessToken !== null && typeof accessToken !== 'undefined') {
      headers.append('X-Access-Token', accessToken);
    }

    return headers;
}

/**
 * Common http requests 
 * @type {Object} Promise
 */
export const apiProxy = {
  get: (resourceUrl, accessToken) => {
    const Promise = promise.Promise;

    const headers = getHeaders(accessToken);

    return new Promise((resolve, reject) => {
      fetch(resourceUrl, {
        // credentials: 'same-origin',
        method: 'get',
        headers: headers,
      })
        .then((response) => {
          resolve(response.json());
        })
        .catch((err) => {
          reject(err);
        });
    });
  },
  post: (resourceUrl, params, accessToken) => {
    const Promise = promise.Promise;

    // Prepare Headers
    const headers = getHeaders(accessToken);

    return new Promise((resolve, reject) => {
      fetch(resourceUrl, {
        credentials: 'same-origin',
        method: 'post',
        headers: headers,
        body: JSON.stringify(params),
      })
        .then((response) => {
          resolve(response.json());
        })
        .catch((err) => {
          reject(err);
        });
    });
  },
  put: (resourceUrl, params, accessToken) => {
    const Promise = promise.Promise;

    // Prepare Headers
    const headers = getHeaders(accessToken);

    return new Promise((resolve, reject) => {
      fetch(resourceUrl, {
        credentials: 'same-origin',
        method: 'put',
        headers: headers,
        body: JSON.stringify(params),
      })
        .then((response) => {
          resolve(response.json());
        })
        .catch((err) => {
          reject(err);
        });
    });
  },
  // delete: (resourceUrl, params) => {
  //   const Promise = promise.Promise;
  //   const requestHeaders = new Headers();
  //   requestHeaders.append('Content-Type', 'application/json');

  //   return new Promise((resolve, reject) => {
  //     fetch(resourceUrl, {
  //       credentials: 'same-origin',
  //       method: 'delete',
  //       headers: requestHeaders,
  //       body: JSON.stringify(params),
  //     })
  //       .then(checkStatus)
  //       .then((response) => {
  //         resolve(response.json());
  //       })
  //       .catch((err) => {
  //         reject(err);
  //       });
  //   });
  // },
  // patch: (resourceUrl, params) => {
  //   const Promise = promise.Promise;
  //   const requestHeaders = new Headers();
  //   requestHeaders.append('Content-Type', 'application/json');

  //   return new Promise((resolve, reject) => {
  //     fetch(resourceUrl, {
  //       credentials: 'same-origin',
  //       method: 'PATCH',
  //       headers: requestHeaders,
  //       body: JSON.stringify(params),
  //     })
  //       .then(checkStatus)
  //       .then((response) => {
  //         resolve(response.json());
  //       })
  //       .catch((err) => {
  //         reject(err);
  //       });
  //   });
  // },
};
