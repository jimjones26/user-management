import React from 'react';
import { useFormik } from 'formik';
import * as Yup from 'yup';
import { TextField, Button } from '@material-ui/core';
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
    <form onSubmit={formik.handleSubmit} style={{ display: 'flex', flexDirection: 'column', gap: '16px' }}>
      <TextField
        name="code"
        label="Verification Code"
        value={formik.values.code}
        onChange={formik.handleChange}
        onBlur={formik.handleBlur}
        error={formik.touched.code && Boolean(formik.errors.code)}
        helperText={formik.touched.code && formik.errors.code}
      />
      <Button type="submit" variant="contained" color="primary">
        Verify
      </Button>
    </form>
  );
}

export default MFAVerificationForm;