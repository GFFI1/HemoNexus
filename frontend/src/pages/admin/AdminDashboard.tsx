import { useEffect, useState } from 'react';
import api from '@/api';
import AddBankModal from './AddBankModal';   // ← NEW component


interface Stats { donors:number; banks:number; units:number }
export default function AdminDashboard(){
  const [stats,setStats]=useState<Stats|null>(null);
  const [err ,setErr ] = useState('');
  const [show,setShow] = useState(false);   // modal flag


  useEffect(() => {
    api.get('/stats')
       .then(r => setStats(r.data))
       .catch(() => setErr('Backend /stats not available'));   
  },[]);

  if (err)        return <p className="p-6 text-red-600">{err}</p>;
  if (!stats)     return <p className="p-6">Loading…</p>;

  return (
    <section className="p-6 space-y-6">
      <h1 className="text-2xl font-bold">Admin Dashboard</h1>
      <div className="grid grid-cols-1 sm:grid-cols-3 gap-4">
      <button className="btn" onClick={()=>setShow(true)}>Add blood bank</button>

        
        <StatCard label="Donors" value={stats.donors}/>
        <StatCard label="Blood Banks" value={stats.banks}/>
        <StatCard label="Units (ml)" value={stats.units}/>
        
        
      </div>
      {show && <AddBankModal onClose={()=>setShow(false)}/>}
    </section>
  );
}

function StatCard({label,value}:{label:string,value:number}){
  return (
    <div className="bg-white rounded shadow p-6 text-center">
      <div className="text-sm text-gray-500">{label}</div>
      <div className="text-3xl font-bold text-purple-800">{value}</div>
    </div>
  );
}
