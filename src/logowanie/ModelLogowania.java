package logowanie;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dbUtil.dbConnection;

public class ModelLogowania {
    Connection connection;

    public ModelLogowania(){
        try{
            this.connection = dbConnection.getConnection();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        if(this.connection == null){
            System.exit(1);
        }



    }

    public boolean czyBazaPolaczona(){
        return this.connection !=null;
    }

    public boolean poprawnoscLogowania(String login, String haslo, String opcja )throws Exception{
        PreparedStatement pr = null;
        ResultSet rs = null;

        String sql = "SELECT * FROM logowanie WHERE login=? AND haslo=? AND grupa=?";

        try{
            pr = this.connection.prepareStatement(sql);
            pr.setString(1, login);
            pr.setString(2,haslo);
            pr.setString(3,opcja);

            rs=pr.executeQuery();

            boolean ok1;
            if(rs.next()){
                return true;
            }
            else return false;

        }
        catch(SQLException e ){
            e.printStackTrace();
            return false;
        }

        finally{
            {
                pr.close();
                rs.close();
            }
        }



    }

}
