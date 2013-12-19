/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recipeclient;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.collections.FXCollections;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import dbclient.*;
import dbclient.datastructs.DirectoryDataStruct;
import dbclient.datastructs.MealDataStruct;
import fileclient.FileConnector;
import java.awt.Desktop;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.stream.util.StreamReaderDelegate;
import javax.xml.transform.stream.StreamResult;

/**
 *
 * @author gameo_000
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private static ListView entryLV;
    @FXML
    private static ListView typeLV;
    @FXML
    private static Button newEntryB;
    @FXML
    private static Button removeEntryB;
    @FXML
    private static Button updEntryB;

    private static FileConnector fc;
    // private static DataConnector dc;

    private static List<DirectoryDataStruct> ldds = null;
    private static int currentID = -1;
    private static Connector c = null;

    @FXML
    private void handleRemoveAction(ActionEvent event) {
        String entry = (String) entryLV.getSelectionModel().getSelectedItem();

        String path = c.getMealPath(ldds.get(currentID).tablename, entry);

        if ((entry != null) && (path != null)) {
            c.removeMeals(ldds.get(currentID).tablename, entry);
            fc.deleteFile(path);
            loadEntry();
        }
    }

    @FXML
    private void handleReplaceAction(ActionEvent event) {
        FileChooser fc = new FileChooser();
        File replacement = fc.showOpenDialog(new Stage());
        if (replacement != null) {
            String entry = (String) entryLV.getSelectionModel().getSelectedItem();
            c.replaceMeals(ldds.get(currentID).tablename, replacement, entry);
            this.fc.replaceFile(entry, replacement);
            loadEntry();
        }
    }

    @FXML
    private void handleAddAction(ActionEvent event) {
        FileChooser fc = new FileChooser();
        List<File> files = fc.showOpenMultipleDialog(new Stage());
        if (files != null) {
            c.addMeals(ldds.get(currentID).tablename, files);
            this.fc.addFiles(files);
            loadEntry();
        }
    }

    @FXML
    private void handleTypeLVClick(MouseEvent arg0) {
        newEntryB.setDisable(false);
        this.loadEntry();
    }

    @FXML
    private void handleTypeLVKey(KeyEvent arg0) {
        this.loadEntry();
    }

    @FXML
    private void handleEntryLVClick(MouseEvent arg0) {
        removeEntryB.setDisable(false);
        updEntryB.setDisable(false);

        if (arg0.getClickCount() == 2) {
            try {
                Desktop.getDesktop().open(new File(ldds.get(currentID).path
                        + c.getMealPath(ldds.get(currentID).tablename, (String) entryLV.getSelectionModel().getSelectedItem())));
            } catch (IOException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    private void handleEntryLVKey(KeyEvent arg0) {
        //todo;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //working with db
        c = new Connector("recipe.db");
        fc = new FileConnector();
        //  dc = new DataConnector();
        ldds = c.getDirs();
        showDirs();

    }

    private void loadEntry() {
        int c = 0;

        for (DirectoryDataStruct dds : ldds) {
            if (dds.displayname.equals((String) typeLV.getSelectionModel().getSelectedItem())) {
                currentID = dds.id;
                fc.setDir(dds.path);
                break;
            }
        }

        showEntry();

    }

    private void showDirs() {
        ArrayList<String> dirs = new ArrayList<>();
        for (DirectoryDataStruct dds : ldds) {
            dirs.add(dds.displayname);
        }
        if (typeLV == null) {
            System.exit(1);
        }
        typeLV.setItems(FXCollections.observableList(dirs));

    }

    private void showEntry() {
        List<MealDataStruct> entrs = null;
        ArrayList<String> meals = new ArrayList<>();

        entrs = c.getMeals(ldds.get(currentID).tablename);

        for (MealDataStruct mds : entrs) {
            meals.add(mds.displayname);
        }
        entryLV.setItems(FXCollections.observableList(meals));
    }

}
