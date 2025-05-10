import React, { useEffect, useState } from 'react';
import api from '@/api';

interface Stats { donors: number; banks: number; units: number }

export default function AdminDashboard() {
  const [stats, setStats] = useState<Stats>({ donors: 0, banks: 0, units: 0 });

  useEffect(() => {
    api.get('/admin/stats').then(r => setStats(r.data));
  }, []);

  return (
    <section className="p-6 space-y-6">
      <h1 className="text-2xl font-semibold">Admin Dashboard</h1>
      <p className="text-sm text-gray-600">Quick statistics &amp; charts go here</p>

      <div className="grid grid-cols-1 sm:grid-cols-3 gap-6">
        <div className="card text-center">
          <p className="text-gray-500">Total Donors</p>
          <p className="text-3xl font-semibold mt-1">{stats.donors}</p>
        </div>

        <div className="card text-center">
          <p className="text-gray-500">Active Banks</p>
          <p className="text-3xl font-semibold mt-1">{stats.banks}</p>
        </div>

        <div className="card text-center">
          <p className="text-gray-500">Units in Stock</p>
          <p className="text-3xl font-semibold mt-1">
            {stats.units.toLocaleString()} ml
          </p>
        </div>
      </div>

      <button className="btn">+ Add Blood Bank</button>
    </section>
  );
}
