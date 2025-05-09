import React, { createContext, useContext, useState } from 'react';
import { jwtDecode } from 'jwt-decode';
import api from '@/api';

interface JwtPayload { roles?: string[] }

interface AuthState {
  token: string | null;
  role : string | null;
  user : string | null;
}

interface AuthCtx extends AuthState {
  /** returns the first role string so callers can redirect immediately */
  login (username: string, password: string): Promise<string>;
  logout(): void;
}

const AuthContext = createContext<AuthCtx>(null!);

export const AuthProvider: React.FC<{ children: React.ReactNode }> = ({ children }) => {
  const [state, setState] = useState<AuthState>(() => {
    const token    = localStorage.getItem('token');
    if (!token || token === 'undefined') return { token: null, role: null, user: null };

    /* safe-decode: catch corrupted or legacy tokens */
    let role: string | null = null;
    try {
      const { roles } = jwtDecode<JwtPayload>(token);
      if (roles && roles.length) role = roles[0];
    } catch { /* ignore – token is bad */ }

    return { token, role, user: null };
  });

  /* ---------- actions ---------- */
  const login = async (username: string, password: string): Promise<string> => {
    const { data } = await api.post('/auth/signin', { username, password });
    localStorage.setItem('token', data.token);

    const { roles = [] } = jwtDecode<JwtPayload>(data.token);
    const role = roles[0] ?? null;

    setState({ token: data.token, role, user: data.username });
    return role ?? '';                               // empty string if backend sent no role
  };

  const logout = () => {
    localStorage.clear();
    setState({ token: null, role: null, user: null });
  };

  return (
    <AuthContext.Provider value={{ ...state, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => useContext(AuthContext);
