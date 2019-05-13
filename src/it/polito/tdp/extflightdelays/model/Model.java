package it.polito.tdp.extflightdelays.model;

import java.awt.Event;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.event.ConnectedComponentTraversalEvent;
import org.jgrapht.event.EdgeTraversalEvent;
import org.jgrapht.event.TraversalListener;
import org.jgrapht.event.VertexTraversalEvent;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.traverse.BreadthFirstIterator;

import it.polito.tdp.extflightdelays.db.ExtFlightDelaysDAO;

public class Model {
	
	//Dichiaro variabili
	List<Airport> aereoporti;
	Graph<Airport, DefaultWeightedEdge> grafo;
	Map<Integer,Airport> idMap;
	List<Rotta> rotte;
	Map<Airport,Airport> visita = new HashMap<>();
	
	public void creaGrafo(int distanzaMinima) {
		
		//Creo grafo
		grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		//Carico lista aereoporti dal DAO
		ExtFlightDelaysDAO dao = new ExtFlightDelaysDAO();
		//Creo mappa per tener traccia degli Id
		idMap = new HashMap<Integer, Airport>();
		aereoporti = dao.loadAllAirports(idMap);
		
		//Aggiungo vertici al grafo
		Graphs.addAllVertices(grafo, aereoporti);
		
		//Aggiungo archi al grafo
		rotte=dao.creaArchi(distanzaMinima);	
		for (Rotta r : rotte) {
			
			//Controllo se esiste già un arco:
			//Se esiste, aggiorno il peso
			DefaultWeightedEdge edge = grafo.getEdge(idMap.get(r.getSource()), idMap.get(r.getDestination()));
			if (edge == null) {
			Graphs.addEdge(grafo, idMap.get(r.getSource()), idMap.get(r.getDestination()), r.getAverage());
			}
			else {
				double peso = grafo.getEdgeWeight(edge);
				double newPeso = (peso + r.getAverage())/2;
				grafo.setEdgeWeight(edge, newPeso);
			}
		}
		
		System.out.println("Grafo creato\n#Vertici: "+grafo.vertexSet().size()+"\nArchi: "+grafo.edgeSet().size());
	}
	
	public boolean testConnessione(Integer id1, Integer id2) {
		
		Set<Airport> visitati = new HashSet<>();
		
		Airport partenza = idMap.get(id1);
		Airport arrivo = idMap.get(id2);
		System.out.println("Testo connessione tra "+partenza+" e "+arrivo);
		
		//Testo in ampiezza
		//Definisco iteratore
		BreadthFirstIterator<Airport, DefaultWeightedEdge> it = new BreadthFirstIterator<>(this.grafo,partenza); // Visita in ampiezza a partire dall'aereoporto di partenza
		
		while(it.hasNext()) {
			visitati.add(it.next());
		}
		
		if (visitati.contains(arrivo)) return true;
		else return false;	
	}

	public List<Airport> trovaPercorso(Integer id1,Integer id2){

	    List<Airport> percorso = new LinkedList<>();
		
		Airport partenza = idMap.get(id1);
		Airport arrivo = idMap.get(id2);
		System.out.println("Cerco percorso tra "+partenza+" e "+arrivo);
		
		BreadthFirstIterator<Airport, DefaultWeightedEdge> it = new BreadthFirstIterator<>(this.grafo,partenza); // Visita in ampiezza a partire dall'aereoporto di partenza

		it.addTraversalListener(new TraversalListener<Airport, DefaultWeightedEdge>() {
			
			@Override
			public void vertexTraversed(VertexTraversalEvent<Airport> arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void vertexFinished(VertexTraversalEvent<Airport> arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override //Evento ogni volta che attraverso un arco
			public void edgeTraversed(EdgeTraversalEvent<DefaultWeightedEdge> ev) {

				Airport sorgente = grafo.getEdgeSource(ev.getEdge());
				Airport destinazione = grafo.getEdgeTarget(ev.getEdge());
				
				if (!visita.containsKey(destinazione) && visita.containsKey(sorgente)) {
					visita.put(destinazione, sorgente);
				} //Caso inverso
				else if ( visita.containsKey(destinazione) && !visita.containsKey(sorgente) ) {
					visita.put(sorgente, destinazione);
				}
				
			}
			
			@Override
			public void connectedComponentStarted(ConnectedComponentTraversalEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void connectedComponentFinished(ConnectedComponentTraversalEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		//Inizializzo percorso con il nodo sorgente
		visita.put(partenza, null);
		
		while (it.hasNext()) {
			it.next();
		}
		
		if (!visita.containsKey(partenza) || !visita.containsKey(arrivo)) {
			return null;
		}
		
		Airport step = arrivo;
		
		while( step!=null ) {
			//Aggiungo sempre in prima posizione
			percorso.add(0,step);
			step = visita.get(step);
		}
		
		return percorso;
	}
	
}
