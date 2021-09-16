package models;

import java.util.UUID;
import java.io.Serializable; //  undo;redo

public class Syndicate implements Serializable{
    private UUID id;
    private UUID employeeId;
    private boolean isAffiliated;
    private Double tax;

    public Syndicate(){
        //empty Syndicate
    }

    public Syndicate(UUID employeeId, boolean isAffiliated, Double tax){
        this.id = UUID.randomUUID();
        this.employeeId = employeeId;
        this.isAffiliated = isAffiliated;
        this.tax = tax;
    }

    public UUID getId(){
        return id;
    }

    public void setId(UUID id){
        this.id = id;
    }

    public Double getTax(){
        return tax;
    }

    public void setTax(Double tax){
        this.tax = tax;
    }

    public UUID getEmployeeId(){
        return employeeId;
    }

    public void setEmployeeId(UUID employeeId){
        this.employeeId = employeeId;
    }

    public boolean getIsAffiliated(){
        return this.isAffiliated;
    }

    public void setAffiliated(boolean isAffiliated){
        this.isAffiliated = isAffiliated;
    }

    @Override
    public String toString(){
        String data = "\n\t\tSyndicate Employee ID: " + getId();
        data += "\n\t\tEmployee ID: " + getEmployeeId();
        data += "\n\t\tAffiliated: " + getIsAffiliated();
        data += "\n\t\tTax: " + getTax();

        return data;
    }
}