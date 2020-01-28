package sample;

//All the necessary imports
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class Controller extends DBConnect implements Initializable {

    //declaring all fxml and stage
    public static Stage stage;


    @FXML
    TextField quantity;
    @FXML
    TextField staffid;
    @FXML
    TextField branchid;
    @FXML
    TextField transid;

    @FXML
    Text idText;
    @FXML
    Text pText;
    @FXML
    Text tableidText;

    @FXML
    Text imgText;


    @FXML
    TextField imageText;


    @FXML private TableView<Table> table;
    @FXML private TableColumn<Table, String> itemname;
    @FXML private TableColumn<Table, Float> price;

    @FXML private TableView<Table> tableT;
    @FXML private TableColumn<Table, String> tableID;
    @FXML private TableColumn<Table, Integer> tableNum;

    @FXML private TableView<Orders> tableO;
    @FXML private TableColumn<Orders, String> itemID;
    @FXML private TableColumn<Orders, String> quan;
    @FXML private TableColumn<Orders, String> tableID2;
    @FXML private TableColumn<Orders, String> transID;


    public ObservableList<Table> list = FXCollections.observableArrayList();
    public ObservableList<Table> tableList = FXCollections.observableArrayList();
    public ObservableList<Orders> orderList = FXCollections.observableArrayList();

    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyy/MM/dd");
    LocalDate date = LocalDate.now();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadData();
    }

    // view image function to view image




    //function to open a new fxml window
    public void newWindow() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("bill.fxml"));
        stage = new Stage();
        stage.setScene(new Scene(root, 600, 500));
        stage.setTitle("Bill");

        stage.show();

    }
    //function to open a new fxml window of about me
    public void newsboutWindow() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("about.fxml"));
        stage = new Stage();
        stage.setScene(new Scene(root, 400, 300));
        stage.setTitle("About Us");
        stage.setResizable(false);
        stage.show();

    }

    //load data to the table view
    public void loadData(){
        table.getItems().clear();
        tableT.getItems().clear();
        tableO.getItems().clear();
        try {
            Connection con = DBConnect.getConnection();
            String sql = String.format("select * from Items");
            //String sql = String.format("select * from items");
            ResultSet rs =  con.createStatement().executeQuery(sql);


            Connection con2 = DBConnect.getConnection();
            String sql2 = String.format("select * from Tables");
            //String sql2 = String.format("select * from tables");
            ResultSet rs2 =  con2.createStatement().executeQuery(sql2);

            Connection con3 = DBConnect.getConnection();
            String sql3 = String.format("select * from Orders");
            //String sql3 = String.format("select * from orders");
            ResultSet rs3 =  con3.createStatement().executeQuery(sql3);


            while (rs.next()) {
                list.add(new Table(rs.getString("ItemName") , rs.getString("Price")));
            }
            while (rs2.next()) {
                tableList.add(new Table(rs2.getString("TableID") , rs2.getString("TableNum")));
            }
            while (rs3.next()) {
                orderList.add(new Orders(rs3.getString("TableID"),rs3.getString("TransID"), rs3.getString("ItemID"),rs3.getString("Quantity")));
            }



        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        itemname.setCellValueFactory(new PropertyValueFactory<Table, String>("tableid"));
        price.setCellValueFactory(new PropertyValueFactory<Table, Float>("tablenum"));
        tableID.setCellValueFactory(new PropertyValueFactory<Table, String>("tableid"));
        tableNum.setCellValueFactory(new PropertyValueFactory<Table, Integer>("tablenum"));

        itemID.setCellValueFactory(new PropertyValueFactory<Orders, String>("itemid"));
        quan.setCellValueFactory(new PropertyValueFactory<Orders, String>("quantity"));
        tableID2.setCellValueFactory(new PropertyValueFactory<Orders, String>("tableid"));
        transID.setCellValueFactory(new PropertyValueFactory<Orders, String>("tablenum"));

        // load dummy data
        table.setItems(list);
        tableT.setItems(tableList);
        tableO.setItems(orderList);


    }

    //load note to edit
    public void loadItem(){
        String item = table.getSelectionModel().getSelectedItem().getTableid();
        System.out.println(item);
        try {
            Connection con = DBConnect.getConnection();
            String sql = String.format("select ItemID,Price from Items where ItemName = '%s' " , item);
            //String sql = String.format("select ItemID,Price from items where ItemName = '%s' " , item);
            ResultSet rs =  con.createStatement().executeQuery(sql);

            while (rs.next()) {

                idText.setText(rs.getString("ItemID"));
                pText.setText(rs.getString("Price"));
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        String t = tableT.getSelectionModel().getSelectedItem().getTableid();
        System.out.println(t);
        try {
            Connection con = DBConnect.getConnection();
            String sql = String.format("select TableID from Tables where TableID = '%s' " , t);
            //String sql = String.format("select TableID from tables where TableID = '%s' " , t);
            ResultSet rs =  con.createStatement().executeQuery(sql);

            while (rs.next()) {

                tableidText.setText(rs.getString("TableID"));
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }


    public void createTransaction(String transid , String date , String tableid , String staffid, String branchid){
        String sql = String.format("insert into Transactions(TransID , TransDate, TableID, StaffID, BranchID ) VALUES('%s' , '%s', '%s', '%s', '%s' )" ,transid,date,tableid,staffid,branchid);
        //String sql = String.format("insert into transactions(TransID , TransDate, TableID, StaffID, BranchID ) VALUES('%s' , '%s', '%s', '%s', '%s' )" ,transid,date,tableid,staffid,branchid);
        try{
            Connection con = DBConnect.getConnection();
            int rs =  con.createStatement().executeUpdate(sql);
            
            System.out.println("Transaction Created");

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public void createT(){

        String t = tableT.getSelectionModel().getSelectedItem().getTableid();
        System.out.println(t);
        createTransaction(transid.getText(),dtf.format(date),t,staffid.getText(),branchid.getText());
    }

    public void createOrder(String itemid , String qu , String tableid2, String transid2){
        String sql = String.format("insert into Orders(ItemID, Quantity , TotalPrice,TableID, TransID ) VALUES('%s' , '%s', (SELECT Price FROM Items WHERE ItemID = '%s') *'%s', '%s', '%s' )" ,itemid,qu,itemid,qu,tableid2,transid2);
        //String sql = String.format("insert into orders(ItemID, Quantity , TotalPrice,TableID, TransID ) VALUES('%s' , '%s', (SELECT Price FROM items WHERE ItemID = '%s') *'%s', '%s', '%s' )" ,itemid,qu,itemid,qu,tableid2,transid2);
        try{
            Connection con = DBConnect.getConnection();
            int rs =  con.createStatement().executeUpdate(sql);
            System.out.println("Order Created");


        }catch (Exception e){
            e.printStackTrace();
            System.out.println("INSERT  : " + e.getMessage());
        }
    }

    public void createO(){
        createOrder(idText.getText(),quantity.getText(),tableidText.getText(),transid.getText());
        idText.setText("");
        quantity.setText("");
        pText.setText("");
        tableidText.setText("");
        loadData();
    }


    //delete row function
    public void deleteNote(){
        String title = table.getSelectionModel().getSelectedItem().getTableid();
        deleteItem(title);
        table.getItems().remove(table.getSelectionModel().getSelectedItem());
    }
}



