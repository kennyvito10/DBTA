package sample;
//All the necessary imports
import javafx.beans.property.SimpleStringProperty;

public class Table {
    private SimpleStringProperty tableid, tablenum;


    //Notes setter and getter
    public Table(String tableid, String tablenum) {
        this.tableid = new SimpleStringProperty(tableid);
        this.tablenum = new SimpleStringProperty(tablenum);
    }

    public String getTableid() {
        return tableid.get();
    }

    public String getTablenum() {
        return tablenum.get();
    }


}