import React, { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { Table, TableBody, TableCell, TableHead, TableRow, Pagination, Typography } from '@material-ui/core';
import { fetchUsers } from '../../store/actions/userActions';

function UserListPage() {
  const dispatch = useDispatch();
  const { users, totalPages } = useSelector((state) => state.user);
  const [page, setPage] = useState(1);

  useEffect(() => {
    dispatch(fetchUsers(page));
  }, [dispatch, page]);

  const handlePageChange = (event, value) => {
    setPage(value);
  };

  return (
    <div style={{ padding: '20px' }}>
      <Typography variant="h4" gutterBottom>
        User List
      </Typography>
      <Table>
        <TableHead>
          <TableRow>
            <TableCell>ID</TableCell>
            <TableCell>Username</TableCell>
            <TableCell>Email</TableCell>
            <TableCell>Status</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {users.map((user) => (
            <TableRow key={user.user_id}>
              <TableCell>{user.user_id}</TableCell>
              <TableCell>{user.username}</TableCell>
              <TableCell>{user.email}</TableCell>
              <TableCell>{user.status}</TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
      <Pagination count={totalPages} page={page} onChange={handlePageChange} style={{ marginTop: '16px' }} />
    </div>
  );
}

export default UserListPage;