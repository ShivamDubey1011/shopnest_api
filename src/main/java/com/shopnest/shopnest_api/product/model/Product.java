package com.shopnest.shopnest_api.product.model;

import com.shopnest.shopnest_api.common.model.BaseDocument;
import lombok.*;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "products")
@CompoundIndex(name = "category_price_idx", def = "{'category':1,'price':1}")
public class Product extends BaseDocument {

    @Indexed(unique = true)
    private String slug;   //used in URLs

    @TextIndexed
    private String name;

    private String description;

    @Field(targetType = FieldType.DECIMAL128)
    private BigDecimal price;

    private Integer stock;

    @Indexed
    private String category;

    private ProductStatus status;

    private List<String> tags;
}
