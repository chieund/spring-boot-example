# Makefile for Order API Docker operations

.PHONY: help build up down restart logs clean test

# Default target
help:
	@echo "Available commands:"
	@echo "  build     - Build Docker images"
	@echo "  up        - Start all services"
	@echo "  up-tools  - Start all services including pgAdmin"
	@echo "  down      - Stop all services"
	@echo "  restart   - Restart all services"
	@echo "  logs      - Show logs for all services"
	@echo "  logs-app  - Show logs for Spring Boot app only"
	@echo "  logs-db   - Show logs for PostgreSQL only"
	@echo "  clean     - Remove containers, networks, and volumes"
	@echo "  test      - Run API tests"
	@echo "  status    - Show status of all services"

# Build Docker images
build:
	@echo "Building Docker images..."
	docker-compose build --no-cache

# Start all services
up:
	@echo "Starting services..."
	docker-compose up -d

# Start all services including pgAdmin
up-tools:
	@echo "Starting services with tools..."
	docker-compose --profile tools up -d

# Stop all services
down:
	@echo "Stopping services..."
	docker-compose down

# Restart all services
restart:
	@echo "Restarting services..."
	docker-compose restart

# Show logs for all services
logs:
	docker-compose logs -f

# Show logs for Spring Boot app only
logs-app:
	docker-compose logs -f order-api

# Show logs for PostgreSQL only
logs-db:
	docker-compose logs -f postgres

# Remove everything (containers, networks, volumes)
clean:
	@echo "Cleaning up..."
	docker-compose down -v --remove-orphans
	docker system prune -f

# Run API tests
test:
	@echo "Testing API endpoints..."
	@echo "Waiting for services to be ready..."
	@sleep 10
	@echo "Testing GET /api/orders..."
	curl -s http://localhost:8089/api/orders | jq . || echo "API not ready yet"

# Show status of all services
status:
	docker-compose ps