.PHONY: help
.DEFAULT_GOAL := help
export MAVEN_OPTS := -Xmx4096m

help:
	@grep -E '^[a-zA-Z_-]+:.*?## .*$$' $(MAKEFILE_LIST) | awk 'BEGIN {FS = ":.*?## "}; {printf "\033[36m%-30s\033[0m %s\n", $$1, $$2}'

mvn-spring-run: ## run mysql-default profile
	mvn clean package spring-boot:run -Dspring-boot.run.profiles=default

