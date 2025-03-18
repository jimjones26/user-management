const initialState = {
  user: null,
  token: null,
  isAuthenticated: false,
  loading: false,
  error: null,
  mfaSetup: false,
  backupCodes: [],
  totpSecret: null,
  mfaVerified: false
};

export default function authReducer(state = initialState, action) {
  switch (action.type) {
    case 'REGISTER_REQUEST':
    case 'LOGIN_REQUEST':
    case 'MFA_SETUP_REQUEST':
      return {
        ...state,
        loading: true,
        error: null
      };

    case 'REGISTER_SUCCESS':
    case 'LOGIN_SUCCESS':
      return {
        ...state,
        loading: false,
        user: action.payload.user,
        token: action.payload.token,
        isAuthenticated: true,
        error: null
      };

    case 'REGISTER_FAILURE':
    case 'LOGIN_FAILURE':
    case 'MFA_SETUP_FAILURE':
      return {
        ...state,
        loading: false,
        error: action.payload,
        isAuthenticated: false
      };

    case 'MFA_SETUP_SUCCESS':
      return {
        ...state,
        loading: false,
        mfaSetup: true,
        backupCodes: action.payload.backupCodes,
        totpSecret: action.payload.totpSecret || null,
        error: null
      };

    case 'MFA_VERIFY_SUCCESS':
      return {
        ...state,
        mfaVerified: true,
        error: null
      };

    case 'LOGOUT':
      return {
        ...initialState
      };

    default:
      return state;
  }
}