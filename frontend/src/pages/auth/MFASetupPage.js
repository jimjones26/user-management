import React from 'react';
import { Typography, Box } from '@mui/material';
import MFASetupForm from '../../components/auth/MFASetupForm';

function MFASetupPage() {
  return (
    <Box sx={{ padding: 3, maxWidth: '400px', margin: '0 auto' }}>
      <Typography variant="h4" gutterBottom>
        Set Up MFA
      </Typography>
      <MFASetupForm />
    </Box>
  );
}

export default MFASetupPage;