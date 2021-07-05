package exceptions;

import model.disasters.Disaster;

public class CitizenAlreadyDeadException extends DisasterException {
	
	public CitizenAlreadyDeadException(Disaster disaster) {
		super(disaster);
	}
	
	public CitizenAlreadyDeadException(Disaster disaster, String m) {
		super(disaster, m);
	}
	

}
