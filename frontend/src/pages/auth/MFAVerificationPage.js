import React from 'react';
import { Typography } from '@material-ui/core';
import MFAVerificationForm from '../../components/auth/MFAVerificationForm';

function MFAVerificationPage() {
  return (
    <div style={{ padding: '20px', maxWidth: '400px', margin: '0 auto' }}>
      <Typography variant="h4" gutterBottom>
        Verify MFA
      </Typography>
      <MFAVerificationForm />
    </div>
  );
}

export default MFAVerificationPage;