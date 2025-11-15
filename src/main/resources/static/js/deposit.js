// Deposit JavaScript
document.addEventListener('DOMContentLoaded', function() {
    // Check authentication
    if (!requireAuth()) {
        return;
    }
    
    // Initialize deposit page
    initializeDepositPage();
    
    // Load initial data
    loadBalance();
    
    // Set up event listeners
    setupEventListeners();
});

function initializeDepositPage() {
    // Display card number (masked)
    const cardNumber = getCardNumber();
    if (cardNumber) {
        const maskedCard = maskCardNumber(cardNumber);
        document.getElementById('cardNumber').textContent = `Card: ${maskedCard}`;
    }
    
    // Set up form
    const depositForm = document.getElementById('depositForm');
    depositForm.addEventListener('submit', handleDeposit);
}

function setupEventListeners() {
    // Amount buttons
    const amountButtons = document.querySelectorAll('.amount-btn');
    amountButtons.forEach(button => {
        button.addEventListener('click', function() {
            const amount = parseFloat(this.textContent.replace('₹', '').replace(',', ''));
            setAmount(amount);
        });
    });
    
    // Amount input validation
    const amountInput = document.getElementById('amount');
    amountInput.addEventListener('input', function() {
        // Only allow numbers and one decimal point
        this.value = this.value.replace(/[^0-9.]/g, '');
        
        // Ensure only one decimal point
        const parts = this.value.split('.');
        if (parts.length > 2) {
            this.value = parts[0] + '.' + parts.slice(1).join('');
        }
        
        // Limit to 2 decimal places
        if (parts[1] && parts[1].length > 2) {
            this.value = parts[0] + '.' + parts[1].slice(0, 2);
        }
    });
}

function handleDeposit(event) {
    event.preventDefault();
    
    const amount = parseFloat(document.getElementById('amount').value);
    const description = document.getElementById('description').value;
    
    if (!amount || amount <= 0) {
        showError('Please enter a valid amount');
        return;
    }
    
    if (amount < 1) {
        showError('Minimum deposit amount is ₹1');
        return;
    }
    
    if (amount > 100000) {
        showError('Maximum deposit amount is ₹1,00,000');
        return;
    }
    
    // Confirm deposit
    if (!confirm(`Are you sure you want to deposit ₹${formatCurrency(amount)}?`)) {
        return;
    }
    
    const token = getAuthToken();
    
    const formData = new FormData();
    formData.append('amount', amount);
    if (description) {
        formData.append('description', description);
    }
    
    fetch('/api/transactions/deposit', {
        method: 'POST',
        headers: {
            'Authorization': `Bearer ${token}`
        },
        body: formData
    })
    .then(response => response.json())
    .then(data => {
        if (data.message) {
            showSuccess(data.message);
            clearForm();
            loadBalance(); // Refresh balance
            
            // Redirect to dashboard after 2 seconds
            setTimeout(() => {
                window.location.href = '/dashboard';
            }, 2000);
        } else {
            showError(data.error || 'Deposit failed');
        }
    })
    .catch(error => {
        console.error('Error:', error);
        showError('Deposit failed. Please try again.');
    });
}

function loadBalance() {
    const token = getAuthToken();
    
    fetch('/api/transactions/balance', {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json'
        }
    })
    .then(response => response.json())
    .then(data => {
        if (data.balance !== undefined) {
            document.getElementById('currentBalance').innerHTML = `
                <span class="currency">₹</span>
                <span class="amount">${formatCurrency(data.balance)}</span>
            `;
        }
    })
    .catch(error => {
        console.error('Error loading balance:', error);
        showError('Failed to load balance');
    });
}

function setAmount(amount) {
    document.getElementById('amount').value = amount;
}

function clearForm() {
    document.getElementById('amount').value = '';
    document.getElementById('description').value = '';
    hideMessages();
}

function refreshBalance() {
    loadBalance();
    showSuccess('Balance refreshed');
}

function goBack() {
    window.location.href = '/dashboard';
}

// Utility functions
function maskCardNumber(cardNumber) {
    if (cardNumber.length >= 16) {
        return `${cardNumber.slice(0, 4)}-****-****-${cardNumber.slice(-4)}`;
    }
    return cardNumber;
}

function formatCurrency(amount) {
    return new Intl.NumberFormat('en-IN', {
        minimumFractionDigits: 2,
        maximumFractionDigits: 2
    }).format(amount);
}

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
    
    // Auto remove after 3 seconds
    setTimeout(() => {
        messageElement.remove();
    }, 3000);
}

function hideMessages() {
    const errorElement = document.getElementById('errorMessage');
    const successElement = document.getElementById('successMessage');
    
    if (errorElement) errorElement.style.display = 'none';
    if (successElement) successElement.style.display = 'none';
}

// Include auth utilities from auth.js
function getAuthToken() {
    return localStorage.getItem('token');
}

function getCardNumber() {
    return localStorage.getItem('cardNumber');
}

function requireAuth() {
    if (!getAuthToken()) {
        window.location.href = '/login';
        return false;
    }
    return true;
}



