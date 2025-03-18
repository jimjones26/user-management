import { toast } from 'react-toastify';
import api from '../../services/api';

export const registerUser = (userData) => async (dispatch) => {
  dispatch({ type: 'REGISTER_REQUEST' });
  try {
    const response = await api.post('/auth/register', userData);
    dispatch({ type: 'REGISTER_SUCCESS', payload: response.data });
    toast.success('Registration successful! Please check your email.');
  } catch (error) {
    const message = error.response?.data?.message || 'Registration failed';
    dispatch({ type: 'REGISTER_FAILURE', payload: message });
    toast.error(message);
  }
};

export const loginUser = (credentials) => async (dispatch) => {
  console.log('loginUser action called with:', credentials); // Debug log
  
  dispatch({ type: 'LOGIN_REQUEST' });
  
  try {
    console.log('Making API request to /auth/login'); // Debug log
    const response = await api.post('/auth/login', credentials);
    console.log('Login API response:', response); // Debug log
    
    dispatch({ type: 'LOGIN_SUCCESS', payload: response.data });
    toast.success('Login successful!');
    
    return { type: 'LOGIN_SUCCESS', payload: response.data };
  } catch (error) {
    console.error('Login error details:', error.response || error); // Debug log
    const message = error.response?.data?.message || 'Login failed';
    dispatch({ type: 'LOGIN_FAILURE', payload: message });
    toast.error(message);
    
    throw error;
  }
};

export const setupMFA = (mfaData) => async (dispatch) => {
  dispatch({ type: 'MFA_SETUP_REQUEST' });
  try {
    const response = await api.post('/auth/mfa/setup', mfaData);
    dispatch({ type: 'MFA_SETUP_SUCCESS', payload: response.data });
    toast.success('MFA setup initiated. Please verify your code.');
  } catch (error) {
    const message = error.response?.data?.message || 'MFA setup failed';
    dispatch({ type: 'MFA_SETUP_FAILURE', payload: message });
    toast.error(message);
  }
};

export const verifyMFA = (code) => async (dispatch) => {
  try {
    const response = await api.post('/auth/mfa/verify', { code });
    dispatch({ type: 'MFA_VERIFY_SUCCESS', payload: response.data });
    toast.success('MFA verified successfully.');
  } catch (error) {
    const message = error.response?.data?.message || 'MFA verification failed';
    toast.error(message);
    throw error;
  }
};

export const logout = () => async (dispatch) => {
  try {
    await api.post('/auth/logout');
    dispatch({ type: 'LOGOUT' });
    toast.success('Logged out successfully.');
  } catch (error) {
    toast.error('Logout failed');
  }
};
