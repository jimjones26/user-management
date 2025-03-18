import React from 'react';
import { Typography, Box } from '@mui/material';
import MFAVerificationForm from '../../components/auth/MFAVerificationForm';

function MFAVerificationPage() {
  return (
    <Box sx={{ padding: 3, maxWidth: '400px', margin: '0 auto' }}>
      <Typography variant="h4" gutterBottom>
        Verify MFA
      </Typography>
      <MFAVerificationForm />
    </Box>
  );
}

export default MFAVerificationPage;