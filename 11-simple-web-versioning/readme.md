# 11-simple-web-versioning
Versioning web services is quite simple. Like always when engineering, there is no perfect solution; but trade-offs.

We can define the following versioning strategies:

- Media Type Versioning (content-negotiation by means of "accept-header"),
- Custom Headers (like eg. X-API-VERSION=2),
- Request Parameter Versioning (?version=2)
- URI versioning (v2/person) 

## HTTP

```bash
# put
$ http put :8080/v1/person name=dnvriend
$ http put :8080/v2/person name=dnvriend age:=42

# url
$ http -v get :8080/v1/person
$ http -v get :8080/v2/person

# parameter
$ http :8080/person version==1
$ http :8080/person version==2

# custom header
$ http -v get :8080/person X-API-VERSION:1
$ http -v get :8080/person X-API-VERSION:2

# content-negotiation
$ http -v get :8080/person accept:'application/vnd.company.app-v1+json'
$ http -v get :8080/person accept:'application/vnd.company.app-v2+json'
```
