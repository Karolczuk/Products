package MK.service;


import MK.model.Category;
import MK.model.Product;
import MK.repository.impl.CountryRepositoryImpl;
import MK.repository.impl.CustomerRepositoryImpl;
import MK.repository.impl.ProductRepositoryImpl;

import java.util.Map;
import java.util.stream.Collectors;

public class DataService {
    private CountryRepositoryImpl countryRepository = new CountryRepositoryImpl();
    private CustomerRepositoryImpl customerRepository = new CustomerRepositoryImpl();
    private ProductRepositoryImpl productRepository = new ProductRepositoryImpl();

    public void showTheMostExpensiveProduct() {
        Map<Category, Product> mapCategoryProduct = productRepository.findAll()
                .stream()
                .collect(Collectors.toMap(p->p.getCategory(), c->c));


    }

//    private ModelMappers modelMapper = new ModelMappers();
//
//    public void addCustomer(CustomerDto companyDto) {
//        // kazda Company musi dostac Country w ktorym jest id lub name
//        // jezeli jest id oraz name pierwszenstwo id
//        // jezeli nie ma kraju o podanej nazwie to musimy go dodac
//
//        try {
//            if (companyDto == null) {
//                throw new MyException(ExceptionCode.CUSTOMER,"COMPANY OBJECT IS NULL");
//            }
//
//            if (companyDto.getCountryDto() == null) {
//                throw new NullPointerException("COUNTRY OBJECT IS NULL");
//            }
//
//            if (companyDto.getCountryDto().getId() == null && companyDto.getCountryDto().getName() == null) {
//                throw new NullPointerException("COUNTRY WITHOUT ID AND NAME");
//            }
//
//            Long id = companyDto.getCountryDto().getId();
//            String name = companyDto.getCountryDto().getName();
//
//            Country country = null;
//            if (id != null) {
//                country = countryRepository.findOne(1L).orElse(null);
//            }
//            else if (name != null) {
//                // najpierw sprawdz czy nie ma takieg kraju
//                country = countryRepository.findByName(name).orElse(null);
//                if (country == null) {
//                    country = countryRepository.saveOrUpdate(Country.builder()
//                            .name(name)
//                            .capital(companyDto.getCountryDto().getCapital())
//                            .teams(new HashSet<>())
//                            .players(new HashSet<>())
//                            .companies(new HashSet<>())
//                            .managers(new HashSet<>())
//                            .build());
//                }
//            }
//
//            Company company = modelMapper.fromCompanyDtoToCompany(companyDto);
//            if (country != null) {
//                company.setCountry(country);
//            }
//            companyRepository.saveOrUpdate(company);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new MyException(ExceptionCode.SERVICE, e.getMessage());
//        }
//    }

}
