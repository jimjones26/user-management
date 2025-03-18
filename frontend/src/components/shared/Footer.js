import React from 'react';
import { Typography } from '@mui/material';

function Footer() {
  return (
    <footer style={{ padding: '20px', textAlign: 'center', marginTop: 'auto' }}>
      <Typography variant="body2">
        © {new Date().getFullYear()} User Management System. All rights reserved.
      </Typography>
    </footer>
  );
}

export default Footer;