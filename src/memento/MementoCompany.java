package memento;

import models.Company;
import utils.GeneralUtils;

public class MementoCompany{
    private String state;

    public MementoCompany(Company company){
        this.state = GeneralUtils.saveState(company);
    }

    public Company restoreState(){
        return GeneralUtils.restoreState(this.state);
    }
}
