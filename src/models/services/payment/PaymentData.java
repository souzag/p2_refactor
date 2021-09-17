package models.services.payment;

import java.io.Serializable;
import java.util.ArrayList;
import java.time.LocalDate;

import models.Employee;

public class PaymentData implements Serializable{
    private String bank;
    private String agency;
    private String account;
    private String paymentMethod;
    private PaymentSchedule paymentSchedule;
    private ArrayList<PayCheck> payChecks;

    public PaymentData(String bank, String agency, String account, String paymentMethod, PaymentSchedule paymentSchedule){
        this.bank = bank;
        this.agency = agency;
        this.account = account;
        this.paymentMethod = paymentMethod;
        this.paymentSchedule = paymentSchedule;
        this.payChecks = new ArrayList<PayCheck>();
    }

    public String getAccount(){
        return account;
    }

    public String getAgency(){
        return agency;
    }

    public String getBank(){
        return bank;
    }

    public String getPaymentMethod(){
        return paymentMethod;
    }

    public PaymentSchedule getPaymentSchedule(){
        return paymentSchedule;
    }

    public ArrayList<PayCheck> getPayChecks(){
        return payChecks;
    }

    @Override
    public String toString(){
        String data = "\n\t\tBank: " + getBank();
        data += "\n\t\tAgency: " + getAgency();
        data += "\n\t\tAccount: " + getAccount();
        data +=  "\n\t\tPayment Method: " + getPaymentMethod();
        data += "\n\t\tSchedule: " + getPaymentSchedule();
        data += "\n\t}";

        return data;
    }

    public boolean verifyPayDate(Employee employee, int week, LocalDate current){
        boolean dateInSchedule = false;
        PaymentSchedule employeeSchedule = employee.getPaymentData().getPaymentSchedule();
        dateInSchedule = employee.getPaymentData().getPaymentSchedule().getStrategy().getDateInSchedule(employeeSchedule, week, current);

        return dateInSchedule;
    }
}