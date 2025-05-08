import axios from 'axios';

// Get all donors (paginated)
const getDonors = async (params = {}) => {
  const { page = 0, size = 10, sort = 'id,desc' } = params;
  const response = await axios.get(`/donors?page=${page}&size=${size}&sort=${sort}`);
  return response.data;
};

// Get donor by ID
const getDonorById = async (id) => {
  const response = await axios.get(`/donors/${id}`);
  return response.data;
};

// Create new donor
const createDonor = async (donorData) => {
  const response = await axios.post('/donors', donorData);
  return response.data;
};

// Update donor
const updateDonor = async (id, donorData) => {
  const response = await axios.put(`/donors/${id}`, donorData);
  return response.data;
};

// Update donor eligibility
const updateDonorEligibility = async (id, isEligible, medicalNotes = '') => {
  const response = await axios.patch(
    `/donors/${id}/eligibility?isEligible=${isEligible}&medicalNotes=${encodeURIComponent(medicalNotes)}`
  );
  return response.data;
};

// Delete donor
const deleteDonor = async (id) => {
  const response = await axios.delete(`/donors/${id}`);
  return response.data;
};

// Search donors
const searchDonors = async (term, page = 0, size = 10, sort = 'id,desc') => {
  const response = await axios.get(
    `/donors/search?term=${encodeURIComponent(term)}&page=${page}&size=${size}&sort=${sort}`
  );
  return response.data;
};

const donorService = {
  getDonors,
  getDonorById,
  createDonor,
  updateDonor,
  updateDonorEligibility,
  deleteDonor,
  searchDonors,
};

export default donorService;