/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.db;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author kaeru
 */
public class SQLReturnObject {
    private ResultSet rs;
    public void setResultSet(ResultSet rs){
        this.rs = rs;
    }
    
    public void print(){
        try {
            Long total = 0L;
            while(rs.next()){
                System.out.println(rs.getString(1)+","+rs.getLong(2));
                total = total+rs.getLong(2);
            }
            
            System.out.println("> Total:"+total);
        } catch (SQLException ex) {
        }
    }
}
