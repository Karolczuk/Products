package MK.service;


import MK.dto.CountryDto;
import MK.dto.ProducerDto;
import MK.dto.TradeDto;
import MK.exceptions.ExceptionCode;
import MK.exceptions.MyException;
import MK.mappers.ModelMappers;
import MK.model.Country;
import MK.model.Customer;
import MK.model.Producer;
import MK.model.Trade;
import MK.repository.impl.CountryRepository;
import MK.repository.impl.ProducerRepository;
import MK.repository.impl.TradeRepository;
import MK.validator.ManagmentProductsValidator;

public class BasicOperationProducer {


    private final ProducerRepository producerRepository;
    private final CountryRepository countryRepository;
    private final TradeRepository tradeRepository;
    private final ManagmentProductsValidator managmentProductsValidator;
    private final ModelMappers modelMapper;


    public BasicOperationProducer(
            ProducerRepository producerRepository,
            CountryRepository countryRepository,
            TradeRepository tradeRepository,
            ManagmentProductsValidator managmentProductsValidator,
            ModelMappers modelMapper)
    {
        this.producerRepository = producerRepository;
        this.countryRepository= countryRepository;
        this.tradeRepository = tradeRepository;
        this.managmentProductsValidator = managmentProductsValidator;
        this.modelMapper = modelMapper;
    }

    public void addProducer(ProducerDto producerDto) {

        if (producerDto == null) {
            throw new MyException(ExceptionCode.PRODUCER, "PRODUCER OBJECT IS NULL");
        }

        if (!managmentProductsValidator.validateProducerFields(producerDto)) {
            throw new MyException(ExceptionCode.PRODUCER, "PRODUCER FIELDS ARE NOT VALID");
        }

        if (managmentProductsValidator.validateProducerInsideDB(producerDto)) {
            throw new MyException(ExceptionCode.PRODUCER, "PRODUCER ALREADY EXISTS");
        }

        Producer producer = modelMapper.fromProducerDtoToProducer(producerDto);


        // -----------------------------------------------------------------------------------
        // ------------------------------- COUNTRY VALIDATION --------------------------------
        // -----------------------------------------------------------------------------------

        CountryDto countryDto = producerDto.getCountryDto();

        if (countryDto == null) {
            throw new MyException(ExceptionCode.COUNTRY, "COUNTRY OBJECT IS NULL");
        }

        if (countryDto.getId() == null && countryDto.getName() == null) {
            throw new MyException(ExceptionCode.CUSTOMER, "COUNTRY WITHOUT ID AND NAME");
        }

        if (!managmentProductsValidator.validateCountryFields(countryDto)) {
            throw new MyException(ExceptionCode.COUNTRY, "COUNTRY FIELDS ARE NOT VALID");
        }

        if (!managmentProductsValidator.validateCountryInsideDB(countryDto)) {
            throw new MyException(ExceptionCode.COUNTRY, "COUNTRY NOT FOUND");
        }





        // -----------------------------------------------------------------------------------
        // ------------------------------- TRADE VALIDATION --------------------------------
        // -----------------------------------------------------------------------------------

        TradeDto tradeDto = producerDto.getTradeDto();


        if (tradeDto == null) {
            throw new MyException(ExceptionCode.TRADE, "TRADE OBJECT IS NULL");
        }

        if (!managmentProductsValidator.validateTradeFields(tradeDto)) {
            throw new MyException(ExceptionCode.TRADE, "TRADE FIELDS ARE NOT VALID");
        }

        if (managmentProductsValidator.validateTradeInsideDB(tradeDto)) {
            throw new MyException(ExceptionCode.TRADE, "TRADE ALREADY EXISTS");
        }



        // -----------------------------------------------------------------------------------
        // ----------------------------------- INSERT INTO DB --------------------------------
        // -----------------------------------------------------------------------------------

        Country country = null;

        if (countryDto.getId() != null) {
            country = countryRepository
                    .findOne(countryDto.getId())
                    .orElse(null);
        }

        if (country == null) {
            country = countryRepository
                    .findByName(countryDto.getName())
                    .orElseThrow(() -> new MyException(ExceptionCode.COUNTRY, "COUNTRY WITH GIVEN ID AND NAME NOT FOUND"));
        }



        Trade trade = null;

        if (tradeDto.getId() != null) {
            trade = tradeRepository
                    .findOne(tradeDto.getId())
                    .orElse(null);
        }

        if (trade == null) {
            trade = tradeRepository
                    .findByName(tradeDto.getName())
                    .orElseThrow(() -> new MyException(ExceptionCode.TRADE, "TRADE WITH GIVEN ID AND NAME NOT FOUND"));
        }

        producer.setCountry(country);
        producer.setTrade(trade);
        producerRepository.saveOrUpdate(producer);
    }

    }



