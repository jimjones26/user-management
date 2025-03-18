import api from '../services/api';


export const debugToken = {
  logTokenStatus: () => {
    const token = localStorage.getItem('token');
    console.log('Current token status:', {
      exists: !!token,
      value: token ? `${token.substr(0, 10)}...` : 'none',
      localStorage: { ...localStorage },
      apiHeader: api.defaults.headers.common['Authorization']
    });
  }
};