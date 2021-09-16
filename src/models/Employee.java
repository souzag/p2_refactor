package models;

import java.time.LocalDate;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.UUID; // immutable universally unique identifier.
import java.util.function.Predicate;
import java.io.Serializable;

import models.services.payment.PaymentData;
import models.services.payment.PayCheck;
import models.services.SaleResult;
import models.services.ServiceTax;
import models.services.TimeCard;

import static java.util.stream.Collectors.toCollection;

public class Employee implements Serializable{
    private UUID id;
    private String name;
    private String address;
    private Double salary;
    private PaymentData paymentData;
    private ArrayList<ServiceTax> serviceTax;
    private Syndicate employeeSyndicate;

    public Employee(){
        // empty employee
    }

    public Employee(String name, String address, Double salary, PaymentData paymentData){
        this.id = UUID.randomUUID();
        this.name = name;
        this.address = address;
        this.salary = salary;
        this.paymentData = paymentData;
        this.employeeSyndicate = null;
        this.serviceTax = new ArrayList<ServiceTax>();
    }

    /*
    *   useful functions:
    *   get - gets the specified item.
    *   set - modifies/replace the specified item.
    */

    public UUID getId(){
        return id;
    }

    public void setId(UUID id){
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getAddress(){
        return address;
    }

    public void setAddress(String address){
        this.address = address;
    }

    public Double getSalary(){
        return salary;
    }

    public void setSalary(Double salary){
        this.salary = salary;
    }

    public Syndicate getEmployeeSyndicate(){
        return employeeSyndicate;
    }

    public void setEmployeeSyndicate(Syndicate employeeSyndicate){
        this.employeeSyndicate = employeeSyndicate;
    }

    public PaymentData getPaymentData(){
        return paymentData;
    }

    public void setPaymentData(PaymentData paymentData){
        this.paymentData = paymentData;
    }

    public ArrayList<ServiceTax> getServiceTax(){
        return serviceTax;
    }

    public void setServiceTax(ArrayList<ServiceTax> serviceTax){
        this.serviceTax = serviceTax;
    }

    @Override
    public String toString(){
        String data = "\n{\n\tUser ID: " + getId();
        data += "\n\tName: " + getName();
        data += "\n\tAddress: " + getAddress();
        data += "\n\tSalary: " + getSalary();
        data += "\n\tService Tax: " + getServiceTax();
        data += "\n\tPayment Data: {" + getPaymentData();

        if(this.employeeSyndicate.getIsAffiliated()){
            data += "\n\tSyndicate: { ";
            data += this.employeeSyndicate.toString();
            data += "\n\t}";
        }

        data += "\n}\n";
        return data;
    }

    // function used in functionality 4 and 5
    public PayCheck makePayment(LocalDate date){
        PayCheck payCheck;
        Double paymentValue = this.getSalary();
        Double taxes = calculateServiceTaxes();
        boolean haveTax = false;

        // if the employee is comissioned, then:
        if(this instanceof Comissioned){
            Comissioned auxEmp = (Comissioned) this;
            ArrayList<SaleResult> sales = auxEmp.getSales();

            if(!sales.isEmpty()){
                for(int i = 0; i < sales.size(); i++){
                    if(sales.get(i).getDate().compareTo(date) <= 0){
                        paymentValue += calculateComission(auxEmp);
                        sales.remove(i);
                    }
                }
            }

            auxEmp.setSales(sales);
        }

        // if the employee is hourly, then:
        if(this instanceof Hourly){
            Hourly auxEmp = (Hourly) this;
            ArrayList<TimeCard> timeCards = auxEmp.getTimeCards();

            //initializing variables
            paymentValue = 0.0;
            boolean found = false;

            if(!timeCards.isEmpty()){
                for(int i = 0; i < timeCards.size(); i++){
                    if(timeCards.get(i).getDate().compareTo(date) <= 0){
                        paymentValue = getPayment(auxEmp, timeCards.get(i));
                        timeCards.remove(i);
                        found = true;
                    }
                }

                if(!found) paymentValue = 0.0;
            }

            auxEmp.setTimeCards(timeCards);
        }

        paymentValue -= taxes;

        payCheck = new PayCheck(this, paymentValue, taxes, haveTax, date);
        this.getPaymentData().getPayChecks().add(payCheck);
        return payCheck;
    }

    private Double getPayment(Hourly employee, TimeCard timeCard){
        double payment = 0.0, hours = 0.0, extraHours = 0.0;

        LocalTime timeEntry = timeCard.getTimeEntry();
        LocalTime timeOut = timeCard.getTimeOut();

        Duration time = Duration.between(timeEntry, timeOut);
        hours = (double) time.getSeconds()/3600;
        System.out.println("Hours:" + hours);

        if(hours <= 0) return payment;

        if(hours > 8.0){
            extraHours = hours - 8.0;
            payment += 8.0 * employee.getSalary();
            payment += extraHours * 1.5 * employee.getSalary();
        }
        else{
            payment = hours * employee.getSalary();
        }

        return payment;
    }

    public Double calculateServiceTaxes(){
        double taxes = 0.0;

        ArrayList<ServiceTax> serviceTaxes;
        ArrayList<PayCheck> payChecks = this.getPaymentData().getPayChecks();

            if(!payChecks.isEmpty()){
                LocalDate lastDate = payChecks.get(payChecks.size() - 1).getDate();
                Predicate<ServiceTax> dateFilter = tax -> tax.getDate().isAfter(lastDate);

                serviceTaxes = this.getServiceTax().stream().filter(dateFilter).collect(toCollection(ArrayList::new));
            }
            else{
                serviceTaxes = this.getServiceTax();
            }

            for(ServiceTax tax : serviceTaxes){
                taxes += tax.getValue();
            }

            taxes += this.getEmployeeSyndicate().getTax();
            return taxes;
        }

        public double calculateComission(Comissioned employee){
            double totalComission = 0.0;

            totalComission += employee.getComission();
            return totalComission;
        }
}