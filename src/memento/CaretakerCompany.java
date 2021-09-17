package memento;

import java.util.Stack;

import models.Company;

public class CaretakerCompany{
    private Company company;
    private Stack<MementoCompany> history;
    private Stack<MementoCompany> redoHistory;

    public CaretakerCompany(Company company){
        this.company = company;
        this.history = new Stack<MementoCompany>();
        this.redoHistory = new Stack<MementoCompany>();
    }

    public void save(){
        this.history.push(this.company.saveState());
    }

    public Company undo(){
        if (this.history.isEmpty()){
            System.out.println("Error: History's empty.");
        }
        else{
            MementoCompany memento = history.pop();
            this.redoHistory.push(this.company.saveState());
            this.company = memento.restoreState();
        }

        return this.company;
    }

    public Company redo(){
        if (this.redoHistory.isEmpty()){
            System.out.println("Error: Redo history's empty.");
        }
        else{
            MementoCompany memento = this.redoHistory.pop();
            this.history.push(this.company.saveState());
            this.company = memento.restoreState();
        }

        return this.company;
    }
}