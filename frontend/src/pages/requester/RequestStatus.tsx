import React from 'react';

const fake = [
  { id: 1, blood: 'O-', units: 2, status: 'Pending' },
  { id: 2, blood: 'A+', units: 1, status: 'Fulfilled' }
];

const RequestStatus: React.FC = () => (
  <section className="p-6">
    <h1 className="text-2xl font-semibold mb-4">My Requests</h1>
    <table className="min-w-full border rounded">
      <thead className="bg-gray-50">
        <tr>
          <th className="px-3 py-2 text-left">Blood</th>
          <th className="px-3 py-2 text-left">Units</th>
          <th className="px-3 py-2 text-left">Status</th>
        </tr>
      </thead>
      <tbody>
        {fake.map(r=>(
          <tr key={r.id} className="even:bg-gray-50">
            <td className="px-3 py-2">{r.blood}</td>
            <td className="px-3 py-2">{r.units}</td>
            <td className="px-3 py-2">{r.status}</td>
          </tr>
        ))}
      </tbody>
    </table>
  </section>
);

export default RequestStatus;