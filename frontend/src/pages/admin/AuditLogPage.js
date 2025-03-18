import React from 'react';
import { Typography } from '@mui/material';
import AuditLogList from '../../components/admin/AuditLogList';

function AuditLogPage() {
  return (
    <div style={{ padding: '20px' }}>
      <Typography variant="h4" gutterBottom>
        Audit Logs
      </Typography>
      <AuditLogList />
    </div>
  );
}

export default AuditLogPage;