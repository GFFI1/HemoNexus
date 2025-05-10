import React, { useEffect, useState } from 'react';
import api from '@/api';

interface Stat { total: number; last: string; next: string }

export default function DonorDashboard() {
  const [s, setS] = useState<Stat>({ total: 0, last: '-', next: '-' });

  useEffect(() => {
    api.get('/donor/dashboard').then(r => setS(r.data));
  }, []);

  return (
    <section className="p-6 space-y-6">
      <h1 className="text-2xl font-semibold">Donor Dashboard</h1>
      <p className="text-sm text-gray-600">
        Welcome! Your next eligible donation date is shown below.
      </p>

      <div className="grid grid-cols-1 sm:grid-cols-3 gap-6">
        <div className="card text-center">
          <p className="text-gray-500">Total Donations</p>
          <p className="text-3xl font-semibold mt-1">{s.total}</p>
        </div>

        <div className="card text-center">
          <p className="text-gray-500">Last Donation</p>
          <p className="text-3xl font-semibold mt-1">{s.last}</p>
        </div>

        <div className="card text-center">
          <p className="text-gray-500">Next Eligible</p>
          <p className="text-3xl font-semibold mt-1">{s.next}</p>
        </div>
      </div>
    </section>
  );
}
