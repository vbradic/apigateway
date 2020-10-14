package com.vukothersoftware.apigateway.unit;

import com.vukothersoftware.exception.UserNotFoundException;
import com.vukothersoftware.service.UserConsumer;
import com.vuksoftware.model.User;
import com.vuksoftware.service.UserProcessor;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserConsumerTest {

    @Mock
    private UserProcessor userProcessor;

    @InjectMocks
    private UserConsumer userConsumer;

    @Test
    public void getUserByIdTest() {

        User user = new User("1", "Vuk", "Bradic", "1987");
        when(this.userProcessor.getUserById(anyString())).thenReturn(user);
        User userResult = userConsumer.getUserById("1");

        assertEquals(user.getName(), userResult.getName());
        assertEquals(user.getId(), userResult.getId());
        assertEquals(user.getSurname(), userResult.getSurname());
        assertEquals(user.getBirthdayYear(), userResult.getBirthdayYear());
    }

    @Test
    public void getUserById_NotFound() {

        when(this.userProcessor.getUserById(anyString())).thenThrow(new UserNotFoundException("User with requested id not created"));
        Exception exception = assertThrows(UserNotFoundException.class, () -> {
            User userResult = userConsumer.getUserById("1");
        });
    }


}
