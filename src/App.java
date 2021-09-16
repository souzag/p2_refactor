import models.Company;
import utils.Menu;

public class App{
    public static void main(String[] args){
        Company company = new Company();

        System.out.println("Payroll System Initialized.\n\nSelect your option:\n");

        Menu.menu(company);
    }
}