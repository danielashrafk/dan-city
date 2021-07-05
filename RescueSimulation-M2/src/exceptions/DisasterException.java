package exceptions;

import model.disasters.Disaster;


public abstract class DisasterException extends SimulationException {

		Disaster disaster;
		public DisasterException(Disaster disaster) {
			super();
			this.disaster = disaster;
		}
		
		public DisasterException(Disaster disaster, String m) {
			super(m);
			this.disaster = disaster;
		}
	
	
	
}
