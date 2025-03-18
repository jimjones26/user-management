import React, { useState } from 'react';
import { useFormik } from 'formik';
import * as Yup from 'yup';
import { TextField, Button, CircularProgress } from '@mui/material';
import { useDispatch } from 'react-redux';
import { loginUser } from '../../store/actions/authActions';
import { useNavigate } from 'react-router-dom';

const validationSchema = Yup.object({
  username: Yup.string().required('Username is required'),
  password: Yup.string().required('Password is required'),
});

function LoginForm() {
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const [isLoading, setIsLoading] = useState(false);

  const formik = useFormik({
    initialValues: { username: '', password: '' },
    validationSchema,
    onSubmit: async (values) => {
      console.log('Login attempt with username:', values.username); // Debug log
      setIsLoading(true);

      try {
        const result = await dispatch(loginUser(values));
        console.log('Login response:', result); // Debug log

        if (result?.type === 'LOGIN_SUCCESS') {
          navigate('/profile');
        }
      } catch (error) {
        console.error('Login error:', error); // Debug log
      } finally {
        setIsLoading(false);
      }
    },
  });

  const handleSubmit = (e) => {
    e.preventDefault();
    console.log('Form submitted'); // Debug log
    formik.handleSubmit(e);
  };

  return (
    <form
      onSubmit={handleSubmit}
      style={{
        display: 'flex',
        flexDirection: 'column',
        gap: '16px',
        maxWidth: '400px',
        margin: '0 auto',
        padding: '20px'
      }}
    >
      <TextField
        fullWidth
        name="username"
        label="Username"
        value={formik.values.username}
        onChange={formik.handleChange}
        onBlur={formik.handleBlur}
        error={formik.touched.username && Boolean(formik.errors.username)}
        helperText={formik.touched.username && formik.errors.username}
        disabled={isLoading}
      />
      <TextField
        fullWidth
        name="password"
        label="Password"
        type="password"
        value={formik.values.password}
        onChange={formik.handleChange}
        onBlur={formik.handleBlur}
        error={formik.touched.password && Boolean(formik.errors.password)}
        helperText={formik.touched.password && formik.errors.password}
        disabled={isLoading}
      />
      <Button
        type="submit"
        variant="contained"
        color="primary"
        disabled={isLoading || !formik.isValid}
        startIcon={isLoading ? <CircularProgress size={20} color="inherit" /> : null}
      >
        {isLoading ? 'Logging in...' : 'Login'}
      </Button>
    </form>
  );
}

export default LoginForm;