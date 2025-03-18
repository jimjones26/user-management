import React from 'react';
import { AppBar, Toolbar, Typography, Button } from '@mui/material';
import { Link } from 'react-router-dom';
import { useSelector, useDispatch } from 'react-redux';
import { logout } from '../../store/actions/authActions';

function NavigationBar() {
  const { isAuthenticated, user } = useSelector((state) => state.auth);
  const dispatch = useDispatch();

  return (
    <AppBar position="static">
      <Toolbar>
        <Typography variant="h6" style={{ flexGrow: 1 }}>
          User Management System
        </Typography>
        {isAuthenticated ? (
          <>
            <Button color="inherit" component={Link} to="/profile">
              Profile
            </Button>
            {user?.roles?.includes('admin') && (
              <Button color="inherit" component={Link} to="/admin/users">
                Admin Dashboard
              </Button>
            )}
            <Button color="inherit" onClick={() => dispatch(logout())}>
              Logout
            </Button>
          </>
        ) : (
          <>
            <Button color="inherit" component={Link} to="/login">
              Login
            </Button>
            <Button color="inherit" component={Link} to="/register">
              Register
            </Button>
          </>
        )}
      </Toolbar>
    </AppBar>
  );
}

export default NavigationBar;