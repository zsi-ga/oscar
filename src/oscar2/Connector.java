

package oscar2;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;
import java.lang.ClassNotFoundException;
import java.sql.DriverAction; 
import java.sql.DriverManager;
import java.sql.ResultSet;

public class Connector implements DriverAction{
   
    public Connection conn;
    public String dbName=null;
    public String url = "jdbc:mysql://localhost:3305/";
    public ResultSet rs = null;
    Connector(String text) {
        conn = null;
        dbName=text;
    }
    

    public Connection connectDatabase() {
        
        try {
            

            Class.forName("org.mariadb.jdbc.Driver");
            conn = DriverManager.getConnection(url, "root","");
            

        } catch (SQLException ex) {

            System.out.println("Hiba a kapcsolódás során!");
            System.out.println(ex.getMessage());
            

        } catch (ClassNotFoundException ex) {
            System.out.println("Nincs meg a driver!");
        }

        return conn;

    }

    public void cloceConnect() {
        if(conn!=null){
            try {
                conn.close();
            } catch (SQLException ex) {
                System.out.println("Hiba a lezárás során!");
            }
            
        }
    }

    @Override
    public void deregister() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}