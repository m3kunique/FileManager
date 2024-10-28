package com.m3k.CloudFileStorage.services;

import com.m3k.CloudFileStorage.exceptions.PasswordMismatchException;
import com.m3k.CloudFileStorage.exceptions.UserAlreadyExistsException;
import com.m3k.CloudFileStorage.models.Customer;
import com.m3k.CloudFileStorage.models.dto.CustomerDto;
import com.m3k.CloudFileStorage.repositories.CustomerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegistrationService {
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    @Autowired
    public RegistrationService(CustomerRepository customerRepository, PasswordEncoder passwordEncoder, ModelMapper modelMapper) {
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public void register(CustomerDto customerDto) {
        Customer customer = modelMapper.map(customerDto, Customer.class);

        customer.setPassword(passwordEncoder.encode(customer.getPassword()));

        customerRepository.save(customer);
    }


    public void validateCustomer(CustomerDto customerDto) {
        if (!customerDto.getPassword().equals(customerDto.getConfirmedPassword())) {
            throw new PasswordMismatchException("Password mismatch");
        }

        if (customerRepository.findByLogin(customerDto.getLogin()).isPresent()) {
            throw new UserAlreadyExistsException("User with this login already exists");
        }
    }
}
