package sample;
//All the necessary imports
import javafx.beans.property.SimpleStringProperty;

public class Orders {
    private final SimpleStringProperty tableid, tablenum, itemid, quan;


    public Orders(String tableid, String tablenum, String itemid, String quan) {
        this.tableid = new SimpleStringProperty(tableid);
        this.tablenum = new SimpleStringProperty(tablenum);
        this.itemid = new SimpleStringProperty(itemid);
        this.quan = new SimpleStringProperty(quan);
    }

    public String getItemid() {return itemid.get();}
    public String getQuantity() {return quan.get();}

    public String getTableid() {
        return tableid.get();
    }

    public String getTablenum() {
        return tablenum.get();
    }


}