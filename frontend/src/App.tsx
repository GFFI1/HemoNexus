import { BrowserRouter, Routes, Route } from "react-router-dom";
import { AuthProvider } from "@/context/AuthContext";
import RequireAuth from "@/components/RequireAuth";
import Login from "@/pages/Login";
import Register from "@/pages/Register";
import AppLayout from "@/layouts/AppLayout";
import AdminDashboard from "@/pages/admin/AdminDashboard";
import NotFound from "@/pages/NotFound"; // Create a NotFound component
import RequestList   from "@/pages/admin/RequestList";
import NewRequest    from "@/pages/requester/NewRequest";

export default function App() {
  return (
    <AuthProvider>
      <BrowserRouter>
        <Routes>
          <Route path="/login"    element={<Login/>}/>
          <Route path="/register" element={<Register/>}/>
          {/* Protected routes */}
          <Route element={<RequireAuth/>}>
            <Route element={<AppLayout/>}>
              <Route index element={<AdminDashboard/>}/>
              {/* add InventoryList, DonorList pages here later */}
            </Route>
          </Route>
          {/* Fallback route for 404 */}
          <Route path="*" element={<NotFound />} />
          <Route path="requests" element={<RequestList/>}/>
          <Route path="new-request" element={<NewRequest/>}/>
        </Routes>
      </BrowserRouter>
    </AuthProvider>
  );
}
