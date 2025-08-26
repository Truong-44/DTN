export const environment = {
  production: false,
  apiUrl: 'http://localhost:8080', // TODO: Xác nhận port backend
  enableDebugTools: true,
  // Backend connection settings
  api: {
    timeout: 30000, // 30 seconds
    retryAttempts: 3,
    retryDelay: 1000, // 1 second
  },
  // Authentication settings
  auth: {
    tokenKey: 'auth_token',
    refreshTokenKey: 'refresh_token',
    tokenExpiryBuffer: 300000, // 5 minutes
  },
  // Upload settings
  upload: {
    maxFileSize: 5 * 1024 * 1024, // 5MB
    allowedImageTypes: ['image/jpeg', 'image/png', 'image/gif'],
  },
};
