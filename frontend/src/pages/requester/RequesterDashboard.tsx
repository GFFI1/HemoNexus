import React from 'react';

const RequesterDashboard: React.FC = () => (
  <section className="p-6">
    <h1 className="text-2xl font-semibold mb-4">Requester Dashboard</h1>
    <div className="grid sm:grid-cols-3 gap-4">
      {['Open','Fulfilled','Rejected'].map((t,i)=>(
        <div key={i} className="rounded-lg border p-4 text-center">
          <p className="text-sm text-gray-500">{t}</p>
          <p className="text-3xl font-bold">0</p>
        </div>
      ))}
    </div>
  </section>
);

export default RequesterDashboard;