package models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;
import java.util.function.Predicate;
import java.io.Serializable;

import models.services.payment.PaymentData;
import models.services.payment.PayCheck;
import models.services.ServiceTax;

import static java.util.stream.Collectors.toCollection;

public abstract class Employee implements Serializable{
    private UUID id;
    private String name;
    private String address;
    private Double salary;
    private PaymentData paymentData;
    private ArrayList<ServiceTax> serviceTax;
    private Syndicate employeeSyndicate;

    public Employee(String name, String address, Double salary, PaymentData paymentData){
        this.id = UUID.randomUUID();
        this.name = name;
        this.address = address;
        this.salary = salary;
        this.paymentData = paymentData;
        this.employeeSyndicate = null;
        this.serviceTax = new ArrayList<ServiceTax>();
    }

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
    public abstract String toString();

    public abstract PayCheck makePayment(LocalDate date);

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

    protected String printSyndicate(){
        String data = "";
        if(this.getEmployeeSyndicate() != null && this.getEmployeeSyndicate().getIsAffiliated() == true){
            data += "\n\tSyndicate: {";
            data += this.getEmployeeSyndicate().toString();
            data += "\n\t}";
        }

        return data;
    }
}