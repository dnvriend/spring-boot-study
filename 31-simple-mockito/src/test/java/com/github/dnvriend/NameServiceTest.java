package com.github.dnvriend;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.github.dnvriend.services.NameService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.WARN)
class NameServiceTest {

    @Test
    void test(@Mock NameService nameService) {
        when(nameService.getUserName("id1")).thenReturn("user1");
        when(nameService.getUserName("id2")).thenReturn("user2");
        assertThat(nameService.getUserName("id1")).isEqualTo("user1");
        assertThat(nameService.getUserName("id2")).isEqualTo("user2");
    }
}
