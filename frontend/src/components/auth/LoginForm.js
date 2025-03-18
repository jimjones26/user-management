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
    initialValues: {
      username: '',
      password: ''
    },
    validationSchema,
    onSubmit: async (values) => {
      setIsLoading(true);
      try {
        // Dispatch returns a Promise when using redux-thunk
        const result = await dispatch(loginUser(values));
        if (result?.type === 'LOGIN_SUCCESS') {
          navigate('/profile');
        }
      } catch (error) {
        console.error('Login error:', error);
      } finally {
        setIsLoading(false);
      }
    }
  });

  return (
    <form
      onSubmit={formik.handleSubmit}
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
        id="username"
        name="username"
        label="Username"
        value={formik.values.username}
        onChange={formik.handleChange}
        onBlur={formik.handleBlur}
        error={formik.touched.username && Boolean(formik.errors.username)}
        helperText={formik.touched.username && formik.errors.username}
        disabled={isLoading}
        margin="normal"
      />
      <TextField
        fullWidth
        id="password"
        name="password"
        label="Password"
        type="password"
        value={formik.values.password}
        onChange={formik.handleChange}
        onBlur={formik.handleBlur}
        error={formik.touched.password && Boolean(formik.errors.password)}
        helperText={formik.touched.password && formik.errors.password}
        disabled={isLoading}
        margin="normal"
      />
      <Button
        fullWidth
        type="submit"
        variant="contained"
        color="primary"
        disabled={isLoading || !formik.isValid}
        sx={{ mt: 2 }}
      >
        {isLoading ? <CircularProgress size={24} /> : 'Login'}
      </Button>
    </form>
  );
}

export default LoginForm;
