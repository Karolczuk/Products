package MK.repository.generic;

import java.util.List;
import java.util.Optional;

public interface GenericRepository<T> {
    T saveOrUpdate(T t);
    void delete(Long id);
    void deleteAll();
    Optional<T> findOne(Long id);
    List<T> findAll();
}
