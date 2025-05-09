import { AppBar, Toolbar, Button } from '@mui/material';
import { Link } from 'react-router-dom';
import { getRole } from '../../utils/auth';

export default function Navbar() {
  const role = getRole();
  return (
    <AppBar position="static">
      <Toolbar sx={{ gap: 2 }}>
        {role === 'ROLE_ADMIN' && (
          <>
            <Button component={Link} to="/admin/dashboard">Dashboard</Button>
            <Button component={Link} to="/admin/donors">Donors</Button>
            <Button component={Link} to="/admin/inventory">Inventory</Button>
            <Button component={Link} to="/admin/requests">Requests</Button>
          </>
        )}
        {role === 'ROLE_DONOR' && (
          <>
            <Button component={Link} to="/donor/dashboard">Dashboard</Button>
            <Button component={Link} to="/donor/schedule">Schedule</Button>
            <Button component={Link} to="/donor/history">History</Button>
          </>
        )}
        {role === 'ROLE_REQUESTER' && (
          <>
            <Button component={Link} to="/requester/dashboard">Dashboard</Button>
            <Button component={Link} to="/requester/new">New Request</Button>
            <Button component={Link} to="/requester/status">Status</Button>
          </>
        )}
      </Toolbar>
    </AppBar>
  );
}