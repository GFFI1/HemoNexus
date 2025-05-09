import React from 'react';

const ScheduleDonation: React.FC = () => (
  <section className="p-6 max-w-lg mx-auto">
    <h1 className="text-2xl font-semibold mb-4">Schedule a Donation</h1>
    <form className="flex flex-col gap-4">
      <label className="flex flex-col">
        <span className="text-sm mb-1">Preferred Date</span>
        <input type="date" className="border rounded p-2" />
      </label>
      <label className="flex flex-col">
        <span className="text-sm mb-1">Location</span>
        <input type="text" placeholder="City / Hospital" className="border rounded p-2" />
      </label>
      <button className="self-end bg-primary text-white px-4 py-2 rounded">Submit</button>
    </form>
  </section>
);

export default ScheduleDonation;