import React from 'react';
import { CircularProgress } from '@material-ui/core';

function LoadingSpinner() {
  return (
    <div style={{ display: 'flex', justifyContent: 'center', padding: '20px' }}>
      <CircularProgress />
    </div>
  );
}

export default LoadingSpinner;