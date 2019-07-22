package com.ashwani.springcrudrestdemo.dao;


import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.ashwani.springcrudrestdemo.model.Customer;

@Repository
public class CustomerDAOImpl implements CustomerDAO {

	// need to inject the session factory
	@Autowired
	private SessionFactory sessionFactory;
			
	@Override
	public List<Customer> getCustomers() {
		
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
				
		// create a query  ... sort by last name
		Query<Customer> theQuery = 
				currentSession.createQuery("from Customer order by id",
											Customer.class);
		
		// execute query and get result list
		List<Customer> customers = theQuery.getResultList();
				
		// return the results
		return customers;
	}

	@Override
	public void saveCustomer(Customer theCustomer) {

		// get current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		// save/upate the customer ... finally LOL
		currentSession.saveOrUpdate(theCustomer);
	}

	@Override
	public Customer getCustomer(int theId) {

		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		// now retrieve/read from database using the primary key
		Customer theCustomer = currentSession.get(Customer.class, theId);
		return theCustomer;
	}

	@Override
	public void deleteCustomer(int theId) {

		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		// delete object with primary key
		Query theQuery = 
				currentSession.createQuery("delete from Customer where id=:customerId");
		theQuery.setParameter("customerId", theId);
		
		theQuery.executeUpdate();		
	}
	
	@Override
	public Customer modifyCustomer(Customer theCustomer) {
		System.out.println("modifyDao");
		Query theQuery = null;
		Integer theId=theCustomer.getId();
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		// modify object with primary key
		if(theCustomer.getFirstName()!=null) {
			theQuery = currentSession.createQuery("update Customer set firstName=:firstName where id=:customerId");
			theQuery.setParameter("firstName", theCustomer.getFirstName());
			theQuery.setParameter("customerId", theId);
			theQuery.executeUpdate();		
		}
		if(theCustomer.getLastName()!=null) {
			theQuery = currentSession.createQuery("update Customer set lastName=:lastName where id=:customerId");
			theQuery.setParameter("lastName", theCustomer.getLastName());
			theQuery.setParameter("customerId", theId);
			theQuery.executeUpdate();		
		}
		if(theCustomer.getEmail()!=null) {
			theQuery = currentSession.createQuery("update Customer set email=:email where id=:customerId");
			theQuery.setParameter("email", theCustomer.getEmail());
			theQuery.setParameter("customerId", theId);
			theQuery.executeUpdate();		
		}
		
		return getCustomer(theId);
	}

}











