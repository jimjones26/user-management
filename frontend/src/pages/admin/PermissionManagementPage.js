import React from 'react';
import { Typography } from '@mui/material';
import PermissionList from '../../components/admin/PermissionList';

function PermissionManagementPage() {
  return (
    <div style={{ padding: '20px' }}>
      <Typography variant="h4" gutterBottom>
        Permission Management
      </Typography>
      <PermissionList />
    </div>
  );
}

export default PermissionManagementPage;