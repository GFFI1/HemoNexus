import React from 'react';
import { Outlet } from 'react-router-dom';
import { Box, Container, Paper, Typography, Link, Grid } from '@mui/material';
import { Bloodtype as BloodtypeIcon } from '@mui/icons-material';
import { useSelector, useDispatch } from 'react-redux';
import { closeAlert } from '../features/alerts/alertSlice';
import Alert from '@mui/material/Alert';
import Snackbar from '@mui/material/Snackbar';

const AuthLayout = () => {
  const { message, type, isOpen } = useSelector((state) => state.alerts);
  const dispatch = useDispatch();

  const handleAlertClose = (event, reason) => {
    if (reason === 'clickaway') {
      return;
    }
    dispatch(closeAlert());
  };

  return (
    <Box 
      sx={{ 
        minHeight: '100vh', 
        display: 'flex', 
        flexDirection: 'column',
        bgcolor: 'background.default' 
      }}
    >
      <Container component="main" maxWidth="xs" sx={{ mt: 8, mb: 2 }}>
        <Paper 
          elevation={3} 
          sx={{ 
            p: 4, 
            display: 'flex', 
            flexDirection: 'column', 
            alignItems: 'center' 
          }}
        >
          <BloodtypeIcon color="primary" sx={{ fontSize: 40, mb: 1 }} />
          <Typography component="h1" variant="h5" sx={{ mb: 3 }}>
            HemoNexus
          </Typography>
          <Outlet />
        </Paper>
      </Container>
      
      <Box 
        component="footer" 
        sx={{ 
          py: 3, 
          px: 2, 
          mt: 'auto', 
          backgroundColor: 'background.paper' 
        }}
      >
        <Container maxWidth="sm">
          <Grid container spacing={2} justifyContent="center">
            <Grid item>
              <Link href="#" variant="body2">
                Privacy Policy
              </Link>
            </Grid>
            <Grid item>
              <Link href="#" variant="body2">
                Terms of Service
              </Link>
            </Grid>
            <Grid item>
              <Link href="#" variant="body2">
                Contact Support
              </Link>
            </Grid>
          </Grid>
          <Typography variant="body2" color="text.secondary" align="center" sx={{ mt: 2 }}>
            {'© '}
            {new Date().getFullYear()}{' '}
            HemoNexus. All rights reserved.
          </Typography>
        </Container>
      </Box>
      
      <Snackbar 
        open={isOpen} 
        autoHideDuration={6000} 
        onClose={handleAlertClose}
        anchorOrigin={{ vertical: 'bottom', horizontal: 'right' }}
      >
        <Alert onClose={handleAlertClose} severity={type} sx={{ width: '100%' }}>
          {message}
        </Alert>
      </Snackbar>
    </Box>
  );
};

export default AuthLayout;