
//author- Syrill

package TheBusiness;

import TheBusiness.Business.Business;
import TheBusiness.CustomerManagement.CustomerDirectory;
import TheBusiness.CustomerManagement.CustomerProfile;
import TheBusiness.OrderManagement.MasterOrderList;
import TheBusiness.OrderManagement.Order;
import TheBusiness.Personnel.Person;
import TheBusiness.Personnel.PersonDirectory;
import TheBusiness.ProductManagement.Product;
import TheBusiness.ProductManagement.ProductCatalog;
import TheBusiness.Supplier.Supplier;
import TheBusiness.Supplier.SupplierDirectory;
import com.github.javafaker.Faker;

import java.util.ArrayList;

public class DataGenerator {
    
    private Faker faker;
    private Business business;
    
    public DataGenerator(Business business) {
        this.faker = new Faker();
        this.business = business;
    }
    
    /**
     * Main method - call this to generate everything
     */
    public void generateAllData() {
        System.out.println("Starting data generation...");
        
        generateSuppliers(50);
        generateCustomers(300);
        generateOrders();
        
        System.out.println("Data generation complete!");
        printSummary();
    }
    
    /**
     * Step 1: Generate suppliers with products
     */
    private void generateSuppliers(int supplierCount) {
        SupplierDirectory supplierDir = business.getSupplierDirectory();
        
        for (int i = 0; i < supplierCount; i++) {
            // Use Faker to create realistic supplier name
            String supplierName = faker.company().name();
            Supplier supplier = supplierDir.newSupplier(supplierName);
            
            // Each supplier gets 50 products
            generateProductsForSupplier(supplier, 50);
        }
        
        System.out.println("✓ Generated " + supplierCount + " suppliers");
    }
    
    /**
     * Step 2: Generate products for a supplier
     */
    private void generateProductsForSupplier(Supplier supplier, int productCount) {
        ProductCatalog catalog = supplier.getProductCatalog();
        
        for (int j = 0; j < productCount; j++) {
            // Use Faker to create realistic product name
            String productName = faker.commerce().productName();
            
            // Random price range
            int floorPrice = 1000 + (int)(Math.random() * 1000);    // 1000-1999
            int ceilingPrice = floorPrice + 1000 + (int)(Math.random() * 2000); // +1000 to +2999
            
            // Target price is the midpoint
            int targetPrice = floorPrice + (ceilingPrice - floorPrice) / 2;
            
            catalog.newProduct(productName, floorPrice, ceilingPrice, targetPrice);
        }
    }
    
    /**
     * Step 3: Generate customers
     */
    private void generateCustomers(int customerCount) {
        CustomerDirectory customerDir = business.getCustomerDirectory();
        PersonDirectory personDir = business.getPersonDirectory();
        
        for (int i = 0; i < customerCount; i++) {
            // Use Faker to create realistic customer name
            String customerName = faker.name().fullName();
            Person person = personDir.newPerson(customerName);
            customerDir.newCustomerProfile(person);
        }
        
        System.out.println("✓ Generated " + customerCount + " customers");
    }
    
    /**
     * Step 4: Generate orders with items
     */
    private void generateOrders() {
        CustomerDirectory customerDir = business.getCustomerDirectory();
        MasterOrderList orderList = business.getMasterOrderList();
        ArrayList<Supplier> suppliers = business.getSupplierDirectory().getSuplierList();
        
        int totalOrders = 0;
        int totalItems = 0;
        
        // Get all customers
        ArrayList<CustomerProfile> customers = customerDir.getCustomerList();
        
        for (CustomerProfile customer : customers) {
            // Each customer gets 1-3 orders
            int orderCount = 1 + (int)(Math.random() * 3);
            
            for (int i = 0; i < orderCount; i++) {
                Order order = orderList.newOrder(customer);
                totalOrders++;
                
                // Each order has 1-10 items
                int itemCount = 1 + (int)(Math.random() * 10);
                
                for (int j = 0; j < itemCount; j++) {
                    // Pick random supplier
                    Supplier randomSupplier = suppliers.get((int)(Math.random() * suppliers.size()));
                    
                    // Pick random product from that supplier
                    ArrayList<Product> products = randomSupplier.getProductCatalog().getProductList();
                    Product randomProduct = products.get((int)(Math.random() * products.size()));
                    
                    // Random actual price (within floor-ceiling range)
                    int floor = randomProduct.getFloorPrice();
                    int ceiling = randomProduct.getCeilingPrice();
                    int actualPrice = floor + (int)(Math.random() * (ceiling - floor));
                    
                    // Random quantity (1-5)
                    int quantity = 1 + (int)(Math.random() * 5);
                    
                    order.newOrderItem(randomProduct, actualPrice, quantity);
                    totalItems++;
                }
            }
        }
        
        System.out.println("✓ Generated " + totalOrders + " orders with " + totalItems + " total items");
    }
    
    /**
     * Print summary of generated data
     */
    private void printSummary() {
        System.out.println("\n=== DATA GENERATION SUMMARY ===");
        System.out.println("Suppliers: " + business.getSupplierDirectory().getSuplierList().size());
        
        int totalProducts = 0;
        for (Supplier s : business.getSupplierDirectory().getSuplierList()) {
            totalProducts += s.getProductCatalog().getProductList().size();
        }
        System.out.println("Products: " + totalProducts);
        
        System.out.println("Customers: " + business.getCustomerDirectory().getCustomerList().size());
        System.out.println("Orders: " + business.getMasterOrderList().getOrders().size());
        System.out.println("===============================\n");
    }
}