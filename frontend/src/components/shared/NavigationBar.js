import React from 'react';
import { AppBar, Toolbar, Typography, Button, Box } from '@mui/material';
import { Link } from 'react-router-dom';
import { useSelector, useDispatch } from 'react-redux';
import { logout } from '../../store/actions/authActions';

function NavigationBar() {
  const auth = useSelector((state) => state.auth) || {};
  const { isAuthenticated, user } = auth;
  const dispatch = useDispatch();

  const handleLogout = () => {
    dispatch(logout());
  };

  return (
    <AppBar position="static">
      <Toolbar>
        <Typography variant="h6" sx={{ flexGrow: 1 }}>
          User Management System
        </Typography>
        <Box sx={{ display: 'flex', gap: 1 }}>
          {isAuthenticated ? (
            <>
              <Button
                color="inherit"
                component={Link}
                to="/profile"
              >
                Profile
              </Button>
              {user?.roles?.includes('admin') && (
                <Button
                  color="inherit"
                  component={Link}
                  to="/admin/users"
                >
                  Admin Dashboard
                </Button>
              )}
              <Button
                color="inherit"
                onClick={handleLogout}
              >
                Logout
              </Button>
            </>
          ) : (
            <>
              <Button
                color="inherit"
                component={Link}
                to="/login"
              >
                Login
              </Button>
              <Button
                color="inherit"
                component={Link}
                to="/register"
              >
                Register
              </Button>
            </>
          )}
        </Box>
      </Toolbar>
    </AppBar>
  );
}

export default NavigationBar;