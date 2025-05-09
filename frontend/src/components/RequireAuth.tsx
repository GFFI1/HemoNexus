import { Navigate, Outlet, useLocation } from 'react-router-dom';
import { useAuth } from '@/context/AuthContext';
import { routeFor } from '@/utils/rolePaths';

const RequireAuth: React.FC = () => {
  const { token, role } = useAuth();
  const location       = useLocation();

  /* 1 ⟶ not logged in */
  if (!token)
    return <Navigate to="/login" replace state={{ from: location }} />;

  /* 2 ⟶ logged in but at root "/" */
  if (location.pathname === '/')
    return <Navigate to={routeFor[role!]} replace />;

  /* 3 ⟶ happy path */
  return <Outlet />;
};

export default RequireAuth;