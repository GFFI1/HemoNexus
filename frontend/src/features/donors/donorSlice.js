import { createSlice, createAsyncThunk } from '@reduxjs/toolkit';
import donorService from './donorService';

const initialState = {
  donors: [],
  donor: null,
  isError: false,
  isSuccess: false,
  isLoading: false,
  message: '',
  pagination: {
    totalItems: 0,
    totalPages: 0,
    currentPage: 0,
  },
};

// Get all donors (paginated)
export const getDonors = createAsyncThunk(
  'donors/getAll',
  async (params, thunkAPI) => {
    try {
      return await donorService.getDonors(params);
    } catch (error) {
      const message =
        error.response?.data?.message || error.message || error.toString();
      return thunkAPI.rejectWithValue(message);
    }
  }
);

// Get donor by ID
export const getDonorById = createAsyncThunk(
  'donors/getById',
  async (id, thunkAPI) => {
    try {
      return await donorService.getDonorById(id);
    } catch (error) {
      const message =
        error.response?.data?.message || error.message || error.toString();
      return thunkAPI.rejectWithValue(message);
    }
  }
);

// Create new donor
export const createDonor = createAsyncThunk(
  'donors/create',
  async (donorData, thunkAPI) => {
    try {
      return await donorService.createDonor(donorData);
    } catch (error) {
      const message =
        error.response?.data?.message || error.message || error.toString();
      return thunkAPI.rejectWithValue(message);
    }
  }
);

// Update donor
export const updateDonor = createAsyncThunk(
  'donors/update',
  async ({ id, donorData }, thunkAPI) => {
    try {
      return await donorService.updateDonor(id, donorData);
    } catch (error) {
      const message =
        error.response?.data?.message || error.message || error.toString();
      return thunkAPI.rejectWithValue(message);
    }
  }
);

// Update donor eligibility
export const updateDonorEligibility = createAsyncThunk(
  'donors/updateEligibility',
  async ({ id, isEligible, medicalNotes }, thunkAPI) => {
    try {
      return await donorService.updateDonorEligibility(id, isEligible, medicalNotes);
    } catch (error) {
      const message =
        error.response?.data?.message || error.message || error.toString();
      return thunkAPI.rejectWithValue(message);
    }
  }
);

// Delete donor
export const deleteDonor = createAsyncThunk(
  'donors/delete',
  async (id, thunkAPI) => {
    try {
      await donorService.deleteDonor(id);
      return id;
    } catch (error) {
      const message =
        error.response?.data?.message || error.message || error.toString();
      return thunkAPI.rejectWithValue(message);
    }
  }
);

// Search donors
export const searchDonors = createAsyncThunk(
  'donors/search',
  async ({ term, page, size, sort }, thunkAPI) => {
    try {
      return await donorService.searchDonors(term, page, size, sort);
    } catch (error) {
      const message =
        error.response?.data?.message || error.message || error.toString();
      return thunkAPI.rejectWithValue(message);
    }
  }
);

export const donorSlice = createSlice({
  name: 'donors',
  initialState,
  reducers: {
    reset: (state) => {
      state.isLoading = false;
      state.isSuccess = false;
      state.isError = false;
      state.message = '';
    },
    clearDonor: (state) => {
      state.donor = null;
    },
  },
  extraReducers: (builder) => {
    builder
      .addCase(getDonors.pending, (state) => {
        state.isLoading = true;
      })
      .addCase(getDonors.fulfilled, (state, action) => {
        state.isLoading = false;
        state.isSuccess = true;
        state.donors = action.payload.content;
        state.pagination = {
          totalItems: action.payload.totalElements,
          totalPages: action.payload.totalPages,
          currentPage: action.payload.number,
        };
      })
      .addCase(getDonors.rejected, (state, action) => {
        state.isLoading = false;
        state.isError = true;
        state.message = action.payload;
      })
      .addCase(getDonorById.pending, (state) => {
        state.isLoading = true;
      })
      .addCase(getDonorById.fulfilled, (state, action) => {
        state.isLoading = false;
        state.isSuccess = true;
        state.donor = action.payload;
      })
      .addCase(getDonorById.rejected, (state, action) => {
        state.isLoading = false;
        state.isError = true;
        state.message = action.payload;
      })
      .addCase(createDonor.pending, (state) => {
        state.isLoading = true;
      })
      .addCase(createDonor.fulfilled, (state, action) => {
        state.isLoading = false;
        state.isSuccess = true;
        state.donors.push(action.payload);
      })
      .addCase(createDonor.rejected, (state, action) => {
        state.isLoading = false;
        state.isError = true;
        state.message = action.payload;
      })
      .addCase(updateDonor.pending, (state) => {
        state.isLoading = true;
      })
      .addCase(updateDonor.fulfilled, (state, action) => {
        state.isLoading = false;
        state.isSuccess = true;
        state.donor = action.payload;
        state.donors = state.donors.map((donor) =>
          donor.id === action.payload.id ? action.payload : donor
        );
      })
      .addCase(updateDonor.rejected, (state, action) => {
        state.isLoading = false;
        state.isError = true;
        state.message = action.payload;
      })
      .addCase(updateDonorEligibility.pending, (state) => {
        state.isLoading = true;
      })
      .addCase(updateDonorEligibility.fulfilled, (state, action) => {
        state.isLoading = false;
        state.isSuccess = true;
        state.donor = action.payload;
        state.donors = state.donors.map((donor) =>
          donor.id === action.payload.id ? action.payload : donor
        );
      })
      .addCase(updateDonorEligibility.rejected, (state, action) => {
        state.isLoading = false;
        state.isError = true;
        state.message = action.payload;
      })
      .addCase(deleteDonor.pending, (state) => {
        state.isLoading = true;
      })
      .addCase(deleteDonor.fulfilled, (state, action) => {
        state.isLoading = false;
        state.isSuccess = true;
        state.donors = state.donors.filter((donor) => donor.id !== action.payload);
      })
      .addCase(deleteDonor.rejected, (state, action) => {
        state.isLoading = false;
        state.isError = true;
        state.message = action.payload;
      })
      .addCase(searchDonors.pending, (state) => {
        state.isLoading = true;
      })
      .addCase(searchDonors.fulfilled, (state, action) => {
        state.isLoading = false;
        state.isSuccess = true;
        state.donors = action.payload.content;
        state.pagination = {
          totalItems: action.payload.totalElements,
          totalPages: action.payload.totalPages,
          currentPage: action.payload.number,
        };
      })
      .addCase(searchDonors.rejected, (state, action) => {
        state.isLoading = false;
        state.isError = true;
        state.message = action.payload;
      });
  },
});

export const { reset, clearDonor } = donorSlice.actions;
export default donorSlice.reducer;