import axios from 'axios';

// Register user
const register = async (userData) => {
  const response = await axios.post('/auth/signup', userData);

  if (response.data) {
    localStorage.setItem('user', JSON.stringify(response.data));
  }

  return response.data;
};

// Login user
const login = async (userData) => {
  const response = await axios.post('/auth/signin', userData);

  if (response.data) {
    localStorage.setItem('user', JSON.stringify(response.data));
    
    // Set token in axios defaults for all future requests
    if (response.data.token) {
      axios.defaults.headers.common['Authorization'] = `Bearer ${response.data.token}`;
    }
  }

  return response.data;
};

// Logout user
const logout = () => {
  localStorage.removeItem('user');
  delete axios.defaults.headers.common['Authorization'];
};

const authService = {
  register,
  login,
  logout,
};

export default authService;