# 15-simple-prometheus-custom-metrics

## Prometheus endpoint
The prometheus endpoint is available at: `http://localhost:8080/actuator/prometheus`

## Prometheus
After `docker-compose up`, Prometheus is available at: `http://localhost:9090`

## Grafana
After `docker-compose up`, Grafana is available at: `http://localhost:3000` 

## Grafana and Prometheus
Login in to Grafana, and add a Prometheus datasource. Configure:

```bash
URL: http://localhost:9090
Access: Browser
Scrape interval: 15s
Query timeout: 60s
HTTP Method: GET
```

## To load test
For load testing I use [vegeta](https://github.com/tsenart/vegeta).

```bash
$ brew install vegeta
$ echo "GET http://localhost:8080/person" | vegeta attack -duration=600s | vegeta report
$ echo "GET http://localhost:8080/person/1" | vegeta attack -duration=600s -rate=35 | vegeta report
$ echo "GET http://localhost:8080/person" | vegeta attack -duration=600s -rate=25 | vegeta report
```

## Grafana queries:
The following queries can be created:

```bash
# get the rate of http status code that has been tagged with {code = '200'} over the last [10s] (gauge)
rate(person_http_status_code_total{code='200'}[10s])

# for statuscode 404 (gauge)
rate(person_http_status_code_total{code='404'}[10s])

# get person by id counter (graph)
rate(person_counter_get_by_id_total[10s])

# get person counter (graph)
rate(person_counter_get_total[10s])
```

## Resources
- [Spring Boot Actuator: Health check, Auditing, Metrics gathering and Monitoring](https://www.callicoder.com/spring-boot-actuator/)
- [Spring Boot Actuator metrics monitoring with Prometheus and Grafana](https://www.callicoder.com/spring-boot-actuator-metrics-monitoring-dashboard-prometheus-grafana/)
- [Micrometer docs](http://micrometer.io/docs)
- [Micrometer - Mapping Spring's health indicators to metrics](http://micrometer.io/docs/guide/healthAsGauge)
- [Spring Boot Docs - Actuator](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#production-ready-metrics)
- [Prometheus](https://prometheus.io/)
- [Grafana](https://grafana.com/)
