package org.scoula.sub.app.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    private ObjectId id;

    private String sku;

    private String slug;

    private String name;

    private ObjectId main_cat_id;

    private ObjectId category_id;

    private Map<String, String> details;

    private List<String> tags;

    private int helpful_votes;
}