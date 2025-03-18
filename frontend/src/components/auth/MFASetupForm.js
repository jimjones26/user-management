import React, { useState } from 'react';
import { useFormik } from 'formik';
import * as Yup from 'yup';
import { TextField, Button, Select, MenuItem, FormControl, InputLabel, Typography } from '@mui/material';
import { useDispatch, useSelector } from 'react-redux';
import { setupMFA, verifyMFA } from '../../store/actions/authActions';
import QRCode from 'qrcode.react';
import { useNavigate } from 'react-router-dom';

const validationSchemaStep1 = Yup.object({
  method: Yup.string().required('Required'),
  phoneNumber: Yup.string().when('method', {
    is: 'sms',
    then: Yup.string().required('Phone number is required'),
  }),
});

const validationSchemaStep2 = Yup.object({
  code: Yup.string().required('Verification code is required'),
});

function MFASetupForm() {
  const [step, setStep] = useState(1);
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const { mfaSetup, backupCodes, totpSecret } = useSelector((state) => state.auth);

  const formikStep1 = useFormik({
    initialValues: { method: 'totp', phoneNumber: '' },
    validationSchema: validationSchemaStep1,
    onSubmit: async (values) => {
      try {
        await dispatch(setupMFA(values));
        setStep(2);
      } catch (error) {
        // Error handled via toast
      }
    },
  });

  const formikStep2 = useFormik({
    initialValues: { code: '' },
    validationSchema: validationSchemaStep2,
    onSubmit: async (values) => {
      try {
        await dispatch(verifyMFA(values.code));
        navigate('/profile');
      } catch (error) {
        // Error handled via toast
      }
    },
  });

  return (
    <div style={{ display: 'flex', flexDirection: 'column', gap: '16px' }}>
      {step === 1 && (
        <form onSubmit={formikStep1.handleSubmit}>
          <FormControl fullWidth>
            <InputLabel>Method</InputLabel>
            <Select
              name="method"
              value={formikStep1.values.method}
              onChange={formikStep1.handleChange}
              onBlur={formikStep1.handleBlur}
            >
              <MenuItem value="totp">TOTP</MenuItem>
              <MenuItem value="sms">SMS</MenuItem>
            </Select>
          </FormControl>
          {formikStep1.values.method === 'sms' && (
            <TextField
              name="phoneNumber"
              label="Phone Number"
              value={formikStep1.values.phoneNumber}
              onChange={formikStep1.handleChange}
              onBlur={formikStep1.handleBlur}
              error={formikStep1.touched.phoneNumber && Boolean(formikStep1.errors.phoneNumber)}
              helperText={formikStep1.touched.phoneNumber && formikStep1.errors.phoneNumber}
            />
          )}
          <Button type="submit" variant="contained" color="primary" style={{ marginTop: '16px' }}>
            Set Up MFA
          </Button>
        </form>
      )}
      {step === 2 && mfaSetup && (
        <div>
          <Typography variant="h6">Backup Codes</Typography>
          <ul>
            {backupCodes.map((code, index) => (
              <li key={index}>{code}</li>
            ))}
          </ul>
          {formikStep1.values.method === 'totp' && totpSecret && (
            <>
              <Typography>Scan this QR code:</Typography>
              <QRCode value={`otpauth://totp/UserManagementSystem?secret=${totpSecret}`} />
            </>
          )}
          <form onSubmit={formikStep2.handleSubmit} style={{ marginTop: '16px' }}>
            <TextField
              name="code"
              label="Verification Code"
              value={formikStep2.values.code}
              onChange={formikStep2.handleChange}
              onBlur={formikStep2.handleBlur}
              error={formikStep2.touched.code && Boolean(formikStep2.errors.code)}
              helperText={formikStep2.touched.code && formikStep2.errors.code}
            />
            <Button type="submit" variant="contained" color="primary" style={{ marginTop: '16px' }}>
              Verify
            </Button>
          </form>
        </div>
      )}
    </div>
  );
}

export default MFASetupForm;