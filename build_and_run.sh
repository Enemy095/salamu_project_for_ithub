#!/usr/bin/env bash
set -e

echo "==== Запуск docker-compose ===="
docker-compose up --build -d

echo "==== Логи сервисов (нажмите Ctrl+C, чтобы выйти) ===="
docker-compose logs -f