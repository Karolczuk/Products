package MK.repository.impl;

import MK.model.Trade;
import MK.repository.generic.GenericRepository;

import java.util.Optional;

public interface TradeRepository extends GenericRepository<Trade> {
        Optional<Trade> findByName(String name);
}
