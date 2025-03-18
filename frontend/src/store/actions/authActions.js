import { toast } from 'react-toastify';
import api from '../../services/api';

// Action Types
export const REGISTER_REQUEST = 'REGISTER_REQUEST';
export const REGISTER_SUCCESS = 'REGISTER_SUCCESS';
export const REGISTER_FAILURE = 'REGISTER_FAILURE';

export const LOGIN_REQUEST = 'LOGIN_REQUEST';
export const LOGIN_SUCCESS = 'LOGIN_SUCCESS';
export const LOGIN_FAILURE = 'LOGIN_FAILURE';

export const MFA_SETUP_REQUEST = 'MFA_SETUP_REQUEST';
export const MFA_SETUP_SUCCESS = 'MFA_SETUP_SUCCESS';
export const MFA_SETUP_FAILURE = 'MFA_SETUP_FAILURE';
export const MFA_VERIFY_SUCCESS = 'MFA_VERIFY_SUCCESS';

export const RESTORE_SESSION = 'RESTORE_SESSION';
export const LOGOUT = 'LOGOUT';

// Action Creators
const registerRequest = () => ({
  type: REGISTER_REQUEST
});

const registerSuccess = (data) => ({
  type: REGISTER_SUCCESS,
  payload: data
});

const registerFailure = (error) => ({
  type: REGISTER_FAILURE,
  payload: error
});

const loginRequest = () => ({
  type: LOGIN_REQUEST
});

const loginSuccess = (data) => ({
  type: LOGIN_SUCCESS,
  payload: data
});

const loginFailure = (error) => ({
  type: LOGIN_FAILURE,
  payload: error
});

const mfaSetupRequest = () => ({
  type: MFA_SETUP_REQUEST
});

const mfaSetupSuccess = (data) => ({
  type: MFA_SETUP_SUCCESS,
  payload: data
});

const mfaSetupFailure = (error) => ({
  type: MFA_SETUP_FAILURE,
  payload: error
});

const mfaVerifySuccess = (data) => ({
  type: MFA_VERIFY_SUCCESS,
  payload: data
});

const logoutAction = () => ({
  type: LOGOUT
});

// Thunk Actions
export const registerUser = (userData) => async (dispatch) => {
  dispatch(registerRequest());
  try {
    const response = await api.post('/auth/register', userData);
    dispatch(registerSuccess(response.data));
    toast.success('Registration successful! Please check your email.');
    return { success: true, data: response.data };
  } catch (error) {
    const message = error.response?.data?.message || 'Registration failed';
    dispatch(registerFailure(message));
    toast.error(message);
    return { success: false, error: message };
  }
};

export const loginUser = (credentials) => async (dispatch) => {
  dispatch(loginRequest());
  
  try {
    const response = await api.post('/auth/login', credentials);
    const { token, user } = response.data;
    
    // Store token in localStorage
    localStorage.setItem('token', token);
    
    // Update API instance with new token
    api.defaults.headers.common['Authorization'] = `Bearer ${token}`;
    
    dispatch(loginSuccess({ token, user }));
    toast.success('Login successful!');
    return { success: true, data: { token, user } };
  } catch (error) {
    const message = error.response?.data?.message || 'Login failed';
    dispatch(loginFailure(message));
    toast.error(message);
    return { success: false, error: message };
  }
};

export const setupMFA = (mfaData) => async (dispatch) => {
  dispatch(mfaSetupRequest());
  try {
    const response = await api.post('/auth/mfa/setup', mfaData);
    dispatch(mfaSetupSuccess(response.data));
    toast.success('MFA setup initiated. Please verify your code.');
    return { success: true, data: response.data };
  } catch (error) {
    const message = error.response?.data?.message || 'MFA setup failed';
    dispatch(mfaSetupFailure(message));
    toast.error(message);
    return { success: false, error: message };
  }
};

export const verifyMFA = (code) => async (dispatch) => {
  try {
    const response = await api.post('/auth/mfa/verify', { code });
    dispatch(mfaVerifySuccess(response.data));
    toast.success('MFA verified successfully.');
    return { success: true, data: response.data };
  } catch (error) {
    const message = error.response?.data?.message || 'MFA verification failed';
    toast.error(message);
    return { success: false, error: message };
  }
};

export const logout = () => async (dispatch) => {
  try {
    await api.post('/auth/logout');
    // Clear token from localStorage
    localStorage.removeItem('token');
    // Remove Authorization header
    delete api.defaults.headers.common['Authorization'];
    
    dispatch(logoutAction());
    toast.success('Logged out successfully.');
    return { success: true };
  } catch (error) {
    // Still remove token and dispatch logout on error
    localStorage.removeItem('token');
    delete api.defaults.headers.common['Authorization'];
    dispatch(logoutAction());
    
    toast.error('Logout failed');
    return { success: false, error: 'Logout failed' };
  }
};

export const restoreSession = () => async (dispatch) => {
  const token = localStorage.getItem('token');
  
  if (token) {
    try {
      // Set token in API instance
      api.defaults.headers.common['Authorization'] = `Bearer ${token}`;
      
      // Verify token and fetch user data
      const response = await api.get('/auth/users/me');
      
      dispatch(loginSuccess({ token, user: response.data }));
      return { success: true, data: response.data };
    } catch (error) {
      // Clear invalid token
      localStorage.removeItem('token');
      delete api.defaults.headers.common['Authorization'];
      
      dispatch(logoutAction());
      toast.error('Session expired, please log in again');
      return { success: false, error: 'Session expired' };
    }
  }
  return { success: false, error: 'No token found' };
};
