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
}