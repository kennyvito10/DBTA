package sample;

//All the necessary imports
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;


import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;



public class Bill_Controller extends DBConnect implements Initializable {
    @FXML private TableView<Bill> table;
    @FXML private TableColumn<Bill, String> tableID;
    @FXML private TableColumn<Bill, String> tableNum;
    @FXML private TableColumn<Bill, String> date;

    @FXML private TableView<Bill> tableFood;
    @FXML private TableColumn<Bill, String> quantity;
    @FXML private TableColumn<Bill, String> itemname;
    @FXML private TableColumn<Bill, String> price;
    //declaring fxml type
    @FXML
    TextField titleText;

    @FXML
    Text location;
    @FXML
    Text datee;
    @FXML
    Text tableid;
    @FXML
    Text cashier;
    @FXML
    Text transid;
    @FXML
    Text subtotal;
    @FXML
    Text tax;
    @FXML
    Text total;

    public ObservableList<Bill> list = FXCollections.observableArrayList();
    public ObservableList<Bill> foodlist = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadData();
    }

    public void loadData(){
        table.getItems().clear();
        try {
            Connection con = DBConnect.getConnection();
            String sql = String.format("select * from Transactions");
            //String sql = String.format("select * from transactions");
            ResultSet rs =  con.createStatement().executeQuery(sql);


            while (rs.next()) {
                list.add(new Bill(rs.getString("TransID") , rs.getString("TableID"), rs.getString("TransDate")));
            }


        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }


        tableID.setCellValueFactory(new PropertyValueFactory<Bill, String>("tableid"));
        tableNum.setCellValueFactory(new PropertyValueFactory<Bill, String>("tablenum"));
        date.setCellValueFactory(new PropertyValueFactory<Bill, String>("date"));




        table.setItems(list);


    }
    //adding notes function
    public void loadBill(){
        String item = table.getSelectionModel().getSelectedItem().getTableid();
        System.out.println(item);
        try {
            Connection con = DBConnect.getConnection();
            String sql = String.format("select * from Transactions where TransID = '%s' " , item);
          //  String sql = String.format("select * from transactions where TransID = '%s' " , item);
            ResultSet rs =  con.createStatement().executeQuery(sql);

            while (rs.next()) {

                titleText.setText(rs.getString("TransID"));

            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }



    }

    public void loadReceipt(){
        String item = titleText.getText();
        System.out.println(item);
        try {
            Connection con = DBConnect.getConnection();
            String sql = String.format("SELECT * FROM Transactions INNER JOIN Branches USING (BranchID) INNER JOIN Staffs USING (StaffID) WHERE TransID = '%s'" , item);
           // String sql = String.format("SELECT * FROM transactions INNER JOIN branches USING (BranchID) INNER JOIN staffs USING (StaffID) WHERE TransID = '%s'" , item);
            ResultSet rs =  con.createStatement().executeQuery(sql);


            while (rs.next()) {

                transid.setText(rs.getString("TransID"));
                datee.setText(rs.getString("TransDate"));
                tableid.setText(rs.getString("TableID"));
                cashier.setText(rs.getString("StaffName"));
                location.setText(rs.getString("Location"));
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        tableFood.getItems().clear();
        try {
            Connection con2 = DBConnect.getConnection();
            String sql2 = String.format("SELECT Quantity,ItemID,TotalPrice,ItemName from Orders INNER JOIN Items using (ItemID) WHERE TransID = '%s'", item);
            //String sql2 = String.format("SELECT Quantity,ItemID,TotalPrice,ItemName from orders INNER JOIN items using (ItemID) WHERE TransID = '%s'", item);
            ResultSet rs2 =  con2.createStatement().executeQuery(sql2);



            while (rs2.next()) {
                foodlist.add(new Bill(rs2.getString("Quantity") , rs2.getString("ItemName"), rs2.getString("TotalPrice")));
            }


        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }


        quantity.setCellValueFactory(new PropertyValueFactory<Bill, String>("tableid"));
        itemname.setCellValueFactory(new PropertyValueFactory<Bill, String>("tablenum"));
        price.setCellValueFactory(new PropertyValueFactory<Bill, String>("date"));
        tableFood.setItems(foodlist);

        try {
            Connection con2 = DBConnect.getConnection();
            String sql2 = String.format("SELECT SUM(TotalPrice), SUM(TotalPrice)*0.1, SUM(TotalPrice)+SUM(TotalPrice)*0.1 FROM Orders WHERE TransID = '%s'", item);
            //String sql2 = String.format("SELECT SUM(TotalPrice), SUM(TotalPrice)*0.1, SUM(TotalPrice)+SUM(TotalPrice)*0.1 FROM orders WHERE TransID = '%s'", item);
            ResultSet rs2 =  con2.createStatement().executeQuery(sql2);



            while (rs2.next()) {
                subtotal.setText(rs2.getString("SUM(TotalPrice)"));
                tax.setText(rs2.getString("SUM(TotalPrice)*0.1"));
                total.setText(rs2.getString("SUM(TotalPrice)+SUM(TotalPrice)*0.1"));

            }


        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }


    }

}
