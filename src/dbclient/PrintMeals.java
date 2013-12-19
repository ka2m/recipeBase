/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbclient;

import dbclient.datastructs.MealDataStruct;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author fau
 */
public class PrintMeals {

    private static Connection c;
    private static String table;

    public PrintMeals(String tablename, Connection c) {
        this.c = c;
        this.table = tablename;
    }

    public List<MealDataStruct> GetMeals() {
        List<MealDataStruct> result = new ArrayList<>();
        try {
            Statement st = c.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM " + table + ";");
            while (rs.next()) {
                MealDataStruct mds = new MealDataStruct();
                mds.displayname = rs.getString("displayname");
                mds.path = rs.getString("path");
                result.add(mds);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PrintMeals.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
}
