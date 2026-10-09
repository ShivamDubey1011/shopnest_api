package com.shopnest.shopnest_api.product.repository;

import com.shopnest.shopnest_api.product.dto.ProductFilter;
import com.shopnest.shopnest_api.product.model.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.Decimal128;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class ProductRepositoryCustomImpl implements ProductRepositoryCustom {
    private final MongoTemplate mongoTemplate;

    @Override
    public Page<Product> search(ProductFilter filter, Pageable pageable) {
        Query query = new Query(buildCriteria(filter)).with(pageable);
        log.debug("Product search query={}", query);

        List<Product> content = mongoTemplate.find(query, Product.class);

        Query countQuery = Query.of(query).limit(-1).skip(-1);

        return PageableExecutionUtils
                .getPage(content, pageable, () -> mongoTemplate.count(countQuery, Product.class));
    }

    private Criteria buildCriteria(ProductFilter filter) {
        List<Criteria> condition = new ArrayList<>();

        if (filter.category() != null) {
            condition.add(Criteria.where("category").is(filter.category()));
        }

        if (filter.status() != null) {
            condition.add(Criteria.where("status").is(filter.status()));
        }

        if (filter.tag() != null) {
            condition.add(Criteria.where("tags").is(filter.tag()));
        }

        if (filter.minPrice() != null || filter.maxPrice() != null) {
            Criteria price = Criteria.where("price");
            if (filter.minPrice() != null) price = price.gte(new Decimal128(filter.minPrice()));
            if (filter.maxPrice() != null) price = price.lte(new Decimal128(filter.maxPrice()));
            condition.add(price);
        }

        return condition.isEmpty() ? new Criteria() : new Criteria().andOperator(condition);
    }
}
