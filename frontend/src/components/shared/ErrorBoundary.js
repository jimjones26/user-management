import React, { Component } from 'react';
import { Typography } from '@material-ui/core';

class ErrorBoundary extends Component {
  state = { hasError: false };

  static getDerivedStateFromError() {
    return { hasError: true };
  }

  componentDidCatch(error, info) {
    console.error('ErrorBoundary caught an error:', error, info);
  }

  render() {
    if (this.state.hasError) {
      return <Typography variant="h6" color="error">Something went wrong.</Typography>;
    }
    return this.props.children;
  }
}

export default ErrorBoundary;