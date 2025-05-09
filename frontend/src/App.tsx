import { BrowserRouter, Routes, Route } from 'react-router-dom';
import { AuthProvider } from '@/context/AuthContext';
import RequireAuth from '@/components/RequireAuth';

/* public pages */
import Login    from '@/pages/Login';
import Register from '@/pages/Register';

/* layout & error */
import AppLayout  from '@/layouts/AppLayout';
import NotFound   from '@/pages/NotFound';

/* admin pages */
import AdminDashboard from '@/pages/admin/AdminDashboard';
import RequestList    from '@/pages/admin/RequestList';

/* donor pages */
import DonorDashboard     from '@/pages/donor/DonorDashboard';
import ScheduleDonation   from '@/pages/donor/ScheduleDonation';
import DonationHistory    from '@/pages/donor/DonationHistory';

/* requester pages */
import RequesterDashboard from '@/pages/requester/RequesterDashboard';
import NewRequest         from '@/pages/requester/NewRequest';
import RequestStatus      from '@/pages/requester/RequestStatus';

export default function App() {
  return (
    <AuthProvider>
      <BrowserRouter>
        <Routes>

          {/* ───────── public ───────── */}
          <Route path="/login"    element={<Login />} />
          <Route path="/register" element={<Register />} />

          {/* ───── protected ───── */}
          <Route element={<RequireAuth />}>

            {/* ADMIN branch */}
            <Route path="/admin" element={<AppLayout />}>
              <Route path="dashboard" element={<AdminDashboard />} />
              <Route path="requests"  element={<RequestList />} />
              {/* add inventoryList & donorList later */}
            </Route>

            {/* DONOR branch */}
            <Route path="/donor" element={<AppLayout />}>
              <Route path="dashboard" element={<DonorDashboard />} />
              <Route path="schedule"  element={<ScheduleDonation />} />
              <Route path="history"   element={<DonationHistory />} />
            </Route>

            {/* REQUESTER branch */}
            <Route path="/requester" element={<AppLayout />}>
              <Route path="dashboard" element={<RequesterDashboard />} />
              <Route path="new"       element={<NewRequest />} />
              <Route path="status"    element={<RequestStatus />} />
            </Route>

          </Route>

          {/* catch-all */}
          <Route path="*" element={<NotFound />} />
        </Routes>
      </BrowserRouter>
    </AuthProvider>
  );
}