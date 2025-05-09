import { Navigate, Outlet, useLocation } from 'react-router-dom';
import { useAuth } from '@/context/AuthContext';
import { routeFor } from '@/utils/rolePaths';

export default function RequireAuth() {
  const { token, role } = useAuth();
  const loc = useLocation();

  /* not logged in → /login */
  if (!token) return <Navigate to="/login" replace state={{ from: loc }} />;

  /* hit root or role root → shove to real dashboard */
  const bareRoots = ['/', '/admin', '/donor', '/requester'];
  if (bareRoots.includes(loc.pathname))
    return <Navigate to={routeFor[role ?? 'ROLE_DONOR']} replace />;

  return <Outlet />;
}
