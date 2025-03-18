import React from 'react';
import { Typography } from '@mui/material';
import RoleEditForm from '../../components/admin/RoleEditForm';

function RoleEditPage() {
  return (
    <div style={{ padding: '20px', maxWidth: '600px', margin: '0 auto' }}>
      <Typography variant="h4" gutterBottom>
        Edit Role
      </Typography>
      <RoleEditForm />
    </div>
  );
}

export default RoleEditPage;