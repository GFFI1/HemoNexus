import React, { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { 
  Container, Grid, Paper, Typography, Box, 
  Card, CardContent, CardHeader, Button,
  CircularProgress
} from '@mui/material';
import { 
  PeopleOutline as PeopleIcon,
  Bloodtype as BloodIcon, 
  LocalHospital as HospitalIcon,
  Assessment as AssessmentIcon
} from '@mui/icons-material';
import { Link } from 'react-router-dom';
import { Bar } from 'react-chartjs-2';
import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  BarElement,
  Title,
  Tooltip,
  Legend,
} from 'chart.js';

import { getDashboardStats } from '../features/dashboard/dashboardService';
import DashboardCard from '../components/dashboard/DashboardCard';

// Register chart components
ChartJS.register(
  CategoryScale,
  LinearScale,
  BarElement,
  Title,
  Tooltip,
  Legend
);

const Dashboard = () => {
  const [stats, setStats] = React.useState({
    totalDonors: 0,
    totalDonations: 0,
    totalBloodBanks: 0,
    pendingRequests: 0,
    bloodInventory: {},
    recentDonations: [],
    donationsByMonth: {}
  });
  const [loading, setLoading] = React.useState(true);
  const { user } = useSelector((state) => state.auth);

  useEffect(() => {
    const fetchDashboardData = async () => {
      try {
        const data = await getDashboardStats();
        setStats(data);
      } catch (error) {
        console.error('Failed to fetch dashboard data:', error);
      } finally {
        setLoading(false);
      }
    };

    fetchDashboardData();
  }, []);

  // Chart data preparation for blood inventory
  const bloodInventoryData = {
    labels: Object.keys(stats.bloodInventory),
    datasets: [
      {
        label: 'Available Units',
        data: Object.values(stats.bloodInventory),
        backgroundColor: [
          'rgba(255, 99, 132, 0.6)',
          'rgba(54, 162, 235, 0.6)',
          'rgba(255, 206, 86, 0.6)',
          'rgba(75, 192, 192, 0.6)',
          'rgba(153, 102, 255, 0.6)',
          'rgba(255, 159, 64, 0.6)',
          'rgba(199, 199, 199, 0.6)',
          'rgba(83, 102, 255, 0.6)',
        ],
        borderColor: [
          'rgba(255, 99, 132, 1)',
          'rgba(54, 162, 235, 1)',
          'rgba(255, 206, 86, 1)',
          'rgba(75, 192, 192, 1)',
          'rgba(153, 102, 255, 1)',
          'rgba(255, 159, 64, 1)',
          'rgba(199, 199, 199, 1)',
          'rgba(83, 102, 255, 1)',
        ],
        borderWidth: 1,
      },
    ],
  };

  if (loading) {
    return (
      <Box sx={{ display: 'flex', justifyContent: 'center', mt: 10 }}>
        <CircularProgress />
      </Box>
    );
  }

  return (
    <Container maxWidth="xl" sx={{ mt: 4, mb: 4 }}>
      <Typography variant="h4" gutterBottom sx={{ fontWeight: 'bold', color: 'primary.main' }}>
        Welcome back, {user?.username || 'User'}
      </Typography>
      
      <Grid container spacing={3}>
        {/* Summary Cards */}
        <Grid item xs={12} sm={6} md={3}>
          <DashboardCard 
            title="Total Donors"
            value={stats.totalDonors}
            icon={<PeopleIcon fontSize="large" />}
            linkTo="/donors"
            color="#d32f2f"
          />
        </Grid>
        <Grid item xs={12} sm={6} md={3}>
          <DashboardCard 
            title="Total Donations"
            value={stats.totalDonations}
            icon={<BloodIcon fontSize="large" />}
            linkTo="/donations"
            color="#1976d2"
          />
        </Grid>
        <Grid item xs={12} sm={6} md={3}>
          <DashboardCard 
            title="Blood Banks"
            value={stats.totalBloodBanks}
            icon={<HospitalIcon fontSize="large" />}
            linkTo="/blood-banks"
            color="#388e3c"
          />
        </Grid>
        <Grid item xs={12} sm={6} md={3}>
          <DashboardCard 
            title="Pending Requests"
            value={stats.pendingRequests}
            icon={<AssessmentIcon fontSize="large" />}
            linkTo="/requests"
            color="#f57c00"
          />
        </Grid>
        
        {/* Blood Inventory Chart */}
        <Grid item xs={12} md={6}>
          <Paper
            sx={{
              p: 2,
              display: 'flex',
              flexDirection: 'column',
              height: 350,
            }}
          >
            <Typography component="h2" variant="h6" color="primary" gutterBottom>
              Blood Inventory
            </Typography>
            <Box sx={{ height: '100%', width: '100%' }}>
              <Bar
                data={bloodInventoryData}
                options={{
                  responsive: true,
                  maintainAspectRatio: false,
                  plugins: {
                    legend: {
                      position: 'top',
                    },
                    title: {
                      display: false,
                    },
                  },
                }}
              />
            </Box>
          </Paper>
        </Grid>
        
        {/* Recent Donations */}
        <Grid item xs={12} md={6}>
          <Paper
            sx={{
              p: 2,
              display: 'flex',
              flexDirection: 'column',
              height: 350,
              overflowY: 'auto',
            }}
          >
            <Box sx={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', mb: 2 }}>
              <Typography component="h2" variant="h6" color="primary">
                Recent Donations
              </Typography>
              <Button component={Link} to="/donations" color="primary">
                View All
              </Button>
            </Box>
            {stats.recentDonations.length > 0 ? (
              stats.recentDonations.map((donation) => (
                <Card key={donation.id} sx={{ mb: 2 }}>
                  <CardContent sx={{ py: 1 }}>
                    <Grid container spacing={2}>
                      <Grid item xs={8}>
                        <Typography variant="subtitle1" sx={{ fontWeight: 'bold' }}>
                          {donation.donorName}
                        </Typography>
                        <Typography variant="body2" color="text.secondary">
                          Blood Type: {donation.bloodType} • {donation.quantity} ml
                        </Typography>
                      </Grid>
                      <Grid item xs={4} sx={{ textAlign: 'right' }}>
                        <Typography variant="body2" color="text.secondary">
                          {new Date(donation.donationDate).toLocaleDateString()}
                        </Typography>
                        <Box sx={{ 
                          display: 'inline-block', 
                          px: 1, 
                          borderRadius: 1, 
                          backgroundColor: 
                            donation.status === 'COMPLETED' ? 'success.light' : 
                            donation.status === 'PENDING' ? 'warning.light' : 'info.light' 
                        }}>
                          <Typography variant="caption">
                            {donation.status}
                          </Typography>
                        </Box>
                      </Grid>
                    </Grid>
                  </CardContent>
                </Card>
              ))
            ) : (
              <Box sx={{ display: 'flex', justifyContent: 'center', alignItems: 'center', height: '80%' }}>
                <Typography variant="body1" color="text.secondary">
                  No recent donations
                </Typography>
              </Box>
            )}
          </Paper>
        </Grid>
      </Grid>
    </Container>
  );
};

export default Dashboard;