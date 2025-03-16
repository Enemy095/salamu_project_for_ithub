@echo off

echo ==== Запуск docker-compose ====
docker-compose up --build -d
if errorlevel 1 (
    echo [Ошибка] Не удалось поднять docker-compose
    exit /b 1
)

echo ==== Логи сервисов (нажмите Ctrl+C, чтобы выйти) ====
docker-compose logs -f