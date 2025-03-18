import React, { useEffect, useState } from 'react';
import { Table, TableBody, TableCell, TableHead, TableRow, Button } from '@material-ui/core';
import axios from 'axios';
import { toast } from 'react-toastify';
import { useNavigate } from 'react-router-dom';

function PermissionList() {
  const [permissions, setPermissions] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchPermissions = async () => {
      try {
        const response = await axios.get('/api/permissions');
        setPermissions(response.data);
      } catch (error) {
        toast.error('Failed to fetch permissions');
      }
    };
    fetchPermissions();
  }, []);

  return (
    <Table>
      <TableHead>
        <TableRow>
          <TableCell>ID</TableCell>
          <TableCell>Name</TableCell>
          <TableCell>Description</TableCell>
          <TableCell>Actions</TableCell>
        </TableRow>
      </TableHead>
      <TableBody>
        {permissions.map((permission) => (
          <TableRow key={permission.permission_id}>
            <TableCell>{permission.permission_id}</TableCell>
            <TableCell>{permission.name}</TableCell>
            <TableCell>{permission.description}</TableCell>
            <TableCell>
              <Button onClick={() => navigate(`/admin/permissions/${permission.permission_id}/edit`)}>
                Edit
              </Button>
            </TableCell>
          </TableRow>
        ))}
      </TableBody>
    </Table>
  );
}

export default PermissionList;