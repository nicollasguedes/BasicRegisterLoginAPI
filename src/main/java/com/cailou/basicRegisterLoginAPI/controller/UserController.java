package com.cailou.basicRegisterLoginAPI.controller;

import com.cailou.basicRegisterLoginAPI.dto.user.*;
import com.cailou.basicRegisterLoginAPI.model.RoleModel;
import com.cailou.basicRegisterLoginAPI.model.UserModel;
import com.cailou.basicRegisterLoginAPI.service.impl.UserServiceImpl;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController()
@RequestMapping("/user")
public class UserController {
    private final UserServiceImpl userService;


    @Autowired
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }


    @ApiOperation("This method is used to save a user.")
    @PostMapping()
    public ResponseEntity<UserResponseDTO> saveUser(@RequestBody @Valid UserRequestDTO user) {
        UserModel userSaved = this.userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(UserResponseDTO.convertToDto(userSaved));
    }

    @ApiOperation("This method is used to list a logged user's data.")
    @GetMapping()
    public ResponseEntity<UserResponseDTO> listUser() {
        var userIdLogged = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserModel user = this.userService.listUser(userIdLogged.toString());
        return ResponseEntity.status(HttpStatus.OK).body(UserResponseDTO.convertToDto(user));
    }

    @ApiOperation("This method is used to update a user password")
    @PatchMapping("update-password/{userId}")
    public ResponseEntity<UserResponseDTO> updatePassword(@PathVariable String userId,
                                                          @RequestBody @Valid PasswordRequestDTO passwordRequestDTO) {
        UserModel user = this.userService.updatePassword(userId, passwordRequestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(UserResponseDTO.convertToDto(user));
    }

    @ApiOperation("This method is used to check a user password")
    @PatchMapping("check-password/{userId}")
    public ResponseEntity<Boolean> updatePassword(@PathVariable String userId,
                                                  @RequestBody @Valid CheckPasswordRequestDTO checkPasswordRequestDTO) {
        boolean equals = this.userService.checkPassword(userId, checkPasswordRequestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(equals);
    }

    @ApiOperation("This method is used to update a user by UserId")
    @PatchMapping("update/{userId}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable String userId,
                                                      @RequestBody @Valid UpdateUserRequestDTO userRequest) {
        UserModel user = this.userService.updateUser(userId, userRequest);
        return ResponseEntity.status(HttpStatus.OK).body(UserResponseDTO.convertToDto(user));
    }

    @ApiOperation("This method is used to list a user's data by Id.")
//    @Secured({"ROLE_ADMIN"})
//    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDTO> listUserById(
            @PathVariable String userId
    ) {
        UserModel user = this.userService.listUser(userId);
        return ResponseEntity.status(HttpStatus.OK).body(UserResponseDTO.convertToDto(user));
    }

    @ApiOperation("This method is used to list all user ROLES")
    @GetMapping("list-all-roles")
    public ResponseEntity<List<RoleResponseDTO>> listAllRoles() {
        List<RoleModel> userList = this.userService.listAllRoles();
        List<RoleResponseDTO> responseList = new ArrayList<>();
        for (RoleModel roleModel : userList) {
            responseList.add(RoleResponseDTO.convertToDto(roleModel));
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseList);
    }

    @ApiOperation("This method is used to switch the active status of a user.")
//    @Secured({"ROLE_ADMIN"})
//    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/switchactivity/{userId}")
    public ResponseEntity<UserResponseDTO> switchActiveUser(
            @PathVariable String userId
    ) {
        UserModel user = this.userService.switchUserActivity(userId);
        return ResponseEntity.status(HttpStatus.OK).body(UserResponseDTO.convertToDto(user));
    }
}