# Fruit Delivery App

Это демонстрационное приложение для учёта поставок фруктов.  
Включает три основные составляющие:
1. **Backend**: Spring Boot (Gradle) + PostgreSQL
2. **Frontend**: React
3. **PostgreSQL**: в Docker-контейнере

---

## Структура репозитория

```
fruit-delivery/
├─ docker-compose.yml
├─ build_and_run.bat         # Скрипт для Windows (cmd)
├─ build_and_run.sh          # Скрипт для Linux/macOS (bash)
├─ backend/                  # Исходники Spring Boot (Gradle)
└─ frontend/                 # Исходники React
```

---

## Требования

1. **Docker** и **Docker Compose** должны быть установлены и запущены (Docker Desktop на Windows, docker engine на Linux/macOS).
2. (Опционально) Для локальной сборки:
   - **Node.js** (если хотите собрать фронтенд вручную).
   - **Gradle** или Gradle Wrapper (если хотите собирать бэкенд вручную).

---

## Шаги запуска

### Использовать предоставленные скрипты

1. Убедитесь, что Docker **запущен**.
2. Запустите **один** из скриптов (в зависимости от вашей среды):
   - **Windows (cmd)**:  
     ```bat
     build_and_run.bat
   - **Linux/macOS**:  
     ```bash
     ./build_and_run.sh
     ```
3. Скрипт соберёт фронтенд, соберёт бэкенд, а затем поднимет Docker Compose в фоновом режиме.


---

## Доступ к сервисам

- **PostgreSQL**: на `localhost:5432` (логин/пароль указываются в `docker-compose.yml` или в `application.properties` бэкенда).
- **Backend (Spring Boot)**: на `http://localhost:8080`.
- **Frontend (React)**: на `http://localhost:3000`.

> При необходимости измените порты в `docker-compose.yml`.

---

## Краткая архитектура

- **React** (Frontend) взаимодействует с **Spring Boot** (Backend) по REST API (например, `GET /api/suppliers`, `POST /api/deliveries` и т.д.).
- **Spring Boot** использует базу **PostgreSQL** для хранения данных (через JDBC и Hibernate).
- **Docker Compose** создаёт общую сеть, позволяя фронтенду, бэкенду и базе данных общаться между собой.

---