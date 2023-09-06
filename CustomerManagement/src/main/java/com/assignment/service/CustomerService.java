package com.assignment.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.assignment.entity.Customer;
import com.assignment.repository.CustomerRepository;

@Service
public class CustomerService {

	@Autowired
	private CustomerRepository customerRepository;
	
	public Customer addCustomer(Customer customer) {
		return customerRepository.save(customer);
	}
	
	public List<Customer> getCustomer(){
		return customerRepository.findAll();
	}
	
	public void deletCustomer(int id) {
		customerRepository.deleteById(id);
	}
	
	public Customer updateCustomerById(int id, Customer updatedCustomer) {
        Customer existingCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        // Update the fields of the existing customer with the values from the updated customer
        existingCustomer.setFirst_name(updatedCustomer.getFirst_name());
        existingCustomer.setFirst_name(updatedCustomer.getLast_name());
        existingCustomer.setStreet(updatedCustomer.getStreet());
        existingCustomer.setAddress(updatedCustomer.getAddress());
        existingCustomer.setCity(updatedCustomer.getCity());
        existingCustomer.setState(updatedCustomer.getState());
        existingCustomer.setEmail(updatedCustomer.getEmail());
        existingCustomer.setPhone(updatedCustomer.getPhone());

        // Save the updated customer to the database
        return customerRepository.save(existingCustomer);
    }
}
