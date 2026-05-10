package org.liamb.domain;

import lombok.ToString;
import org.liamb.interfaces.Reportable;

import java.util.List;

@ToString(callSuper = true)
public class Admin extends User implements Reportable {

    public Admin(String name) {
        super(name);
    }

    public Admin(String id, String name, List<Item> borrowedItems) {
        super(id, name, borrowedItems);
    }

    @Override
    public void generateReport() {
        //TODO write method, unit test and exception handling
    }

    public void backupData() {
        //TODO write method, unit test and exception handling
    }
}
