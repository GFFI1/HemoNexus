import { useState } from 'react';
import api from '@/api';

export default function NewRequestModal({ onClose }:{onClose:()=>void}) {
  const [bankId, setBankId]   = useState('');
  const [type, setType]       = useState('');
  const [units, setUnits]     = useState(1);
  const [urgency, setUrgency] = useState('Normal');

  const submit = async (e:React.FormEvent) => {
    e.preventDefault();
    await api.post('/requester/requests',{
      bloodBankId: Number(bankId),
      bloodType:   type,
      units,
      urgency
    });
    onClose();
  };

  return (
    <div className="fixed inset-0 bg-black/50 flex items-center justify-center">
      <form onSubmit={submit}
            className="bg-white p-6 rounded-lg w-80 space-y-3"
            aria-label="Create new blood request">
        <h2 className="text-lg font-semibold">New Blood Request</h2>

        <label className="block">
          <span className="text-sm">Blood Bank ID</span>
          <input
            aria-label="Blood bank ID"
            placeholder="e.g. 3"
            value={bankId}
            onChange={e=>setBankId(e.target.value)}
            required
            className="border rounded w-full px-2 py-1 mt-1" />
        </label>

        <label className="block">
          <span className="text-sm">Blood Type</span>
          <select
            aria-label="Blood type"
            value={type}
            onChange={e=>setType(e.target.value)}
            required
            className="border rounded w-full px-2 py-1 mt-1">
            <option value="">Select Type</option>
            {['A+','A-','B+','B-','O+','O-','AB+','AB-'].map(b=>(
              <option key={b} value={b}>{b}</option>
            ))}
          </select>
        </label>

        <label className="block">
          <span className="text-sm">Units</span>
          <input
            aria-label="Units"
            placeholder="Units"
            type="number" min={1}
            value={units}
            onChange={e=>setUnits(Number(e.target.value))}
            className="border rounded w-full px-2 py-1 mt-1" />
        </label>

        <label className="block">
          <span className="text-sm">Urgency</span>
          <select
            aria-label="Urgency"
            value={urgency}
            onChange={e=>setUrgency(e.target.value)}
            className="border rounded w-full px-2 py-1 mt-1">
            <option value="Normal">Normal</option>
            <option value="Urgent">Urgent</option>
          </select>
        </label>

        <div className="text-right space-x-2 pt-3">
          <button type="button" onClick={onClose} className="btn-outline">Cancel</button>
          <button className="btn">Submit</button>
        </div>
      </form>
    </div>
  );
}
