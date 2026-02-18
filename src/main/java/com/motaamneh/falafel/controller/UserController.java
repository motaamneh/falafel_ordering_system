package com.motaamneh.falafel.controller;

import com.motaamneh.falafel.dto.request.UserUpdateRequestDto;
import com.motaamneh.falafel.dto.response.UserResponseDto;
import com.motaamneh.falafel.security.JwtUtil;
import com.motaamneh.falafel.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final JwtUtil jwtUtil;

    public UserController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    private Integer getCurrentId(HttpServletRequest request){
        String token = request.getHeader("Authorization").substring(7);
        return jwtUtil.extractId(token);

    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Integer id,HttpServletRequest request) {
        Integer callerId = getCurrentId(request);

        String token = request.getHeader("Authorization").substring(7);
        String role = jwtUtil.extractRole(token);

        if(role.equals("USER") && !callerId.equals(id)){
            return ResponseEntity.status(403).build();
        }
        return ResponseEntity.ok(userService.findUserById(id));

    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> updateUser(@PathVariable Integer id
            ,@RequestBody UserUpdateRequestDto userUpdateRequestDto
            ,HttpServletRequest request) {

        Integer callerId = getCurrentId(request);
        if(!callerId.equals(id)){
            return ResponseEntity.status(403).build();
        }
        return ResponseEntity.ok(userService.updateUser(id,userUpdateRequestDto));
    }

    @GetMapping("/allUsers")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PatchMapping("/{id}/enable")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> enableUser(@PathVariable Integer id){
        userService.enableUser(id);
        return ResponseEntity.noContent().build();

    }

    @PatchMapping("/{id}/disable")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> disableUser(@PathVariable Integer id){
        userService.disableUser(id);
        return ResponseEntity.noContent().build();

    }







}
