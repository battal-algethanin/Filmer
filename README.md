# Filmer

## Project Overview
Filmer is a full-stack web application that allows users to browse, search, and rent movies from the IMDb dataset. This project is developed as part of the **SWE 481 (Advanced Web Applications Engineering)** course at King Saud University, College of Computer and Information Sciences.

The project is inspired by the University of California, Irvine course CS122B and follows modern full-stack development practices, emphasizing security, scalability, and software engineering best practices.

### Project Objectives
- Design and implement a fully functional, secure, and scalable web application
- Apply RESTful API principles and modern web development patterns
- Implement database integration with proper schema design
- Develop authentication and authorization mechanisms
- Write and maintain automated tests
- Address performance, security, and scalability concerns
- Follow collaborative development workflows using Git and GitHub

### Key Features
- Movie browsing and search functionality
- Movie rental system
- User authentication and access control
- Responsive user interface
- RESTful API backend
- Secure data handling and HTTPS communication
- Performance-optimized database queries

## Team Members
| Name | Student ID |
|------|------------|
| **Battal Algethanin** | **444100185** |
| **Bader Aldakhil** | **444102492** |
| **Faisal Alangari** | **444101279** |
| **Ibrahim Alathel** | **444105821** |

## Technology Stack
* **Frontend:** Angular (v16+)
* **Backend:** Spring Boot with Java 17
* **Database:** PostgreSQL 15
* **Development Tools:** Docker, Maven, Git, pgAdmin
* **Testing:** JUnit, Jasmine/Karma
* **CI/CD:** GitHub Actions

## Repository Structure
```
Filmer/
├── backend/          # Spring Boot server application
│   ├── src/          # Java source code
│   ├── lib/          # External dependencies
│   ├── data/         # IMDb dataset files
│   └── pom.xml       # Maven configuration
├── frontend/         # Angular client application
├── external/         # External tools (JDK)
├── .github/          # CI/CD workflows and GitHub Actions
├── docker-compose.yaml
└── README.md
```

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

### Quick Start with Docker

#### 1. Clone the Repository
```bash
git clone https://github.com/battal-algethanin/Filmer.git
cd Filmer
```

#### 2. Start Services
```bash
docker-compose up -d
```

This will start PostgreSQL and pgAdmin. The database schema will be automatically initialized.

#### 3. Access pgAdmin
- Open browser: **http://localhost:5050**
- Login credentials are configured in `docker-compose.yml`
- Add server connection using details from `docker-compose.yml`

#### 4. Configure Application
Copy the example environment file and configure your local settings:
```bash
cd backend
cp .env.example .env
```

Edit `.env` file with your local database configuration.

#### 5. Run Backend
```bash
cd backend
mvn spring-boot:run
```

#### 6. Run Frontend
```bash
cd frontend
npm install
npm start
```

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
## Useful Commands

### Docker
```bash
docker-compose up -d          # Start services
docker-compose down           # Stop services
docker-compose down -v        # Stop and remove data
docker-compose logs -f        # View logs
docker-compose ps             # Check status
```

### Development
```bash
# Backend
cd backend
mvn clean install
mvn spring-boot:run

# Frontend
cd frontend
npm install
npm start
```

## Troubleshooting

**Port conflicts**: If ports 5432 or 5050 are in use, modify port mappings in `docker-compose.yml`

**Database connection issues**: Verify environment variables match `docker-compose.yml` configuration

**Schema not loading**: Run `docker-compose down -v && docker-compose up -d` to reset database

---

## Environment Configuration

**Important:** This project uses environment variables for sensitive configuration. Never commit credentials, API keys, or secrets to GitHub.

Create a `.env` file in the backend directory with your local database configuration:
```
DB_URL=jdbc:postgresql://localhost:5432/filmer
DB_USER=your_username
DB_PASSWORD=your_password
```

The `.env` file is included in `.gitignore` and will not be committed to the repository.

## Development Workflow

### Branching Strategy
- Each team member works on their own development branch
- All changes must be submitted via Pull Requests (PRs)
- PRs require at least two reviewer approvals before merging
- Direct commits to main branch are blocked

### Code Quality
- All code must pass automated tests before merging
- GitHub Actions run linting, formatting, and testing checks
- Follow clean code principles and proper documentation
- All API endpoints must be documented with request/response formats

## Testing

This project includes comprehensive test coverage:
- **Unit Tests**: Backend services and frontend components
- **Integration Tests**: API endpoints and database operations
- **End-to-End Tests**: Complete user workflows
- **Performance Tests**: Response time and load testing

Run tests before submitting any PR:
```bash
# Backend tests
cd backend
mvn test

# Frontend tests
cd frontend
npm test
```

## Academic Integrity

This project is developed for educational purposes as part of the SWE 481 course at King Saud University, College of Computer and Information Sciences. All work must follow academic integrity policies.

**Course Instructor:** Dr. Mohammad Abdullah - Alwadud

---

*This project follows the specifications and requirements outlined in the SWE 481 course curriculum and is inspired by UC Irvine's CS122B course.*