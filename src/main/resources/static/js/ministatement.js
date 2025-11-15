// Mini Statement JavaScript
document.addEventListener('DOMContentLoaded', function() {
    // Check authentication
    if (!requireAuth()) {
        return;
    }
    
    // Initialize mini statement page
    initializeMiniStatementPage();
    
    // Load initial data
    loadMiniStatement();
});

function initializeMiniStatementPage() {
    // Display card number (masked)
    const cardNumber = getCardNumber();
    if (cardNumber) {
        const maskedCard = maskCardNumber(cardNumber);
        document.getElementById('cardNumber').textContent = `Card: ${maskedCard}`;
    }
    
    // Set last updated time
    document.getElementById('lastUpdated').textContent = new Date().toLocaleString();
}

function loadMiniStatement() {
    const token = getAuthToken();
    
    // Load balance
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
                <span class="value">${formatCurrency(data.balance)}</span>
            `;
        }
    })
    .catch(error => {
        console.error('Error loading balance:', error);
        showError('Failed to load balance');
    });
    
    // Load mini statement
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
            displayTransactions(data.transactions);
        }
    })
    .catch(error => {
        console.error('Error loading mini statement:', error);
        showError('Failed to load mini statement');
        document.getElementById('transactionsList').innerHTML = '<div class="error">Failed to load transactions</div>';
    });
}

function displayTransactions(transactions) {
    const transactionsList = document.getElementById('transactionsList');
    
    if (transactions.length === 0) {
        transactionsList.innerHTML = '<div class="no-transactions">No transactions found</div>';
        return;
    }
    
    const transactionsHTML = transactions.map(transaction => {
        const date = new Date(transaction.date).toLocaleDateString();
        const time = new Date(transaction.date).toLocaleTimeString();
        const amount = formatCurrency(transaction.amount);
        const type = transaction.type.toLowerCase();
        const isDeposit = type === 'deposit';
        const description = transaction.description || '';
        
        return `
            <div class="transaction-item">
                <div class="transaction-info">
                    <div class="transaction-type ${type}">
                        <i class="fas ${isDeposit ? 'fa-arrow-down' : 'fa-arrow-up'}"></i>
                        ${type.charAt(0).toUpperCase() + type.slice(1)}
                    </div>
                    <div class="transaction-date">${date} at ${time}</div>
                    ${description ? `<div class="transaction-description">${description}</div>` : ''}
                </div>
                <div class="transaction-amount ${isDeposit ? 'positive' : 'negative'}">
                    ${isDeposit ? '+' : '-'}₹${amount}
                </div>
            </div>
        `;
    }).join('');
    
    transactionsList.innerHTML = transactionsHTML;
}

function refreshStatement() {
    loadMiniStatement();
    showSuccess('Statement refreshed');
}

function printStatement() {
    const printContent = generatePrintContent();
    const printWindow = window.open('', '_blank');
    printWindow.document.write(printContent);
    printWindow.document.close();
    printWindow.print();
}

function downloadStatement() {
    // For now, just show a message
    showError('PDF download feature will be available soon');
}

function generatePrintContent() {
    const cardNumber = getCardNumber();
    const maskedCard = maskCardNumber(cardNumber);
    const currentDate = new Date().toLocaleString();
    const balance = document.querySelector('.current-balance .value').textContent;
    
    return `
        <!DOCTYPE html>
        <html>
        <head>
            <title>Mini Statement - ${maskedCard}</title>
            <style>
                body { font-family: Arial, sans-serif; margin: 20px; }
                .header { text-align: center; margin-bottom: 30px; }
                .info { margin-bottom: 20px; }
                .transaction { margin-bottom: 10px; padding: 10px; border-bottom: 1px solid #ccc; }
                .positive { color: green; }
                .negative { color: red; }
            </style>
        </head>
        <body>
            <div class="header">
                <h1>Bank Management System</h1>
                <h2>Mini Statement</h2>
            </div>
            <div class="info">
                <p><strong>Card Number:</strong> ${maskedCard}</p>
                <p><strong>Statement Date:</strong> ${currentDate}</p>
                <p><strong>Current Balance:</strong> ₹${balance}</p>
            </div>
            <div class="transactions">
                ${document.getElementById('transactionsList').innerHTML}
            </div>
        </body>
        </html>
    `;
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



