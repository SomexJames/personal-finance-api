package org.example.controller.v1;

import org.example.model.User;
import org.example.model.Account;
import org.example.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private User user;
    private Account account;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User("John Doe");
        account = new Account();
    }

    @Test
    void createUser_shouldReturnCreatedUser() {
        when(userService.createUser(user)).thenReturn(user);

        ResponseEntity<User> response = userController.createUser(user);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(user, response.getBody());
        verify(userService, times(1)).createUser(user);
    }

    @Test
    void addAccount_shouldReturnCreatedAccount() {
        String userId = user.getId();
        when(userService.addAccount(userId, account)).thenReturn(account);

        ResponseEntity<Account> response = userController.addAccount(userId, account);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(account, response.getBody());
        verify(userService, times(1)).addAccount(userId, account);
    }

    @Test
    void getUser_shouldReturnUserIfExists() {
        String userId = user.getId();
        when(userService.getUserById(userId)).thenReturn(user);

        ResponseEntity<User> response = userController.getUser(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
        verify(userService, times(1)).getUserById(userId);
    }

    @Test
    void getUser_shouldReturnNotFoundIfUserDoesNotExist() {
        String userId = "non-existent-id";
        when(userService.getUserById(userId)).thenReturn(null);

        ResponseEntity<User> response = userController.getUser(userId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(userService, times(1)).getUserById(userId);
    }
}
