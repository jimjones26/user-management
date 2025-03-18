import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { Provider } from 'react-redux';
import store from './store';
import NavigationBar from './components/shared/NavigationBar';
import Footer from './components/shared/Footer';
import ErrorBoundary from './components/shared/ErrorBoundary';
import RegistrationPage from './pages/auth/RegistrationPage';
import LoginPage from './pages/auth/LoginPage';
import MFASetupPage from './pages/auth/MFASetupPage';
import MFAVerificationPage from './pages/auth/MFAVerificationPage';
import PasswordResetRequestPage from './pages/auth/PasswordResetRequestPage';
import PasswordResetPage from './pages/auth/PasswordResetPage';
import ProfilePage from './pages/profile/ProfilePage';
import NotificationPreferencesPage from './pages/profile/NotificationPreferencesPage';
import UserListPage from './pages/admin/UserListPage';
import UserDetailsPage from './pages/admin/UserDetailsPage';
import UserEditPage from './pages/admin/UserEditPage';
import RoleManagementPage from './pages/admin/RoleManagementPage';
import PermissionManagementPage from './pages/admin/PermissionManagementPage';
import AuditLogPage from './pages/admin/AuditLogPage';
import UserImportPage from './pages/enterprise/UserImportPage';
import UserExportPage from './pages/enterprise/UserExportPage';

function App() {
  return (
    <Provider store={store}>
      <Router>
        <ErrorBoundary>
          <NavigationBar />
          <Routes>
            <Route path="/register" element={<RegistrationPage />} />
            <Route path="/login" element={<LoginPage />} />
            <Route path="/mfa/setup" element={<MFASetupPage />} />
            <Route path="/mfa/verify" element={<MFAVerificationPage />} />
            <Route path="/password-reset/request" element={<PasswordResetRequestPage />} />
            <Route path="/password-reset" element={<PasswordResetPage />} />
            <Route path="/profile" element={<ProfilePage />} />
            <Route path="/notifications" element={<NotificationPreferencesPage />} />
            <Route path="/admin/users" element={<UserListPage />} />
            <Route path="/admin/users/:userId" element={<UserDetailsPage />} />
            <Route path="/admin/users/:userId/edit" element={<UserEditPage />} />
            <Route path="/admin/roles" element={<RoleManagementPage />} />
            <Route path="/admin/permissions" element={<PermissionManagementPage />} />
            <Route path="/admin/audit-logs" element={<AuditLogPage />} />
            <Route path="/enterprise/import" element={<UserImportPage />} />
            <Route path="/enterprise/export" element={<UserExportPage />} />
          </Routes>
          <Footer />
        </ErrorBoundary>
      </Router>
    </Provider>
  );
}

export default App;