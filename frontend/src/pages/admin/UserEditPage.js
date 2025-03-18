import React from 'react';
import { Typography } from '@material-ui/core';
import UserEditForm from '../../components/admin/UserEditForm';
import { useParams } from 'react-router-dom';

function UserEditPage() {
  const { userId } = useParams();

  return (
    <div style={{ padding: '20px', maxWidth: '600px', margin: '0 auto' }}>
      <Typography variant="h4" gutterBottom>
        Edit User
      </Typography>
      <UserEditForm userId={userId} />
    </div>
  );
}

export default UserEditPage;