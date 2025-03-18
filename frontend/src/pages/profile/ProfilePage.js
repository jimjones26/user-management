import React, { useState } from 'react';
import { useSelector } from 'react-redux';
import { Typography, Card, CardContent, Button } from '@mui/material';
import ProfileEditForm from '../../components/profile/ProfileEditForm';

function ProfilePage() {
  const { user } = useSelector((state) => state.auth);
  const [editMode, setEditMode] = useState(false);

  if (!user) return <Typography>Please log in.</Typography>;

  return (
    <div style={{ padding: '20px', maxWidth: '600px', margin: '0 auto' }}>
      <Typography variant="h4" gutterBottom>
        Profile
      </Typography>
      {editMode ? (
        <ProfileEditForm user={user} onSave={() => setEditMode(false)} />
      ) : (
        <Card>
          <CardContent>
            <Typography>Username: {user.username}</Typography>
            <Typography>Email: {user.email}</Typography>
            <Button onClick={() => setEditMode(true)} variant="outlined" color="primary" style={{ marginTop: '16px' }}>
              Edit
            </Button>
          </CardContent>
        </Card>
      )}
    </div>
  );
}

export default ProfilePage;