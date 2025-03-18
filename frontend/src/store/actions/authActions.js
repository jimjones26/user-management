import axios from 'axios';
import { toast } from 'react-toastify';

export const registerUser = (userData) => async (dispatch) => {
  dispatch({ type: 'REGISTER_REQUEST' });
  try {
    const response = await axios.post('/api/register', userData);
    dispatch({ type: 'REGISTER_SUCCESS', payload: response.data });
    toast.success('Registration successful! Please check your email.');
  } catch (error) {
    const message = error.response?.data?.message || 'Registration failed';
    dispatch({ type: 'REGISTER_FAILURE', payload: message });
    toast.error(message);
  }
};

export const loginUser = (credentials) => async (dispatch) => {
  dispatch({ type: 'LOGIN_REQUEST' });
  try {
    const response = await axios.post('/api/login', credentials);
    dispatch({ type: 'LOGIN_SUCCESS', payload: response.data });
    toast.success('Login successful!');
  } catch (error) {
    const message = error.response?.data?.message || 'Login failed';
    dispatch({ type: 'LOGIN_FAILURE', payload: message });
    toast.error(message);
  }
};

export const setupMFA = (mfaData) => async (dispatch) => {
  dispatch({ type: 'MFA_SETUP_REQUEST' });
  try {
    const response = await axios.post('/api/mfa/setup', mfaData);
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
    const response = await axios.post('/api/mfa/verify', { code });
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
    await axios.post('/api/logout');
    dispatch({ type: 'LOGOUT' });
    toast.success('Logged out successfully.');
  } catch (error) {
    toast.error('Logout failed');
  }
};