package strategy;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.io.Serializable;

import utils.GeneralUtils;
import models.services.payment.PaymentSchedule;

public class MonthlyStrategy implements ScheduleStrategy, Serializable{
    @Override
    public int getMethodDiv(){
        return 1;
    }

    @Override
    public boolean getDateInSchedule(PaymentSchedule employeeSchedule, int week, LocalDate current){
        if(employeeSchedule.getMonthDay() == null){
            return current.isEqual(GeneralUtils.getLastJobDay(current.with(TemporalAdjusters.lastDayOfMonth())));
        }
        else{
            return(employeeSchedule.getMonthDay() == current.getDayOfMonth());
        }
    }
}
