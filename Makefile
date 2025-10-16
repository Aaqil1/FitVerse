SHELL := /bin/bash

.PHONY: help build test clean docker-up docker-down docker-logs frontend-install

help:
	@echo "Available targets:"
	@echo "  build              Run mvn clean install for the entire monorepo"
	@echo "  test               Run mvn verify with coverage checks"
	@echo "  clean              Remove Maven build artifacts"
	@echo "  docker-up          Start infrastructure stack via docker-compose"
	@echo "  docker-down        Stop stack and remove containers"
	@echo "  docker-logs        Tail docker-compose logs"
	@echo "  frontend-install   Install frontend dependencies using pnpm"

build:
	mvn -T1C clean install

test:
	mvn -T1C verify

clean:
	find . -type d -name target -prune -exec rm -rf {} +
@echo "Available targets:"
@echo "  build              Run mvn clean install for the entire monorepo"
@echo "  test               Run mvn verify with coverage checks"
@echo "  clean              Remove Maven build artifacts"
@echo "  docker-up          Start infrastructure stack via docker-compose"
@echo "  docker-down        Stop stack and remove containers"
@echo "  docker-logs        Tail docker-compose logs"
@echo "  frontend-install   Install frontend dependencies using pnpm"

build:
mvn -T1C clean install

test:
mvn -T1C verify

clean:
find . -type d -name target -prune -exec rm -rf {} +

DOCKER_COMPOSE := docker compose --env-file .env

docker-up:
	$(DOCKER_COMPOSE) up --build -d

docker-down:
	$(DOCKER_COMPOSE) down --remove-orphans

docker-logs:
	$(DOCKER_COMPOSE) logs -f

frontend-install:
	@if [ -d frontend-react ]; then \
		cd frontend-react && pnpm install; \
	else \
		echo "frontend-react module not present yet"; \
	fi
$(DOCKER_COMPOSE) up --build -d

docker-down:
$(DOCKER_COMPOSE) down --remove-orphans

docker-logs:
$(DOCKER_COMPOSE) logs -f

frontend-install:
@if [ -d frontend-react ]; then cd frontend-react && pnpm install; else echo "frontend-react module not present yet"; fi
