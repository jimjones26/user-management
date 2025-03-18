import React from 'react';
import { Typography, Button, Box } from '@mui/material';
import axios from 'axios';
import { toast } from 'react-toastify';
import { saveAs } from 'file-saver';

function UserExportPage() {
  const handleExport = async () => {
    try {
      const response = await axios.get('/api/users/export', {
        responseType: 'blob',
        headers: {
          'Content-Type': 'text/csv',
        }
      });

      const blob = new Blob([response.data], { type: 'text/csv;charset=utf-8' });
      saveAs(blob, `users_export_${new Date().toISOString().split('T')[0]}.csv`);
      toast.success('Users exported successfully');
    } catch (error) {
      console.error('Export failed:', error);
      toast.error(error.response?.data?.message || 'Failed to export users');
    }
  };

  return (
    <Box sx={{
      padding: 3,
      maxWidth: '600px',
      margin: '0 auto',
      display: 'flex',
      flexDirection: 'column',
      gap: 2
    }}>
      <Typography variant="h4" gutterBottom>
        Export Users
      </Typography>
      <Typography variant="body1" gutterBottom>
        Download a CSV file containing all user data.
      </Typography>
      <Button
        onClick={handleExport}
        variant="contained"
        sx={{ alignSelf: 'flex-start' }}
      >
        Export to CSV
      </Button>
    </Box>
  );
}

export default UserExportPage;