// PIN Change JavaScript
document.addEventListener('DOMContentLoaded', function() {
    // Check authentication
    if (!requireAuth()) {
        return;
    }
    
    // Initialize form
    initializePinChangeForm();
});

function initializePinChangeForm() {
    const form = document.getElementById('pinChangeForm');
    
    // Add form submission handler
    form.addEventListener('submit', handlePinChange);
    
    // Add input validation
    setupInputValidation();
    
    // Focus on first input
    document.getElementById('currentPin').focus();
}

function setupInputValidation() {
    const inputs = document.querySelectorAll('input[type="password"]');
    
    inputs.forEach(input => {
        // Only allow numeric input
        input.addEventListener('input', function(e) {
            this.value = this.value.replace(/[^0-9]/g, '');
            
            // Limit to 6 digits
            if (this.value.length > 6) {
                this.value = this.value.slice(0, 6);
            }
        });
        
        // Auto-focus next field when 6 digits entered
        input.addEventListener('input', function(e) {
            if (this.value.length === 6) {
                const nextInput = getNextInput(this);
                if (nextInput) {
                    nextInput.focus();
                }
            }
        });
    });
    
    // Real-time PIN confirmation validation
    const confirmPin = document.getElementById('confirmPin');
    const newPin = document.getElementById('newPin');
    
    confirmPin.addEventListener('input', function() {
        validatePinMatch();
    });
    
    newPin.addEventListener('input', function() {
        validatePinMatch();
    });
}

function getNextInput(currentInput) {
    const inputs = Array.from(document.querySelectorAll('input[type="password"]'));
    const currentIndex = inputs.indexOf(currentInput);
    return inputs[currentIndex + 1] || null;
}

function validatePinMatch() {
    const newPin = document.getElementById('newPin').value;
    const confirmPin = document.getElementById('confirmPin').value;
    const confirmInput = document.getElementById('confirmPin');
    
    if (confirmPin.length === 6) {
        if (newPin === confirmPin) {
            confirmInput.style.borderColor = '#28a745';
            confirmInput.style.backgroundColor = '#f8fff9';
        } else {
            confirmInput.style.borderColor = '#dc3545';
            confirmInput.style.backgroundColor = '#fff8f8';
        }
    } else {
        confirmInput.style.borderColor = '';
        confirmInput.style.backgroundColor = '';
    }
}

function handlePinChange(event) {
    event.preventDefault();
    
    const currentPin = document.getElementById('currentPin').value;
    const newPin = document.getElementById('newPin').value;
    const confirmPin = document.getElementById('confirmPin').value;
    
    // Validation
    if (!validatePinChangeForm(currentPin, newPin, confirmPin)) {
        return;
    }
    
    // Show loading state
    const submitButton = event.target.querySelector('button[type="submit"]');
    const originalText = submitButton.innerHTML;
    submitButton.innerHTML = '<i class="fas fa-spinner fa-spin"></i> Changing PIN...';
    submitButton.disabled = true;
    
    // Make API call
    changePinAPI(currentPin, newPin)
        .then(response => {
            if (response.message) {
                showSuccess('PIN changed successfully! Please login again with your new PIN.');
                
                // Clear form
                clearForm();
                
                // Redirect to login after 3 seconds
                setTimeout(() => {
                    logout();
                }, 3000);
            } else {
                showError(response.error || 'PIN change failed');
            }
        })
        .catch(error => {
            console.error('Error:', error);
            // Don't show error if it's already been handled (session expired)
            if (!error.message.includes('Session expired') && !error.message.includes('not authenticated')) {
                showError(error.message || 'PIN change failed. Please try again.');
            }
        })
        .finally(() => {
            // Restore button state (only if not redirecting)
            if (!error || (!error.message.includes('Session expired') && !error.message.includes('not authenticated'))) {
                submitButton.innerHTML = originalText;
                submitButton.disabled = false;
            }
        });
}

function validatePinChangeForm(currentPin, newPin, confirmPin) {
    // Check if all fields are filled
    if (!currentPin || !newPin || !confirmPin) {
        showError('Please fill in all fields');
        return false;
    }
    
    // Check PIN length
    if (currentPin.length !== 6 || newPin.length !== 6 || confirmPin.length !== 6) {
        showError('All PINs must be exactly 6 digits');
        return false;
    }
    
    // Check if new PIN matches confirmation
    if (newPin !== confirmPin) {
        showError('New PIN and confirmation do not match');
        return false;
    }
    
    // Check if new PIN is different from current PIN
    if (currentPin === newPin) {
        showError('New PIN must be different from current PIN');
        return false;
    }
    
    // Check for weak PINs
    if (isWeakPin(newPin)) {
        showError('Please choose a stronger PIN. Avoid sequences like 1234 or repeated digits like 1111');
        return false;
    }
    
    return true;
}

function isWeakPin(pin) {
    // Check for common weak patterns (6-digit)
    const weakPatterns = [
        '000000', '111111', '222222', '333333', '444444', '555555', '666666', '777777', '888888', '999999',
        '123456', '654321', '012345', '543210', '135790', '246810', '987654', '678901'
    ];
    
    return weakPatterns.includes(pin);
}

async function changePinAPI(oldPin, newPin) {
    const token = getAuthToken();
    
    // Check if token exists
    if (!token) {
        showError('Please log in to continue');
        setTimeout(() => window.location.href = '/login', 1500);
        throw new Error('No authentication token found');
    }
    
    try {
        const response = await fetch('/api/auth/change-pin', {
            method: 'POST',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: `oldPin=${encodeURIComponent(oldPin)}&newPin=${encodeURIComponent(newPin)}`
        });
        
        // Handle 401 Unauthorized
        if (response.status === 401) {
            localStorage.removeItem('token');
            showError('Your session has expired. Please log in again.');
            setTimeout(() => window.location.href = '/login', 1500);
            throw new Error('Session expired');
        }
        
        const data = await response.json();
        
        if (!response.ok) {
            throw new Error(data.error || 'PIN change failed');
        }
        
        return data;
        
    } catch (error) {
        console.error('PIN change error:', error);
        if (!error.message.includes('Session expired') && !error.message.includes('not authenticated')) {
            throw error;
        }
        throw error;
    }
}

function clearForm() {
    document.getElementById('pinChangeForm').reset();
    
    // Clear any validation styling
    const inputs = document.querySelectorAll('input[type="password"]');
    inputs.forEach(input => {
        input.style.borderColor = '';
        input.style.backgroundColor = '';
    });
    
    // Focus on first input
    document.getElementById('currentPin').focus();
}

function goBack() {
    window.location.href = '/dashboard';
}

function logout() {
    // Clear stored data
    localStorage.removeItem('token');
    localStorage.removeItem('cardNumber');
    
    // Redirect to login
    window.location.href = '/login';
}

// Utility functions
function showError(message) {
    showMessage(message, 'error');
}

function showSuccess(message) {
    showMessage(message, 'success');
}

function showMessage(message, type) {
    // Remove existing messages
    const existingMessages = document.querySelectorAll('.error-message, .success-message');
    existingMessages.forEach(msg => msg.remove());
    
    // Create new message
    const messageElement = document.createElement('div');
    messageElement.className = `${type}-message`;
    messageElement.textContent = message;
    messageElement.style.display = 'block';
    
    document.body.appendChild(messageElement);
    
    // Auto remove after 5 seconds for success, 3 seconds for error
    const timeout = type === 'success' ? 5000 : 3000;
    setTimeout(() => {
        messageElement.remove();
    }, timeout);
}

// Include auth utilities
function getAuthToken() {
    return localStorage.getItem('token');
}

function requireAuth() {
    if (!getAuthToken()) {
        window.location.href = '/login';
        return false;
    }
    return true;
}
