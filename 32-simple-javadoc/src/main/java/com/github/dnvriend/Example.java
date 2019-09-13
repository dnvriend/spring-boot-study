package com.github.dnvriend;

class Example {

    /**
     * Flattens a {@link Stream}&lt;{@link Optional}&lt;{@link T}&gt;&gt; to a {@link Stream}&lt;{@link
     * T}&gt;
     *
     * @param stream a {@link Stream}&gt{@link Optional}&lt
     * @param <T>    the type of the elements
     * @return a {@link Stream}&lt{@link T}&gt
     */
    public static <T> Stream<T> flatten(Stream<Optional<T>> stream) {
        return stream.filter(Optional::isPresent).map(Optional::get);
    }

    /**
     * Returns <code>Optional.empty()</code> if the list is empty or <code>Optional.of(xs)</code> if
     * the list is non-empty
     *
     * @param xs  the list to evaluate
     * @param <T> the type of the list
     * @return {@link Optional}&lt;{@link T}&gt; that is undefined if the list is empty or defined if
     * the list is not empty
     */
    public static <T> Optional<List<T>> nel(List<T> xs) {
        return xs.isEmpty() ? Optional.empty() : Optional.of(xs);
    }

    /**
     * Return the first element, of a list, if defined, as an {@link Optional}
     *
     * @param xs  the list to evaluate
     * @param <T> the type of the list
     * @return {@link Optional}&lt;{@link T}&gt; that is undefined if the list is empty or defined if
     * the list is not empty
     */
    public static <T> Optional<T> headOption(List<T> xs) {
        return xs.stream().findFirst();
    }

}