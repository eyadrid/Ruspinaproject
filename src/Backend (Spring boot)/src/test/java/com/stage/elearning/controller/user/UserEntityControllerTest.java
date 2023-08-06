package com.stage.elearning.controller.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.stage.elearning.dto.user.UserEntityDTO;
import com.stage.elearning.dto.user.UserEntityDTOMapper;
import com.stage.elearning.model.role.Role;
import com.stage.elearning.model.user.UserEntity;
import com.stage.elearning.repository.UserEntityRepository;
import com.stage.elearning.service.user.UserEntityServiceImpl;
import com.stage.elearning.utility.CustomResponseEntity;

import java.time.LocalDate;
import java.time.ZoneOffset;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

class UserEntityControllerTest {

    @Test
    void testFetchUserById() {
        Role role = new Role();
        role.setId(1L);
        role.setName("Name");

        UserEntity userEntity = new UserEntity();
        userEntity.setAddress("42 Main St");
        Date creatingDate = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        userEntity.setCreatingDate(creatingDate);
        userEntity.setEmail("jane.doe@example.org");
        userEntity.setEnabled(true);
        userEntity.setFullName("Dr Jane Doe");
        UUID id = UUID.randomUUID();
        userEntity.setId(id);
        userEntity.setPassword("iloveyou");
        userEntity.setPhoneNumber("6625550144");
        userEntity.setRole(role);
        UserEntityRepository userEntityRepository = mock(UserEntityRepository.class);
        when(userEntityRepository.fetchUserWithId(Mockito.<UUID>any())).thenReturn(Optional.of(userEntity));
        UserEntityController userEntityController = new UserEntityController(
                new UserEntityServiceImpl(userEntityRepository, new UserEntityDTOMapper()));
        ResponseEntity<CustomResponseEntity<UserEntityDTO>> actualFetchUserByIdResult = userEntityController
                .fetchUserById(UUID.randomUUID());
        assertTrue(actualFetchUserByIdResult.hasBody());
        assertTrue(actualFetchUserByIdResult.getHeaders().isEmpty());
        assertEquals(200, actualFetchUserByIdResult.getStatusCodeValue());
        CustomResponseEntity<UserEntityDTO> body = actualFetchUserByIdResult.getBody();
        assertTrue(body.isStatusString());
        assertEquals(HttpStatus.OK, body.getStatus());
        UserEntityDTO data = body.getData();
        assertEquals("42 Main St", data.address());
        assertSame(role, data.role());
        assertEquals("6625550144", data.phoneNumber());
        assertSame(creatingDate, data.creationDate());
        assertEquals("Dr Jane Doe", data.fullName());
        assertTrue(data.isEnabled());
        assertSame(id, data.id());
        assertEquals("jane.doe@example.org", data.email());
        verify(userEntityRepository).fetchUserWithId(Mockito.<UUID>any());
    }


    @Test
    void testFetchAllUsers() {
        UserEntityRepository userEntityRepository = mock(UserEntityRepository.class);
        when(userEntityRepository.fetchAllUsers()).thenReturn(new ArrayList<>());
        ResponseEntity<CustomResponseEntity<List<UserEntityDTO>>> actualFetchAllUsersResult = (new UserEntityController(
                new UserEntityServiceImpl(userEntityRepository, new UserEntityDTOMapper()))).fetchAllUsers(1L);
        assertTrue(actualFetchAllUsersResult.hasBody());
        assertTrue(actualFetchAllUsersResult.getHeaders().isEmpty());
        assertEquals(200, actualFetchAllUsersResult.getStatusCodeValue());
        CustomResponseEntity<List<UserEntityDTO>> body = actualFetchAllUsersResult.getBody();
        assertTrue(body.isStatusString());
        assertEquals(HttpStatus.OK, body.getStatus());
        assertTrue(body.getData().isEmpty());
        verify(userEntityRepository).fetchAllUsers();
    }

    @Test
    void testSearchUsers() {
        UserEntityRepository userEntityRepository = mock(UserEntityRepository.class);
        when(userEntityRepository.searchUsers(Mockito.<String>any(), Mockito.<UUID>any(), Mockito.<String>any(),
                Mockito.<String>any())).thenReturn(new ArrayList<>());
        UserEntityController userEntityController = new UserEntityController(
                new UserEntityServiceImpl(userEntityRepository, new UserEntityDTOMapper()));
        ResponseEntity<CustomResponseEntity<List<UserEntityDTO>>> actualSearchUsersResult = userEntityController
                .searchUsers("Dr Jane Doe", UUID.randomUUID(), "jane.doe@example.org", "6625550144");
        assertTrue(actualSearchUsersResult.hasBody());
        assertTrue(actualSearchUsersResult.getHeaders().isEmpty());
        assertEquals(200, actualSearchUsersResult.getStatusCodeValue());
        CustomResponseEntity<List<UserEntityDTO>> body = actualSearchUsersResult.getBody();
        assertTrue(body.isStatusString());
        assertEquals(HttpStatus.OK, body.getStatus());
        assertTrue(body.getData().isEmpty());
        verify(userEntityRepository).searchUsers(Mockito.<String>any(), Mockito.<UUID>any(), Mockito.<String>any(),
                Mockito.<String>any());
    }


    @Test
    void testFetchCurrentUser2() {
        UserEntityServiceImpl userEntityService = mock(UserEntityServiceImpl.class);
        when(userEntityService.fetchCurrentUser(Mockito.<UserDetails>any())).thenReturn(null);
        UserEntityController userEntityController = new UserEntityController(userEntityService);
        assertNull(userEntityController.fetchCurrentUser(new UserEntity()));
        verify(userEntityService).fetchCurrentUser(Mockito.<UserDetails>any());
    }
}

