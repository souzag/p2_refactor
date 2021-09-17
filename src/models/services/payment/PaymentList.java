package models.services.payment;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

public class PaymentList implements Serializable{
    private ArrayList<PayCheck> payChecks;
    private LocalDate date;

    public PaymentList(ArrayList<PayCheck> payChecks, LocalDate date){
        this.payChecks = payChecks;
        this.date = date;
    }

    public ArrayList<PayCheck> getPayChecks(){
        return payChecks;
    }

    public LocalDate getDate(){
        return date;
    }

    @Override
    public String toString() {
        return "\nPayment List:\nDate: "+ this.getDate() +", "+listPayChecks();
    }

    private String listPayChecks(){
        String str = "";
        for(PayCheck paycheck : this.getPayChecks()){
            str += paycheck.toString();
        }

        return str;
    }
}