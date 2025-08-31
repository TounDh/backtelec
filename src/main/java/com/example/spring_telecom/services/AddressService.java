package com.example.spring_telecom.services;

import com.example.spring_telecom.entities.Address;
import com.example.spring_telecom.entities.User;
import com.example.spring_telecom.repositories.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    public Optional<Address> getAddressByUser(User user) {
        return addressRepository.findByUser(user);
    }

    public Address saveOrUpdateAddress(Address address, User user) {
        Optional<Address> existingAddress = addressRepository.findByUser(user);

        if (existingAddress.isPresent()) {
            Address addressToUpdate = existingAddress.get();
            addressToUpdate.setStreet(address.getStreet());
            addressToUpdate.setGovernorate(address.getGovernorate());
            addressToUpdate.setCity(address.getCity());
            addressToUpdate.setZipCode(address.getZipCode());
            addressToUpdate.setLongitude(address.getLongitude());
            addressToUpdate.setLatitude(address.getLatitude());
            addressToUpdate.setCountry(address.getCountry());
            return addressRepository.save(addressToUpdate);
        } else {
            address.setUser(user);
            return addressRepository.save(address);
        }
    }

    public void deleteAddress(Long id) {
        addressRepository.deleteById(id);
    }

    public boolean addressExistsForUser(User user) {
        return addressRepository.existsByUser(user);
    }
}