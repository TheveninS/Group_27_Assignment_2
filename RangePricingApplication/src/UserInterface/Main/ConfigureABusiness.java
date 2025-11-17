/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UserInterface.Main;

import MarketingManagement.MarketingPersonDirectory;
import MarketingManagement.MarketingPersonProfile;
import TheBusiness.Business.Business;
import TheBusiness.CustomerManagement.CustomerDirectory;
import TheBusiness.CustomerManagement.CustomerProfile;
import TheBusiness.OrderManagement.MasterOrderList;
import TheBusiness.OrderManagement.Order;
import TheBusiness.OrderManagement.OrderItem;
import TheBusiness.Personnel.Person;
import TheBusiness.Personnel.PersonDirectory;
import TheBusiness.ProductManagement.Product;
import TheBusiness.ProductManagement.ProductCatalog;
import TheBusiness.SalesManagement.SalesPersonDirectory;
import TheBusiness.SalesManagement.SalesPersonProfile;
import TheBusiness.Supplier.Supplier;
import TheBusiness.Supplier.SupplierDirectory;
import TheBusiness.UserAccountManagement.UserAccount;
import TheBusiness.UserAccountManagement.UserAccountDirectory;
import TheBusiness.DataGenerator;

/**
 *
 * @author kal bugrara
 */
class ConfigureABusiness {
    
    /**
     * Initialize business with auto-generated data using DataGenerator
     * This creates 50 suppliers with 50 products each, 300 customers, and orders
     */
    static Business initializeWithGeneratedData() {
        Business business = new Business("Xerox");
        
        // Generate all data automatically
        DataGenerator generator = new DataGenerator(business);
        generator.generateAllData();
        
        // Create admin accounts
        PersonDirectory persondirectory = business.getPersonDirectory();
        Person xeroxadminperson001 = persondirectory.newPerson("Xerox admin");
        Person xeroxsalesperson001 = persondirectory.newPerson("Xerox sales");
        Person xeroxmarketingperson001 = persondirectory.newPerson("Xerox marketing");
        
        // Create Sales person profile
        SalesPersonDirectory salespersondirectory = business.getSalesPersonDirectory();
        SalesPersonProfile salespersonprofile = salespersondirectory.newSalesPersonProfile(xeroxsalesperson001);
        
        // Create Marketing person profile
        MarketingPersonDirectory marketingpersondirectory = business.getMarketingPersonDirectory();
        MarketingPersonProfile marketingpersonprofile0 = marketingpersondirectory.newMarketingPersonProfile(xeroxmarketingperson001);
        
        // Create Admin
        TheBusiness.Personnel.EmployeeDirectory employeedirectory = new TheBusiness.Personnel.EmployeeDirectory(business);
        TheBusiness.Personnel.EmployeeProfile employeeprofile0 = employeedirectory.newEmployeeProfile(xeroxadminperson001);
        
        // Create User accounts
        UserAccountDirectory uadirectory = business.getUserAccountDirectory();
        UserAccount ua1 = uadirectory.newUserAccount(salespersonprofile, "sales", "XXXX");
        UserAccount ua2 = uadirectory.newUserAccount(marketingpersonprofile0, "marketing", "XXXX");
        UserAccount ua3 = uadirectory.newUserAccount(employeeprofile0, "admin", "XXXX");
        
        return business;
    }

    /**
     * Main initialize method - calls the data generator version
     */
    static Business initialize() {
        return initializeWithGeneratedData();
    }
}