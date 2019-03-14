package MK.repository.impl;

import MK.exceptions.ExceptionCode;
import MK.exceptions.MyException;
import MK.model.Shop;
import MK.repository.generic.AbstractGenericRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.Optional;

public class ShopRepositoryImpl extends AbstractGenericRepository<Shop> implements ShopRepository {

    @Override
    public Optional<Shop> findByName(String shopName, String countryName) {
        Optional<Shop> shop = Optional.empty();
        EntityManager em = null;
        EntityTransaction tx = null;
        try {
            em = emf.createEntityManager();
            tx = em.getTransaction();
            tx.begin();
            shop = em
                    .createQuery("select s from Shop s where s.name = :shopName and s.country.name = :countryName", Shop.class)
                    .setParameter("shopName", shopName)
                    .setParameter("countryName",countryName)
                    .getResultList().stream().findFirst();

            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException(ExceptionCode.SHOP, e.getMessage());
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return shop;
    }

}

