import React from 'react';
import { Typography, Button } from '@material-ui/core';
import { useNavigate } from 'react-router-dom';
import RoleList from '../../components/admin/RoleList';

function RoleManagementPage() {
  const navigate = useNavigate();

  return (
    <div style={{ padding: '20px' }}>
      <Typography variant="h4" gutterBottom>
        Role Management
      </Typography>
      <Button
        variant="contained"
        color="primary"
        onClick={() => navigate('/admin/roles/new')}
        style={{ marginBottom: '20px' }}
      >
        Create New Role
      </Button>
      <RoleList />
    </div>
  );
}

export default RoleManagementPage;