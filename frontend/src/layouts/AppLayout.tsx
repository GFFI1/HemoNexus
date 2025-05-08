import { Outlet, Link, useNavigate } from "react-router-dom";
import { useAuth } from "@/context/AuthContext";

export default function AppLayout() {
  const nav = useNavigate();
  const { dispatch } = useAuth();

  const logout = () => {
    dispatch({ type:"LOGOUT" });
    nav("/login");
  };

  return (
    <div className="min-h-full grid grid-cols-[16rem_1fr]">
      <aside className="bg-neutral-900 text-white p-4 space-y-4">
        <h1 className="text-xl font-bold">HemoNexus</h1>
        <nav className="space-y-2">
          <Link to="/"        className="block hover:underline">Dashboard</Link>
          <Link to="/donors"  className="block hover:underline">Donors</Link>
          <Link to="/banks"   className="block hover:underline">Blood Banks</Link>
          <Link to="/inventory" className="block hover:underline">Inventory</Link>
        </nav>
        <button onClick={logout} className="mt-6 text-sm underline">Logout</button>
      </aside>
      <main className="bg-neutral-50 p-6">
        <Outlet/>
      </main>
    </div>
  );
}
