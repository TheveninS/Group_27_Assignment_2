/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TheBusiness;

import TheBusiness.Business.Business;

/**
 *
 * @author kal bugrara
 */
public class RangePricingApplication {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Initialize business with generated data
        Business business = ConfigureABusiness.initializeWithGeneratedData();
        
        System.out.println("\nBusiness initialized successfully!");
        System.out.println("Ready to start pricing analysis...");
    }

}