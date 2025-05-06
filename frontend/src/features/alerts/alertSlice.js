import { createSlice } from '@reduxjs/toolkit';

const initialState = {
  isOpen: false,
  type: 'info', // 'error', 'warning', 'info', 'success'
  message: '',
};

export const alertSlice = createSlice({
  name: 'alerts',
  initialState,
  reducers: {
    setAlert: (state, action) => {
      state.isOpen = true;
      state.type = action.payload.type;
      state.message = action.payload.message;
    },
    closeAlert: (state) => {
      state.isOpen = false;
    },
  },
});

export const { setAlert, closeAlert } = alertSlice.actions;
export default alertSlice.reducer;