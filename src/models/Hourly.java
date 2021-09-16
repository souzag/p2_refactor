package models;

import java.util.ArrayList;

import models.services.TimeCard;
import models.services.payment.PaymentData;

public class Hourly extends Employee{
    private ArrayList<TimeCard> timeCards;

    public Hourly(){
        // empty Hourly
    }

    public Hourly(String name, String address, Double salary, PaymentData paymentData){
        super(name, address, salary, paymentData);
        this.timeCards = new ArrayList<TimeCard>();
    }

    /*
     *   useful functions:
     *   get - gets the specified item.
     *   set - modifies/replace the specified item.
     */

    public ArrayList<TimeCard> getTimeCards(){
        return timeCards;
    }

    public void setTimeCards(ArrayList<TimeCard> timeCards){
        this.timeCards = timeCards;
    }

    @Override
    public String toString(){
        String data = "\n\n{\n\tUser ID: " + getId();
        data += "\n\tName: " + getName();
        data += "\n\tAddress: " + getAddress();
        data += "\n\tSalary: " + getSalary();
        data += "\n\tTime Cards: " + getTimeCards();
        data += "\n\tPayment Data: {" + getPaymentData();

        if(this.getEmployeeSyndicate().getIsAffiliated()){      // If true, it means the employee belongs to
            data += "\n\tSyndicate: {";                           // a Syndicate. Hence, we add this to the data.
            data += this.getEmployeeSyndicate().toString();
            data += "\n\t}";
        }

        data += "\n}";
        return data;
    }
}