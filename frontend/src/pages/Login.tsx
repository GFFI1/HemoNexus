import React from 'react';
import { useForm, SubmitHandler } from 'react-hook-form';
import { useNavigate, Link }      from 'react-router-dom';

import { useAuth }   from '@/context/AuthContext';
import { routeFor }  from '@/utils/rolePaths';
import { Button }    from '@/components/ui/button';   // swap to <button> if you don’t use shadcn

type LoginData = { username: string; password: string };

const Login: React.FC = () => {
  const { register, handleSubmit, formState: { errors } } = useForm<LoginData>();
  const { login }  = useAuth();
  const navigate   = useNavigate();

  const onSubmit: SubmitHandler<LoginData> = async (form) => {
    try {
      const role = await login(form.username, form.password);  // role comes back here
      navigate(routeFor[role]);
    } catch {
      alert('Invalid credentials');
    }
  };

  return (
    <div className="h-screen grid place-items-center bg-neutral-100">
      <form
        onSubmit={handleSubmit(onSubmit)}
        className="bg-white p-8 rounded-xl w-80 space-y-4 shadow">

        <h1 className="text-xl font-semibold text-center">Login</h1>

        <input
          {...register('username', { required: true })}
          placeholder="Username"
          className="border rounded w-full p-2"
        />
        {errors.username && <p className="text-red-600 text-sm">Username required</p>}

        <input
          {...register('password', { required: true })}
          type="password"
          placeholder="Password"
          className="border rounded w-full p-2"
        />
        {errors.password && <p className="text-red-600 text-sm">Password required</p>}

        <Button type="submit" className="w-full">Sign&nbsp;in</Button>

        <p className="text-xs text-center">
          No account?
          <Link to="/register" className="underline ml-1">Sign&nbsp;up</Link>
        </p>
      </form>
    </div>
  );
};

export default Login;
