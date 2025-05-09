import React from 'react';
import { useForm } from 'react-hook-form';
import api from '@/api';
import { useNavigate } from 'react-router-dom';
import { Button } from '@/components/ui/button';

type Form = {
  bloodType:string; units:number; urgency:string;
  hospital:string; location:string;
};

export default function NewRequest() {
  const { register, handleSubmit, formState:{errors} } = useForm<Form>();
  const nav=useNavigate();

  const submit = (d:Form) =>
    api.post('/requester/requests', d)
       .then(()=> nav('/requester/dashboard'))
       .catch(()=> alert('Failed to submit'));

  return (
    <section className="p-6 max-w-lg mx-auto">
      <h1 className="text-2xl font-semibold mb-4">New Blood Request</h1>
      <form onSubmit={handleSubmit(submit)} className="space-y-4">
        {/* blood type */}
        <select {...register('bloodType',{required:true})}
                className="border rounded p-2 w-full">
          <option value="">Blood Type…</option>
          {['A+','A-','B+','B-','O+','O-','AB+','AB-']
             .map(t=><option key={t}>{t}</option>)}
        </select>
        {errors.bloodType && <p className="text-red-600 text-sm">Required</p>}

        <input type="number" min={1} {...register('units',{required:true})}
               placeholder="Units needed" className="border rounded p-2 w-full"/>
        {errors.units && <p className="text-red-600 text-sm">Required</p>}

        <select {...register('urgency',{required:true})}
                className="border rounded p-2 w-full">
          <option value="">Urgency…</option>
          <option>LOW</option><option>MEDIUM</option><option>HIGH</option>
        </select>
        {errors.urgency && <p className="text-red-600 text-sm">Required</p>}

        <input {...register('hospital',{required:true})} placeholder="Hospital name"
               className="border rounded p-2 w-full"/>
        {errors.hospital && <p className="text-red-600 text-sm">Required</p>}

        <input {...register('location',{required:true})} placeholder="Location"
               className="border rounded p-2 w-full"/>
        {errors.location && <p className="text-red-600 text-sm">Required</p>}

        <Button type="submit" className="w-full">Submit</Button>
      </form>
    </section>
  );
}
