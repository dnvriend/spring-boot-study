# 12-simple-jpa-data-web-avro

## HTTP

```bash
# put
$ http put :8080/person name=dnvriend
$ http put :8080/person name=dnvriend age:=42

# paging
$ http :8080/person size==1
$ http :8080/person size==1 page==0
$ http :8080/person size==1 page==1
$ http :8080/person size==1 page==2
$ http :8080/person sort==id
```

## Resources
- [spring-data-jpa - Reference](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/)
- [Apache AVRO 1.9.0 - Getting Started Java](http://avro.apache.org/docs/1.9.0/gettingstartedjava.html)
