# 02-simple-web

## k8s
To call the service, the port will be exposed on localhost, eg:

```
$ kubectl get svc
NAME                 TYPE        CLUSTER-IP      EXTERNAL-IP   PORT(S)          AGE
kubernetes           ClusterIP   10.96.0.1       <none>        443/TCP          2h
simple-web-service   NodePort    10.108.210.80   <none>        8080:32539/TCP   6m
```

You can call the service with:

```
$ http :32539
HTTP/1.1 200
Content-Length: 11
Content-Type: text/plain;charset=UTF-8

Hello World
```

## Swagger URL
The swagger-ui is available at [http://localhost:8080/swagger-ui.html] and the API is available at: [http://localhost:8080/v2/api-docs]

## Curl to the 'hello' endpoint

HTTP:
```bash
$ curl \
    --user root:root \
    -X GET "http://localhost:30952/hello" \
    -H "accept: application/json"
```

SSL:
```bash
$ curl \
    --insecure \
    --user root:root \
    -X GET "https://localhost:30952/hello" \
    -H "accept: application/json"
```