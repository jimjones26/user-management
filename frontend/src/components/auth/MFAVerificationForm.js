import React from 'react';
import { useFormik } from 'formik';
import * as Yup from 'yup';
import { TextField, Button, Box } from '@mui/material';
import { useDispatch } from 'react-redux';
import { verifyMFA } from '../../store/actions/authActions';
import { useNavigate } from 'react-router-dom';
import { toast } from 'react-toastify';

const validationSchema = Yup.object({
  code: Yup.string().required('Verification code is required'),
});

function MFAVerificationForm() {
  const dispatch = useDispatch();
  const navigate = useNavigate();

  const formik = useFormik({
    initialValues: { code: '' },
    validationSchema,
    onSubmit: async (values) => {
      try {
        await dispatch(verifyMFA(values.code));
        toast.success('MFA verified successfully');
        navigate('/profile');
      } catch (error) {
        toast.error('MFA verification failed');
      }
    },
  });

  return (
    <Box component="form" onSubmit={formik.handleSubmit} sx={{ display: 'flex', flexDirection: 'column', gap: 2 }}>
      <TextField
        name="code"
        label="Verification Code"
        value={formik.values.code}
        onChange={formik.handleChange}
        onBlur={formik.handleBlur}
        error={formik.touched.code && Boolean(formik.errors.code)}
        helperText={formik.touched.code && formik.errors.code}
      />
      <Button type="submit" variant="contained">
        Verify
      </Button>
    </Box>
  );
}

export default MFAVerificationForm;