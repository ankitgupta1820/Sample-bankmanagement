// Authentication JavaScript
document.addEventListener('DOMContentLoaded', function() {
    const loginForm = document.getElementById('loginForm');
    const errorMessage = document.getElementById('errorMessage');
    
    // Generate form number for signup
    generateFormNumber();
    
    loginForm.addEventListener('submit', handleLogin);
});

function generateFormNumber() {
    const formNumberField = document.getElementById('formNumber');
    if (formNumberField) {
        const random = Math.floor(Math.random() * 9000) + 1000;
        formNumberField.value = random.toString();
    }
}

function handleLogin(event) {
    event.preventDefault();
    
    const cardNumber = document.getElementById('cardNumber').value;
    const pin = document.getElementById('pin').value;
    
    if (!cardNumber || !pin) {
        showError('Please fill in all fields');
        return;
    }
    
    const loginData = {
        cardNumber: cardNumber,
        pin: pin
    };
    
    fetch('/api/auth/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(loginData)
    })
    .then(response => response.json())
    .then(data => {
        if (data.token) {
            // Store token and card number
            localStorage.setItem('token', data.token);
            localStorage.setItem('cardNumber', data.cardNumber);
            
            showSuccess('Login successful! Redirecting...');
            
            // Redirect to dashboard
            setTimeout(() => {
                window.location.href = '/dashboard';
            }, 1500);
        } else {
            showError(data.error || 'Login failed');
        }
    })
    .catch(error => {
        console.error('Error:', error);
        showError('Login failed. Please try again.');
    });
}

function clearForm() {
    document.getElementById('cardNumber').value = '';
    document.getElementById('pin').value = '';
    hideMessages();
}

function showError(message) {
    const errorElement = document.getElementById('errorMessage');
    errorElement.textContent = message;
    errorElement.style.display = 'block';
    
    setTimeout(() => {
        errorElement.style.display = 'none';
    }, 5000);
}

function showSuccess(message) {
    // Create success message element if it doesn't exist
    let successElement = document.getElementById('successMessage');
    if (!successElement) {
        successElement = document.createElement('div');
        successElement.id = 'successMessage';
        successElement.className = 'success-message';
        successElement.style.display = 'none';
        document.body.appendChild(successElement);
    }
    
    successElement.textContent = message;
    successElement.style.display = 'block';
    
    setTimeout(() => {
        successElement.style.display = 'none';
    }, 3000);
}

function hideMessages() {
    const errorElement = document.getElementById('errorMessage');
    const successElement = document.getElementById('successMessage');
    
    if (errorElement) errorElement.style.display = 'none';
    if (successElement) successElement.style.display = 'none';
}

// Utility function to get auth token
function getAuthToken() {
    return localStorage.getItem('token');
}

// Utility function to get card number
function getCardNumber() {
    return localStorage.getItem('cardNumber');
}

// Check if user is authenticated
function isAuthenticated() {
    return getAuthToken() !== null;
}

// Redirect to login if not authenticated
function requireAuth() {
    if (!isAuthenticated()) {
        window.location.href = '/login';
        return false;
    }
    return true;
}



