package oscar2;

import java.sql.Statement;
import java.util.Scanner;
import java.sql.SQLException;
import java.io.FileNotFoundException;  
import java.sql.ResultSet;
import java.io.FileReader;
import java.sql.Connection;

public class Statement 
{

    private String dbName;
    private String path;
    private String fileName;
    private String switcher;

    Statement(String dbName, String path, String fileName, String switcher) {
       
        this.dbName = dbName;
        this.path= path;
        this.fileName=fileName;
        this.switcher=switcher;
    }

    public void getOscar() 
    {
        Statement stmt = null;
        ResultSet rs = null;
        String sql = null;
        FileReader reader=null;
        Connect connect = new Connect(dbName);
        Connection conn = connect.connectDatabase();
        try{
            
            stmt = conn.createStatement();
            stmt.executeQuery("CREATE DATABASE IF NOT EXISTS oscar DEFAULT CHARSET=utf8 COLLATE=utf8_hungarian_ci;");
            stmt.execute("USE oscar;");
            stmt.executeUpdate("create table if not EXISTS filmek(azon varchar(15) PRIMARY Key,cim varchar(255),ev int,dij int,jelol int);");
            try{
            if(switcher.equals("-f") || switcher.equals("--force"))stmt.executeQuery("TRUNCATE TABLE filmek;");
            }catch(NullPointerException ex){System.out.println("Nincs kapcsoló!");}
            try{
                reader=new FileReader(path+fileName);
            }catch(FileNotFoundException ex){
                System.out.println("Fájl nem található!");
            }
            Scanner scan= new Scanner(reader);
            String row= scan.nextLine();
            while(scan.hasNext()){
                row = scan.nextLine();
                String rowSplit[] = row.split(";");
                stmt.executeQuery("INSERT INTO filmek values ("+rowSplit[0]+","+rowSplit[1]+","+rowSplit[2]+","+rowSplit[3]+","+rowSplit[4]+");");
            }
            stmt.close();

        } catch (SQLException ex) {
           
            System.out.println("Hiba a lekérdezés során!");
        }
    }

}