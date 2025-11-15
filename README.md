# Bank Management System

A modern web-based Bank Management System built with Spring Boot, featuring a beautiful and responsive frontend with HTML, CSS, and JavaScript.

## 🚀 Features

### Core Banking Features
- **User Authentication**: Secure login with card number and PIN
- **Account Management**: Complete user registration and account setup
- **Balance Inquiry**: Real-time account balance checking
- **Deposit**: Money deposit functionality with transaction history
- **Withdrawal**: Secure money withdrawal with balance validation
- **Fast Cash**: Quick withdrawal with predefined amounts
- **Mini Statement**: View recent transactions (last 5)
- **PIN Change**: Secure PIN modification
- **Transaction History**: Complete transaction records

### Technical Features
- **Modern UI/UX**: Responsive design with gradient backgrounds and smooth animations
- **JWT Authentication**: Secure token-based authentication
- **Spring Security**: Comprehensive security implementation
- **RESTful API**: Clean and well-structured API endpoints
- **Database Integration**: MySQL database with JPA/Hibernate
- **Real-time Updates**: Dynamic balance and transaction updates
- **Mobile Responsive**: Optimized for all device sizes

## 🛠️ Technology Stack

### Backend
- **Java 17**
- **Spring Boot 3.2.0**
- **Spring Security**
- **Spring Data JPA**
- **MySQL Database**
- **JWT (JSON Web Tokens)**
- **Maven**

### Frontend
- **HTML5**
- **CSS3** (with modern features like backdrop-filter, gradients)
- **Vanilla JavaScript** (ES6+)
- **Font Awesome Icons**
- **Google Fonts (Poppins)**

## 📋 Prerequisites

Before running the application, ensure you have:

- **Java 17** or higher
- **Maven 3.6+**
- **MySQL 8.0+**
- **IDE** (IntelliJ IDEA, Eclipse, or VS Code)

## 🔧 Installation & Setup

### 1. Clone the Repository
```bash
git clone <repository-url>
cd bank-management-system
```

### 2. Database Setup
Create a MySQL database named `bankmanagementsystem`:

```sql
CREATE DATABASE bankmanagementsystem;
```

### 3. Database Configuration
Update the database credentials in `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/bankmanagementsystem?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
spring.datasource.username=your_username
spring.datasource.password=your_password
```

### 4. Build and Run
```bash
# Build the project
mvn clean install

# Run the application
mvn spring-boot:run
```

The application will be available at: `http://localhost:8080`

## 📱 Usage

### 1. Account Registration
- Navigate to the signup page
- Fill in all required personal and address details
- Set your PIN
- Submit the form to create an account
- Note down your generated card number

### 2. Login
- Enter your card number and PIN
- Click "Sign In" to access your dashboard

### 3. Dashboard Features
- **View Balance**: See your current account balance
- **Quick Actions**: Access deposit, withdraw, and transfer options
- **Fast Cash**: Quick withdrawal with predefined amounts (₹500, ₹1000, ₹2000, ₹5000, ₹10000, ₹15000)
- **Recent Transactions**: View your last 5 transactions
- **Banking Services**: Access mini statement, PIN change, and profile options

### 4. Transactions
- **Deposit**: Add money to your account with optional description
- **Withdraw**: Remove money with balance validation and daily limits
- **Mini Statement**: View detailed transaction history
- **Balance Inquiry**: Check current balance anytime

## 🗄️ Database Schema

The application uses three main entities:

### Users Table
- Personal information (name, DOB, gender, etc.)
- Address details
- Additional information (religion, occupation, etc.)
- Unique form number for each user

### Login Table (Accounts)
- Card number (unique)
- PIN (encrypted)
- Form number reference
- Account creation timestamps

### Bank Table (Transactions)
- Transaction ID
- PIN reference
- Transaction date and time
- Transaction type (Deposit/Withdrawal)
- Amount
- Optional description
- Balance after transaction

## 🔒 Security Features

- **JWT Authentication**: Secure token-based session management
- **Password Encryption**: PINs are encrypted using BCrypt
- **CORS Configuration**: Proper cross-origin resource sharing setup
- **Input Validation**: Client and server-side validation
- **SQL Injection Prevention**: Using JPA/Hibernate parameterized queries
- **XSS Protection**: Proper input sanitization

## 🎨 UI/UX Features

- **Modern Design**: Beautiful gradient backgrounds and glassmorphism effects
- **Responsive Layout**: Works perfectly on desktop, tablet, and mobile
- **Smooth Animations**: Hover effects and transitions
- **Intuitive Navigation**: Easy-to-use interface
- **Real-time Feedback**: Success and error messages
- **Loading States**: Visual feedback during API calls
- **Accessibility**: Proper contrast and readable fonts

## 📁 Project Structure

```
src/
├── main/
│   ├── java/com/bank/
│   │   ├── BankManagementSystemApplication.java
│   │   ├── config/
│   │   │   └── SecurityConfig.java
│   │   ├── controller/
│   │   │   ├── AuthController.java
│   │   │   ├── TransactionController.java
│   │   │   └── WebController.java
│   │   ├── dto/
│   │   │   ├── LoginRequest.java
│   │   │   ├── LoginResponse.java
│   │   │   └── SignupRequest.java
│   │   ├── entity/
│   │   │   ├── Account.java
│   │   │   ├── Transaction.java
│   │   │   └── User.java
│   │   ├── repository/
│   │   │   ├── AccountRepository.java
│   │   │   ├── TransactionRepository.java
│   │   │   └── UserRepository.java
│   │   ├── security/
│   │   │   ├── CustomUserDetailsService.java
│   │   │   ├── JwtAuthenticationEntryPoint.java
│   │   │   └── JwtRequestFilter.java
│   │   ├── service/
│   │   │   ├── AuthService.java
│   │   │   └── TransactionService.java
│   │   └── util/
│   │       └── JwtUtil.java
│   └── resources/
│       ├── static/
│       │   ├── css/
│       │   │   └── style.css
│       │   └── js/
│       │       ├── auth.js
│       │       ├── dashboard.js
│       │       ├── deposit.js
│       │       ├── ministatement.js
│       │       ├── signup.js
│       │       └── withdraw.js
│       ├── templates/
│       │   ├── dashboard.html
│       │   ├── deposit.html
│       │   ├── login.html
│       │   ├── ministatement.html
│       │   ├── signup.html
│       │   └── withdraw.html
│       └── application.properties
└── test/
```

## 🚀 API Endpoints

### Authentication
- `POST /api/auth/login` - User login
- `POST /api/auth/signup` - User registration
- `POST /api/auth/change-pin` - Change PIN

### Transactions
- `GET /api/transactions/balance` - Get account balance
- `POST /api/transactions/deposit` - Deposit money
- `POST /api/transactions/withdraw` - Withdraw money
- `GET /api/transactions/fast-cash` - Fast cash withdrawal
- `GET /api/transactions/history` - Get transaction history
- `GET /api/transactions/mini-statement` - Get mini statement

### Web Pages
- `GET /` - Login page
- `GET /login` - Login page
- `GET /signup` - Registration page
- `GET /dashboard` - User dashboard
- `GET /deposit` - Deposit page
- `GET /withdraw` - Withdrawal page
- `GET /ministatement` - Mini statement page

## 🔧 Configuration

### Application Properties
```properties
# Server Configuration
server.port=8080

# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/bankmanagementsystem
spring.datasource.username=root
spring.datasource.password=your_password

# JPA Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# JWT Configuration
jwt.secret=mySecretKey
jwt.expiration=86400000
```

## 🐛 Troubleshooting

### Common Issues

1. **Database Connection Error**
   - Ensure MySQL is running
   - Check database credentials
   - Verify database exists

2. **Port Already in Use**
   - Change port in `application.properties`
   - Or stop the process using port 8080

3. **Build Errors**
   - Ensure Java 17 is installed
   - Run `mvn clean install` to rebuild

4. **Frontend Not Loading**
   - Check browser console for errors
   - Ensure all static resources are accessible

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests if applicable
5. Submit a pull request

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 🙏 Acknowledgments

- Spring Boot community for excellent documentation
- Font Awesome for beautiful icons
- Google Fonts for typography
- Modern CSS techniques for stunning UI

## 📞 Support

For support or questions, please contact:
- Email: support@bankmanagement.com
- Documentation: [Link to documentation]

---

**Note**: This is a demo application for educational purposes. For production use, additional security measures and compliance requirements should be implemented.



