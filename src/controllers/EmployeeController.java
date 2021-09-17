package controllers;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Scanner;

import models.*;
import utils.EmployeeUtils;
import utils.ValueHolder;

public class EmployeeController{
    public static void listEmployees(ArrayList<Employee> employees){
        for(Employee employee : employees){
            System.out.println(employee.toString());
        }
    }

    public static void removeEmployee(Scanner input, ArrayList<Employee> employees){
        System.out.print("Enter de ID of the Employee: ");
        String id = input.nextLine();

        Employee removedEmployee = null;
        for(Employee employee : employees){
            if(employee.getId().toString().equals(id)){
                removedEmployee = employee;
                employees.remove(employee);
                break;
            }
        }

        if(removedEmployee == null){
            System.out.println("\nError: Employee not found.\n");
        }
        else{
            System.out.println("\nEmployee removed successfully.\n");
        }
    }

    public static void registerNewEmployee(Scanner input, ArrayList<Employee> employees){
        Employee employee = EmployeeUtils.readEmployeeBasicData(input).getEmployee();

        if(employee == null){
            System.out.println("\nError: Employee not registered.\n");
            return;
        }

        employees.add(employee);
        System.out.println(employee);
        System.out.println("\nEmployee registered successfully.\n");
    }

    public static void updateEmployee(Scanner input, ArrayList<Employee> employees){
        Employee employee = EmployeeUtils.findEmployee(input, employees);

        if(employee == null) return;

        ValueHolder valueHolder = EmployeeUtils.readEmployeeBasicData(input);
        Employee auxiliarEmployee = valueHolder.getEmployee();

        if(auxiliarEmployee == null){
            System.out.println("\nError: Invalid Data. Employee update failed.");
            return;
        }

        employee.setName(auxiliarEmployee.getName());
        employee.setAddress(auxiliarEmployee.getAddress());
        employee.setSalary(auxiliarEmployee.getSalary());
        employee.setEmployeeSyndicate(auxiliarEmployee.getEmployeeSyndicate());
        employee.setPaymentData(auxiliarEmployee.getPaymentData());

        if(auxiliarEmployee instanceof Comissioned){
            Comissioned updatedEmployee = new Comissioned(employee.getName(),
                                                            employee.getAddress(), employee.getSalary(),
                                                            valueHolder.getComission(), employee.getPaymentData());
            updatedEmployee.setId(employee.getId());
            updatedEmployee.setEmployeeSyndicate(auxiliarEmployee.getEmployeeSyndicate());
            EmployeeUtils.removeSpecificEmployee(employee.getId().toString(), employees);
            employees.add(updatedEmployee);
            System.out.println(updatedEmployee);
        }
        else{
            System.out.println(employee);
        }

        System.out.println("\nEmployee updated successfully.\n");
    }

    public static void editEmployeeSchedule(Scanner input, ArrayList<Employee> employees){
        Employee employee = EmployeeUtils.findEmployee(input, employees);
        int scheduleOption;

        if(EmployeeUtils.wasEmployeeFound(employee)){
            System.out.println("Schedule: ");
            System.out.println("1 - Monthly\n2 - Weekly\n3 - Every two weeks");
            System.out.print("User: ");
            scheduleOption = input.nextInt();

            switch(scheduleOption){
                case 1:
                    employee.getPaymentData().getPaymentSchedule().setSchedule("Monthly");
                    break;
                case 2:
                    employee.getPaymentData().getPaymentSchedule().setSchedule("Weekly");
                    employee.getPaymentData().getPaymentSchedule().setWeekDay(DayOfWeek.FRIDAY);
                    break;
                case 3:
                    employee.getPaymentData().getPaymentSchedule().setSchedule("Every two weeks");
                    employee.getPaymentData().getPaymentSchedule().setWeekDay(DayOfWeek.FRIDAY);
                    break;
                default:
                    System.out.println("\n\nError: Invalid Option. Employee registered unsuccessfully.\n\n");
                    break;
            }

            System.out.println(employee);
        }
        else{
            return;
        }
    }
}