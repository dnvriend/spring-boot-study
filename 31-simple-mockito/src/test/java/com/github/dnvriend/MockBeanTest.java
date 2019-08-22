package com.github.dnvriend;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.github.dnvriend.services.NameService;
import com.github.dnvriend.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class
MockBeanTest {

    @Mock
    NameService nameService;

    @Spy
    @InjectMocks
    UserService userService;

    @Test
    void testInjectedMocksAndSpy() {
        when(nameService.getUserName(anyString())).thenReturn("mock user name");
        Mockito.lenient().when(userService.getUserName(anyString())).thenCallRealMethod();
        assertThat(userService.getUserName("userId")).isEqualTo("mock user name");
        Mockito.verify(userService).getUserName("userId");
    }
}
