package models;

import java.util.ArrayList;
import java.time.LocalDate;

import models.services.SaleResult;
import models.services.payment.PayCheck;
import models.services.payment.PaymentData;

public class Comissioned extends Employee{
    private Double comission;
    private ArrayList<SaleResult> sales;

    public Comissioned(){}

    public Comissioned(String name, String address, Double salary, Double comission, PaymentData paymentData){
        super(name, address, salary, paymentData);
        this.comission = comission;
        this.sales = new ArrayList<SaleResult>();
    }

    public Double getComission(){
        return comission;
    }

    public void setComission(Double comission){
        this.comission = comission;
    }

    public ArrayList<SaleResult> getSales(){
        return sales;
    }

    public void setSales(ArrayList<SaleResult> sales){
        this.sales = sales;
    }

    @Override
    public PayCheck makePayment(LocalDate date){
        PayCheck payCheck;
        Double paymentValue = this.getSalary();
        Double taxes = calculateServiceTaxes();
        boolean haveTax = false;

        Comissioned auxComissioned = (Comissioned) this;
        ArrayList<SaleResult> sales = auxComissioned.getSales();
        if(!sales.isEmpty()){
            for(int i = 0; i < sales.size(); i++){
                if(sales.get(i).getDate().compareTo(date) <= 0){
                    paymentValue += calculateComission(auxComissioned);
                    sales.remove(i);
                }
            }
        }

        auxComissioned.setSales(sales);
        paymentValue -= taxes;

        payCheck = new PayCheck(this, paymentValue, taxes, haveTax, date);
        this.getPaymentData().getPayChecks().add(payCheck);

        return payCheck;
    }

    public double calculateComission(Comissioned employee){
        double totalComission = 0.0;
        totalComission += this.getComission();

        return totalComission;
    }

    @Override
    public String toString(){
        String data = "\n\n{\n\tUser ID: " + getId();
        data += "\n\tName: " + getName();
        data += "\n\tAddress: " + getAddress();
        data += "\n\tSalary: " + getSalary();
        data += "\n\tComission: " + getComission();
        data += "\n\tSales: " + getSales();
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