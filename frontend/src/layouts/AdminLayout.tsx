import React from 'react';
import { Outlet, NavLink } from 'react-router-dom';
import { useAuth } from '@/context/AuthContext';
import { Button } from '@/components/ui/button';

export default function AdminLayout() {
  const { logout } = useAuth();

  return (
    <div className="flex min-h-screen">
      {/* sidebar */}
      <aside className="w-56 bg-gray-800 text-white flex flex-col p-4">
        <h2 className="text-lg font-semibold mb-6">ADMIN</h2>
        <NavLink to="/admin/donors"     className="mb-2 hover:underline">Donors</NavLink>
        <NavLink to="/admin/requesters" className="mb-2 hover:underline">Requesters</NavLink>
        <NavLink to="/admin/requests"   className="mb-2 hover:underline">View Requests</NavLink>
        <Button onClick={logout} className="mt-auto">Logout</Button>
      </aside>

      {/* content */}
      <main className="flex-1 bg-neutral-100">
        <Outlet />
      </main>
    </div>
  );
}
