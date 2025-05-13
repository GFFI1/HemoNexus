import axios from 'axios';

const api = axios.create({
  baseURL: 'http://localhost:8080/api'
});

api.interceptors.request.use(cfg => {
  const tok = localStorage.getItem('token');
  if (tok) cfg.headers.Authorization = `Bearer ${tok}`;
  return cfg;
});

export default api;
