package exceptions;

public abstract class SimulationException extends Exception {
	
	public SimulationException() {
		super();
	}

	public SimulationException(String m) { 
		super(m);
	}
}
