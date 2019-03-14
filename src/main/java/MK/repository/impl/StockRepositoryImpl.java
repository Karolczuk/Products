package MK.repository.impl;

import MK.exceptions.ExceptionCode;
import MK.exceptions.MyException;
import MK.model.Producer;
import MK.model.Product;
import MK.model.Shop;
import MK.model.Stock;
import MK.repository.generic.AbstractGenericRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.Optional;

public class StockRepositoryImpl  extends AbstractGenericRepository<Stock> implements StockRepository{
    @Override
    public Optional<Stock> findByName(String productName,String categoryName, String shopName, String countryName) {
        Optional<Stock> stock = Optional.empty();
        EntityManager em = null;
        EntityTransaction tx = null;
        try {
            em = emf.createEntityManager();
            tx = em.getTransaction();
            tx.begin();
            stock = em
                    .createQuery("select s from Stock s where s.product.name = :productName and s.product.category.name = :categoryName and  s.shop.name = :shopName and s.shop.country.name = :countryName", Stock.class)
                    .setParameter("productName", productName)
                    .setParameter("categoryName",categoryName)
                    .setParameter("shopName", shopName)
                    .setParameter("countryName", countryName)
                    .getResultList().stream().findFirst();

            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException(ExceptionCode.STOCK, e.getMessage());
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return stock;
    }
    }

