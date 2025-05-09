import React from 'react';

const fakeRows = [
  { id: 1, date: '2025-02-11', location: 'St Mary Hospital', units: 1 },
  { id: 2, date: '2024-10-08', location: 'Blood Drive – Uni', units: 1 }
];

const DonationHistory: React.FC = () => (
  <section className="p-6">
    <h1 className="text-2xl font-semibold mb-4">Donation History</h1>
    <table className="min-w-full border rounded">
      <thead className="bg-gray-50">
        <tr>
          <th className="px-3 py-2 text-left">Date</th>
          <th className="px-3 py-2 text-left">Location</th>
          <th className="px-3 py-2 text-left">Units</th>
        </tr>
      </thead>
      <tbody>
        {fakeRows.map(r=>(
          <tr key={r.id} className="even:bg-gray-50">
            <td className="px-3 py-2">{r.date}</td>
            <td className="px-3 py-2">{r.location}</td>
            <td className="px-3 py-2">{r.units}</td>
          </tr>
        ))}
      </tbody>
    </table>
  </section>
);

export default DonationHistory;