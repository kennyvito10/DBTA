package sample;
//All the necessary imports
import javafx.beans.property.SimpleStringProperty;

public class Bill {
    private final SimpleStringProperty tableid, tablenum, date;


    public Bill(String tableid, String tablenum, String date) {
        this.tableid = new SimpleStringProperty(tableid);
        this.tablenum = new SimpleStringProperty(tablenum);
        this.date = new SimpleStringProperty(date);

    }

    public String getDate() {return date.get();}

    public String getTableid() {
        return tableid.get();
    }

    public String getTablenum() {
        return tablenum.get();
    }


}