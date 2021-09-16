package models;

import java.util.ArrayList;
import java.time.LocalDate;
import java.time.Duration;
import java.time.LocalTime;

import models.services.TimeCard;
import models.services.payment.PayCheck;
import models.services.payment.PaymentData;

public class Hourly extends Employee{
    private ArrayList<TimeCard> timeCards;

    public Hourly(){}

    public Hourly(String name, String address, Double salary, PaymentData paymentData){
        super(name, address, salary, paymentData);
        this.timeCards = new ArrayList<TimeCard>();
    }

    public ArrayList<TimeCard> getTimeCards(){
        return timeCards;
    }

    public void setTimeCards(ArrayList<TimeCard> timeCards){
        this.timeCards = timeCards;
    }

    public PayCheck makePayment(LocalDate date){
        PayCheck payCheck;
        Double paymentValue = this.getSalary();
        Double taxes = calculateServiceTaxes(); 
        boolean haveTax = false;

        ArrayList<TimeCard> timeCards = this.getTimeCards();
        paymentValue = 0.0;
        boolean found = false;

        if(!timeCards.isEmpty()){
            for(int i = 0; i < timeCards.size(); i++){
                if(timeCards.get(i).getDate().compareTo(date) <= 0){
                    paymentValue = this.getPayment(this, timeCards.get(i));
                    timeCards.remove(i);
                    found = true;
                }
            }
            
            if(!found) paymentValue = 0.0;
        }

        this.setTimeCards(timeCards);
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

        if(hours <= 0)
        {
            return payment;
        } 

        if(hours > 8.0){
            extraHours = hours - 8.0;
            payment += 8.0 * employee.getSalary();

            payment += extraHours * 1.5 * employee.getSalary();
        }
        else
        {
            payment = hours * employee.getSalary();
        }

        return payment;
    }

    @Override
    public String toString(){
        String data = "\n\n{\n\tUser ID: " + getId();
        data += "\n\tName: " + getName();
        data += "\n\tAddress: " + getAddress();
        data += "\n\tSalary: " + getSalary();
        data += "\n\tTime Cards: " + getTimeCards();
        data += "\n\tPayment Data: {" + getPaymentData();

        if(this.getEmployeeSyndicate().getIsAffiliated()){
            data += "\n\tSyndicate: {";
            data += this.getEmployeeSyndicate().toString();
            data += "\n\t}";
        }

        data += "\n}";
        return data;
    }
}