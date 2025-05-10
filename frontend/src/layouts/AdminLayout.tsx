import React from 'react';
import { Outlet, NavLink } from 'react-router-dom';
import { useAuth } from '@/context/AuthContext';

export default function AdminLayout() {
  const { logout } = useAuth();

  return (
    <div className="flex min-h-screen">
      {/* sidebar */}
      <aside className="w-56 bg-slate-900 text-white flex flex-col p-4">
        <h2 className="text-lg font-semibold mb-6 tracking-wide">ADMIN</h2>

        <NavLink to="/admin/donors"     className="mb-2 hover:underline">Donors</NavLink>
        <NavLink to="/admin/requesters" className="mb-2 hover:underline">Requesters</NavLink>
        <NavLink to="/admin/requests"   className="mb-2 hover:underline">View Requests</NavLink>

        <button onClick={logout} className="btn-outline mt-auto">Logout</button>
      </aside>

      {/* main */}
      <main className="flex-1 bg-neutral-100">
        <Outlet />
      </main>
    </div>
  );
}
