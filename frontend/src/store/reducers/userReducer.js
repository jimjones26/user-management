const initialState = {
  users: [],
  totalPages: 0,
};

export default function userReducer(state = initialState, action) {
  switch (action.type) {
    case 'FETCH_USERS_SUCCESS':
      return { ...state, users: action.payload.users, totalPages: action.payload.totalPages };
    case 'UPDATE_USER_SUCCESS':
      return {
        ...state,
        users: state.users.map((u) => (u.user_id === action.payload.user_id ? action.payload : u)),
      };
    default:
      return state;
  }
}