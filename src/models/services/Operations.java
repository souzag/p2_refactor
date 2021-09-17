package models.services;

import java.io.Serializable;
import java.time.LocalDate;

public abstract class Operations implements Serializable{
    private Double value;
    private LocalDate date;

    public Operations(Double value, LocalDate date){
        this.value = value;
        this.date = date;
    }

    public Double getValue(){
        return value;
    }

    public LocalDate getDate(){
        return date;
    }
}