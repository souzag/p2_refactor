package models.services.payment;

import java.io.Serializable;
import java.time.DayOfWeek;

public class PaymentSchedule implements Serializable{
    private Integer monthDay;
    private DayOfWeek weekDay;
    private String schedule;

    public PaymentSchedule(){

    }

    public PaymentSchedule(Integer monthDay, DayOfWeek weekDay, String schedule){
        this.monthDay = monthDay;
        this.weekDay = weekDay;
        this.schedule = schedule;
    }

    public Integer getMonthDay(){
        return monthDay;
    }

    public void setMonthDay(Integer monthDay){
        this.monthDay = monthDay;
    }

    public String getSchedule(){
        return schedule;
    }

    public void setSchedule(String schedule){
        this.schedule = schedule;
    }

    public DayOfWeek getWeekDay(){
        return weekDay;
    }

    public void setWeekDay(DayOfWeek weekDay){
        this.weekDay = weekDay;
    }

    @Override
    public String toString(){
        return " Payment Schedule {" + "Day of the Month: " +getMonthDay() + ", Week Day: " + getWeekDay() +
                ", Type of Schedule: '" + getSchedule() + '\'' + '}';
    }
}