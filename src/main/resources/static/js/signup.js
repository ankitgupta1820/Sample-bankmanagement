// Signup JavaScript
document.addEventListener('DOMContentLoaded', function() {
    const signupForm = document.getElementById('signupForm');
    const errorMessage = document.getElementById('errorMessage');
    const successMessage = document.getElementById('successMessage');
    
    // Generate form number
    generateFormNumber();
    
    signupForm.addEventListener('submit', handleSignup);
    
    // Add input validation
    addInputValidation();
});

function generateFormNumber() {
    const formNumberField = document.getElementById('formNumber');
    if (formNumberField) {
        const random = Math.floor(Math.random() * 9000) + 1000;
        formNumberField.value = random.toString();
    }
}

function handleSignup(event) {
    event.preventDefault();
    
    const formData = new FormData(event.target);
    const signupData = {};
    
    // Convert FormData to object
    for (let [key, value] of formData.entries()) {
        if (key === 'dateOfBirth') {
            signupData[key] = value;
        } else if (key === 'gender' || key === 'maritalStatus') {
            signupData[key] = value.toUpperCase();
        } else {
            signupData[key] = value;
        }
    }
    
    // Validate required fields
    if (!validateSignupForm(signupData)) {
        return;
    }
    
    fetch('/api/auth/signup', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(signupData)
    })
    .then(response => response.json())
    .then(data => {
        if (data.cardNumber) {
            showSuccess(`Account created successfully! Your card number is: ${data.cardNumber}`);
            
            // Clear form
            clearForm();
            
            // Redirect to login after 3 seconds
            setTimeout(() => {
                window.location.href = '/login';
            }, 3000);
        } else {
            showError(data.error || 'Signup failed');
        }
    })
    .catch(error => {
        console.error('Error:', error);
        showError('Signup failed. Please try again.');
    });
}

function validateSignupForm(data) {
    const requiredFields = [
        'name', 'fathersName', 'dateOfBirth', 'gender', 'email',
        'maritalStatus', 'address', 'city', 'state', 'pinCode',
        'religion', 'category', 'income', 'education', 'occupation',
        'panNumber', 'aadharNumber', 'seniorCitizen', 'existingAccount', 'pin'
    ];
    
    for (let field of requiredFields) {
        if (!data[field] || data[field].trim() === '') {
            showError(`Please fill in the ${field.replace(/([A-Z])/g, ' $1').toLowerCase()}`);
            return false;
        }
    }
    
    // Validate email format
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(data.email)) {
        showError('Please enter a valid email address');
        return false;
    }
    
    // Validate PIN code
    if (data.pinCode.length !== 6 || !/^\d{6}$/.test(data.pinCode)) {
        showError('PIN code must be exactly 6 digits');
        return false;
    }
    
    // Validate PIN length
    if (data.pin.length < 4) {
        showError('PIN must be at least 4 characters long');
        return false;
    }
    
    return true;
}

function addInputValidation() {
    // PIN code validation
    const pinCodeInput = document.getElementById('pinCode');
    if (pinCodeInput) {
        pinCodeInput.addEventListener('input', function() {
            this.value = this.value.replace(/[^0-9]/g, '');
            if (this.value.length > 6) {
                this.value = this.value.slice(0, 6);
            }
        });
    }
    
    // Email validation
    const emailInput = document.getElementById('email');
    if (emailInput) {
        emailInput.addEventListener('blur', function() {
            const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
            if (this.value && !emailRegex.test(this.value)) {
                this.style.borderColor = '#dc3545';
                showFieldError(this, 'Please enter a valid email address');
            } else {
                this.style.borderColor = '#e1e5e9';
                hideFieldError(this);
            }
        });
    }
    
    // Name validation (only letters and spaces)
    const nameInputs = ['name', 'fathersName', 'city', 'state'];
    nameInputs.forEach(fieldName => {
        const input = document.getElementById(fieldName);
        if (input) {
            input.addEventListener('input', function() {
                this.value = this.value.replace(/[^a-zA-Z\s]/g, '');
            });
        }
    });
    
    // PAN number validation
    const panInput = document.getElementById('panNumber');
    if (panInput) {
        panInput.addEventListener('input', function() {
            this.value = this.value.toUpperCase().replace(/[^A-Z0-9]/g, '');
        });
    }
    
    // Aadhar number validation
    const aadharInput = document.getElementById('aadharNumber');
    if (aadharInput) {
        aadharInput.addEventListener('input', function() {
            this.value = this.value.replace(/[^0-9]/g, '');
            if (this.value.length > 12) {
                this.value = this.value.slice(0, 12);
            }
        });
    }
}

function showFieldError(input, message) {
    let errorElement = input.parentNode.querySelector('.field-error');
    if (!errorElement) {
        errorElement = document.createElement('small');
        errorElement.className = 'field-error';
        errorElement.style.color = '#dc3545';
        errorElement.style.fontSize = '0.8rem';
        input.parentNode.appendChild(errorElement);
    }
    errorElement.textContent = message;
}

function hideFieldError(input) {
    const errorElement = input.parentNode.querySelector('.field-error');
    if (errorElement) {
        errorElement.remove();
    }
}

function clearForm() {
    const form = document.getElementById('signupForm');
    form.reset();
    generateFormNumber();
    hideMessages();
    
    // Reset all field styles
    const inputs = form.querySelectorAll('input, select, textarea');
    inputs.forEach(input => {
        input.style.borderColor = '#e1e5e9';
        hideFieldError(input);
    });
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
    const successElement = document.getElementById('successMessage');
    successElement.textContent = message;
    successElement.style.display = 'block';
    
    setTimeout(() => {
        successElement.style.display = 'none';
    }, 5000);
}

function hideMessages() {
    const errorElement = document.getElementById('errorMessage');
    const successElement = document.getElementById('successMessage');
    
    if (errorElement) errorElement.style.display = 'none';
    if (successElement) successElement.style.display = 'none';
}



