import { useForm } from "react-hook-form";
import api from "@/api";
import { useAuth } from "@/context/AuthContext";
import { useNavigate, Link } from "react-router-dom";
import { Button } from "@/components/ui/button";

export default function Login() {
  const { register, handleSubmit, formState:{errors} } =
    useForm<{username:string;password:string}>();
  const { dispatch } = useAuth();
  const nav = useNavigate();

  const submit = (d:{username:string;password:string}) =>
    api.post("/auth/signin", d).then(r => {
      dispatch({ type:"LOGIN",
        payload:{ user:r.data.username, token:r.data.token, roles:r.data.roles }});
      nav("/");
    }).catch(() => alert("Invalid credentials"));

  return (
    <div className="h-full grid place-items-center bg-neutral-100">
      <form onSubmit={handleSubmit(submit)}
            className="bg-white p-8 rounded-xl w-80 space-y-4 shadow">
        <h1 className="text-xl font-semibold text-center">Login</h1>
        <input {...register("username",{required:true})}
               placeholder="Username" className="input"/>
        {errors.username && <p className="text-red-600 text-sm">Required</p>}
        <input {...register("password",{required:true})}
               type="password" placeholder="Password" className="input"/>
        {errors.password && <p className="text-red-600 text-sm">Required</p>}
        <Button className="w-full">Sign in</Button>
        <p className="text-xs text-center">
          No account? <Link to="/register" className="underline">Sign up</Link>
        </p>
      </form>
    </div>
  );
}
