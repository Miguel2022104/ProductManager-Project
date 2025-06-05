package edu.unac;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductManagerTest {
    @Test
    void testAddAndGetProduct() {
        ProductManager manager = new ProductManager();
        Product p = new Product("1", "Laptop", "Electronics", 1200.0);
        manager.addProduct(p);

        Product result = manager.getProductById("1");
        assertNotNull(result);
        assertEquals("Laptop", result.getName());
    }

    @Test
    void testDuplicateIdThrowsException() {
        ProductManager manager = new ProductManager();
        Product p1 = new Product("1", "Laptop", "Electronics", 1200.0);
        Product p2 = new Product("1", "Tablet", "Electronics", 800.0);

        manager.addProduct(p1);
        assertThrows(IllegalArgumentException.class, () -> manager.addProduct(p2));
    }

    @Test
    void testRemoveProduct() {
        ProductManager manager = new ProductManager();
        manager.addProduct(new Product("1", "Laptop", "Electronics", 1200.0));
        manager.removeProduct("1");

        assertNull(manager.getProductById("1"));
    }

    @Test
    void testGetProductsByCategory() {
        ProductManager manager = new ProductManager();
        manager.addProduct(new Product("1", "Laptop", "Electronics", 1200.0));
        manager.addProduct(new Product("2", "Shirt", "Clothing", 40.0));
        manager.addProduct(new Product("3", "Monitor", "Electronics", 300.0));

        List<Product> electronics = manager.getProductsByCategory("Electronics");
        assertEquals(2, electronics.size());
        assertTrue(electronics.stream().anyMatch(p -> p.getName().equals("Laptop")));
        assertTrue(electronics.stream().anyMatch(p -> p.getName().equals("Monitor")));
    }

    @Test
    void testListAllProducts() {
        ProductManager manager = new ProductManager();
        Product p1 = new Product("1", "Laptop", "Electronics", 1200.0);
        Product p2 = new Product("2", "Shirt", "Clothing", 40.0);
        Product p3 = new Product("3", "Book", "Education", 25.0);

        manager.addProduct(p1);
        manager.addProduct(p2);
        manager.addProduct(p3);

        List<Product> allProducts = manager.listAll();

        assertEquals(3, allProducts.size());
        assertTrue(allProducts.contains(p1));
        assertTrue(allProducts.contains(p2));
        assertTrue(allProducts.contains(p3));
    }

    @Test
    void testGetProductsByPriceRange() {
        ProductManager manager = new ProductManager();
        manager.addProduct(new Product("1", "Laptop", "Electronics", 1200.0));
        manager.addProduct(new Product("2", "Shirt", "Clothing", 40.0));
        manager.addProduct(new Product("3", "Book", "Education", 25.0)); // Este es el segundo producto que cae en el rango (20.0, 100.0)
        manager.addProduct(new Product("4", "Tablet", "Electronics", 500.0));
        manager.addProduct(new Product("5", "Headphones", "Electronics", 150.0));

        // Test a range that includes multiple products
        List<Product> affordableProducts = manager.getProductsByPriceRange(20.0, 100.0);
        // CORRECCIÓN: Esperamos 2 productos, no 1.
        assertEquals(2, affordableProducts.size());
        assertTrue(affordableProducts.stream().anyMatch(p -> p.getName().equals("Shirt")));
        assertTrue(affordableProducts.stream().anyMatch(p -> p.getName().equals("Book")));


        // Test a range that includes a product at the boundary (min)
        List<Product> midRangeProducts = manager.getProductsByPriceRange(150.0, 500.0);
        assertEquals(2, midRangeProducts.size());
        assertTrue(midRangeProducts.stream().anyMatch(p -> p.getName().equals("Headphones")));
        assertTrue(midRangeProducts.stream().anyMatch(p -> p.getName().equals("Tablet")));

        // Test a range that includes a product at the boundary (max)
        List<Product> expensiveProducts = manager.getProductsByPriceRange(1000.0, 1500.0);
        assertEquals(1, expensiveProducts.size());
        assertEquals("Laptop", expensiveProducts.get(0).getName());

        // Test a range with no products
        List<Product> emptyRange = manager.getProductsByPriceRange(2000.0, 3000.0);
        assertTrue(emptyRange.isEmpty());

        // Test a range where min > max
        List<Product> invalidRange = manager.getProductsByPriceRange(100.0, 10.0);
        assertTrue(invalidRange.isEmpty());
    }
}