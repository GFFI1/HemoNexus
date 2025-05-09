import { useAuth } from '@/context/AuthContext';
import { Button } from '@/components/ui/button'; // Adjust the path based on your project structure
import { Outlet } from 'react-router-dom';

export default function DonorLayout(){
  const { logout } = useAuth();
  return (
    <div className="min-h-screen bg-neutral-100 p-4">
      <header className="flex justify-end mb-4">
        <Button onClick={logout}>Logout</Button>
      </header>
      <Outlet/>
    </div>
  );
}
