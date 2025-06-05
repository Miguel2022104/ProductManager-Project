package edu.unac;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProductManager {
    private final Map<String, Product> products = new HashMap<>();

    public void addProduct(Product product) {
        if (products.containsKey(product.getId())) {
            throw new IllegalArgumentException("Product ID already exists");
        }
        products.put(product.getId(), product);
    }

    public Product getProductById(String id) {
        return products.get(id);
    }

    public void removeProduct(String id) {
        products.remove(id);
    }

    public List<Product> getProductsByCategory(String category) {
        return products.values().stream()
                .filter(p -> p.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());
    }

    public List<Product> listAll() {
        return new ArrayList<>(products.values());
    }
}
