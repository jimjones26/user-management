import React from 'react';
import { Typography } from '@material-ui/core';
import LoginForm from '../../components/auth/LoginForm';

function LoginPage() {
  return (
    <div style={{ padding: '20px', maxWidth: '400px', margin: '0 auto' }}>
      <Typography variant="h4" gutterBottom>
        Login
      </Typography>
      <LoginForm />
    </div>
  );
}

export default LoginPage;