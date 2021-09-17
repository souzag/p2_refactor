package utils;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Scanner;

import models.*;
import models.services.payment.PaymentData;
import models.services.payment.PaymentSchedule;
import strategy.BiWeeklyStrategy;
import strategy.MonthlyStrategy;
import strategy.WeeklyStrategy;

public class EmployeeUtils{
    public static void listHourly(ArrayList<Employee> employees){
        for(Employee employee : employees){
            if(employee instanceof Hourly){
                System.out.println(employee.toString());
            }
        }
    }

    public static void listComissioned(ArrayList<Employee> employees){
        for(Employee employee : employees){
            if(employee instanceof Comissioned){
                System.out.println(employee.toString());
            }
        }
    }

    public static boolean wasEmployeeFound(Employee employee){
        if(employee == null){
            System.out.println("Employee not found.");
            return false;
        }

        return true;
    }

    public static boolean warningEmptyEmployeesList(ArrayList<Employee> employees){
        if(employees.size() == 0){
            System.out.println("\n\nThere are no registered employees.\n\n");
            return true;
        }
        else{
            return false;
        }
    }

    public static void removeSpecificEmployee(String id, ArrayList<Employee> employees){
        for(Employee employee : employees){
            if(employee.getId().toString().equals(id)){
                employees.remove(employee);
                break;
            }
        }
    }

    public static Employee findEmployee(Scanner input, ArrayList<Employee> employees){
        if(warningEmptyEmployeesList(employees)) return null;

        System.out.print("Enter the employee ID: ");
        String id = input.nextLine();
        Employee wantedEmployee = null;

        for(Employee employee : employees){
            if(employee.getId().toString().equals(id)){
                wantedEmployee = employee;
                break;
            }
        }

        if(!wasEmployeeFound(wantedEmployee)) return null;
        return wantedEmployee;
    }

    public static ValueHolder readEmployeeBasicData(Scanner input){
        String name, address, bank = "", agency = "", account = "", paymentMethod = "";
        PaymentSchedule schedule = new PaymentSchedule();

        Double salary, comission = 0.0, tax = 0.0;
        int empOption, syndicateOption, paymentMethodOption;

        PaymentData paymentData = null;
        Syndicate syndicate = null;
        Employee employee = null;

        System.out.print("Name of the employee: ");
        name = input.nextLine();

        System.out.print("Address: ");
        address = input.nextLine();

        System.out.print("Bank: ");
        bank = input.nextLine();

        System.out.print("Agency: ");
        agency = input.nextLine();

        System.out.print("Account: ");
        account = input.nextLine();

        System.out.println("Payment Method: ");
        System.out.println("1 - Check by the post office.\n2 - Bank Deposit.\n3 - Cash");
        System.out.print("User: ");

        paymentMethodOption = input.nextInt();

        switch(paymentMethodOption){
            case 1:
                paymentMethod = "Check by the post office.";
                break;
            case 2:
                paymentMethod = "Bank Deposit.";
                break;
            case 3:
                paymentMethod = "Cash.";
                break;
            default:
                System.out.println("\n\n Error: Invalid input. Please, try again. \n\n");
                break;
        }

        schedule = createPaymentSchedule(input);
        paymentData = new PaymentData(bank, agency, account, paymentMethod, schedule);

        System.out.println("Enter the type of the employee, which:");
        System.out.println("1 - Hourly\n2 - Comissioned\n3 - Salaried");
        System.out.print("User: ");

        empOption = input.nextInt();

        switch(empOption){
            case 1:
                System.out.print("Enter the hourly salary: ");
                salary = input.nextDouble();
                input.nextLine();

                employee = new Hourly(name, address, salary, paymentData);
                break;
            case 2:
                System.out.print("Enter the comissioned salary: ");
                salary = input.nextDouble();
                input.nextLine();

                System.out.print("Enter the comission: ");
                comission = input.nextDouble();
                input.nextLine();

                employee = new Comissioned(name, address, salary, comission, paymentData);
                break;
            case 3:
                System.out.print("Enter the salaried salary: ");
                salary = input.nextDouble();
                input.nextLine();

                employee = new Salaried(name, address, salary, paymentData);
                break;
            default:
                System.out.println("\n\n Error: Invalid option. Please, try again. \n\n");
                return new ValueHolder(employee, comission);
        }

        System.out.println("Is the employee affiliated to the syndicate?");
        System.out.println("1 - Yes\n2 - No\n");
        System.out.print("User: ");
        syndicateOption = input.nextInt();

        switch(syndicateOption){
            case 1:
                System.out.print("Enter the syndicate tax: ");
                tax = input.nextDouble();
                input.nextLine();

                syndicate = new Syndicate(employee.getId(), true, tax);
                employee.setEmployeeSyndicate(syndicate);
                break;
            case 2:
                syndicate = new Syndicate(employee.getId(), false, tax);
                break;
            default:
                System.out.println("\n\n Error: Invalid option. Please, try again. \n\n");
                syndicate = new Syndicate(employee.getId(), false, tax);
                break;
        }

        employee.setEmployeeSyndicate(syndicate);
        return new ValueHolder(employee, comission);
    }

    public static PaymentSchedule createPaymentSchedule(Scanner input){
        System.out.println("Enter a type of schedule, which:");
        System.out.println("1 - Monthly\n2 - Weekly\n3 - Every two weeks");
        System.out.print("User: ");

        int option = input.nextInt(), week;
        DayOfWeek weekDay;

        switch(option){
            case 1:
                System.out.println("Enter the day of the month (1 to 28) to put in the schedule: ");
                int day = input.nextInt();

                if(day < 0 || day > 28){
                    return new PaymentSchedule(null, null, "Monthly", new MonthlyStrategy());
                }
                else{
                    return new PaymentSchedule(day, null, "Monthly", new MonthlyStrategy());
                }
            case 2:
                System.out.println("Enter the day of the week, which:");
                System.out.println("1 - Monday\n2 - Tuesday\n3 - Wednesday\n4 - Thursday\n5 - Friday");
                System.out.print("User: ");
                week = input.nextInt();
                weekDay = DayOfWeek.of(week);

                if(week < 0 || week > 6){
                    return new PaymentSchedule(null, DayOfWeek.FRIDAY, "Weekly", new WeeklyStrategy());
                }
                else{
                    return new PaymentSchedule(null, DayOfWeek.FRIDAY, "Weekly", new WeeklyStrategy());
                }
            case 3:
                System.out.println("Enter the day of the week, which:");
                System.out.println("1 - Monday\n2 - Tuesday\n3 - Wednesday\n4 - Thursday\n5 - Friday");
                System.out.print("User: ");
                week = input.nextInt();
                weekDay = DayOfWeek.of(week);

                if(week < 0 || week > 6){
                    return new PaymentSchedule(null, DayOfWeek.FRIDAY, "Every two weeks", new BiWeeklyStrategy());
                }
                else{
                    return new PaymentSchedule(null, weekDay, "Every two weeks", new BiWeeklyStrategy());
                }
            default:
                System.out.println("\n\n Error: Invalid option. A month schedule was created by default. \n\n");
                return new PaymentSchedule(null, null, "Monthly", new MonthlyStrategy());
        }
    }
}

