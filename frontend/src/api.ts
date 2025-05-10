import axios from 'axios';

/**
 * ONE place to set the backend root.
 * The Spring app already adds `/api` via `server.servlet.context-path`,
 * so we include it here and omit `/api` in every individual call.
 */
const api = axios.create({
  baseURL: 'http://localhost:8080/api'
});

api.interceptors.request.use(cfg => {
  const token = localStorage.getItem('token');
  if (token) cfg.headers.Authorization = `Bearer ${token}`;
  return cfg;
});

export default api;
