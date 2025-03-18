import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { Typography, Card, CardContent } from '@material-ui/core';
import axios from 'axios';
import { toast } from 'react-toastify';

function UserDetailsPage() {
  const { userId } = useParams();
  const [user, setUser] = useState(null);

  useEffect(() => {
    const fetchUser = async () => {
      try {
        const response = await axios.get(`/api/users/${userId}`);
        setUser(response.data);
      } catch (error) {
        toast.error('Failed to fetch user details');
      }
    };
    fetchUser();
  }, [userId]);

  if (!user) return <Typography>Loading...</Typography>;

  return (
    <div style={{ padding: '20px', maxWidth: '600px', margin: '0 auto' }}>
      <Typography variant="h4" gutterBottom>
        User Details
      </Typography>
      <Card>
        <CardContent>
          <Typography>ID: {user.user_id}</Typography>
          <Typography>Username: {user.username}</Typography>
          <Typography>Email: {user.email}</Typography>
          <Typography>Status: {user.status}</Typography>
        </CardContent>
      </Card>
    </div>
  );
}

export default UserDetailsPage;