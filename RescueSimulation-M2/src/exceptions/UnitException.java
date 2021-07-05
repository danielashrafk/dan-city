package exceptions;

import model.units.Unit;
import simulation.Rescuable;

public abstract class UnitException extends SimulationException {
	Unit unit;
	Rescuable target;
	public UnitException(Unit unit, Rescuable target) {
		super();
		this.unit = unit;
		this.target = target;
	}
	
	public UnitException(Unit unit, Rescuable target, String m) {
		super(m);
		this.unit = unit;
		this.target = target;
	}
}
