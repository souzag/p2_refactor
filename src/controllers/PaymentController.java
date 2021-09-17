package controllers;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Scanner;

import models.Company;
import models.Employee;
import models.Hourly;
import models.Comissioned;
import models.services.TimeCard;
import models.services.ServiceTax;
import models.services.SaleResult;
import models.services.payment.PayCheck;

import utils.EmployeeUtils;
import utils.GeneralUtils;

public class PaymentController{
    public static void LaunchPayroll(Scanner input, Company company){
        if(EmployeeUtils.warningEmptyEmployeesList(company.getEmployees())) return;

        int count, week = -1;

        String stringDate;
        PayCheck payCheck;
        ArrayList<PayCheck> payCheckList = new ArrayList<PayCheck>();

        System.out.print("Enter the first date of the month (YYYY-MM-DD): ");
        stringDate = input.nextLine();
        ArrayList<Integer> firstDate = GeneralUtils.convertDateToArray(stringDate);

        System.out.print("Enter the last date of the month (YYYY-MM-DD): ");
        stringDate = input.nextLine();

        ArrayList<Integer> lastDate = GeneralUtils.convertDateToArray(stringDate);
        LocalDate first = LocalDate.of(firstDate.get(0), firstDate.get(1), firstDate.get(2));
        LocalDate last = LocalDate.of(lastDate.get(0), lastDate.get(1), lastDate.get(2));
        long size = ChronoUnit.DAYS.between(first, last.plusDays(1));

        for(count = 0; count < size; count++){
            LocalDate current = first.plusDays(count);

            if(current.getDayOfWeek() == first.getDayOfWeek()){
                week++;
            }

            for(Employee employee : company.getEmployees()){
                if(employee.getPaymentData().verifyPayDate(employee, week, current)){
                    payCheck = employee.makePayment(current);
                    payCheckList.add(payCheck);
                }
            }
        }
    }

    public static void launchTimeCard(Scanner input, ArrayList<Employee> employees){
        Employee employee;
        employee = EmployeeUtils.findEmployee(input, employees);
        if(!EmployeeUtils.wasEmployeeFound(employee))
        {
            return;
        }

        Hourly hourlyEmployee = (Hourly) employee;
        System.out.print("Enter the entry hour (HH): ");
        int entryHour = input.nextInt();
    
        System.out.print("Enter the out hour (HH): ");
        int outHour = input.nextInt();
        input.nextLine();
    
        System.out.print("Enter the date (YYYY-MM-DD): ");
        String date = input.nextLine();
    
        ArrayList<Integer> dateData = GeneralUtils.convertDateToArray(date);
        LocalTime employeeEntry = LocalTime.of(entryHour, 00, 00);
        LocalTime employeeOut = LocalTime.of(outHour, 00, 00);
        LocalDate employeeDate = LocalDate.of(dateData.get(0), dateData.get(1), dateData.get(2));
        TimeCard timeCard = new TimeCard(employeeDate, employeeEntry, employeeOut);
        ArrayList<TimeCard> newTimeCards = hourlyEmployee.getTimeCards();
    
        newTimeCards.add(timeCard);
        hourlyEmployee.setTimeCards(newTimeCards);   
    }

    public static void launchSaleResult(Scanner input, ArrayList<Employee> employees){
        Employee employee;
        employee = EmployeeUtils.findEmployee(input, employees);
        if(!EmployeeUtils.wasEmployeeFound(employee))
        {
            return;
        }
    
        Comissioned comissionedEmployee = (Comissioned) employee;
        System.out.print("Enter the value of the sale: ");
        Double value = input.nextDouble();
        input.nextLine();
    
        System.out.print("Enter the date (YYYY-MM-DD): ");
        String date = input.nextLine();
    
        ArrayList<Integer> dateData = GeneralUtils.convertDateToArray(date);
        LocalDate saleDate = LocalDate.of(dateData.get(0), dateData.get(1), dateData.get(2));
        SaleResult newSale = new SaleResult(value, saleDate);
        ArrayList<SaleResult> sales = comissionedEmployee.getSales();
    
        sales.add(newSale);
        comissionedEmployee.setSales(sales);
    }

    public static void launchServiceTax(Scanner input, ArrayList<Employee> employees){
        Employee updateEmployee;
        updateEmployee = EmployeeUtils.findEmployee(input, employees);
        if(updateEmployee == null)
        {
            return;
        }
            
        System.out.print("Enter the value of the service tax: ");
        Double value = input.nextDouble();
        input.nextLine();
    
        System.out.print("Enter the date (YYYY-MM-DD): ");
        String date = input.nextLine();
    
        ArrayList<Integer> dateData = GeneralUtils.convertDateToArray(date);
        LocalDate serviceTaxDate = LocalDate.of(dateData.get(0), dateData.get(1), dateData.get(2));
        ServiceTax serviceTax = new ServiceTax(value, serviceTaxDate);
        ArrayList<ServiceTax> serviceTaxList = updateEmployee.getServiceTax();
            
        serviceTaxList.add(serviceTax);
        System.out.println("\nService tax registered sucessfully!\n");
    }
}