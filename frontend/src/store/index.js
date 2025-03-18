import { createStore, applyMiddleware, combineReducers } from 'redux';
import { composeWithDevTools } from 'redux-devtools-extension';
import thunk from 'redux-thunk';
import authReducer from './reducers/authReducer';
import userReducer from './reducers/userReducer';

// Root reducer combining all reducers
const rootReducer = combineReducers({
  auth: authReducer,
  user: userReducer
});

// Create store with middleware
const store = createStore(
  rootReducer,
  composeWithDevTools(
    applyMiddleware(thunk)
  )
);

export default store;
