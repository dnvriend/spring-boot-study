package com.github.dnvriend;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import com.github.dnvriend.domain.Car;
import com.github.dnvriend.domain.User;
import com.github.dnvriend.exeptions.EngineNotStartedException;
import com.github.dnvriend.service.UserService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockingDetails;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.exceptions.verification.NeverWantedButInvoked;
import org.mockito.exceptions.verification.NoInteractionsWanted;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ExampleTest {

    /**
     * We can inject a mock for an instance variable
     */
    @Mock
    UserService userService;

    @Spy
    List<String> spiedList = new ArrayList<>();

    @Captor
    ArgumentCaptor argCaptor;

    @Mock
    Map<String, String> wordMap;

    @InjectMocks
    MyDictionary dict = new MyDictionary();

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
        // mock creation
        List mockList = mock(ArrayList.class);

        // using the mock object
        mockList.add("one");
        // verification
        Mockito.verify(mockList).add("one");
        assertEquals(0, mockList.size());

        // using the mock object
        when(mockList.size()).thenReturn(100);
        assertEquals(100, mockList.size());
    }

    @Test
    void injectedMock(@Mock List<String> mockedList) {
        // using the mock object
        mockedList.add("one");
        //verification
        Mockito.verify(mockedList).add("one");
        assertEquals(0, mockedList.size());

        // using the mock object
        when(mockedList.size()).thenReturn(100);
        assertEquals(100, mockedList.size());
    }

    @Test
    void manuallySpy() {
        // spy creation
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
        List mockList = mock(List.class);
        // Captures arguments for further assertions.
        ArgumentCaptor<String> arg = ArgumentCaptor.forClass(String.class);

        mockList.add("one");
//        Mockito.verify(mockList, never()).add(arg.capture());
        Mockito.verify(mockList, atMost(1)).add(arg.capture());

        assertEquals("one", arg.getValue());
    }

    @Test
    void injectCapture(@Mock List mockedList) {
        mockedList.add("one");
        Mockito.verify(mockedList).add(argCaptor.capture());

        assertEquals("one", argCaptor.getValue());
    }

    @Test
    void injectMockTest() {
        // wordMap gets mocked
        // note that wordMap is also injected into MyDictionary
        when(wordMap.get("aWord")).thenReturn("aMeaning");
        // assert that wordMap has been injected in MyDictionary
        assertEquals("aMeaning", dict.getMeaning("aWord"));
    }

    @Test
    void testMockWithException(@Mock List<String> mockList) {
        // create mock
        doThrow(new RuntimeException()).when(mockList).clear();
        assertThrows(RuntimeException.class, mockList::clear);
    }

    @Test
    void testMockOrderFirst(@Mock List<String> singleMock) {
        // using a single mock
        singleMock.add("was added first");
        singleMock.add("was added second");

        // create an inOrder verifier for a single mock
        InOrder inOrder = inOrder(singleMock);

        // following will make sure that add is first called with "was added first", then with "was added second"
        inOrder.verify(singleMock).add("was added first");
        inOrder.verify(singleMock).add("was added second");
    }

    @Test
    void testMockOrderSecond(@Mock List firstMock, @Mock List secondMock) {
        // using mocks
        firstMock.add("was called first");
        secondMock.add("was called second");

        // create inOrder object passing any mocks that need to be verified in order
        InOrder inOrder = inOrder(firstMock, secondMock);

        // following will make sure that firstMock was called before secondMock
        inOrder.verify(firstMock).add("was called first");
        inOrder.verify(secondMock).add("was called second");
    }

    @Test
    void interactionNeverHappened(@Mock List mockOne, @Mock List mockTwo, @Mock List mockThree) {
        // using mocks - only mockOne is interacted
        mockOne.add("one");
        // ordinary verification
        verify(mockOne).add("one");

        // verify that method was never called on a mock
        verify(mockOne, never()).add("two");

        // verify that other mocks were not interacted
        verifyZeroInteractions(mockTwo, mockThree);
    }

    @Test
    void redundantInvocations(@Mock List mockedList) {
        // using mocks
        mockedList.add("one");
        mockedList.add("two");

        verify(mockedList).add("one");

        //following verification will fail
        assertThrows(NoInteractionsWanted.class, () -> verifyNoMoreInteractions(mockedList));
    }

    @Test
    void multipleReturn(@Mock List<String> mockedList) {
        when(mockedList.get(anyInt())).thenReturn("one", "two", "three");
        assertThat(mockedList.get(0)).isEqualTo("one");
        assertThat(mockedList.get(0)).isEqualTo("two");
        assertThat(mockedList.get(0)).isEqualTo("three");
    }

    @Test
    void voidMock(@Mock Car car) {
        doThrow(EngineNotStartedException.class).when(car).shiftGear();
        assertThrows(EngineNotStartedException.class, () -> car.shiftGear());
    }

    @Test
    void mockingDetailsTest(@Mock User user) {
        when(user.getName()).thenReturn("dnvriend");
        when(user.getAge()).thenReturn(42);
        assertThat(user.getName()).isEqualTo("dnvriend");
        assertThat(user.getAge()).isEqualTo(42);
        assertThat(mockingDetails(user).isMock()).isTrue();
        MockingDetails details = mockingDetails(user);
        System.out.println(details.printInvocations());
    }
}
