import React from 'react';
import { Outlet, NavLink, useNavigate } from 'react-router-dom';
import { useAuth } from '@/context/AuthContext';
import { Button } from '@/components/ui/button';

export default function RequesterLayout() {
  const { logout } = useAuth();
  const nav = useNavigate();

  return (
    <div className="flex flex-col min-h-screen">
      <header className="flex items-center bg-primary text-white p-4">
        <h1 className="text-lg font-semibold flex-1">Requester</h1>
        <Button onClick={() => nav('/requester/new')} className="mr-4">
          New Request
        </Button>
        <Button onClick={logout}>Logout</Button>
      </header>
      <main className="flex-1 bg-neutral-100">
        <Outlet />
      </main>
    </div>
  );
}
