import React, { useState, useEffect } from 'react';
import { useFormik } from 'formik';
import * as Yup from 'yup';
import { Checkbox, FormControlLabel, Button } from '@material-ui/core';
import { useSelector } from 'react-redux';
import axios from 'axios';
import { toast } from 'react-toastify';

const validationSchema = Yup.object({
  emailNotifications: Yup.boolean(),
  smsNotifications: Yup.boolean(),
});

function NotificationPreferencesForm() {
  const { user } = useSelector((state) => state.auth);
  const [preferences, setPreferences] = useState({ emailNotifications: false, smsNotifications: false });

  useEffect(() => {
    const fetchPreferences = async () => {
      try {
        const response = await axios.get(`/api/users/${user.user_id}/notifications`);
        setPreferences(response.data);
      } catch (error) {
        toast.error('Failed to fetch notification preferences');
      }
    };
    fetchPreferences();
  }, [user.user_id]);

  const formik = useFormik({
    initialValues: preferences,
    validationSchema,
    enableReinitialize: true,
    onSubmit: async (values) => {
      try {
        await axios.put(`/api/users/${user.user_id}/notifications`, values);
        toast.success('Notification preferences updated');
      } catch (error) {
        toast.error('Update failed');
      }
    },
  });

  return (
    <form onSubmit={formik.handleSubmit} style={{ display: 'flex', flexDirection: 'column', gap: '16px' }}>
      <FormControlLabel
        control={
          <Checkbox
            name="emailNotifications"
            checked={formik.values.emailNotifications}
            onChange={formik.handleChange}
          />
        }
        label="Email Notifications"
      />
      <FormControlLabel
        control={
          <Checkbox
            name="smsNotifications"
            checked={formik.values.smsNotifications}
            onChange={formik.handleChange}
          />
        }
        label="SMS Notifications"
      />
      <Button type="submit" variant="contained" color="primary">
        Save
      </Button>
    </form>
  );
}

export default NotificationPreferencesForm;