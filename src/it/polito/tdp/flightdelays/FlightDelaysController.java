

/**
 * Sample Skeleton for 'ExtFlightDelays.fxml' Controller Class
 */

package it.polito.tdp.flightdelays;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.w3c.dom.NamedNodeMap;

import it.polito.tdp.extflightdelays.model.Airport;
import it.polito.tdp.extflightdelays.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
/**
 * Sample Skeleton for 'FlightDelays.fxml' Controller Class
 */

public class FlightDelaysController {
	
	private Model model;
	Map<String,Airport> nameMap = new HashMap<String,Airport>();

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML // fx:id="distanzaMinima"
    private TextField distanzaMinima; // Value injected by FXMLLoader

    @FXML // fx:id="btnAnalizza"
    private Button btnAnalizza; // Value injected by FXMLLoader

    @FXML // fx:id="cmbBoxAeroportoPartenza"
    private ComboBox<String> cmbBoxAeroportoPartenza; // Value injected by FXMLLoader

    @FXML // fx:id="cmbBoxAeroportoArrivo"
    private ComboBox<String> cmbBoxAeroportoArrivo; // Value injected by FXMLLoader

    @FXML // fx:id="btnAeroportiConnessi"
    private Button btnAeroportiConnessi; // Value injected by FXMLLoader

    @FXML
    void doAnalizzaAeroporti(ActionEvent event) {

    	try {
    	model.creaGrafo(Integer.parseInt(distanzaMinima.getText()));
    	txtResult.appendText("Grafo Creato!");
    	}catch(NumberFormatException e) {
    		txtResult.appendText("Inserire un valore corretto");
    	}
    	
    }

    @FXML
    void doTestConnessione(ActionEvent event) {
    	
    	txtResult.clear();
    	
    	//Acquisisco id aereoporti
    	int id1 = nameMap.get(cmbBoxAeroportoPartenza.getValue()).getId();
    	int id2 = nameMap.get(cmbBoxAeroportoArrivo.getValue()).getId();
    	
    	if (model.testConnessione(id1, id2)) {
    	List<Airport> aereoporti = model.trovaPercorso(id1,id2);
    	txtResult.appendText("PERCORSO:\n");
    		for (Airport a : aereoporti) {
    			txtResult.appendText(a.getAirportName()+"\n");
    		}
    	}
    	else {
    	    txtResult.appendText("Nessun percorso trovato");
    	}
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'FlightDelays.fxml'.";
        assert distanzaMinima != null : "fx:id=\"distanzaMinima\" was not injected: check your FXML file 'FlightDelays.fxml'.";
        assert btnAnalizza != null : "fx:id=\"btnAnalizza\" was not injected: check your FXML file 'FlightDelays.fxml'.";
        assert cmbBoxAeroportoPartenza != null : "fx:id=\"cmbBoxAeroportoPartenza\" was not injected: check your FXML file 'FlightDelays.fxml'.";
        assert cmbBoxAeroportoArrivo != null : "fx:id=\"cmbBoxAeroportoArrivo\" was not injected: check your FXML file 'FlightDelays.fxml'.";
        assert btnAeroportiConnessi != null : "fx:id=\"btnAeroportiConnessi\" was not injected: check your FXML file 'FlightDelays.fxml'.";

    }
    
    public void setModel(Model model) {
    	
		this.model = model;
		cmbBoxAeroportoPartenza.getItems().addAll(model.getAereoporti(nameMap));
		cmbBoxAeroportoArrivo.getItems().addAll(model.getAereoporti(nameMap));
	}
}





