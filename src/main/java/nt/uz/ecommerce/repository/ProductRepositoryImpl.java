package nt.uz.ecommerce.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import lombok.extern.slf4j.Slf4j;
import nt.uz.ecommerce.model.Category;
import nt.uz.ecommerce.model.Products;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
@Slf4j

public class ProductRepositoryImpl {

    @Autowired
    private EntityManager entityManager;
    
    public Page<Products> universalSearch(String query, List<String> filter,String sorting, String ordering, Integer size, Integer currentPage) {
        size = Math.max(size, 0);
        int page = Math.max(currentPage,0);

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Products> criteriaQuery = criteriaBuilder.createQuery(Products.class);
        Root<Products> root = criteriaQuery.from(Products.class);
        criteriaQuery.select(root);

        Predicate namePredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + query.toLowerCase() + "%");
        Predicate categoryPredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("category").get("name")), "%" + query.toLowerCase() + "%");
        Predicate descriptionPredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), "%" + query.toLowerCase() + "%");
        Predicate finalPredicate = criteriaBuilder.or(namePredicate, categoryPredicate, descriptionPredicate);



        TypedQuery<Category> customQuery = entityManager.createQuery("select c from Category c where LOWER( c.name ) LIKE :query", Category.class);
        customQuery.setParameter("query","%"+query.toLowerCase()+"%");

        if(filter != null && !filter.isEmpty()){
            Predicate filterPredicate = root.get("brand").get("name").in(filter);
            finalPredicate = criteriaBuilder.and(filterPredicate,filterPredicate);
            criteriaQuery.where(finalPredicate);
        }else {
            criteriaQuery.where(finalPredicate);
        }
        if (sorting != null && ordering != null) {
            Path<Object> sortPath;
            if (ordering.equalsIgnoreCase("ascending")) {
                sortPath = root.get(sorting);
                criteriaQuery.orderBy(criteriaBuilder.asc(sortPath));
            } else if (ordering.equalsIgnoreCase("descending")) {
                sortPath = root.get(sorting);
                criteriaQuery.orderBy(criteriaBuilder.desc(sortPath));
            }
        }

        TypedQuery<Products> search = entityManager.createQuery(criteriaQuery);

        long count = search.getResultList().size();


        if (count > 0 && count / size <= page){
            if (count % size == 0) {
                page = (int) count / size - 1;
            } else {
                page = (int) count / size;
            }
        }
        search.setFirstResult(size * page);
        search.setMaxResults(size);

        if(search.getResultList().isEmpty()){
            if(!customQuery.getResultList().isEmpty()){
                return null;//getWithSort(customQuery.getResultList().get(0).getId(),filter,sorting,ordering,currentPage);
            }
        }
        return new PageImpl<>(search.getResultList(),PageRequest.of(page,size),count);

    }

//    public Page<Products> universalSearch1(Map<String, String> params) {
//        int page = 0, size = 10;
//
//        if (params.containsKey("size")) {
//            size = Integer.parseInt(params.get("size"));
//        }
//        if (params.containsKey("page")) {
//            page = Integer.parseInt(params.get("page"));
//        }
//
//        Map<String, Boolean> str = Map.of("name", true,
//                                        "category", true,
//                                    "description", true);
//
//        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
//        CriteriaQuery<Products> cr = cb.createQuery(Products.class);
//        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
//        Root<Products> root = cr.from(Products.class);
//        List<Predicate> predicates = new ArrayList<>();
//
//        params.forEach((columnName, value) -> {
//            if (str.containsKey(columnName)) {
//                predicates.add(cb.like(root.get(columnName), columnName));
//            }
//            else {
//                predicates.add(cb.equal(root.get(columnName), columnName));
//            }
//        });
//
//        cr.select(root)
//                .where((Predicate[]) predicates.toArray())
//                .orderBy(cb.asc(root.get("columnName")));

//        cq.select(root)
//                .where(cb.count((Predicate[]) predicates.toArray()));
//        List<Products> products = entityManager.createQuery(cr).getResultList();

//        return null;
//    }

    public List<Products> universalSearch2(Map<String, String> params){
        String sqlQuery = "select p from Products p where 1=1 ";
        StringBuilder queryCondition = new StringBuilder();

        generateQueryCondition(queryCondition, params);

        Query query = entityManager.createQuery(sqlQuery + queryCondition, Products.class);

        setParams(query, params);

        return query.getResultList();
    }

    public Page<Products> universalSearch3(Map<String, String> params){
        int page = 0, size = 10;

        if (params.containsKey("size")) {
            size = Integer.parseInt(params.get("size"));
        }
        if (params.containsKey("page")) {
            page = Integer.parseInt(params.get("page"));
        }

        String sqlQuery = "select p from Products p where 1=1 ";
        String sqlCountQuery = "select count(1) from Product p where 1=1 ";
        StringBuilder queryCondition = new StringBuilder();

        generateQueryCondition(queryCondition, params);

        Query query = entityManager.createQuery(sqlQuery + queryCondition, Products.class);
        Query countQuery = entityManager.createQuery(sqlQuery + queryCondition, Products.class);
        setParams(query, params);
        setParams(countQuery, params);

        long count = (long) countQuery.getSingleResult();

        if (page>= count / size) {
            if (count % size == 0) {
                page = (int) count / size - 1;
            }
        }
        else {
            page = (int) count / size;
        }

        query.setFirstResult(size * page);
        query.setMaxResults(size);

        List<Products> products = query.getResultList();
        return new PageImpl<>(products, PageRequest.of(page, size), count);
    }

    private void generateQueryCondition(StringBuilder queryCondition, Map<String, String> params){
        if (params.containsKey("name")){
            queryCondition.append(" AND upper(p.name) like :name ");
        }
        if (params.containsKey("amount")){
            queryCondition.append(" AND p.amount = :amount");
        }
        if (params.containsKey("price")){
            queryCondition.append(" AND p.price = :price");
        }
        //...
    }

    private void setParams(Query query, Map<String, String> params){
        if (params.containsKey("name")){
            query.setParameter("name", "%" + params.get("name").toUpperCase() + "%");
        }
        if (params.containsKey("amount")){
            query.setParameter("amount", Integer.parseInt(params.get("amount")));
        }
        if (params.containsKey("price")){
            query.setParameter("price", Integer.parseInt(params.get("price")));
        }
    }

    private void getCategoriesRecursive(Integer categoryId, List<Integer> categoryIds) {
        categoryIds.add(categoryId);

        List<Integer> childIds = entityManager.createQuery(
                        "SELECT c.id FROM Category c WHERE c.parentCategoryId = :categoryId",
                        Integer.class
                )
                .setParameter("categoryId", categoryId)
                .getResultList();

        for (Integer childId : childIds) {
            getCategoriesRecursive(childId, categoryIds);
        }
    }

}
