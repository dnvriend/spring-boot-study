package com.github.dnvriend;

import com.github.dnvriend.repositories.TodoRepository;
import com.github.dnvriend.services.PostgresqlService;
import com.github.dnvriend.services.TodoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.scheduler.Schedulers;

@SpringBootTest(classes = {TestConfig.class})
class ReactiveDatabaseTest {

    @Autowired
    TodoRepository todoRepository;

    @Autowired
    TodoService todoService;

    void saveTodos() {
        todoRepository
            .saveAll(todoService.getTodos())
//            .collectList()
            .subscribeOn(Schedulers.elastic())
            .collectList()
            .block();
//        assertThat(todoRepository.findAll().collectList().block()).isNotEmpty();
    }


    @BeforeEach
    void setUp() {
        todoRepository.deleteAll().block();
    }

    @Test
    void testCountTodos() {
        saveTodos();
//        Long numberOfTodos = todoRepository.count().block();
//        assertThat(numberOfTodos).isEqualTo(0);
    }

//    @Test
//    void saveAllTodos() {
//        saveTodos();
//        System.out.println(listOfSavedTodos);
//    }

}
