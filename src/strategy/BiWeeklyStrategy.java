package strategy;

import java.io.Serializable;
import java.time.LocalDate;

import models.services.payment.PaymentSchedule;

public class BiWeeklyStrategy implements ScheduleStrategy, Serializable{
    @Override
    public int getMethodDiv(){
        return 2;
    }

    @Override
    public boolean getDateInSchedule(PaymentSchedule employeeSchedule, int week, LocalDate current){
        return (employeeSchedule.getWeekDay() == current.getDayOfWeek() && week % 2 == 0);
    }
}
