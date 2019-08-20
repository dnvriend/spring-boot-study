package com.github.dnvriend;

import com.github.dnvriend.domain.User;
import com.github.dnvriend.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;

@ExtendWith(MockitoExtension.class)
class ExampleTest {

    /**
     * We can inject a mock for an instance variable
     */
    @Mock
    UserService userService;

    /**
     * We can also inject mock objects into method parameters
     */
    @Test
    void testAddUser(@Mock UserService userService, @Mock List<User> users) {
        // Lenient stubs bypass "strict stubbing" validation
        Mockito.lenient().when(userService.getUserName(anyString())).thenReturn("Mock user name");
    }

    @Test
    void manuallyCreateMocks() {
        // we can manually create mocks
        List mockList = Mockito.mock(ArrayList.class);

        mockList.add("one");
        Mockito.verify(mockList).add("one");
        assertEquals(0, mockList.size());

        Mockito.when(mockList.size()).thenReturn(100);
        assertEquals(100, mockList.size());
    }

    @Test
    void injectedMock(@Mock List<String> mockedList) {
        mockedList.add("one");
        Mockito.verify(mockedList).add("one");
        assertEquals(0, mockedList.size());

        Mockito.when(mockedList.size()).thenReturn(100);
        assertEquals(100, mockedList.size());
    }

    @Test
    void manuallySpy() {
        List<String> spyList = Mockito.spy(new ArrayList<String>());
        // use real methods to add elements
        spyList.add("one");
        spyList.add("two");

        // verify whether we have added 'one' and 'two'
        Mockito.verify(spyList).add("one");
        Mockito.verify(spyList).add("two");

        assertEquals(2, spyList.size());

        // stub the 'size()' method using 'Mockito.doReturn()'
        Mockito.doReturn(100).when(spyList).size();
        assertEquals(100, spyList.size());
    }

    @Spy List<String> spiedList = new ArrayList<>();
    // @Spy does not work on parameters
    @Test
    void injectedSpy() {
        // use real methods to add elements
        spiedList.add("one");
        spiedList.add("two");

        Mockito.verify(spiedList).add("one");
        Mockito.verify(spiedList).add("two");

        assertEquals(2, spiedList.size());

        // stub the 'size()' method using 'Mockito.doReturn()'
        Mockito.doReturn(100).when(spiedList).size();
        assertEquals(100, spiedList.size());
    }

    @Test
    void manualCaptor() {
        List mockList = Mockito.mock(List.class);
        // Captures arguments for further assertions.
        ArgumentCaptor<String> arg = ArgumentCaptor.forClass(String.class);

        mockList.add("one");
        Mockito.verify(mockList, never()).add(arg.capture());

        assertEquals("one", arg.getValue());
    }

    @Captor
    ArgumentCaptor argCaptor;
    @Test
    void injectCapture(@Mock List mockedList) {
        mockedList.add("one");
        Mockito.verify(mockedList).add(argCaptor.capture());

        assertEquals("one", argCaptor.getValue());
    }

    @Mock
    Map<String, String> wordMap;

    @InjectMocks
    MyDictionary dict = new MyDictionary();

    @Test
    void injectMockTest() {
        // wordMap gets mocked
        // note that wordMap is also injected into MyDictionary
        Mockito.when(wordMap.get("aWord")).thenReturn("aMeaning");
        // assert that wordMap has been injected in MyDictionary
        assertEquals("aMeaning", dict.getMeaning("aWord"));
    }
}
