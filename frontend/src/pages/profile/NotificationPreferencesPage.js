import React from 'react';
import { Typography, Box } from '@mui/material';
import NotificationPreferencesForm from '../../components/profile/NotificationPreferencesForm';

function NotificationPreferencesPage() {
  return (
    <Box sx={{ padding: 3, maxWidth: '600px', margin: '0 auto' }}>
      <Typography variant="h4" gutterBottom>
        Notification Preferences
      </Typography>
      <NotificationPreferencesForm />
    </Box>
  );
}

export default NotificationPreferencesPage;