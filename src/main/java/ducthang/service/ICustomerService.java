package ducthang.service;

import ducthang.model.Customer;

import java.util.List;
import java.util.Optional;

public interface ICustomerService {
    public List<Customer> findAllCustomer();
    public void save(Customer customer);
    public void delete(int id);
    public Customer findById(int id);
}
