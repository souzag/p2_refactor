package utils;

import java.time.DateTimeException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Stack;

import controllers.EmployeeController;
import controllers.PaymentController;
import models.Company;

public class Menu{
    public static void menu(Company company){
        int option = 13;

        Scanner input = new Scanner(System.in);
        Stack<String> undo = new Stack<>();
        Stack<String> redo = new Stack<>();

        try{
            while(option != 0){
                System.out.println("0  - Exit;");
                System.out.println("1  - Register an employee;");
                System.out.println("2  - Remove an employee;");
                System.out.println("3  - Launch a timecard;");
                System.out.println("4  - Launch a sale result;");
                System.out.println("5  - Launch a service tax;");
                System.out.println("6  - Update an employee data;");
                System.out.println("7  - List the employees;");
                System.out.println("8  - Launch the payroll;");
                System.out.println("9  - Show payment lists;");
                System.out.println("10 - Edit payment schedule;");
                System.out.println("11 - Register a new payment list;");
                System.out.println("12 - Show new payment's lists;");
                System.out.println("13 - Undo;");
                System.out.println("14 - Redo.");

                System.out.print("\nUser: ");
                option = input.nextInt();
                input.nextLine();

                switch(option){
                    case 0:
                        System.out.println("System is now closed.");
                        break;
                    case 1:
                        undo.push(GeneralUtils.saveState(company));
                        EmployeeController.registerNewEmployee(input, company.getEmployees());
                        break;
                    case 2:
                        if(!EmployeeUtils.warningEmptyEmployeesList(company.getEmployees())){
                            undo.push(GeneralUtils.saveState(company));
                            EmployeeController.listEmployees(company.getEmployees());
                            EmployeeController.removeEmployee(input, company.getEmployees());
                        }
                        break;
                    case 3:
                        if(!EmployeeUtils.warningEmptyEmployeesList(company.getEmployees())){
                            undo.push(GeneralUtils.saveState(company));
                            EmployeeUtils.listHourly(company.getEmployees());
                            EmployeeController.launchTimeCard(input, company.getEmployees());
                        }
                        break;
                    case 4:
                        if(!EmployeeUtils.warningEmptyEmployeesList(company.getEmployees())){
                            undo.push(GeneralUtils.saveState(company));
                            EmployeeUtils.listComissioned(company.getEmployees());
                            EmployeeController.launchSaleResult(input, company.getEmployees());
                        }
                        break;
                    case 5:
                        if(!EmployeeUtils.warningEmptyEmployeesList(company.getEmployees())){
                            undo.push(GeneralUtils.saveState(company));
                            EmployeeController.listEmployees(company.getEmployees());
                            EmployeeController.launchServiceTax(input, company.getEmployees());
                        }
                        break;
                    case 6:
                        if(!EmployeeUtils.warningEmptyEmployeesList(company.getEmployees())){
                            undo.push(GeneralUtils.saveState(company));
                            EmployeeController.listEmployees(company.getEmployees());
                            EmployeeController.updateEmployee(input, company.getEmployees());
                        }
                        break;
                    case 7:
                        EmployeeController.listEmployees(company.getEmployees());
                        break;
                    case 8:
                        if(!EmployeeUtils.warningEmptyEmployeesList(company.getEmployees())){
                            undo.push(GeneralUtils.saveState(company));
                            EmployeeController.listEmployees(company.getEmployees());
                            PaymentController.LaunchPayroll(input, company);
                        }
                        break;
                    case 9:
                        if(company.getPaymentLists().size() > 0){
                            System.out.println(company.getPaymentLists());
                        }
                        else{
                            System.out.println("\n\n Error: There's no registered payment list. \n\n");
                        }
                        break;
                    case 10:
                        if(!EmployeeUtils.warningEmptyEmployeesList(company.getEmployees())){
                            undo.push(GeneralUtils.saveState(company));
                            EmployeeController.listEmployees(company.getEmployees());
                            EmployeeController.editEmployeeSchedule(input, company.getEmployees());
                        }
                        break;
                    case 11:
                        undo.push(GeneralUtils.saveState(company));
                        company.getPaymentSchedules().add(EmployeeUtils.createPaymentSchedule(input));
                        break;
                    case 12:
                        System.out.println(company.getPaymentSchedules());
                        break;
                    case 13:
                        if(!undo.isEmpty()){
                            redo.push(GeneralUtils.saveState(company));
                            String state = undo.pop();
                            company = GeneralUtils.restoreState(state);
                        }
                        else{
                            System.out.println("\n\n Error: There's nothing to undo. \n\n");
                        }
                        break;
                    case 14:
                        if(!redo.isEmpty()){
                            undo.push(GeneralUtils.saveState(company));
                            String state = redo.pop();
                            company = GeneralUtils.restoreState(state);
                        }
                        else{
                            System.out.println("\n\n Error: There's nothing to undo. \n\n");
                        }
                        break;
                    default:
                        System.out.println("\n\n Error: Invalid option. Please, try again. \n\n");
                        break;
                }
            }
        }

        catch(InputMismatchException err){
            System.out.println("\n\n Error: Invalid option. Please, try again. \n\n");
            menu(company);
        }
        catch(NumberFormatException err){
            System.out.println("\n\n Error: Invalid entry. Please, try again. \n\n");
            menu(company);
        }
        catch(DateTimeException err){
            System.out.println("\n\n Error: Invalid date. Please, try again. \n\n");
            menu(company);
        }
        catch(IndexOutOfBoundsException err){
            System.out.println("\n\n Error: Invalid date. Please, try again. \n\n");
            menu(company);
        }

        input.close();
    }
}