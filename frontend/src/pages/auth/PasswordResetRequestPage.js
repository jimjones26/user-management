import React from 'react';
import { Typography } from '@material-ui/core';
import PasswordResetRequestForm from '../../components/auth/PasswordResetRequestForm';

function PasswordResetRequestPage() {
  return (
    <div style={{ padding: '20px', maxWidth: '400px', margin: '0 auto' }}>
      <Typography variant="h4" gutterBottom>
        Request Password Reset
      </Typography>
      <PasswordResetRequestForm />
    </div>
  );
}

export default PasswordResetRequestPage;