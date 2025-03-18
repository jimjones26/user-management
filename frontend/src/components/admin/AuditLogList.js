import React, { useEffect, useState } from 'react';
import { Table, TableBody, TableCell, TableHead, TableRow } from '@material-ui/core';
import axios from 'axios';
import { toast } from 'react-toastify';

function AuditLogList() {
  const [logs, setLogs] = useState([]);

  useEffect(() => {
    const fetchLogs = async () => {
      try {
        const response = await axios.get('/api/audit-logs');
        setLogs(response.data);
      } catch (error) {
        toast.error('Failed to fetch audit logs');
      }
    };
    fetchLogs();
  }, []);

  return (
    <Table>
      <TableHead>
        <TableRow>
          <TableCell>ID</TableCell>
          <TableCell>User ID</TableCell>
          <TableCell>Action</TableCell>
          <TableCell>Timestamp</TableCell>
          <TableCell>Details</TableCell>
        </TableRow>
      </TableHead>
      <TableBody>
        {logs.map((log) => (
          <TableRow key={log.log_id}>
            <TableCell>{log.log_id}</TableCell>
            <TableCell>{log.user_id}</TableCell>
            <TableCell>{log.action}</TableCell>
            <TableCell>{new Date(log.timestamp).toLocaleString()}</TableCell>
            <TableCell>{log.details}</TableCell>
          </TableRow>
        ))}
      </TableBody>
    </Table>
  );
}

export default AuditLogList;