package it.polito.tdp.extflightdelays.model;

public class Rotta {

	private int source;
	private int destination;
	private double average;
	
	public Rotta(int source, int destination, double average) {
		super();
		this.source = source;
		this.destination = destination;
		this.average = average;
	}

	public int getSource() {
		return source;
	}

	public void setSource(int source) {
		this.source = source;
	}

	public int getDestination() {
		return destination;
	}

	public void setDestination(int destination) {
		this.destination = destination;
	}

	public double getAverage() {
		return average;
	}

	public void setAverage(double average) {
		this.average = average;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + destination;
		result = prime * result + source;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Rotta other = (Rotta) obj;
		if (destination != other.destination)
			return false;
		if (source != other.source)
			return false;
		return true;
	}

	
}