import React from 'react';
import { useForm } from 'react-hook-form';
import { Link, useNavigate } from 'react-router-dom';
import api from '@/api';
import { Button } from '@/components/ui/button';

type Form = {
  username: string;
  email:    string;
  password: string;
  role:     'donor' | 'requester';
};

export default function Register() {
  const { register, handleSubmit, formState:{errors} } = useForm<Form>();
  const nav = useNavigate();

  const submit = (d: Form) =>
    api.post('/auth/signup', { ...d, roles:[d.role] })
       .then(() => nav('/login'))
       .catch(() => alert('Signup failed'));

  return (
    <div className="h-screen grid place-items-center bg-neutral-100">
      <form onSubmit={handleSubmit(submit)}
            className="bg-white p-8 rounded-xl w-96 space-y-4 shadow">

        <h1 className="text-xl font-semibold text-center">Register</h1>

        <input {...register('username', { required:true })} placeholder="Username"
               className="border rounded p-2 w-full" />
        {errors.username && <p className="text-red-600 text-sm">Required</p>}

        <input {...register('email', { required:true })} type="email"
               placeholder="Email" className="border rounded p-2 w-full" />
        {errors.email && <p className="text-red-600 text-sm">Required</p>}

        <input {...register('password', { required:true })} type="password"
               placeholder="Password" className="border rounded p-2 w-full" />
        {errors.password && <p className="text-red-600 text-sm">Required</p>}

        <select {...register('role', { required:true })}
                className="border rounded p-2 w-full">
          <option value="">Choose role…</option>
          <option value="donor">Donor</option>
          <option value="requester">Requester</option>
        </select>
        {errors.role && <p className="text-red-600 text-sm">Pick a role</p>}

        <Button type="submit" className="w-full">Sign&nbsp;up</Button>

        <p className="text-xs text-center mt-2">
          Have an account?
          <Link to="/login" className="underline ml-1">Sign&nbsp;in</Link>
        </p>
      </form>
    </div>
  );
}
