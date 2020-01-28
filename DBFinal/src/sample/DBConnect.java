package sample;

//All the necessary imports
import java.sql.*;



public class DBConnect {
    //declaration for sql
    public Connection con;
    public Connection con123;
    public Statement st;
    public Statement st123;
    public ResultSet rs;


    private Connection conn;
    private Statement stm;
    private ResultSet rset;
    private String url;
    private String user = "root";
    private String pass ="";
    private String query;


    //get connetction uel
    public static Connection getConnection() throws SQLException, ClassNotFoundException, SQLException {

        //Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/finaldb","root","");


        Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://dbta.1ez.xyz/17_PhoStreet",
                    "KLE1391", "f31yymwe");

        } catch (SQLException e) {
            e.printStackTrace();
        }


        return con;

        //return connection;
    }

//    DBConnect(){
//        try {
//
//            Class.forName("com.mysql.jdbc.Driver");
//            // Create Connection
//            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/finaldb","root","");
//            st = con.createStatement();
//        }
//        catch (Exception e){
//            System.out.println("Error : " + e);
//        }
//    }



    DBConnect(){
        try {
            conn = DriverManager.getConnection("jdbc:mysql://dbta.1ez.xyz/17_PhoStreet", user, pass);
            stm = conn.createStatement(rset.TYPE_SCROLL_INSENSITIVE, rset.CONCUR_UPDATABLE);
        } catch (SQLException e) {
            System.out.println("DB  : " + e.getMessage());
        }
    }





    public void closecon(){
        try {
            conn.close();
            System.out.println("DB : DISCONNECTED");

        } catch (SQLException e) {
            System.out.println("DB :"+e.getMessage());
        }
    }


//    public void createTransaction(String transid , String date , String tableid , String staffid, String branchid){
//        String sql = String.format("insert into Transactions(TransID , TransDate, TableID, StaffID, BranchID ) VALUES('%s' , '%s', '%s', '%s', '%s' )" ,transid,date,tableid,staffid,branchid);
//        //String sql = String.format("insert into transactions(TransID , TransDate, TableID, StaffID, BranchID ) VALUES('%s' , '%s', '%s', '%s', '%s' )" ,transid,date,tableid,staffid,branchid);
//        try{
//            st123 = con123.createStatement();
//            st123.executeUpdate(sql);
//
//            System.out.println("Transaction Created");
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }




    //delete item from sql database
    void deleteItem(String title){

        String sql = String.format("delete from notes where title = '%s' "  ,title);
        String ssql = String.format("drop table %s" , title);


        try {
            st = con.createStatement();
            st.executeUpdate(sql);
            System.out.println("Row Deleted");
            st = con.createStatement();
            st.executeUpdate(ssql);
            System.out.println("Table Deleted");


        } catch (Exception e){
            System.out.println(e);
        }
    }
}
