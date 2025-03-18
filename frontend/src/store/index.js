import { createStore, applyMiddleware, combineReducers } from 'redux';
import { composeWithDevTools } from 'redux-devtools-extension';
import thunk from 'redux-thunk';
import authReducer from './reducers/authReducer';
import userReducer from './reducers/userReducer';

// Custom middleware for JWT validation
const tokenValidationMiddleware = store => next => action => {
  if (action.type === 'LOGIN_SUCCESS' || action.type === 'REGISTER_SUCCESS') {
    try {
      const token = action.payload?.token;
      if (token && token.includes('-')) {
        // Invalid token format detected
        return next({
          type: `${action.type.split('_')[0]}_FAILURE`,
          payload: 'Invalid authentication token format'
        });
      }
    } catch (error) {
      console.error('Token validation error:', error);
      return next({
        type: `${action.type.split('_')[0]}_FAILURE`,
        payload: 'Authentication failed'
      });
    }
  }
  return next(action);
};

// Error handling middleware
const errorHandlingMiddleware = store => next => action => {
  if (action.type.endsWith('_FAILURE')) {
    // Log errors for monitoring
    console.error('Action error:', action.payload);
  }
  return next(action);
};

const rootReducer = combineReducers({
  auth: authReducer,
  user: userReducer,
});

const middleware = [
  thunk,
  tokenValidationMiddleware,
  errorHandlingMiddleware
];

const store = createStore(
  rootReducer,
  composeWithDevTools(applyMiddleware(...middleware))
);

export default store;
