import React from 'react';
import { Typography } from '@material-ui/core';
import RegistrationForm from '../../components/auth/RegistrationForm';

function RegistrationPage() {
  return (
    <div style={{ padding: '20px', maxWidth: '400px', margin: '0 auto' }}>
      <Typography variant="h4" gutterBottom>
        Register
      </Typography>
      <RegistrationForm />
    </div>
  );
}

export default RegistrationPage;