package com.stage.elearning.service.role;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.stage.elearning.exceptions.ResourceNotFoundException;
import com.stage.elearning.model.role.Role;
import com.stage.elearning.repository.RoleRepository;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {RoleServiceImpl.class})
@ExtendWith(SpringExtension.class)
class RoleServiceImplTest {
    @MockBean
    private RoleRepository roleRepository;

    @Autowired
    private RoleServiceImpl roleServiceImpl;


    @Test
    void testFetchRoleByName() {
        Role role = new Role();
        role.setId(1L);
        role.setName("Name");
        Optional<Role> ofResult = Optional.of(role);
        when(roleRepository.fetchRoleByName(Mockito.<String>any())).thenReturn(ofResult);
        assertSame(role, roleServiceImpl.fetchRoleByName("Role Name"));
        verify(roleRepository).fetchRoleByName(Mockito.<String>any());
    }


    @Test
    void testFetchRoleByName2() {
        when(roleRepository.fetchRoleByName(Mockito.<String>any())).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> roleServiceImpl.fetchRoleByName("Role Name"));
        verify(roleRepository).fetchRoleByName(Mockito.<String>any());
    }

    @Test
    void testFetchRoleByName3() {
        when(roleRepository.fetchRoleByName(Mockito.<String>any()))
                .thenThrow(new ResourceNotFoundException("An error occurred"));
        assertThrows(ResourceNotFoundException.class, () -> roleServiceImpl.fetchRoleByName("Role Name"));
        verify(roleRepository).fetchRoleByName(Mockito.<String>any());
    }
}

