# Java Billing project [Junjie Zhu] [CMPE-202]

## Overview
### This is a Java Maven Project that is capable of simulating the shopping and billing process given storage and shopping info in the CSV format.

## Instructions on running the program

1. Download the source code.
2. Extract it to an arbitrary folder.
3. CRITICAL -> do not change the content of src/input/storage_list.csv.
4. CRITICAL -> change the content of the src/input/shoppinglist.csv file to any desired input.
   * The following format is expected, otherwise you will encounter error during the program execution.
     * values seperated by comma(',').
     * The 1st row should contain only the header info: Item,Quantity,CardNumber
     * credit card number on the 2nd row and 3rd column, and it should be one of the following number:
       * [5410000000000000, 4120000000000, 341000000000000, 6010000000000000]
     * starting from the 2nd row, items name on the 1st column(under header 'Item'), item quantity on the 2nd column(under header 'Quantity').
     * Item should be one of the selling item in the storage list: src/input/storage_list.csv.
     * Item quantity should be less than the corresponding item quantity in the storage.
     * Items category(Essentials,Luxury,Misc) quantity should be less than the category cap in the register:
       * Essentials cap = 3
       * Luxury = 4
       * Misc = 6
5. Run the program using one of the following option.
   * Option 1 (Recommended): Run it using a modern IDE tool.
       * 3a. Using an arbitrary IDE (IntelliJ IDEA, Eclipse, etc), load/open as a Maven project.
       * 4a. Run the Main function.

   * Option 2: Run it using terminal.
       * 2b. CRITICAL -> make sure you have JAVA 17 SDK installed, and JAVA runtime version higher than 61.0.
       * 3b. Change your current working directory to target/classes/cmpe.
       * 4b. Open a terminal, execute the main class: Java Billing.
           * Example usage:
               * C:\Local Project\CMPE-202-individual-project-Junjie_Zhu\target\classes\cmpe> Java Billing

## Instructions on checking the output
* Go to folder src/output, you will see a billing_log.log file that has the complete log record of the previous run.
* In the same folder, based on the termination situation, you will get one of the following result:
  * if the program terminated correctly, you will see a billing_list.csv file that contains the output billing info.
  * if the program terminated due to any error/exception, you will see an error.txt file that contains the error/exception info.

## Utilized design pattern

Facade Pattern ->
* Inside the Billing class main function, there is a simple function call on line 26:
    * Bill bill = register.checkOut();
* This checkOut() method from Register class return a Bill object by calling multiple complex functions inside the functions:
    * itemQuantityCapValidation() -> Perform item quantity cap validation for all purchase items.
    * itemCategoryCapValidation() -> Perform item category cap validation for all purchase items.
    * int finalPrice = calculateFinalPrice() -> Perform final price calculation based on all purchase items.
    * HashMap<String, Item> billingList = writeBillingList() -> Writing billing item info for Bill object construction.
    * Bill bill = new Bill(finalPrice, billingList) -> construct bill object based on final price and billing item info.

## Class diagram
  [Billing_Class_Diagram](Billing_Class_Diagram.png)



