import { createStore, applyMiddleware, combineReducers } from 'redux';
import { composeWithDevTools } from 'redux-devtools-extension';
import thunk from 'redux-thunk';
import authReducer from './reducers/authReducer';
import userReducer from './reducers/userReducer';

// Create and export the store setup function
const configureStore = () => {
  const rootReducer = combineReducers({
    auth: authReducer,
    user: userReducer
  });

  // Explicitly apply thunk middleware first
  const middlewares = [thunk];
  
  const store = createStore(
    rootReducer,
    composeWithDevTools(applyMiddleware(...middlewares))
  );

  return store;
};

// Create and export the store instance
const store = configureStore();

export default store;
