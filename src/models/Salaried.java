package models;

import java.time.LocalDate;

import models.services.payment.PayCheck;
import models.services.payment.PaymentData;

public class Salaried extends Employee{
    public Salaried(){}

    public Salaried(String name, String address, Double salary, PaymentData paymentData){
        super(name, address, salary, paymentData);
    }

    @Override
    public PayCheck makePayment(LocalDate date){
        PayCheck payCheck;
        Double paymentValue = this.getSalary();
        Double taxes = calculateServiceTaxes(); 
        boolean haveTax = false;

        paymentValue -= taxes;
        payCheck = new PayCheck(this, paymentValue, taxes, haveTax, date);
        this.getPaymentData().getPayChecks().add(payCheck);
        
        return payCheck;
    }

    @Override
    public String toString(){
        String data = "\n\n{\n\tUser ID: " + getId();
        data += "\n\tName: " + getName();
        data += "\n\tAddress: " + getAddress();
        data += "\n\tSalary: " + getSalary();
        data += "\n\tService Tax: " + getServiceTax();
        data += "\n\tPayment Data: {" + getPaymentData();
        data += printSyndicate();
        data += "\n}\n";
        return data;
    }
}