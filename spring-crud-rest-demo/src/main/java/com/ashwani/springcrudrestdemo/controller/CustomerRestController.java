package com.ashwani.springcrudrestdemo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ashwani.springcrudrestdemo.exceptions.CustomerNotFoundException;
import com.ashwani.springcrudrestdemo.model.Customer;
import com.ashwani.springcrudrestdemo.service.CustomerService;

@RestController
@RequestMapping("/api")
public class CustomerRestController {

	@Autowired
	CustomerService customerService;
	
	@GetMapping("/customers")
	public List<Customer> getAllCustomers() {
		System.out.println("Get");
		if(customerService.getCustomers()==null) {
			throw new CustomerNotFoundException("No Customer Found In Database");
		}
		return customerService.getCustomers();
	}
	
	@GetMapping("/customers/{customerid}")
	public Customer getCustomerById(@PathVariable("customerid") Integer id) {
		System.out.println("Get");
		if(customerService.getCustomer(id)==null) {
			throw new CustomerNotFoundException("Customer Not Found In Database with id - "+id);
		}
		return customerService.getCustomer(id);
	}
	
	@PostMapping("/customers")
	public Customer addCustomer(@RequestBody Customer newCustomer) {
		System.out.println("Post");
	try {
		newCustomer.setId(0);
		customerService.saveCustomer(newCustomer);}
	catch(Exception e) {
		e.printStackTrace();
	}
		return newCustomer;
	}
	
	@PutMapping("/customers")
	public Customer updateCustomer(@RequestBody Customer newCustomer) {
		System.out.println("Put");
		if(customerService.getCustomer(newCustomer.getId())==null) {
			throw new CustomerNotFoundException("Customer Not Found In Database with id - "+newCustomer.getId());
		}
		customerService.saveCustomer(newCustomer);
		return newCustomer;
	}
	
	@DeleteMapping("/customers/{customerid}")
	public String deleteCustomer(@PathVariable("customerid") Integer id) {
		System.out.println("Delete");
		if(customerService.getCustomer(id)==null) {
			throw new CustomerNotFoundException("Customer Not Found In Database with id - "+id);
		}
		customerService.deleteCustomer(id);
		return "Customer Deleted from Database with id - "+id;
	}
	
	@PatchMapping("/customers")
	public Customer modifyCustomer(@RequestBody Customer newCustomer) {
		System.out.println("Patch");
		if(customerService.getCustomer(newCustomer.getId())==null) {
			throw new CustomerNotFoundException("Customer Not Found In Database with id - "+newCustomer.getId());			
		}
		return 	customerService.modifyCustomer(newCustomer);
	}
}
