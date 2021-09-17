package models;

import java.util.ArrayList;
import java.io.Serializable;

import memento.MementoCompany;
import models.services.payment.PaymentList;
import models.services.payment.PaymentSchedule;

public class Company implements Serializable{
    private ArrayList<Employee> employees;
    private ArrayList<PaymentList> paymentLists;
    private ArrayList<PaymentSchedule> paymentSchedules;

    public Company(){
        this.employees = new ArrayList<Employee>();
        this.paymentLists = new ArrayList<PaymentList>();
        this.paymentSchedules = new ArrayList<PaymentSchedule>();
    }

    public ArrayList<Employee> getEmployees(){
        return employees;
    }

    public void setEmployees(ArrayList<Employee> employees){
        this.employees = employees;
    }

    public ArrayList<PaymentList> getPaymentLists(){
        return paymentLists;
    }

    public void setPaymentLists(ArrayList<PaymentList> paymentLists){
        this.paymentLists = paymentLists;
    }

    public ArrayList<PaymentSchedule> getPaymentSchedules(){
        return paymentSchedules;
    }

    public void setPaymentSchedules(ArrayList<PaymentSchedule> paymentSchedules){
        this.paymentSchedules = paymentSchedules;
    }

    public MementoCompany saveState(){
        return new MementoCompany(this);
    }
}