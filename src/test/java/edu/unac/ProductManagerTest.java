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

        List<Product> electronics = manager.getProductsByCategory("Electronics");
        assertEquals(1, electronics.size());
        assertEquals("Laptop", electronics.get(0).getName());
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
        // Datos de prueba mínimos para cubrir los escenarios
        manager.addProduct(new Product("A", "Product A", "Category", 50.0));
        manager.addProduct(new Product("B", "Product B", "Category", 150.0));
        manager.addProduct(new Product("C", "Product C", "Category", 75.0));

        // Encontrar productos en un rango válido
        List<Product> foundProducts = manager.getProductsByPriceRange(40.0, 100.0);
        assertEquals(2, foundProducts.size(), "Debe encontrar 2 productos en el rango de 40 a 100.");
        assertTrue(foundProducts.stream().anyMatch(p -> p.getName().equals("Product A")), "Debe incluir Product A.");
        assertTrue(foundProducts.stream().anyMatch(p -> p.getName().equals("Product C")), "Debe incluir Product C.");


        // Rango sin productos
        List<Product> emptyRange = manager.getProductsByPriceRange(200.0, 300.0);
        assertTrue(emptyRange.isEmpty(), "No debe encontrar productos en un rango vacío.");
    }

}