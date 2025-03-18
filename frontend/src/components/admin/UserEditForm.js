import React, { useEffect, useState } from 'react';
import { useFormik } from 'formik';
import * as Yup from 'yup';
import { TextField, Button, Select, MenuItem, FormControl, InputLabel } from '@mui/material';
import axios from 'axios';
import { toast } from 'react-toastify';
import { useNavigate } from 'react-router-dom';

const validationSchema = Yup.object({
  username: Yup.string().required('Required'),
  email: Yup.string().email('Invalid email').required('Required'),
  status: Yup.string().oneOf(['active', 'inactive', 'deleted']).required('Required'),
});

function UserEditForm({ userId }) {
  const [user, setUser] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchUser = async () => {
      try {
        const response = await axios.get(`/api/users/${userId}`);
        setUser(response.data);
      } catch (error) {
        toast.error('Failed to fetch user');
      }
    };
    fetchUser();
  }, [userId]);

  const formik = useFormik({
    initialValues: user ? { username: user.username, email: user.email, status: user.status } : { username: '', email: '', status: '' },
    validationSchema,
    enableReinitialize: true,
    onSubmit: async (values) => {
      try {
        await axios.put(`/api/users/${userId}`, values);
        toast.success('User updated successfully');
        navigate(`/admin/users/${userId}`);
      } catch (error) {
        toast.error('Update failed');
      }
    },
  });

  if (!user) return <Typography>Loading...</Typography>;

  return (
    <form onSubmit={formik.handleSubmit} style={{ display: 'flex', flexDirection: 'column', gap: '16px' }}>
      <TextField
        name="username"
        label="Username"
        value={formik.values.username}
        onChange={formik.handleChange}
        onBlur={formik.handleBlur}
        error={formik.touched.username && Boolean(formik.errors.username)}
        helperText={formik.touched.username && formik.errors.username}
      />
      <TextField
        name="email"
        label="Email"
        type="email"
        value={formik.values.email}
        onChange={formik.handleChange}
        onBlur={formik.handleBlur}
        error={formik.touched.email && Boolean(formik.errors.email)}
        helperText={formik.touched.email && formik.errors.email}
      />
      <FormControl>
        <InputLabel>Status</InputLabel>
        <Select
          name="status"
          value={formik.values.status}
          onChange={formik.handleChange}
        >
          <MenuItem value="active">Active</MenuItem>
          <MenuItem value="inactive">Inactive</MenuItem>
          <MenuItem value="deleted">Deleted</MenuItem>
        </Select>
      </FormControl>
      <Button type="submit" variant="contained" color="primary">
        Save
      </Button>
    </form>
  );
}

export default UserEditForm;