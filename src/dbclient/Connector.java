/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbclient;

import dbclient.datastructs.DirectoryDataStruct;
import dbclient.datastructs.MealDataStruct;
import java.io.File;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.DriverManager;

import java.util.List;

/**
 *
 * @author Vlad Slepukhin <slepukhinvd@sgu.ru>
 */
public class Connector {

    private static Connection c;
    private static String dbName;

    public Connector(String name) {
        Connector.dbName = name;
        try {
            this.connect();
        } catch (Exception ex) {
            System.out.println("Can't Connect");
            System.exit(-1);
        }
    }

    private void connect() throws Exception {
        Class.forName("org.sqlite.JDBC");
        c = DriverManager.getConnection("jdbc:sqlite:" + dbName);
        c.setAutoCommit(false);
    }

    public Connection getConnection() {
        return this.c;
    }

    public List<DirectoryDataStruct> getDirs() {
        PrintDirs pd = new PrintDirs(c);
        return pd.getDirs();

    }

    public List<MealDataStruct> getMeals(String tableName) {
        PrintMeals pm = new PrintMeals(tableName, c);
        return pm.GetMeals();
    }

    public void addMeals(String tableName, List<File> files) {
        InsertMeals im = new InsertMeals(tableName, files, c);
        im.doInsert();
    }
    
    public void replaceMeals(String tableName, File f, String oldFile)
    {
        ReplaceMeals rm = new ReplaceMeals(tableName, f, oldFile, c);
        rm.doReplace();
    }
    
    public void removeMeals(String tableName, String displayName)
    {
        DeleteMeals dm = new DeleteMeals(tableName, displayName, c);
        dm.doDelete();
    }
    
    public String getMealPath(String tableName, String displayName)
    {
        GetMealPath gms = new GetMealPath();
        return gms.get(tableName, displayName, c);
    }
}
