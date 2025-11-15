# Bank Management System - Deployment Guide

## 🚀 Option 1: Deploy to Render (Recommended)

Render supports Java applications and provides free PostgreSQL/MySQL databases.

### Prerequisites
1. GitHub account
2. Render account (sign up at https://render.com)
3. Push your code to GitHub

### Steps:

#### 1. Prepare Your Repository
```bash
# Initialize git (if not already done)
git init
git add .
git commit -m "Initial commit"

# Create a new repository on GitHub and push
git remote add origin https://github.com/YOUR_USERNAME/bank-management-system.git
git branch -M main
git push -u origin main
```

#### 2. Deploy on Render

**Option A: Using render.yaml (Blueprint)**
1. Go to https://render.com/dashboard
2. Click "New +" → "Blueprint"
3. Connect your GitHub repository
4. Render will automatically detect `render.yaml` and create services
5. Click "Apply" to deploy

**Option B: Manual Setup**
1. **Create Database:**
   - Go to Render Dashboard
   - Click "New +" → "PostgreSQL" (or MySQL)
   - Name: `bank-db`
   - Database: `bankmanagementsystem`
   - User: `bankuser`
   - Click "Create Database"
   - Copy the "Internal Database URL"

2. **Create Web Service:**
   - Click "New +" → "Web Service"
   - Connect your GitHub repository
   - Configure:
     - **Name:** bank-management-system
     - **Environment:** Java
     - **Build Command:** `mvn clean package -DskipTests`
     - **Start Command:** `java -jar target/*.jar`
     - **Instance Type:** Free

3. **Set Environment Variables:**
   - `PORT` = 8080
   - `DATABASE_URL` = (paste your database internal URL)
   - `DB_USERNAME` = bankuser
   - `DB_PASSWORD` = (from database credentials)
   - `JWT_SECRET` = (generate a secure random string)

4. Click "Create Web Service"

#### 3. Access Your Application
- Your app will be available at: `https://bank-management-system-XXXX.onrender.com`
- First deployment takes 5-10 minutes

---

## 🌐 Option 2: Deploy to Railway

Railway is another excellent option for full-stack Java apps.

### Steps:
1. Go to https://railway.app
2. Click "Start a New Project"
3. Select "Deploy from GitHub repo"
4. Choose your repository
5. Railway auto-detects Java and deploys
6. Add MySQL database from Railway marketplace
7. Set environment variables (same as Render)

---

## 📦 Option 3: Deploy to Heroku

### Steps:
1. Install Heroku CLI
2. Create `Procfile`:
   ```
   web: java -jar target/*.jar
   ```
3. Deploy:
   ```bash
   heroku login
   heroku create bank-management-system
   heroku addons:create jawsdb:kitefin  # MySQL addon
   git push heroku main
   ```

---

## 🔧 Option 4: Deploy Backend to Render + Frontend to Netlify

If you want to separate frontend and backend:

### Backend (Render):
Follow Option 1 above

### Frontend (Netlify):
1. Extract static files from `src/main/resources/static` and `templates`
2. Update API URLs to point to Render backend
3. Deploy to Netlify:
   ```bash
   npm install -g netlify-cli
   netlify deploy --prod
   ```

---

## ⚙️ Important Notes

### Database Migration
- Render/Railway provide PostgreSQL for free
- To use PostgreSQL instead of MySQL:
  1. Update `pom.xml` - replace MySQL dependency with PostgreSQL
  2. Update `application.properties` dialect to PostgreSQL

### Security Considerations
1. **Never commit sensitive data** (passwords, API keys)
2. Use environment variables for all secrets
3. Change JWT secret in production
4. Enable HTTPS (automatic on Render/Railway)
5. Update CORS settings for production domain

### Environment Variables Required
- `PORT` - Server port (provided by platform)
- `DATABASE_URL` - Database connection string
- `DB_USERNAME` - Database username
- `DB_PASSWORD` - Database password
- `JWT_SECRET` - JWT signing secret (generate new for production)

### Testing Deployment
1. Test login functionality
2. Test all CRUD operations
3. Test Fast Cash feature
4. Test PIN change
5. Check database persistence

---

## 🐛 Troubleshooting

### Build Fails
- Check Java version in `pom.xml` matches Render's Java version
- Ensure all dependencies are in `pom.xml`
- Check build logs for specific errors

### Database Connection Issues
- Verify DATABASE_URL format
- Check database credentials
- Ensure database is in same region as web service

### Application Won't Start
- Check start command is correct
- Verify PORT environment variable
- Check application logs

---

## 📝 Post-Deployment Checklist

- [ ] Application loads successfully
- [ ] Login works
- [ ] Database operations work
- [ ] Fast Cash feature works
- [ ] PIN change works
- [ ] All pages load correctly
- [ ] No console errors
- [ ] HTTPS is enabled
- [ ] Environment variables are set
- [ ] Database backups configured

---

## 🔗 Useful Links

- Render Docs: https://render.com/docs
- Railway Docs: https://docs.railway.app
- Heroku Java: https://devcenter.heroku.com/categories/java-support
- Spring Boot Deployment: https://spring.io/guides/gs/spring-boot/
