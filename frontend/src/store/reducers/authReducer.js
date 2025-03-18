// Action Types
export const REGISTER_REQUEST = 'REGISTER_REQUEST';
export const REGISTER_SUCCESS = 'REGISTER_SUCCESS';
export const REGISTER_FAILURE = 'REGISTER_FAILURE';
export const LOGIN_REQUEST = 'LOGIN_REQUEST';
export const LOGIN_SUCCESS = 'LOGIN_SUCCESS';
export const LOGIN_FAILURE = 'LOGIN_FAILURE';
export const MFA_SETUP_REQUEST = 'MFA_SETUP_REQUEST';
export const MFA_SETUP_SUCCESS = 'MFA_SETUP_SUCCESS';
export const MFA_SETUP_FAILURE = 'MFA_SETUP_FAILURE';
export const MFA_VERIFY_SUCCESS = 'MFA_VERIFY_SUCCESS';
export const RESTORE_SESSION = 'RESTORE_SESSION';
export const LOGOUT = 'LOGOUT';

const initialState = {
  user: null,
  token: null,
  isAuthenticated: false,
  loading: false,
  error: null,
  // MFA related state
  mfaSetup: false,
  backupCodes: [],
  totpSecret: null,
  mfaVerified: false
};

export default function authReducer(state = initialState, action) {
  switch (action.type) {
    case REGISTER_REQUEST:
    case LOGIN_REQUEST:
    case MFA_SETUP_REQUEST:
      return {
        ...state,
        loading: true,
        error: null
      };

    case REGISTER_SUCCESS:
    case LOGIN_SUCCESS:
    case RESTORE_SESSION:
      return {
        ...state,
        loading: false,
        user: action.payload.user,
        token: action.payload.token,
        isAuthenticated: true,
        error: null
      };

    case REGISTER_FAILURE:
    case LOGIN_FAILURE:
    case MFA_SETUP_FAILURE:
      return {
        ...state,
        loading: false,
        error: action.payload,
        isAuthenticated: false,
        user: null,
        token: null
      };

    case MFA_SETUP_SUCCESS:
      return {
        ...state,
        loading: false,
        mfaSetup: true,
        backupCodes: action.payload.backupCodes,
        totpSecret: action.payload.totpSecret || null,
        error: null
      };

    case MFA_VERIFY_SUCCESS:
      return {
        ...state,
        mfaVerified: true,
        error: null,
        // If the MFA verification includes updated user/token info
        ...(action.payload?.user && { user: action.payload.user }),
        ...(action.payload?.token && { token: action.payload.token })
      };

    case LOGOUT:
      return {
        ...initialState
      };

    default:
      return state;
  }
}
