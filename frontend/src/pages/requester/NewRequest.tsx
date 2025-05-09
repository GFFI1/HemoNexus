import React from 'react';

const NewRequest: React.FC = () => (
  <section className="p-6 max-w-lg mx-auto">
    <h1 className="text-2xl font-semibold mb-4">New Blood Request</h1>
    <form className="flex flex-col gap-4">
      <label className="flex flex-col">
        <span className="text-sm mb-1">Blood Type</span>
        <select className="border rounded p-2">
          {['A+','A-','B+','B-','O+','O-','AB+','AB-'].map(t=><option key={t}>{t}</option>)}
        </select>
      </label>
      <label className="flex flex-col">
        <span className="text-sm mb-1">Units Needed</span>
        <input type="number" min={1} className="border rounded p-2" />
      </label>
      <label className="flex flex-col">
        <span className="text-sm mb-1">Location</span>
        <input type="text" className="border rounded p-2" />
      </label>
      <button className="self-end bg-primary text-white px-4 py-2 rounded">Submit</button>
    </form>
  </section>
);

export default NewRequest;