import React from 'react';
import { useFormik } from 'formik';
import * as Yup from 'yup';
import { TextField, Button } from '@material-ui/core';
import axios from 'axios';
import { toast } from 'react-toastify';

const validationSchema = Yup.object({
  email: Yup.string().email('Invalid email').required('Required'),
});

function PasswordResetRequestForm() {
  const formik = useFormik({
    initialValues: { email: '' },
    validationSchema,
    onSubmit: async (values) => {
      try {
        await axios.post('/api/password-reset/request', values);
        toast.success('Password reset link sent to your email');
      } catch (error) {
        toast.error(error.response?.data?.message || 'Request failed');
      }
    },
  });

  return (
    <form onSubmit={formik.handleSubmit} style={{ display: 'flex', flexDirection: 'column', gap: '16px' }}>
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
      <Button type="submit" variant="contained" color="primary">
        Request Reset
      </Button>
    </form>
  );
}

export default PasswordResetRequestForm;