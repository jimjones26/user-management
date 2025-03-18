import React from 'react';
import { Typography } from '@material-ui/core';
import NotificationPreferencesForm from '../../components/profile/NotificationPreferencesForm';

function NotificationPreferencesPage() {
  return (
    <div style={{ padding: '20px', maxWidth: '600px', margin: '0 auto' }}>
      <Typography variant="h4" gutterBottom>
        Notification Preferences
      </Typography>
      <NotificationPreferencesForm />
    </div>
  );
}

export default NotificationPreferencesPage;