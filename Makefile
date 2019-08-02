.PHONY: help
.DEFAULT_GOAL := help

help:
	@grep -E '^[a-zA-Z_-]+:.*?## .*$$' $(MAKEFILE_LIST) | awk 'BEGIN {FS = ":.*?## "}; {printf "\033[36m%-30s\033[0m %s\n", $$1, $$2}'

docker-compose-up-mysql: ## launch-mysql
	docker-compose up mysql -d

docker-compose-logs-mysql: ## show docker-compose mysql logs
	docker-compose logs mysql

mvn-spring-run-mysql: ## run mysql-default profile
	mvn spring-boot:run -Dspring-boot.run.profiles=mysql-default

mvn-spring-run-h2: ## run h2-default profile
	mvn spring-boot:run -Dspring-boot.run.profiles=h2-default
