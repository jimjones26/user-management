import React from 'react';
import { Typography } from '@material-ui/core';
import RoleList from '../../components/admin/RoleList';

function RoleManagementPage() {
  return (
    <div style={{ padding: '20px' }}>
      <Typography variant="h4" gutterBottom>
        Role Management
      </Typography>
      <RoleList />
    </div>
  );
}

export default RoleManagementPage;