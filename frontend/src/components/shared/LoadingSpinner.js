import React from 'react';
import { CircularProgress } from '@mui/material';

function LoadingSpinner() {
  return (
    <div style={{ display: 'flex', justifyContent: 'center', padding: '20px' }}>
      <CircularProgress />
    </div>
  );
}

export default LoadingSpinner;