package TheBusiness;

import com.github.javafaker.Faker;

public class TestFaker {
    public static void main(String[] args) {
        Faker faker = new Faker();
        
        System.out.println("Testing Java Faker...\n");
        
        System.out.println("Company: " + faker.company().name());
        System.out.println("Product: " + faker.commerce().productName());
        System.out.println("Person: " + faker.name().fullName());
        
        System.out.println("\n✓ Java Faker is working!");
    }
}
