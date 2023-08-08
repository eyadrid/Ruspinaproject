package com.stage.elearning.controller.user;

import com.stage.elearning.dto.user.UserEntityDTO;
import com.stage.elearning.model.role.Role;
import com.stage.elearning.service.user.UserEntityService;
import com.stage.elearning.utility.CustomResponseEntity;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("api/v1/user_entity")
@RestController
public class UserEntityController {

    private final UserEntityService userEntityService;

    public UserEntityController(UserEntityService userEntityService)
    {
        this.userEntityService = userEntityService;
    }

    @GetMapping("/admin/get/id/{userId}")
    public ResponseEntity<CustomResponseEntity<UserEntityDTO>> fetchUserById(@PathVariable("userId") final UUID userId) {
        return userEntityService.fetchUserById(userId);
    }

    @GetMapping("/admin/get/all/users")
    public ResponseEntity<CustomResponseEntity<List<UserEntityDTO>>> fetchAllUsers(
            @RequestParam(value = "pageNumber", required = true) final long pageNumber
    )
    {
        return  userEntityService.fetchAllUsers(pageNumber);
    }

    @GetMapping("/admin/search")
    public ResponseEntity<CustomResponseEntity<List<UserEntityDTO>>> searchUsers(
            @RequestParam(name = "fullName", required = false) String fullName,
            @RequestParam(name = "id", required = false) UUID id,
            @RequestParam(name = "email", required = false) String email,
            @RequestParam(name = "phoneNumber", required = false) String phoneNumber
    )
    {
        return userEntityService.searchUser(fullName , id , email ,phoneNumber);
    }

    @GetMapping("/all/get/current_user")
    public ResponseEntity<CustomResponseEntity<UserEntityDTO>> fetchCurrentUser(@AuthenticationPrincipal UserDetails userDetails)
    {
        return userEntityService.fetchCurrentUser(userDetails);
    }


}
