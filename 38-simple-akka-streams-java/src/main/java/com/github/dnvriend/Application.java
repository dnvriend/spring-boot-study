package com.github.dnvriend;

import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

public class Application {

    // monadic composition rules still hold
    public static void main(String[] args) {
        Stream
                .of(Optional.of(1))
                .map(Application::optToStream)
                .findFirst();
    }

    static <T> Stream<T> optToStream(Optional<T> opt) {
        return opt.isPresent() ? opt.map(Stream::of).orElseGet(Stream::empty);
    }

}
