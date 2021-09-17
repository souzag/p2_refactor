package strategy;

import java.io.Serializable;
import java.time.LocalDate;

import models.services.payment.PaymentSchedule;

public class WeeklyStrategy implements ScheduleStrategy, Serializable{
    @Override
    public int getMethodDiv(){
        return 4;
    }

    @Override
    public boolean getDateInSchedule(PaymentSchedule employeeSchedule, int week, LocalDate current){
        return (employeeSchedule.getWeekDay() == current.getDayOfWeek());
    }
}