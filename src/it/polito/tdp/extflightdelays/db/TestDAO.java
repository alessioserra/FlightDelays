package it.polito.tdp.extflightdelays.db;

import java.util.HashMap;
import java.util.Map;

import it.polito.tdp.extflightdelays.model.Airport;

public class TestDAO {

	public static void main(String[] args) {

		ExtFlightDelaysDAO dao = new ExtFlightDelaysDAO();
		
		Map<Integer, Airport> idMap = new HashMap<>();

		System.out.println(dao.loadAllAirlines());
		System.out.println(dao.loadAllAirports(idMap));
	}

}
