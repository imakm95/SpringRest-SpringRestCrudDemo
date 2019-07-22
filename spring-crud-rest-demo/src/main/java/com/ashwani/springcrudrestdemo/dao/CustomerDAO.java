package com.ashwani.springcrudrestdemo.dao;

import java.util.List;
import com.ashwani.springcrudrestdemo.model.Customer;

public interface CustomerDAO {
	public List<Customer> getCustomers();
	public void saveCustomer(Customer theCustomer);
	public Customer getCustomer(int theId);
	public void deleteCustomer(int theId);	
	public Customer modifyCustomer(Customer theCustomer);
}
