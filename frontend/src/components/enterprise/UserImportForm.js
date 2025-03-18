import React from 'react';
import { useFormik } from 'formik';
import * as Yup from 'yup';
import { Button, Input } from '@mui/material';
import { useDispatch } from 'react-redux';
import axios from 'axios';
import { toast } from 'react-toastify';

const validationSchema = Yup.object({
  file: Yup.mixed().required('A CSV file is required'),
});

function UserImportForm() {
  const dispatch = useDispatch();

  const formik = useFormik({
    initialValues: { file: null },
    validationSchema,
    onSubmit: async (values) => {
      const formData = new FormData();
      formData.append('file', values.file);
      try {
        await axios.post('/api/users/import', formData, {
          headers: { 'Content-Type': 'multipart/form-data' },
        });
        toast.success('Users imported successfully.');
      } catch (error) {
        const message = error.response?.data?.message || 'Import failed';
        toast.error(message);
      }
    },
  });

  return (
    <form onSubmit={formik.handleSubmit} style={{ display: 'flex', flexDirection: 'column', gap: '16px' }}>
      <Input
        type="file"
        name="file"
        onChange={(event) => formik.setFieldValue('file', event.currentTarget.files[0])}
        error={formik.touched.file && Boolean(formik.errors.file)}
      />
      {formik.touched.file && formik.errors.file && <Typography color="error">{formik.errors.file}</Typography>}
      <Button type="submit" variant="contained" color="primary">
        Import
      </Button>
    </form>
  );
}

export default UserImportForm;