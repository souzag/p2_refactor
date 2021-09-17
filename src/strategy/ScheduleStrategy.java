package strategy;

import java.time.LocalDate;
import models.services.payment.PaymentSchedule;

public interface ScheduleStrategy{
    public int getMethodDiv();

    public boolean getDateInSchedule(PaymentSchedule employeeSchedule, int week, LocalDate current);
}