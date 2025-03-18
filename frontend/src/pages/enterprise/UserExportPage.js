import React from 'react';
import { Typography, Button } from '@mui/material';
import axios from 'axios';
import { toast } from 'react-toastify';
import { saveAs } from 'file-saver';

function UserExportPage() {
  const handleExport = async () => {
    try {
      const response = await axios.get('/api/users/export', { responseType: 'blob' });
      const blob = new Blob([response.data], { type: 'text/csv' });
      saveAs(blob, 'users.csv');
      toast.success('Users exported successfully');
    } catch (error) {
      toast.error('Export failed');
    }
  };

  return (
    <div style={{ padding: '20px', maxWidth: '600px', margin: '0 auto' }}>
      <Typography variant="h4" gutterBottom>
        Export Users
      </Typography>
      <Button onClick={handleExport} variant="contained" color="primary">
        Export to CSV
      </Button>
    </div>
  );
}

export default UserExportPage;