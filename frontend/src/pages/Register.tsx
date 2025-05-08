/* ───────────────── src/pages/Register.tsx ───────────────── */

import { useForm } from "react-hook-form";
import { yupResolver } from "@hookform/resolvers/yup";
import * as yup from "yup";
import api from "@/api";
import { Button } from "@/components/ui/button";
import { Link, useNavigate } from "react-router-dom";
import { useState } from "react";

/* ---------- validation schema ----------------------------------------- */
const schema = yup.object({
  username: yup.string().required("Username is required"),
  email:    yup.string().email("Invalid email").required("Email is required"),
  password: yup.string().min(8, "Min 8 characters").required(),
  confirm:  yup.string()
              .oneOf([yup.ref("password")], "Passwords do not match")
              .required("Confirm your password")
}).required();

/* ---------- component -------------------------------------------------- */
type FormInputs = yup.InferType<typeof schema>;

export default function Register() {
  const nav = useNavigate();
  const [error, setError] = useState<string>();

  const { register, handleSubmit, formState:{errors, isSubmitting} } =
    useForm<FormInputs>({ resolver: yupResolver(schema) });

  const onSubmit = async (data:FormInputs) => {
    setError(undefined);
    try {
      await api.post("/auth/signup", {
        username: data.username,
        email:    data.email,
        password: data.password,
        roles:    ["user"]          // default role; adjust if you allow selection
      });
      nav("/login?registered=1");   // redirect to login with flag
    } catch (e:any) {
      const msg = e.response?.data?.message ?? "Registration failed";
      setError(msg);
    }
  };

  return (
    <div className="h-full grid place-items-center bg-neutral-100">
      <form onSubmit={handleSubmit(onSubmit)}
            className="bg-white p-8 rounded-xl w-96 shadow space-y-4">
        <h1 className="text-2xl font-semibold text-center">Create account</h1>

        {/* USERNAME ----------------------------------------------------- */}
        <div>
          <input
            {...register("username")}
            placeholder="Username"
            className="input"
          />
          {errors.username && (
            <p className="text-red-600 text-sm mt-1">{errors.username.message}</p>
          )}
        </div>

        {/* EMAIL -------------------------------------------------------- */}
        <div>
          <input
            {...register("email")}
            placeholder="Email"
            className="input"
          />
          {errors.email && (
            <p className="text-red-600 text-sm mt-1">{errors.email.message}</p>
          )}
        </div>

        {/* PASSWORD ----------------------------------------------------- */}
        <div>
          <input
            type="password"
            {...register("password")}
            placeholder="Password"
            className="input"
          />
          {errors.password && (
            <p className="text-red-600 text-sm mt-1">{errors.password.message}</p>
          )}
        </div>

        {/* CONFIRM ------------------------------------------------------ */}
        <div>
          <input
            type="password"
            {...register("confirm")}
            placeholder="Confirm password"
            className="input"
          />
          {errors.confirm && (
            <p className="text-red-600 text-sm mt-1">{errors.confirm.message}</p>
          )}
        </div>

        {/* ERROR BANNER ------------------------------------------------- */}
        {error && (
          <p className="text-red-600 text-sm text-center">{error}</p>
        )}

        {/* SUBMIT ------------------------------------------------------- */}
        <Button disabled={isSubmitting} className="w-full">
          {isSubmitting ? "Creating..." : "Register"}
        </Button>

        {/* FOOTER LINK -------------------------------------------------- */}
        <p className="text-xs text-center">
          Already have an account?{" "}
          <Link to="/login" className="underline">Log in</Link>
        </p>
      </form>
    </div>
  );
}

/* ---------- simple Tailwind input style ------------------------------- */
/* (Add once to src/index.css if you don’t already have an .input class)  */
/*
.input {
  @apply w-full rounded-md border px-3 py-2 text-sm outline-none
          focus:ring-2 focus:ring-neutral-600;
}
*/
