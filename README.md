# Java Billing project [Junjie Zhu] [CMPE-202]

## Overview
### This is a Java Maven Project that is capable of simulating the shopping and billing process given storage and shopping info (as CSV files that are stored in the src/input folder).

## Instructions on running the project

1. Download the source code.
2. Extract it to an arbitrary folder.
3. Run it.
* Option 1 (Recomended): Run it using a modern IDE tool
    * 3a. Using an arbitrary IDE (IntelliJ IDEA, Eclipse, etc), load/open as a Maven project
    * 4a. Run the Mian function.

* Option 2: Run it using terminal
    * 2b. CRITICAL - make sure you have JAVA 17 SDK installed, and JAVA runtime version higher than 61.0
    * 3b. Change your current working directory to target/classes/cmpe
    * 4b. Open a terminal, execute the main class: Java Billing
        * Example usage:
            * C:\Local Project\CMPE-202-individual-project-Junjie_Zhu\target\classes\cmpe> Java Billing

## Utlized design pattern

Facade Pattern ->
* Inside the Billing class main function, there is a simple function call on line 26:
    * Bill bill = register.checkOut();
* This checkOut() method from Register class return a Bill object by calling multiple complex functions inside the functions:
    * itemQuantityCapValidation() -> Perform item quantity cap validation for all purchase items.
    * itemCategoryCapValidation() -> Perform item category cap validation for all purchase items.
    * int finalPrice = calculateFinalPrice() -> Perform final price calculation based on all purchase items.
    * HashMap<String, Item> billingList = writeBillingList() -> Writing billing item info for Bill object construction.
    * Bill bill = new Bill(finalPrice, billingList) -> construct bill object based on final price and billing item info.

  



