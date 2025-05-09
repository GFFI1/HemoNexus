import jwtDecode from 'jwt-decode';

export const saveToken = (token) => localStorage.setItem('token', token);
export const getToken  = () => localStorage.getItem('token');

export const getRole = () => {
  const t = getToken();
  if (!t) return null;
  const { roles } = jwtDecode(t);      // spring adds "roles" claim
  return Array.isArray(roles) ? roles[0] : roles; // take first role
};
