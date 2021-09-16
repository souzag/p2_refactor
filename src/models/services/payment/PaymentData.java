package models.services.payment;

import java.io.Serializable;
import java.util.ArrayList;

public class PaymentData implements Serializable{
    private String bank;
    private String agency;
    private String account;
    private String paymentMethod;
    private PaymentSchedule paymentSchedule;
    private ArrayList<PayCheck> payChecks;

    public PaymentData(){

    }

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

    public void setAccount(String account){
        this.account = account;
    }

    public String getAgency(){
        return agency;
    }

    public void setAgency(String agency){
        this.agency = agency;
    }

    public String getBank(){
        return bank;
    }

    public void setBank(String bank){
        this.bank = bank;
    }

    public String getPaymentMethod(){
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod){
        this.paymentMethod = paymentMethod;
    }

    public PaymentSchedule getPaymentSchedule(){
        return paymentSchedule;
    }

    //tirar duvida com o rodrigo sobre isso
    public void setPaymentSchedule(PaymentSchedule paymentSchedule){
        this.paymentSchedule = paymentSchedule;
    }

    public ArrayList<PayCheck> getPayChecks(){
        return payChecks;
    }

    public void setPayChecks(ArrayList<PayCheck> payChecks){
        this.payChecks = payChecks;
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
}