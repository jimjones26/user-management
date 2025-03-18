import React from 'react';
import { useFormik } from 'formik';
import * as Yup from 'yup';
import { TextField, Button } from '@mui/material';
import { useDispatch } from 'react-redux';
import { updateUser } from '../../store/actions/userActions';

const validationSchema = Yup.object({
  username: Yup.string().required('Required'),
  email: Yup.string().email('Invalid email').required('Required'),
});

function ProfileEditForm({ user, onSave }) {
  const dispatch = useDispatch();

  const formik = useFormik({
    initialValues: { username: user.username, email: user.email },
    validationSchema,
    onSubmit: async (values) => {
      try {
        await dispatch(updateUser(user.user_id, values));
        onSave();
      } catch (error) {
        // Error handled via toast
      }
    },
  });

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
      <Button type="submit" variant="contained" color="primary">
        Save
      </Button>
    </form>
  );
}

export default ProfileEditForm;