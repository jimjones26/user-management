import React from 'react';
import { Typography } from '@material-ui/core';
import MFASetupForm from '../../components/auth/MFASetupForm';

function MFASetupPage() {
  return (
    <div style={{ padding: '20px', maxWidth: '400px', margin: '0 auto' }}>
      <Typography variant="h4" gutterBottom>
        Set Up MFA
      </Typography>
      <MFASetupForm />
    </div>
  );
}

export default MFASetupPage;