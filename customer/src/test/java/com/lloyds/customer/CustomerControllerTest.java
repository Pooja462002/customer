package com.lloyds.customer;

import com.lloyds.customer.controller.CustomerController;
import com.lloyds.customer.model.Customer;
import com.lloyds.customer.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerControllerTest {

	@Mock
	private CustomerService customerService;

	@InjectMocks
	private CustomerController customerController;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void getAllCustomers_ShouldReturnCustomerList() {
		// Arrange
		Customer customer1 = new Customer(1L, "John Doe", "john.doe@example.com");
		Customer customer2 = new Customer(2L, "Jane Doe", "jane.doe@example.com");
		List<Customer> mockCustomers = Arrays.asList(customer1, customer2);

		when(customerService.getAllCustomers()).thenReturn(mockCustomers);

		// Act
		List<Customer> result = customerController.getAllCustomers();

		// Assert
		assertEquals(2, result.size());
		assertEquals("John Doe", result.get(0).getFirstName());
		verify(customerService, times(1)).getAllCustomers();
	}

	@Test
	void getCustomerById_ShouldReturnCustomer_WhenIdExists() {
		// Arrange
		Long customerId = 1L;
		Customer mockCustomer = new Customer(customerId, "John Doe", "john.doe@example.com");

		when(customerService.getCustomerById(customerId)).thenReturn(mockCustomer);

		// Act
		ResponseEntity<Customer> response = customerController.getCustomerById(customerId);

		// Assert
		assertNotNull(response);
		assertEquals(200, response.getStatusCodeValue());
		assertEquals("John Doe", response.getBody().getFirstName());
		verify(customerService, times(1)).getCustomerById(customerId);
	}

	@Test
	void createCustomer_ShouldReturnCreatedCustomer() {
		// Arrange
		Customer mockCustomer = new Customer(1L, "John Doe", "john.doe@example.com");

		when(customerService.createCustomer(mockCustomer)).thenReturn(mockCustomer);

		// Act
		ResponseEntity<Customer> response = customerController.createCustomer(mockCustomer);

		// Assert
		assertNotNull(response);
		assertEquals(200, response.getStatusCodeValue());
		assertEquals("John Doe", response.getBody().getFirstName());
		verify(customerService, times(1)).createCustomer(mockCustomer);
	}

	@Test
	void updateCustomer_ShouldReturnUpdatedCustomer() {
		// Arrange
		Long customerId = 1L;
		Customer updatedCustomer = new Customer(customerId, "John Doe Updated", "john.updated@example.com");

		when(customerService.updateCustomer(customerId, updatedCustomer)).thenReturn(updatedCustomer);

		// Act
		ResponseEntity<Customer> response = customerController.updateCustomer(customerId, updatedCustomer);

		// Assert
		assertNotNull(response);
		assertEquals(200, response.getStatusCodeValue());
		assertEquals("John Doe Updated", response.getBody().getFirstName());
		verify(customerService, times(1)).updateCustomer(customerId, updatedCustomer);
	}

	@Test
	void deleteCustomer_ShouldReturnNoContent() {
		// Arrange
		Long customerId = 1L;

		doNothing().when(customerService).deleteCustomer(customerId);

		// Act
		ResponseEntity<Void> response = customerController.deleteCustomer(customerId);

		// Assert
		assertNotNull(response);
		assertEquals(204, response.getStatusCodeValue());
		verify(customerService, times(1)).deleteCustomer(customerId);
	}
}
