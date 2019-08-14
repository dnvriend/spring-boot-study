.PHONY: help
.DEFAULT_GOAL := help
MAVEN_CLI_OPTS ?= -Duser.timezone=UTC -DfailIfNoTests=false
mvn_cmd := mvn $(MAVEN_CLI_OPTS)
export MAVEN_OPTS := -Xmx1024m

help:
	@grep -E '^[a-zA-Z_-]+:.*?## .*$$' $(MAKEFILE_LIST) | awk 'BEGIN {FS = ":.*?## "}; {printf "\033[36m%-30s\033[0m %s\n", $$1, $$2}'

## maven targets

mvn-clean: ## clean
	mvn clean

mvn-compile: ## compile
	$(mvn_cmd) compile

mvn-test: ## run unit test
	$(mvn_cmd) test

mvn-integration-test: ## run integration test
	$(mvn_cmd) integration-test

mvn-sonar: ## sonarqube
	$(mvn_cmd) verify sonar:sonar -B

mvn-show-deps: ## show dependencies
	$(mvn_cmd) dependency:tree

mvn-install: ## build and install artifacts
	$(mvn_cmd) install

mvn-install-test-skip: ## build and install artifacts without running tests
	$(mvn_cmd) install -Dmaven.test.skip=true

mvn-release-prepare-dry-run: ## test the release-prepare process
	$(mvn_cmd) release:prepare -B -DdryRun=true -Darguments="-Dmaven.test.skip=true"

mvn-release-clean: ## cleanup a release
	$(mvn_cmd) release:clean

mvn-release-prepare: ## release prepare
	$(mvn_cmd) release:prepare -B -Darguments="-Dmaven.test.skip=true"

mvn-release-perform: ## perform a release
	$(mvn_cmd) release:perform -B -Darguments="-Dmaven.test.skip=true"

mvn-show-buildplan: ## show the maven buildplan
	$(mvn_cmd) fr.jcgay.maven.plugins:buildplan-maven-plugin:list

mvn-show-buildplan-by-phase: ## show the maven buildplan, group by phase
	$(mvn_cmd) fr.jcgay.maven.plugins:buildplan-maven-plugin:list-phase

mvn-show-buildplan-by-plugin: ## show the maven buildplan, group by plugin
	$(mvn_cmd) fr.jcgay.maven.plugins:buildplan-maven-plugin:list-plugin

mvn-run-debug: profile := default
mvn-run-debug: ## run spring boot app in debug mode
	$(mvn_cmd) -Dspring-boot.run.profiles=$(profile) spring-boot:run -Ddebug=true

mvn-run: profile := default
mvn-run: ## run spring boot app
	$(mvn_cmd) -Dspring-boot.run.profiles=$(profile) spring-boot:run
