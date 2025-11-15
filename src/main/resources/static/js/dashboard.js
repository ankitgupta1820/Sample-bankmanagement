// Dashboard JavaScript
document.addEventListener('DOMContentLoaded', function() {
    // Check authentication
    if (!requireAuth()) {
        return;
    }
    
    // Initialize dashboard
    initializeDashboard();
    
    // Load initial data
    loadDashboardData();
    
    // Set up event listeners
    setupEventListeners();
});

function initializeDashboard() {
    // Display card number (masked)
    const cardNumber = getCardNumber();
    if (cardNumber) {
        const maskedCard = maskCardNumber(cardNumber);
        document.getElementById('cardNumber').textContent = `Card: ${maskedCard}`;
    }
    
    // Set last login time
    const lastLogin = new Date().toLocaleString();
    document.getElementById('lastLogin').textContent = lastLogin;
}

function loadDashboardData() {
    loadBalance();
    loadRecentTransactions();
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

function loadRecentTransactions() {
    const token = getAuthToken();
    
    fetch('/api/transactions/mini-statement', {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json'
        }
    })
    .then(response => response.json())
    .then(data => {
        if (data.transactions) {
            displayRecentTransactions(data.transactions);
        }
    })
    .catch(error => {
        console.error('Error loading transactions:', error);
        showError('Failed to load recent transactions');
    });
}

function displayRecentTransactions(transactions) {
    const transactionsList = document.getElementById('recentTransactions');
    
    if (transactions.length === 0) {
        transactionsList.innerHTML = '<div class="no-transactions">No recent transactions</div>';
        return;
    }
    
    const transactionsHTML = transactions.map(transaction => {
        const date = new Date(transaction.date).toLocaleDateString();
        const time = new Date(transaction.date).toLocaleTimeString();
        const amount = formatCurrency(transaction.amount);
        const type = transaction.type.toLowerCase();
        const isDeposit = type === 'deposit';
        
        return `
            <div class="transaction-item">
                <div class="transaction-info">
                    <div class="transaction-type ${type}">${type.charAt(0).toUpperCase() + type.slice(1)}</div>
                    <div class="transaction-date">${date} ${time}</div>
                </div>
                <div class="transaction-amount ${isDeposit ? 'positive' : 'negative'}">
                    ${isDeposit ? '+' : '-'}${amount}
                </div>
            </div>
        `;
    }).join('');
    
    transactionsList.innerHTML = transactionsHTML;
}

function setupEventListeners() {
    // Use event delegation for fast cash buttons
    document.addEventListener('click', function(event) {
        const fastCashBtn = event.target.closest('.fast-cash-btn');
        if (!fastCashBtn || fastCashBtn.disabled) return;
        
        const amount = parseFloat(fastCashBtn.textContent.replace(/[^0-9.]/g, ''));
        fastCash(amount);
    });
}

async function handleFastCash(amount) {
    const token = getAuthToken();
    
    // Check if token exists
    if (!token) {
        showError('Please log in to continue');
        setTimeout(() => window.location.href = '/login', 1500);
        throw new Error('No authentication token found');
    }

    try {
        const response = await fetch(`/api/transactions/fast-cash?amount=${amount}`, {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            }
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
            throw new Error(data.message || 'Fast cash withdrawal failed');
        }

        showSuccess(data.message || 'Transaction successful');
        loadDashboardData();
        return data;
        
    } catch (error) {
        console.error('Transaction error:', error);
        if (!error.message.includes('Session expired') && !error.message.includes('not authenticated')) {
            showError(error.message || 'An error occurred during the transaction');
        }
        throw error;
    }
}

function refreshBalance() {
    loadBalance();
    showSuccess('Balance refreshed');
}

function navigateTo(page) {
    switch(page) {
        case 'deposit':
            window.location.href = '/deposit';
            break;
        case 'withdraw':
            window.location.href = '/withdraw';
            break;
        case 'transactions':
            window.location.href = '/transactions';
            break;
        case 'ministatement':
            window.location.href = '/ministatement';
            break;
        case 'pinchange':
            window.location.href = '/pinchange';
            break;
        case 'profile':
            showError('Profile page coming soon');
            break;
        case 'support':
            showError('Support page coming soon');
            break;
        case 'transfer':
            showError('Transfer functionality coming soon');
            break;
        default:
            console.log('Unknown page:', page);
    }
}

function logout() {
    // Clear stored data
    localStorage.removeItem('token');
    localStorage.removeItem('cardNumber');
    
    // Redirect to login
    window.location.href = '/login';
}

let isFastCashProcessing = false;

async function fastCash(amount) {
    if (isFastCashProcessing) {
        console.log('Fast cash operation already in progress');
        return;
    }
    
    const confirmed = confirm(`Are you sure you want to withdraw ₹${formatCurrency(amount)}?`);
    if (!confirmed) return;
    
    isFastCashProcessing = true;
    const buttons = document.querySelectorAll('.fast-cash-btn');
    
    try {
        // Disable all buttons and show loading state
        buttons.forEach(btn => {
            btn.disabled = true;
            btn.classList.add('processing');
        });
        
        await handleFastCash(amount);
        
    } catch (error) {
        // Error is already handled in handleFastCash
        console.error('Fast cash error:', error);
    } finally {
        // Re-enable buttons
        isFastCashProcessing = false;
        buttons.forEach(btn => {
            btn.disabled = false;
            btn.classList.remove('processing');
        });
    }
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



