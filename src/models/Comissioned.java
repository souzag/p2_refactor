package models;

import models.services.SaleResult;
import java.util.ArrayList;
import models.services.payment.PaymentData;

public class Comissioned extends Employee{
    private Double comission;
    private ArrayList<SaleResult> sales;

    public Comissioned(){
        // empty Comissioned
    }

    public Comissioned(String name, String address, Double salary, Double comission, PaymentData paymentData){
        super(name, address, salary, paymentData);
        this.comission = comission;
        this.sales = new ArrayList<SaleResult>();
    }

    /*
     *   useful functions:
     *   get - gets the specified item.
     *   set - modifies/replace the specified item.
     */

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
    public String toString(){
        String data = "\n\n{\n\tUser ID: " + getId();
        data += "\n\tName: " + getName();
        data += "\n\tAddress: " + getAddress();
        data += "\n\tSalary: " + getSalary();
        data += "\n\tComission: " + getComission();
        data += "\n\tSales: " + getSales();
        data += "\n\tPayment Data: {" + getPaymentData();

        if(this.getEmployeeSyndicate().getIsAffiliated()){       // If true, it means the employee belongs to
            data += "\n\tSyndicate: {";                          // a Syndicate. Hence, we add this to the data.
            data += this.getEmployeeSyndicate().toString();
            data += "\n\t}";
        }

        data += "\n}";
        return data;
    }
}