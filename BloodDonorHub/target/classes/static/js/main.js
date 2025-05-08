// HemoNexus main JavaScript file

// Wait for the DOM to be fully loaded
document.addEventListener('DOMContentLoaded', function() {
    console.log('HemoNexus Application Loaded');
    
    // Initialize any UI components
    initializeUI();
    
    // Check authentication status
    checkAuthStatus();
});

// Initialize UI components
function initializeUI() {
    // Set up any global UI handlers
    setupFormValidation();
    setupAlertDismiss();
    setupDatePickers();
}

// Check authentication status
function checkAuthStatus() {
    const token = localStorage.getItem('token');
    
    if (token) {
        // Set up authentication headers for API requests
        setupAuthHeaders(token);
        
        // Update UI for authenticated user
        updateUIForAuthenticatedUser();
    } else {
        // Update UI for non-authenticated user
        updateUIForNonAuthenticatedUser();
    }
}

// Set up authentication headers for API requests
function setupAuthHeaders(token) {
    // For future fetch or axios requests
    // axios.defaults.headers.common['Authorization'] = `Bearer ${token}`;
}

// Update UI for authenticated user
function updateUIForAuthenticatedUser() {
    const authElements = document.querySelectorAll('.auth-only');
    const nonAuthElements = document.querySelectorAll('.non-auth-only');
    
    // Show elements that require authentication
    authElements.forEach(element => {
        element.style.display = '';
    });
    
    // Hide elements for non-authenticated users
    nonAuthElements.forEach(element => {
        element.style.display = 'none';
    });
}

// Update UI for non-authenticated user
function updateUIForNonAuthenticatedUser() {
    const authElements = document.querySelectorAll('.auth-only');
    const nonAuthElements = document.querySelectorAll('.non-auth-only');
    
    // Hide elements that require authentication
    authElements.forEach(element => {
        element.style.display = 'none';
    });
    
    // Show elements for non-authenticated users
    nonAuthElements.forEach(element => {
        element.style.display = '';
    });
}

// Set up form validation
function setupFormValidation() {
    const forms = document.querySelectorAll('.needs-validation');
    
    Array.from(forms).forEach(form => {
        form.addEventListener('submit', event => {
            if (!form.checkValidity()) {
                event.preventDefault();
                event.stopPropagation();
            }
            
            form.classList.add('was-validated');
        }, false);
    });
}

// Set up alert dismiss functionality
function setupAlertDismiss() {
    const alertCloseButtons = document.querySelectorAll('.alert .close');
    
    alertCloseButtons.forEach(button => {
        button.addEventListener('click', function() {
            const alert = this.closest('.alert');
            alert.style.display = 'none';
        });
    });
}

// Set up date pickers
function setupDatePickers() {
    const datePickers = document.querySelectorAll('.date-picker');
    
    // If using a date picker library, initialize it here
    // For example, with flatpickr:
    // flatpickr(datePickers, { dateFormat: 'Y-m-d' });
}

// Helper function to format dates
function formatDate(dateString) {
    const date = new Date(dateString);
    return date.toLocaleDateString();
}

// Helper function to format blood types with proper styling
function formatBloodType(bloodType) {
    // Convert blood type to CSS class name
    const cssClass = `blood-type-${bloodType.toLowerCase().replace('+', '-pos').replace('-', '-neg')}`;
    
    return `<span class="blood-type ${cssClass}">${bloodType}</span>`;
}

// Helper function for API calls
async function apiCall(url, method = 'GET', data = null) {
    try {
        const options = {
            method,
            headers: {
                'Content-Type': 'application/json'
            }
        };
        
        // Add token if available
        const token = localStorage.getItem('token');
        if (token) {
            options.headers['Authorization'] = `Bearer ${token}`;
        }
        
        // Add body for non-GET requests
        if (method !== 'GET' && data) {
            options.body = JSON.stringify(data);
        }
        
        const response = await fetch(url, options);
        
        // Handle unauthorized responses
        if (response.status === 401) {
            localStorage.removeItem('token');
            window.location.href = '/login';
            return;
        }
        
        // Parse JSON response
        const result = await response.json();
        
        if (!response.ok) {
            throw new Error(result.message || 'Something went wrong');
        }
        
        return result;
    } catch (error) {
        console.error('API call error:', error);
        showErrorMessage(error.message);
        throw error;
    }
}

// Show error message to user
function showErrorMessage(message) {
    const alertContainer = document.getElementById('alert-container');
    
    if (alertContainer) {
        alertContainer.innerHTML = `
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                ${message}
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
        `;
    }
}

// Show success message to user
function showSuccessMessage(message) {
    const alertContainer = document.getElementById('alert-container');
    
    if (alertContainer) {
        alertContainer.innerHTML = `
            <div class="alert alert-success alert-dismissible fade show" role="alert">
                ${message}
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
        `;
    }
}