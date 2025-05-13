import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import { AuthProvider } from '@/context/AuthContext';
import RequireAuth from '@/components/RequireAuth';

import Login    from '@/pages/Login';
import Register from '@/pages/Register';

import NotFound from '@/pages/NotFound';

import AdminLayout     from '@/layouts/AdminLayout';
import AdminDashboard  from '@/pages/admin/AdminDashboard';
import RequestList     from '@/pages/admin/RequestList';
import DonorList       from '@/pages/admin/DonorList';
import RequesterList   from '@/pages/admin/RequesterList';

import DonorLayout       from '@/layouts/DonorLayout';
import DonorDashboard    from '@/pages/donor/DonorDashboard';
import DonationHistory   from '@/pages/donor/DonationHistory';

import RequesterLayout    from '@/layouts/RequesterLayout';
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

          {/* ───── protected & role-aware ───── */}
          <Route element={<RequireAuth />}>



            {/* ADMIN branch */}
            <Route path="/admin" element={<AdminLayout />}>
            <Route index element={<Navigate to="donors" replace />} />
              <Route path="dashboard" element={<AdminDashboard />} />
              <Route path="requests"  element={<RequestList />} />
              <Route path="donors"     element={<DonorList />} />
              <Route path="requesters" element={<RequesterList />} />
            </Route>

            {/* DONOR branch */}
            <Route path="/donor" element={<DonorLayout />}>
              <Route index element={<Navigate to="dashboard" replace />} />
              <Route path="dashboard" element={<DonorDashboard />} />
              <Route path="history"   element={<DonationHistory />} />
            </Route>

            {/* REQUESTER branch */}
            <Route path="/requester" element={<RequesterLayout />}>
              <Route index element={<Navigate to="dashboard" replace />} />
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
