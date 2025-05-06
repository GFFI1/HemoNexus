import { format, parseISO } from 'date-fns';

// Format date with specific format
export const formatDate = (dateString, dateFormat = 'MMM dd, yyyy') => {
  if (!dateString) return 'N/A';
  try {
    const date = typeof dateString === 'string' ? parseISO(dateString) : dateString;
    return format(date, dateFormat);
  } catch (error) {
    console.error('Date formatting error:', error);
    return 'Invalid Date';
  }
};

// Format date and time
export const formatDateTime = (dateString, dateFormat = 'MMM dd, yyyy h:mm a') => {
  if (!dateString) return 'N/A';
  try {
    const date = typeof dateString === 'string' ? parseISO(dateString) : dateString;
    return format(date, dateFormat);
  } catch (error) {
    console.error('Date formatting error:', error);
    return 'Invalid Date';
  }
};

// Format blood type with visual style
export const formatBloodType = (bloodType) => {
  if (!bloodType) return 'Unknown';
  
  // Format the blood type (remove spaces, ensure proper format)
  const formattedType = bloodType.toUpperCase().replace(/\s/g, '');
  
  return `${formattedType}`;
};

// Format status with visual style
export const formatStatus = (status) => {
  if (!status) return '';
  
  // Convert to lowercase and remove spaces for CSS class
  const statusClass = status.toLowerCase().replace(/\s/g, '_');
  
  return {
    text: status.replace(/_/g, ' ').toUpperCase(),
    className: `status-${statusClass}`,
  };
};

// Format currency
export const formatCurrency = (amount, currency = 'USD') => {
  if (amount === null || amount === undefined) return 'N/A';
  
  return new Intl.NumberFormat('en-US', {
    style: 'currency',
    currency,
  }).format(amount);
};

// Format number with commas
export const formatNumber = (number, decimalPlaces = 0) => {
  if (number === null || number === undefined) return 'N/A';
  
  return new Intl.NumberFormat('en-US', {
    minimumFractionDigits: decimalPlaces,
    maximumFractionDigits: decimalPlaces,
  }).format(number);
};

// Format phone number (simple US format)
export const formatPhoneNumber = (phoneNumber) => {
  if (!phoneNumber) return '';
  
  // Remove all non-digits
  const cleaned = phoneNumber.replace(/\D/g, '');
  
  // Check if it's a valid length for US number
  if (cleaned.length === 10) {
    return `(${cleaned.substring(0, 3)}) ${cleaned.substring(3, 6)}-${cleaned.substring(6)}`;
  } else if (cleaned.length === 11 && cleaned.charAt(0) === '1') {
    return `+1 (${cleaned.substring(1, 4)}) ${cleaned.substring(4, 7)}-${cleaned.substring(7)}`;
  }
  
  // Return as is if it doesn't match expected formats
  return phoneNumber;
};

// Format name (capitalize first letter of each word)
export const formatName = (name) => {
  if (!name) return '';
  
  return name
    .split(' ')
    .map(word => word.charAt(0).toUpperCase() + word.slice(1).toLowerCase())
    .join(' ');
};

// Calculate age from birth date
export const calculateAge = (birthDate) => {
  if (!birthDate) return null;
  
  const today = new Date();
  const birthDateObj = typeof birthDate === 'string' ? parseISO(birthDate) : birthDate;
  
  let age = today.getFullYear() - birthDateObj.getFullYear();
  const monthDiff = today.getMonth() - birthDateObj.getMonth();
  
  if (monthDiff < 0 || (monthDiff === 0 && today.getDate() < birthDateObj.getDate())) {
    age--;
  }
  
  return age;
};