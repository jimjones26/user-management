import React from 'react';
import { Typography } from '@material-ui/core';
import UserImportForm from '../../components/enterprise/UserImportForm';

function UserImportPage() {
  return (
    <div style={{ padding: '20px', maxWidth: '600px', margin: '0 auto' }}>
      <Typography variant="h4" gutterBottom>
        Import Users
      </Typography>
      <UserImportForm />
    </div>
  );
}

export default UserImportPage;