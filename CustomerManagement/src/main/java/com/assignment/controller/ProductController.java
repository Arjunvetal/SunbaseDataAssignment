package com.assignment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import com.assignment.dto.AuthRequest;
import com.assignment.entity.Customer;
import com.assignment.entity.UserInfo;
import com.assignment.service.CustomerService;
import com.assignment.service.JwtService;
import com.assignment.service.ProductService;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService service;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private CustomerService customerService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome this endpoint is not secure";
    }

    @PostMapping("/new")
    public String addNewUser(@RequestBody UserInfo userInfo) {
        return service.addUser(userInfo);
    }
    
    @PostMapping("create")
    public Customer createCust(@RequestBody Customer customer) {
    	return customerService.addCustomer(customer);
    }
    
    @GetMapping("/get_customer_list")
    public List<Customer> getAllCustomer(){
    	return customerService.getCustomer();
    }
    
    @DeleteMapping("delete/{id}")
    public void deleteCustomer(@PathVariable int id) {
    	customerService.deletCustomer(id);
    }
    
    @PutMapping("/update/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable int id, @RequestBody Customer updatedCustomer) {
        Customer updated = customerService.updateCustomerById(id, updatedCustomer);
        return ResponseEntity.ok(updated);
    }
    
    @PostMapping("/authenticate")
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(authRequest.getUsername());
        } else {
            throw new UsernameNotFoundException("invalid user request !");
        }


    }
}