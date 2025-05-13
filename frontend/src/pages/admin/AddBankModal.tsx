import { useState } from 'react';
import api from '@/api';

export default function AddBankModal({onClose}:{onClose:()=>void}) {
  const [name,setName] = useState('');
  const [city,setCity] = useState('');

  async function submit(e:React.FormEvent){
    e.preventDefault();
    await api.post('/admin/blood-banks',{ name, city, managerId:1 }); 
    onClose();
  }

  return (
    <div className="fixed inset-0 bg-black/40 grid place-items-center">
      <form onSubmit={submit} className="card w-80 space-y-3">
        <h2 className="text-lg font-semibold">New blood bank</h2>
        <input className="border rounded w-full px-2 py-1" required
               placeholder="Name" value={name} onChange={e=>setName(e.target.value)}/>
        <input className="border rounded w-full px-2 py-1" required
               placeholder="City" value={city} onChange={e=>setCity(e.target.value)}/>
        <div className="flex justify-end gap-2 pt-2">
          <button type="button" className="btn-outline" onClick={onClose}>Cancel</button>
          <button className="btn">Save</button>
        </div>
      </form>
    </div>
  );
}
