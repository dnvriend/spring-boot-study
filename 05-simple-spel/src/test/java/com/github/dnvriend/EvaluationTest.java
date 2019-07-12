package com.github.dnvriend;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.lang.NonNull;

import static org.assertj.core.api.Assertions.assertThat;

class EvaluationTest {

    private ExpressionParser parser;

    public <T> T evaluateAs(@NonNull Class<T> ct, @NonNull String expression) {
        Expression exp = parser.parseExpression(expression);
        return exp.getValue(ct);
    }

    @BeforeEach
    void setup() {
        this.parser = new SpelExpressionParser();
    }

    @Test
    void given_Spel_literal_HelloWorld_when_Evaluated_then_result_is_HelloWorld() {
        assertThat(evaluateAs(String.class, "'Hello World'")).isEqualTo("Hello World");
    }

    @Test
    void given_Spel_method_invocation_when_Evaludated_then_result_is_HelloWorld_with_exclamation() {
        assertThat(evaluateAs(String.class, "'Hello World'.concat('!')")).isEqualTo("Hello World!");
    }

    @Test
    void given_Spel_bean_method_invocation_when_Evaluated_then_result_is_bytes() {
        assertThat(evaluateAs(Object.class, "'Hello World'.bytes")).isEqualTo("Hello World".getBytes());
    }

    @Test
    void given_Spel_with_dot_notation_when_Evaluated_then_result_is_length_of_HelloWorld() {
        assertThat(evaluateAs(Integer.class, "'Hello World'.bytes.length")).isEqualTo(11);
    }

    @Test
    void given_Spel_with_constructor_when_Evaludated_then_result_is_uppercased() {
        assertThat(evaluateAs(String.class, "new String('hello world').toUpperCase()")).isEqualTo("HELLO WORLD");
    }
}
