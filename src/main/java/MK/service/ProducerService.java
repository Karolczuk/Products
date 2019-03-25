package MK.service;


import MK.dto.CountryDto;
import MK.dto.ProducerDto;
import MK.dto.TradeDto;
import MK.exceptions.ExceptionCode;
import MK.exceptions.MyException;
import MK.mappers.ModelMappers;
import MK.model.Country;
import MK.model.Producer;
import MK.model.Trade;
import MK.repository.impl.CountryRepository;
import MK.repository.impl.ProducerRepository;
import MK.repository.impl.TradeRepository;
import MK.validator.impl.model.CountryModelValidator;
import MK.validator.impl.model.ProducerModelValidator;
import MK.validator.impl.model.TradeModelValidator;
import MK.validator.impl.persistence.ProducerPersistenceValidator;

public class ProducerService {


    private final ProducerRepository producerRepository;
    private final CountryRepository countryRepository;
    private final TradeRepository tradeRepository;
    private final TradeModelValidator tradeModelValidator;
    private final CountryModelValidator countryModelValidator;
    private final ProducerModelValidator producerModelValidator;
    private final ProducerPersistenceValidator producerPersistenceValidator;
    private final ModelMappers modelMapper;


    public ProducerService(
            ProducerRepository producerRepository,
            CountryRepository countryRepository,
            TradeRepository tradeRepository,
            TradeModelValidator tradeModelValidator,
            ProducerModelValidator producerModelValidator,
            ProducerPersistenceValidator producerPersistenceValidator,
            CountryModelValidator countryModelValidator,
            ModelMappers modelMapper) {
        this.producerRepository = producerRepository;
        this.countryRepository = countryRepository;
        this.tradeRepository = tradeRepository;
        this.tradeModelValidator = tradeModelValidator;
        this.producerModelValidator = producerModelValidator;
        this.producerPersistenceValidator = producerPersistenceValidator;
        this.countryModelValidator = countryModelValidator;
        this.modelMapper = modelMapper;
    }

    public void addProducer(ProducerDto producerDto) {

        if (producerDto == null) {
            throw new MyException(ExceptionCode.PRODUCER, "PRODUCER OBJECT IS NULL");
        }

        if (!producerModelValidator.validateProducerFields(producerDto)) {
            throw new MyException(ExceptionCode.PRODUCER, "PRODUCER FIELDS ARE NOT VALID");
        }

        if (producerPersistenceValidator.validateProducerInsideDB(producerDto)) {
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

        if (!countryModelValidator.validateCountryFields(countryDto)) {
            throw new MyException(ExceptionCode.COUNTRY, "COUNTRY FIELDS ARE NOT VALID");
        }



        // -----------------------------------------------------------------------------------
        // ------------------------------- TRADE VALIDATION --------------------------------
        // -----------------------------------------------------------------------------------

        TradeDto tradeDto = producerDto.getTradeDto();


        if (tradeDto == null) {
            throw new MyException(ExceptionCode.TRADE, "TRADE OBJECT IS NULL");
        }

        if (!tradeModelValidator.validateTradeFields(tradeDto)) {
            throw new MyException(ExceptionCode.TRADE, "TRADE FIELDS ARE NOT VALID");
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



