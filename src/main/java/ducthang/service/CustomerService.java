package ducthang.service;

import ducthang.model.Customer;
import ducthang.repository.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService implements ICustomerService {
    @Autowired
    CustomerRepo customerRepo;

    @Override
    public List<Customer> findAllCustomer() {
        return (List<Customer>) customerRepo.findAll();
    }

    @Override
    public void save(Customer customer) {
        customerRepo.save(customer);
    }

    @Override
    public void delete(int id) {
        customerRepo.deleteById(id);
    }

    @Override
    public Customer findById(int id) {
        return customerRepo.findById(id).get();
    }
}
