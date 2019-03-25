package MK.validator.impl.persistence;

import MK.dto.CustomerOrderDto;
import MK.repository.impl.CustomerOrderRepository;
import MK.repository.impl.CustomerRepository;
import MK.repository.impl.StockRepository;
import MK.validator.Validator;

import java.util.HashMap;
import java.util.Map;

public class CustomerOrderPersistenceValidator implements Validator<CustomerOrderDto> {

    private CustomerOrderRepository customerOrderRepository;
    private StockRepository stockRepository;
    private Map<String, String> errors = new HashMap<>();


    public CustomerOrderPersistenceValidator(CustomerOrderRepository customerOrderRepository, StockRepository stockRepository) {
        this.customerOrderRepository = customerOrderRepository;
        this.stockRepository = stockRepository;
    }

    public boolean validaterCustomerOrderInsideDB(CustomerOrderDto customerOrderDto) {

        return stockRepository.findAll()
                .stream()
                .filter(s -> s.getProduct().getName().equals(customerOrderDto.getProductDto().getName()))
                .filter(s -> s.getQuantity() >= customerOrderDto.getQuantity())
                .findFirst()
                .isPresent();
    }

    @Override
    public Map<String, String> validate(CustomerOrderDto customerOrder) {

        errors.clear();

        if (customerOrder == null) {
            errors.put("customerOrder", "customerOrder object is null");
        }

        if (!validaterCustomerOrderInsideDB(customerOrder)) {
            errors.put("model", "customerOrder model is not correct: " + customerOrder);
        }
        return errors;
    }

    @Override
    public boolean hasErrors() {
        return false;
    }
}
