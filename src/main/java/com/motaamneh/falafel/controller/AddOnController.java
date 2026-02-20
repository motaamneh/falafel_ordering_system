package com.motaamneh.falafel.controller;

import com.motaamneh.falafel.dto.request.AddOnCreateRequestDto;
import com.motaamneh.falafel.dto.request.AddOnUpdateRequestDto;
import com.motaamneh.falafel.dto.response.AddOnPublicDto;
import com.motaamneh.falafel.dto.response.AddOnResponseDto;
import com.motaamneh.falafel.security.JwtUtil;
import com.motaamneh.falafel.service.AddOnService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AddOnController {

    private final AddOnService addOnService;
    private final JwtUtil jwtUtil;

    public AddOnController(AddOnService addOnService, JwtUtil jwtUtil) {
        this.addOnService = addOnService;
        this.jwtUtil = jwtUtil;
    }

    private Integer getCurrentId(HttpServletRequest request){
        String token = request.getHeader("Authorization").substring(7);
        return jwtUtil.extractId(token);

    }
    // Only the restaurant that owns this restaurantId can create add-ons for it
    @PostMapping("/restaurants/{restaurantId}/add-ons")
    @PreAuthorize("hasRole('RESTAURANT')")
    public ResponseEntity<AddOnResponseDto>
    createAddOn(@PathVariable Integer restaurantId,

                @RequestBody AddOnCreateRequestDto dto,

                HttpServletRequest request) {
        Integer callerId = getCurrentId(request);
        if (!callerId.equals(restaurantId)) {
            return
                    ResponseEntity.status(403).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(addOnService.createAddOn(restaurantId,
                dto));
    }

    // GET /api/restaurants/{restaurantId}/add-ons —Public
    @GetMapping("/restaurants/{restaurantId}/add-ons")
    public ResponseEntity<List<AddOnPublicDto>>
    getAvailableAddOns(@PathVariable Integer
                               restaurantId) {
        return ResponseEntity.ok(addOnService.getAvailableAddOns(restaurantId));
    }

    // PUT /api/add-ons/{id} — Restaurant owner updates their own add-on
    // Note: the service should verify this add-on belongs to the caller's restaurant
    @PutMapping("/add-ons/{id}")
    @PreAuthorize("hasRole('RESTAURANT')")
    public ResponseEntity<AddOnResponseDto>
    updateAddOn(@PathVariable Integer id,

                @RequestBody AddOnUpdateRequestDto dto,
                HttpServletRequest request) {
        // Ownership check should happen inside the service using the add-on's restaurantId
        return ResponseEntity.ok(addOnService.updateAddOn(id, dto));
    }

    // DELETE /api/add-ons/{id} — Restaurant owner deletes their own add-on
    @DeleteMapping("/add-ons/{id}")
    @PreAuthorize("hasRole('RESTAURANT')")
    public ResponseEntity<Void>
    deleteAddOn(@PathVariable Integer id) {
        addOnService.deleteAddOn(id);
        return ResponseEntity.noContent().build();
    }
}






