import axios from 'axios';
import { toast } from 'react-toastify';

export const updateUser = (userId, userData) => async (dispatch) => {
  try {
    const response = await axios.put(`/api/users/${userId}`, userData);
    dispatch({ type: 'UPDATE_USER_SUCCESS', payload: response.data });
    toast.success('Profile updated successfully.');
  } catch (error) {
    const message = error.response?.data?.message || 'Update failed';
    toast.error(message);
    throw error;
  }
};

export const fetchUsers = (page = 1, size = 20) => async (dispatch) => {
  try {
    const response = await axios.get(`/api/users?page=${page}&size=${size}`);
    dispatch({ type: 'FETCH_USERS_SUCCESS', payload: response.data });
  } catch (error) {
    const message = error.response?.data?.message || 'Failed to fetch users';
    toast.error(message);
  }
};