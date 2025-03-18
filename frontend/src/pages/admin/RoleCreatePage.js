import React from 'react';
import { Typography } from '@mui/material';
import RoleCreateForm from '../../components/admin/RoleCreateForm';

function RoleCreatePage() {
  return (
    <div style={{ padding: '20px', maxWidth: '600px', margin: '0 auto' }}>
      <Typography variant="h4" gutterBottom>
        Create Role
      </Typography>
      <RoleCreateForm />
    </div>
  );
}

export default RoleCreatePage;