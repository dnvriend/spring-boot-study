.PHONY: help
.DEFAULT_GOAL := help
port := $(kubectl get svc simple-web-service -o json | jq '.spec.ports[].nodePort')

help:
	@grep -E '^[a-zA-Z_-]+:.*?## .*$$' $(MAKEFILE_LIST) | awk 'BEGIN {FS = ":.*?## "}; {printf "\033[36m%-30s\033[0m %s\n", $$1, $$2}'

run: ## run app
	mvn spring-boot:run

clean: ## mvn clean
	mvn clean

package: clean ## mvn package
	mvn package

docker-build: package ## build the docker image, calls clean -> package
	docker build -f Dockerfile -t simple-web .

docker-run: ## run the docker image
	docker run --rm -p 8080:8080 simple-web

k-deploy: k-create-configmap ## deploy the config
	kubectl apply -f deployment.yaml

k-remove: ## delete the config
	kubectl delete -f deployment.yaml
	kubectl delete configmaps nginx-default-conf

#call-hello: ## call hello endpoint
#	echo http :$(port)

k-port-forward: ## enable port forwarding
	kubectl port-forward simple-web-6d659b759d-2q7tl 8080:8080
	http :8080

k-exec-nginx: ## exec into the nginx container
	kubectl exec -it simple-web-c5fddc6f9-d49ld -c authenticator /bin/bash

k-create-configmap: ## create configmap from file
#	kubectl create configmap nginx-default-conf --from-file=nginx/default.conf
	kubectl create configmap nginx-default-conf --from-file=nginx/

k-show-configmap: ## show the configmap
	kubectl get configmaps nginx-default-conf -o yaml

k-remove-configmap: ## remove the configmap
	kubectl delete configmaps nginx-default-conf

nginx-reload: ## reload the nginx config
	nginx -s reload

nginx-show-version: ## show the version and exit
	nginx -v

nginx-test-config: ## test the config and exit
	nginx -T

nginx-cat-config: ## cat the default config
	cat /etc/nginx/conf.d/default.conf

