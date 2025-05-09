import React from 'react';

const DonorDashboard: React.FC = () => (
  <section className="p-6">
    <h1 className="text-2xl font-semibold mb-4">Donor Dashboard</h1>
    <p className="text-gray-600">Welcome!  Your next eligible donation date is shown below.</p>
    {/* replace with real stats when API is ready */}
    <div className="mt-6 grid sm:grid-cols-3 gap-4">
      {['Total Donations','Last Donation','Next Eligible'].map((t,i)=>(
        <div key={i} className="rounded-lg border p-4 text-center">
          <p className="text-sm text-gray-500">{t}</p>
          <p className="text-3xl font-bold">0</p>
        </div>
      ))}
    </div>
  </section>
);

export default DonorDashboard;
