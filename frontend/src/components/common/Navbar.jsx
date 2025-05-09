import { Link, useNavigate } from 'react-router-dom';
import { useAuth } from '@/context/AuthContext';
import { Button } from '@/components/ui/button';

export default function Navbar() {
  const { role, logout } = useAuth();
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate('/login');
  };

  return (
    <nav className="flex gap-4 p-4 bg-gray-800 text-white">
      {role === 'ROLE_ADMIN' && (
        <>
          <Link to="/admin/dashboard">Dashboard</Link>
          <Link to="/admin/requests">Requests</Link>
        </>
      )}
      {role === 'ROLE_DONOR' && (
        <>
          <Link to="/donor/dashboard">Dashboard</Link>
          <Link to="/donor/history">History</Link>
        </>
      )}
      {role === 'ROLE_REQUESTER' && (
        <>
          <Link to="/requester/dashboard">Dashboard</Link>
          <Link to="/requester/status">Status</Link>
        </>
      )}
      {/* always show logout on the right */}
      {role && <Button onClick={handleLogout} className="ml-auto">Logout</Button>}
    </nav>
  );
}
