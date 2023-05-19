package nt.uz.ecommerce.repository;

import nt.uz.ecommerce.model.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Products, Integer> {
//    @Query(value = "select * from product p where (p.price, p.category_id) in (select max(p2.price), p2.category_id from product p2 group by p2.category_id)",
//            nativeQuery = true)
//    List<Products> getExpensiveProducts();
//
//    @Query(value = "select p from Product p where (p.price, p.category.id) in (select max(p2.price), p2.category.id from Product p2 group by p2.category.id)")
//    List<Products> getExpensiveProducts2();
//
//    @Query(name = "findProductById")
//    List<Products> findProductById(@Param("id") Integer id,
//                                  @Param("name") String name,
//                                  @Param("amount") Integer amount,
//                                  @Param("price") Integer price);
}
