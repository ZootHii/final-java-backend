package com.zoothii.finaljavabackend.api.controllers;

import com.zoothii.finaljavabackend.business.abstracts.RoleService;
import com.zoothii.finaljavabackend.core.entities.Role;
import com.zoothii.finaljavabackend.core.utulities.results.DataResult;
import com.zoothii.finaljavabackend.core.utulities.results.ErrorDataResult;
import com.zoothii.finaljavabackend.core.utulities.results.Result;
import com.zoothii.finaljavabackend.entities.concretes.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/api/")
@RestController
@CrossOrigin
public class RolesController {
    private final RoleService roleService;

    @Autowired
    public RolesController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("roles")
    public ResponseEntity<DataResult<List<Role>>> getRoles() throws InterruptedException {
        var result = roleService.getRoles();
        if (!result.isSuccess()) {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("role")
    public ResponseEntity<Result> createRole(@Valid @RequestBody Role role) {
        var result = roleService.createRole(role);
        if (!result.isSuccess()) {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("role")
    public ResponseEntity<Result> deleteRole(@Valid @RequestBody Role role) {
        var result = this.roleService.deleteRole(role);
        if (!result.isSuccess()){
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("role/name")
    public ResponseEntity<DataResult<Role>> getRoleByName(@RequestParam String name) {
        var result = roleService.getRoleByName(name);
        if (!result.isSuccess()) {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // ****** VALIDATION ******
    // todo tek seferlik kullan??m haline getir
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDataResult<Object> validationExceptionHandler(MethodArgumentNotValidException exception) {
        Map<String, String> validationErrors = new HashMap<>();
        for (FieldError fieldError : exception.getBindingResult().getFieldErrors()) {
            validationErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return new ErrorDataResult<>(validationErrors, "validation errors");
    }

}
