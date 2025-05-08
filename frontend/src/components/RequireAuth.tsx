import { Navigate, Outlet } from "react-router-dom";
import { useAuth } from "@/context/AuthContext";

export default function RequireAuth() {
  const { state } = useAuth();
  return state.token ? <Outlet/> : <Navigate to="/login" replace/>;
}
