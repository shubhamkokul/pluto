package castiel.solutionbyhour.persistence;

import castiel.solutionbyhour.model.data.AddressEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class AddressRepository {

    // Create or persist an address (if it doesn't already exist)
    @Transactional
    public AddressEntity createAddress(AddressEntity addressEntity) {
        AddressEntity.persist(addressEntity);
        return addressEntity;
    }

    // Find an address by its ID
    public AddressEntity findAddressById(Long addressId) {
        return AddressEntity.find("address_id", addressId).firstResult();
    }

    // Find all addresses for a customer
    public List<AddressEntity> findAddressesByCustomerId(Long customerId) {
        return AddressEntity.list("customer_id", customerId);
    }

    // Update an existing address
    @Transactional
    public AddressEntity updateAddress(Long addressId, AddressEntity updatedAddressEntity) {
        AddressEntity addressEntity = findAddressById(addressId);
        if (addressEntity != null) {
            addressEntity.street = updatedAddressEntity.street;
            addressEntity.city = updatedAddressEntity.city;
            addressEntity.state = updatedAddressEntity.state;
            addressEntity.postalCode = updatedAddressEntity.postalCode;
            addressEntity.country = updatedAddressEntity.country;
            addressEntity.isPrimary = updatedAddressEntity.isPrimary;
            AddressEntity.persist(addressEntity);  // Save the updated address back to the database
        }
        return addressEntity; // Return the updated address
    }

    // Set an address as primary for a customer
    @Transactional
    public AddressEntity setPrimaryAddress(Long addressId) {
        AddressEntity addressEntity = findAddressById(addressId);
        if (addressEntity != null) {
            // First, reset other addresses' "isPrimary" field to false
            AddressEntity.update("isPrimary = false where customerId = ?1", addressEntity.customerId);
            // Then, set this address as the primary one
            addressEntity.isPrimary = true;
            AddressEntity.persist(addressEntity);
        }
        return addressEntity; // Return the updated address
    }

    // Delete an address by its ID
    @Transactional
    public boolean deleteAddress(Long addressId) {
        AddressEntity addressEntity = findAddressById(addressId);
        if (addressEntity != null) {
            AddressEntity.deleteById(addressId);  // Deletes the address
            return true;  // Return true if deletion was successful
        }
        return false;  // Return false if address was not found
    }
}
