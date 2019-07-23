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
```

## Resources
- [Spring Boot Actuator: Health check, Auditing, Metrics gathering and Monitoring](https://www.callicoder.com/spring-boot-actuator/)
- [Spring Boot Actuator metrics monitoring with Prometheus and Grafana](https://www.callicoder.com/spring-boot-actuator-metrics-monitoring-dashboard-prometheus-grafana/)
- [Micrometer docs](http://micrometer.io/docs)
- [Micrometer - Mapping Spring's health indicators to metrics](http://micrometer.io/docs/guide/healthAsGauge)
- [Spring Boot Docs - Actuator](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#production-ready-metrics)