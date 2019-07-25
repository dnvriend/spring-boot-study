.PHONY: help
.DEFAULT_GOAL := help
MAVEN_CLI_OPTS := "-Duser.timezone=UTC"
MAVEN_OPTS := "-Xmx4096m"

help:
	@grep -E '^[a-zA-Z_-]+:.*?## .*$$' $(MAKEFILE_LIST) | awk 'BEGIN {FS = ":.*?## "}; {printf "\033[36m%-30s\033[0m %s\n", $$1, $$2}'

gen-test-sources: ## generate assertj test sources
	MAVEN_OPTS=$(MAVEN_OPTS) mvn generate-test-sources

compile: ## compile
	MAVEN_OPTS=$(MAVEN_OPTS) mvn $(MAVEN_CLI_OPTS) compile

test: ## test
	MAVEN_OPTS=$(MAVEN_OPTS) mvn $(MAVEN_CLI_OPTS) test

show-deps: ## show dependencies
	MAVEN_OPTS=$(MAVEN_OPTS) mvn $(MAVEN_CLI_OPTS) dependency:tree
