package com.m3k.CloudFileStorage.services;

import com.m3k.CloudFileStorage.models.Customer;
import com.m3k.CloudFileStorage.repositories.CustomerRepository;
import com.m3k.CloudFileStorage.security.CustomerDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerDetailsService implements UserDetailsService {
    private final CustomerRepository customerRepository;
    @Autowired
    public CustomerDetailsService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Optional<Customer> customer = customerRepository.findByLogin(login);

        if (customer.isEmpty()){
            throw new UsernameNotFoundException("User not found");
        }

        return new CustomerDetails(customer.get());
    }
}
