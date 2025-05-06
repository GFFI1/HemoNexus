import { configureStore } from '@reduxjs/toolkit';
import authReducer from '../features/auth/authSlice';
import donorReducer from '../features/donors/donorSlice';
import bloodBankReducer from '../features/bloodBanks/bloodBankSlice';
import donationReducer from '../features/donations/donationSlice';
import inventoryReducer from '../features/inventory/inventorySlice';
import requestReducer from '../features/requests/requestSlice';
import alertReducer from '../features/alerts/alertSlice';

export const store = configureStore({
  reducer: {
    auth: authReducer,
    donors: donorReducer,
    bloodBanks: bloodBankReducer,
    donations: donationReducer,
    inventory: inventoryReducer,
    requests: requestReducer,
    alerts: alertReducer,
  },
});