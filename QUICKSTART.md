# 🚀 Filmer - Quick Start Guide

## Get Started in 3 Minutes!

### 1️⃣ Install Docker Desktop
Download and install: https://www.docker.com/products/docker-desktop/

### 2️⃣ Clone & Start
```bash
git clone https://github.com/battal-algethanin/Filmer.git
cd Filmer
docker-compose up -d
```

### 3️⃣ Access pgAdmin
1. Open browser: **http://localhost:5050**
2. Login: `admin@filmer.com` / `admin`
3. Add server:
   - Name: `Filmer Database`
   - Host: `postgres`
   - Port: `5432`
   - Database: `filmer`
   - Username: `postgres`
   - Password: `filmer_dev_password`

### ✅ Done!
Your database is ready with all 9 tables created automatically!

---

## What's Running?

- **PostgreSQL**: localhost:5432
- **pgAdmin**: http://localhost:5050

## Common Commands

```bash
# Stop services
docker-compose down

# Restart services
docker-compose restart

# View logs
docker-compose logs -f

# Fresh start (removes all data)
docker-compose down -v && docker-compose up -d
```

## Next Steps

See [README.md](README.md) for complete documentation.
