package MK.repository.impl;

import MK.model.EGuarantee;
import MK.model.GuaranteeComponents;
import MK.repository.generic.GenericRepository;

import java.util.Optional;

public interface GuaranteeRepository extends GenericRepository<GuaranteeComponents> {
    Optional<GuaranteeComponents> findByName(EGuarantee name);

}
