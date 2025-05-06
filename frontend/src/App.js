import React from 'react';
import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import { ThemeProvider, createTheme } from '@mui/material/styles';
import CssBaseline from '@mui/material/CssBaseline';
import { useSelector } from 'react-redux';

// Layouts
import MainLayout from './layouts/MainLayout';
import AuthLayout from './layouts/AuthLayout';

// Authentication Pages
import Login from './pages/auth/Login';
import Register from './pages/auth/Register';
import ForgotPassword from './pages/auth/ForgotPassword';

// Main Application Pages
import Dashboard from './pages/Dashboard';
import DonorList from './pages/donors/DonorList';
import DonorDetails from './pages/donors/DonorDetails';
import AddDonor from './pages/donors/AddDonor';
import BloodBankList from './pages/bloodBanks/BloodBankList';
import BloodBankDetails from './pages/bloodBanks/BloodBankDetails';
import AddBloodBank from './pages/bloodBanks/AddBloodBank';
import DonationList from './pages/donations/DonationList';
import DonationDetails from './pages/donations/DonationDetails';
import AddDonation from './pages/donations/AddDonation';
import InventoryList from './pages/inventory/InventoryList';
import AddInventory from './pages/inventory/AddInventory';
import BloodRequestList from './pages/requests/BloodRequestList';
import AddBloodRequest from './pages/requests/AddBloodRequest';
import RequestDetails from './pages/requests/RequestDetails';
import Reports from './pages/reports/Reports';
import UserProfile from './pages/profile/UserProfile';
import UserManagement from './pages/admin/UserManagement';
import Settings from './pages/settings/Settings';
import NotFound from './pages/NotFound';

// Create theme
const theme = createTheme({
  palette: {
    primary: {
      main: '#d32f2f',
      light: '#ef5350',
      dark: '#b71c1c',
    },
    secondary: {
      main: '#1976d2',
      light: '#42a5f5',
      dark: '#0d47a1',
    },
    background: {
      default: '#f5f5f5',
    },
  },
  typography: {
    fontFamily: '"Roboto", "Helvetica", "Arial", sans-serif',
    h5: {
      fontWeight: 600,
    },
    h6: {
      fontWeight: 500,
    },
  },
});

const App = () => {
  const { isLoggedIn, user } = useSelector((state) => state.auth);

  // Protected route component
  const ProtectedRoute = ({ children, allowedRoles = [] }) => {
    if (!isLoggedIn) {
      return <Navigate to="/login" replace />;
    }

    // If no specific roles are required or user has one of the required roles
    if (
      allowedRoles.length === 0 ||
      user.roles.some(role => allowedRoles.includes(role))
    ) {
      return children;
    }

    // If user doesn't have the required role, redirect to dashboard
    return <Navigate to="/dashboard" replace />;
  };

  return (
    <ThemeProvider theme={theme}>
      <CssBaseline />
      <BrowserRouter>
        <Routes>
          {/* Auth Routes */}
          <Route element={<AuthLayout />}>
            <Route path="/login" element={!isLoggedIn ? <Login /> : <Navigate to="/dashboard" />} />
            <Route path="/register" element={!isLoggedIn ? <Register /> : <Navigate to="/dashboard" />} />
            <Route path="/forgot-password" element={!isLoggedIn ? <ForgotPassword /> : <Navigate to="/dashboard" />} />
          </Route>

          {/* Main Application Routes */}
          <Route element={<MainLayout />}>
            <Route path="/" element={<Navigate to="/dashboard" replace />} />
            
            <Route path="/dashboard" element={
              <ProtectedRoute>
                <Dashboard />
              </ProtectedRoute>
            } />
            
            {/* Donor Routes */}
            <Route path="/donors" element={
              <ProtectedRoute>
                <DonorList />
              </ProtectedRoute>
            } />
            <Route path="/donors/add" element={
              <ProtectedRoute>
                <AddDonor />
              </ProtectedRoute>
            } />
            <Route path="/donors/:id" element={
              <ProtectedRoute>
                <DonorDetails />
              </ProtectedRoute>
            } />
            
            {/* Blood Bank Routes */}
            <Route path="/blood-banks" element={
              <ProtectedRoute>
                <BloodBankList />
              </ProtectedRoute>
            } />
            <Route path="/blood-banks/add" element={
              <ProtectedRoute allowedRoles={['ROLE_ADMIN', 'ROLE_BLOOD_BANK_ADMIN']}>
                <AddBloodBank />
              </ProtectedRoute>
            } />
            <Route path="/blood-banks/:id" element={
              <ProtectedRoute>
                <BloodBankDetails />
              </ProtectedRoute>
            } />
            
            {/* Donation Routes */}
            <Route path="/donations" element={
              <ProtectedRoute>
                <DonationList />
              </ProtectedRoute>
            } />
            <Route path="/donations/add" element={
              <ProtectedRoute>
                <AddDonation />
              </ProtectedRoute>
            } />
            <Route path="/donations/:id" element={
              <ProtectedRoute>
                <DonationDetails />
              </ProtectedRoute>
            } />
            
            {/* Inventory Routes */}
            <Route path="/inventory" element={
              <ProtectedRoute>
                <InventoryList />
              </ProtectedRoute>
            } />
            <Route path="/inventory/add" element={
              <ProtectedRoute allowedRoles={['ROLE_ADMIN', 'ROLE_BLOOD_BANK_ADMIN']}>
                <AddInventory />
              </ProtectedRoute>
            } />
            
            {/* Blood Request Routes */}
            <Route path="/requests" element={
              <ProtectedRoute>
                <BloodRequestList />
              </ProtectedRoute>
            } />
            <Route path="/requests/add" element={
              <ProtectedRoute>
                <AddBloodRequest />
              </ProtectedRoute>
            } />
            <Route path="/requests/:id" element={
              <ProtectedRoute>
                <RequestDetails />
              </ProtectedRoute>
            } />
            
            {/* Reports Route */}
            <Route path="/reports" element={
              <ProtectedRoute allowedRoles={['ROLE_ADMIN', 'ROLE_BLOOD_BANK_ADMIN']}>
                <Reports />
              </ProtectedRoute>
            } />
            
            {/* User Profile & Admin Routes */}
            <Route path="/profile" element={
              <ProtectedRoute>
                <UserProfile />
              </ProtectedRoute>
            } />
            <Route path="/users" element={
              <ProtectedRoute allowedRoles={['ROLE_ADMIN']}>
                <UserManagement />
              </ProtectedRoute>
            } />
            <Route path="/settings" element={
              <ProtectedRoute>
                <Settings />
              </ProtectedRoute>
            } />
            
            {/* 404 Page */}
            <Route path="*" element={<NotFound />} />
          </Route>
        </Routes>
      </BrowserRouter>
    </ThemeProvider>
  );
};

export default App;