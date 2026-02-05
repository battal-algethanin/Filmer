# Filmer (SWE 481)
## this project is under construction
## Project Overview
This project is part of the **SWE 481 (Advanced Web Applications Engineering)** course at King Saud University. It is a full-stack web application inspired by the CS122B course, designed to allow users to browse, search, and rent movies using the IMDb dataset.

## Team Members
| Name | Student ID |
|------|------------|
| **Battal Algethanin** | **444100185** |
| **Bader Aldakhil** | **444102492** |
| **Faisal Alangari** | **444101279** |
| **Ibrahim Alathel** | **444105821** |

## Technical Stack
* **Frontend:** Angular (v16+)
* **Backend:** Spring Boot (Java 17+)
* **Database:** PostgreSQL

## Project Structure
The repository is organized as follows:
* frontend: Source code for the Angular client application.
* backend: Source code for the Spring Boot server application.
* .github/workflows: CI/CD pipelines for automated testing.

## Setup & Installation

### Prerequisites
* **Docker Desktop** - [Download here](https://www.docker.com/products/docker-desktop/)
  - Includes Docker and Docker Compose
  - Works on Windows, Mac, and Linux
* **Java JDK 17+** - [Download here](https://www.oracle.com/java/technologies/downloads/)
* **Node.js & npm** (v18+) - [Download here](https://nodejs.org/)
* **Maven** (v3.8+) - Usually bundled with IDE or [download here](https://maven.apache.org/download.cgi)
* **Git** - [Download here](https://git-scm.com/downloads)

---

### 🐳 Docker Setup (Recommended)

This is the **easiest and fastest** way to get started. No manual PostgreSQL installation needed!

#### Step 1: Clone the Repository
```bash
git clone https://github.com/battal-algethanin/Filmer.git
cd Filmer
```

#### Step 2: Start Docker Services
```bash
# Start PostgreSQL and pgAdmin
docker-compose up -d

# Verify services are running
docker-compose ps
```

**What this does:**
- ✅ Downloads and runs PostgreSQL 15 in a container
- ✅ Downloads and runs pgAdmin (database management tool)
- ✅ Automatically creates the `filmer` database
- ✅ Automatically runs `schema.sql` to create all tables
- ✅ Sets up persistent storage (data survives container restarts)

#### Step 3: Access pgAdmin (Database Management)

1. Open your browser and go to: **http://localhost:5050**
2. Login with:
   - **Email:** `admin@filmer.com`
   - **Password:** `admin`

3. **Add PostgreSQL Server in pgAdmin:**
   - Right-click "Servers" → "Register" → "Server"
   - **General Tab:**
     - Name: `Filmer Database`
   - **Connection Tab:**
     - Host: `postgres` (the service name from docker-compose)
     - Port: `5432`
     - Database: `filmer`
     - Username: `postgres`
     - Password: `filmer_dev_password`
   - Click "Save"

4. **Verify Schema:**
   - Navigate to: Servers → Filmer Database → Databases → filmer → Schemas → public → Tables
   - You should see all 9 tables: movies, stars, genres, customers, etc.

#### Step 4: Configure Environment Variables

**Option A: Create `.env` file (Easiest)**
```bash
# Copy the example file
cd backend
cp .env.example .env
```

The `.env` file already has the correct values for Docker:
```
DB_URL=jdbc:postgresql://localhost:5432/filmer
DB_USER=postgres
DB_PASSWORD=filmer_dev_password
```

**Option B: Set in IntelliJ IDEA**
1. Open `Run > Edit Configurations`
2. Select your Spring Boot application
3. Under `Environment Variables`, add:
   ```
   DB_URL=jdbc:postgresql://localhost:5432/filmer
   DB_USER=postgres
   DB_PASSWORD=filmer_dev_password
   ```

**Option C: Set in Terminal (Windows PowerShell)**
```powershell
$env:DB_URL="jdbc:postgresql://localhost:5432/filmer"
$env:DB_USER="postgres"
$env:DB_PASSWORD="filmer_dev_password"
```

**Option D: Set in Terminal (Mac/Linux)**
```bash
export DB_URL="jdbc:postgresql://localhost:5432/filmer"
export DB_USER="postgres"
export DB_PASSWORD="filmer_dev_password"
```

#### Step 5: Run the Backend (Future Phase)

**Using Maven:**
```bash
cd backend
mvn spring-boot:run
```

**Using IDE:**
- Run the main application class (will be created in future phases)

The backend will start on `http://localhost:8080`

#### Step 6: Run the Frontend (Future Phase)

```bash
cd frontend
npm install
npm start
```

The frontend will start on `http://localhost:4200`

---

### 🛠️ Docker Commands Reference

```bash
# Start all services
docker-compose up -d

# Stop all services (keeps data)
docker-compose down

# Stop and remove all data (fresh start)
docker-compose down -v

# View logs
docker-compose logs -f

# View PostgreSQL logs only
docker-compose logs -f postgres

# Restart services
docker-compose restart

# Check service status
docker-compose ps

# Access PostgreSQL CLI directly
docker exec -it filmer-postgres psql -U postgres -d filmer
```

---

### 🔍 Troubleshooting

**Issue:** `Port 5432 is already in use`  
**Solution:** You have PostgreSQL running locally. Either:
- Stop local PostgreSQL: `net stop postgresql-x64-15` (Windows)
- Change Docker port in `docker-compose.yml`: `"5433:5432"`

**Issue:** `Port 5050 is already in use`  
**Solution:** Change pgAdmin port in `docker-compose.yml`: `"5051:80"`

**Issue:** Docker containers won't start  
**Solution:** 
```bash
docker-compose down -v
docker-compose up -d
```

**Issue:** Can't connect to database from Spring Boot  
**Solution:** Verify environment variables are set correctly and match docker-compose.yml

**Issue:** Schema tables not created  
**Solution:** 
```bash
# Recreate database with schema
docker-compose down -v
docker-compose up -d
```

---

### 📋 Development Checklist

Before starting development, ensure:
- [x] Docker Desktop is installed and running
- [x] `docker-compose up -d` executed successfully
- [x] pgAdmin accessible at http://localhost:5050
- [x] Database `filmer` visible in pgAdmin with 9 tables
- [x] Environment variables configured (`.env` file or IDE)
- [ ] Backend starts without errors (Phase 3)
- [ ] Frontend starts without errors (Phase 3)

---

### 🔒 Security Notes

> **IMPORTANT:** The credentials in `docker-compose.yml` are for **local development only**!
> 
> - ✅ Safe for local development
> - ❌ **NEVER use these in production**
> - ✅ Production should use proper secret management (AWS Secrets Manager, Azure Key Vault, etc.)
> - ✅ The `.env` file is in `.gitignore` and won't be committed

---

### 📊 What's Running?

| Service | URL | Purpose |
|---------|-----|---------|
| **PostgreSQL** | `localhost:5432` | Database server |
| **pgAdmin** | http://localhost:5050 | Database management UI |
| **Backend** | http://localhost:8080 | Spring Boot API (Phase 3) |
| **Frontend** | http://localhost:4200 | Angular app (Phase 3) |

---


### Prerequisites
* Java JDK 17+
* Node.js & npm
* PostgreSQL

## License
This project is for educational purposes under the supervision of **Dr. Mohammad Abdullah - Alwadud**.
