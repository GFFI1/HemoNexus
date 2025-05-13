import React, { createContext, useContext, useState } from 'react';
import { jwtDecode } from 'jwt-decode';
import api from '@/api';
import { routeFor } from '@/utils/rolePaths';

interface JwtPayload { roles?: string[]; authorities?: string[] }

interface AuthState {
  token: string | null;
  role : string | null;
  user : string | null;
}
interface AuthCtx extends AuthState {
  login  (u: string, p: string): Promise<void>;
  logout (): void;
}

const AuthContext = createContext<AuthCtx>(null!);

export const AuthProvider: React.FC<{ children: React.ReactNode }> = ({ children }) => {
  const [state, setState] = useState<AuthState>(() => {
    const token = localStorage.getItem('token');
    if (!token || token === 'undefined') return { token: null, role: null, user: null };

    try {
      const { roles, authorities } = jwtDecode<JwtPayload>(token);
      const role = roles?.[0] ?? authorities?.[0] ?? null;
      return { token, role, user: null };
    } catch { return { token: null, role: null, user: null }; }
  });

  /* ------------ actions ------------ */
  const login = async (username: string, password: string) => {
    try {
      const { data } = await api.post('/auth/signin', { username, password });
      localStorage.setItem('token', data.token);
      console.log('Login successful. Token:', data.token);
      console.log('User data:', {
        username: data.username,
        roles: data.roles,
        ...data,
      });
  
      const roleFromResp: string | undefined = data.roles?.[0];
      const { roles = [], authorities = [] } = jwtDecode<JwtPayload>(data.token);
      const roleDecoded = roles[0] ?? authorities[0] ?? null;
  
      const role = roleFromResp ?? roleDecoded;
      setState({ token: data.token, role, user: data.username });
  
      window.location.replace(routeFor[role ?? 'ROLE_DONOR']);
    } catch (error: any) {
      console.error('Login failed:', error.response?.data || error.message || error);
      alert('Login failed. Please check your credentials and try again.');
    }
  };
  ;

  const logout = () => {
    localStorage.removeItem('token');
    setState({ token: null, role: null, user: null });
    window.location.replace('/login');
  };

  return <AuthContext.Provider value={{ ...state, login, logout }}>{children}</AuthContext.Provider>;
};

export const useAuth = () => useContext(AuthContext);
