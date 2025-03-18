import React from 'react';
import { Typography } from '@mui/material';
import PasswordResetForm from '../../components/auth/PasswordResetForm';
import { useSearchParams } from 'react-router-dom';

function PasswordResetPage() {
  const [searchParams] = useSearchParams();
  const token = searchParams.get('token');

  return (
    <div style={{ padding: '20px', maxWidth: '400px', margin: '0 auto' }}>
      <Typography variant="h4" gutterBottom>
        Reset Password
      </Typography>
      <PasswordResetForm token={token} />
    </div>
  );
}

export default PasswordResetPage;