import React, { useEffect, useState } from 'react';
import { useFormik } from 'formik';
import * as Yup from 'yup';
import { TextField, Button } from '@mui/material';
import axios from 'axios';
import { toast } from 'react-toastify';
import { useNavigate, useParams } from 'react-router-dom';

const validationSchema = Yup.object({
  name: Yup.string().required('Required'),
  description: Yup.string(),
});

function RoleEditForm() {
  const { roleId } = useParams();
  const [role, setRole] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchRole = async () => {
      try {
        const response = await axios.get(`/api/roles/${roleId}`);
        setRole(response.data);
      } catch (error) {
        toast.error('Failed to fetch role');
      }
    };
    fetchRole();
  }, [roleId]);

  const formik = useFormik({
    initialValues: role ? { name: role.name, description: role.description } : { name: '', description: '' },
    validationSchema,
    enableReinitialize: true, // Reinitialize form when role data loads
    onSubmit: async (values) => {
      try {
        await axios.put(`/api/roles/${roleId}`, values);
        toast.success('Role updated successfully');
        navigate('/admin/roles');
      } catch (error) {
        toast.error('Update failed');
      }
    },
  });

  if (!role) return <div>Loading...</div>;

  return (
    <form onSubmit={formik.handleSubmit} style={{ display: 'flex', flexDirection: 'column', gap: '16px' }}>
      <TextField
        name="name"
        label="Role Name"
        value={formik.values.name}
        onChange={formik.handleChange}
        onBlur={formik.handleBlur}
        error={formik.touched.name && Boolean(formik.errors.name)}
        helperText={formik.touched.name && formik.errors.name}
      />
      <TextField
        name="description"
        label="Description"
        value={formik.values.description}
        onChange={formik.handleChange}
        onBlur={formik.handleBlur}
        error={formik.touched.description && Boolean(formik.errors.description)}
        helperText={formik.touched.description && formik.errors.description}
      />
      <Button type="submit" variant="contained" color="primary">
        Save
      </Button>
    </form>
  );
}

export default RoleEditForm;