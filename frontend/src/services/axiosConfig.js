import axios from 'axios';
import { store } from '../app/store';
import { logout } from '../features/auth/authSlice';
import { setAlert } from '../features/alerts/alertSlice';

// Create an instance of axios
const axiosInstance = axios.create({
  baseURL: '/api',
  headers: {
    'Content-Type': 'application/json',
  },
});

// Add a request interceptor
axiosInstance.interceptors.request.use(
  (config) => {
    // Get token from state
    const { auth } = store.getState();
    
    // If token exists, add to headers
    if (auth.user && auth.user.token) {
      config.headers.Authorization = `Bearer ${auth.user.token}`;
    }
    
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

// Add a response interceptor
axiosInstance.interceptors.response.use(
  (response) => {
    return response;
  },
  (error) => {
    const { status } = error.response || {};
    
    // Handle 401 - Unauthorized
    if (status === 401) {
      // Dispatch logout action and redirect
      store.dispatch(logout());
      store.dispatch(setAlert({ 
        type: 'error', 
        message: 'Your session has expired. Please log in again.' 
      }));
    }
    
    // Handle 403 - Forbidden
    if (status === 403) {
      store.dispatch(setAlert({ 
        type: 'error', 
        message: 'You do not have permission to perform this action.' 
      }));
    }
    
    // Handle 500 - Server Error
    if (status >= 500) {
      store.dispatch(setAlert({ 
        type: 'error', 
        message: 'A server error occurred. Please try again later.' 
      }));
    }
    
    return Promise.reject(error);
  }
);

export default axiosInstance;