import React from 'react';
import { useForm } from 'react-hook-form';
import { Link } from 'react-router-dom';
import { useAuth } from '@/context/AuthContext';
import { Button } from '@/components/ui/button';

type LoginData = { username: string; password: string };

export default function Login() {
  const { register, handleSubmit, formState: { errors } } = useForm<LoginData>();
  const { login } = useAuth();

  return (
    <div className="h-screen grid place-items-center bg-neutral-100">
      <form
        onSubmit={handleSubmit(({ username, password }) => login(username, password))}
        className="bg-white p-8 rounded-xl w-80 space-y-4 shadow">

        <h1 className="text-xl font-semibold text-center">Login</h1>

        <input {...register('username', { required: true })}
               placeholder="Username" className="border rounded p-2 w-full" />
        {errors.username && <p className="text-red-600 text-sm">Username required</p>}

        <input {...register('password', { required: true })}
               type="password" placeholder="Password" className="border rounded p-2 w-full" />
        {errors.password && <p className="text-red-600 text-sm">Password required</p>}

        <Button type="submit" className="w-full">Sign&nbsp;in</Button>

        <p className="text-xs text-center">
          No account?
          <Link to="/register" className="underline ml-1">Sign&nbsp;up</Link>
        </p>
      </form>
    </div>
  );
}
