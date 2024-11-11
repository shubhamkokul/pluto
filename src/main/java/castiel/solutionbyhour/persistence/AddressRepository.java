package castiel.solutionbyhour.persistence;

import castiel.solutionbyhour.model.data.Address;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class AddressRepository {

    // Create or persist an address (if it doesn't already exist)
    @Transactional
    public Address createAddress(Address address) {
        Address.persist(address);
        return address;
    }

    // Find an address by its ID
    public Address findAddressById(Long addressId) {
        return Address.find("address_id", addressId).firstResult();
    }

    // Find all addresses for a customer
    public List<Address> findAddressesByCustomerId(Long customerId) {
        return Address.list("customer_id", customerId);
    }

    // Update an existing address
    @Transactional
    public Address updateAddress(Long addressId, Address updatedAddress) {
        Address address = findAddressById(addressId);
        if (address != null) {
            address.street = updatedAddress.street;
            address.city = updatedAddress.city;
            address.state = updatedAddress.state;
            address.postalCode = updatedAddress.postalCode;
            address.country = updatedAddress.country;
            address.isPrimary = updatedAddress.isPrimary;
            Address.persist(address);  // Save the updated address back to the database
        }
        return address; // Return the updated address
    }

    // Set an address as primary for a customer
    @Transactional
    public Address setPrimaryAddress(Long addressId) {
        Address address = findAddressById(addressId);
        if (address != null) {
            // First, reset other addresses' "isPrimary" field to false
            Address.update("isPrimary = false where customerId = ?1", address.customerId);
            // Then, set this address as the primary one
            address.isPrimary = true;
            Address.persist(address);
        }
        return address; // Return the updated address
    }

    // Delete an address by its ID
    @Transactional
    public boolean deleteAddress(Long addressId) {
        Address address = findAddressById(addressId);
        if (address != null) {
            Address.deleteById(addressId);  // Deletes the address
            return true;  // Return true if deletion was successful
        }
        return false;  // Return false if address was not found
    }
}
