package com.github.dnvriend.meterbinder;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.MeterBinder;

import java.util.concurrent.atomic.AtomicLong;

public class PersonStatsProbe implements MeterBinder {
    private final AtomicLong putPersonCounter = new AtomicLong();

    private final AtomicLong getPersonCounter = new AtomicLong();

    private final AtomicLong getPersonByIdCounter = new AtomicLong();

    @Override
    public void bindTo(MeterRegistry registry) {
        Gauge.builder("person_counter_put", this, value -> putPersonCounter.get())
                .description("number of persons put")
                .baseUnit("total")
                .register(registry);

        Gauge.builder("person_counter_get", this, value -> getPersonCounter.get())
                .description("number of persons get")
                .baseUnit("total")
                .register(registry);

        Gauge.builder("person_counter_get_by_id", this, value -> getPersonByIdCounter.get())
                .description("number of persons get by id")
                .baseUnit("total")
                .register(registry);
    }

    public void incrementPutPersonCounter() {
        this.putPersonCounter.incrementAndGet();
    }

    public void incrementGetPersonCounter() {
        this.getPersonCounter.incrementAndGet();
    }

    public void incrementGetPersonByIdCounter() {
        this.getPersonByIdCounter.incrementAndGet();
    }
}
