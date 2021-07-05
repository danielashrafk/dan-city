package controller;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;

import exceptions.CitizenAlreadyDeadException;
import exceptions.DisasterException;
import exceptions.IncompatibleTargetException;
import exceptions.SimulationException;
import exceptions.UnitException;
import model.disasters.Disaster;
import model.events.SOSListener;
import model.infrastructure.ResidentialBuilding;
import model.people.Citizen;
import model.people.CitizenState;
import model.units.Ambulance;
import model.units.DiseaseControlUnit;
import model.units.Evacuator;
import model.units.FireTruck;
import model.units.Unit;
import model.units.UnitState;
import simulation.Address;
import simulation.Rescuable;
import simulation.Simulator;
import view.RescueSimulationView;

public class CommandCenter implements SOSListener, ActionListener {

	private Simulator engine;
	private ArrayList<ResidentialBuilding> visibleBuildings;
	private ArrayList<Citizen> visibleCitizens;
	private RescueSimulationView rescueSimulationView;
	private ArrayList<Citizen> deadCitizens; 
	private ArrayList<ResidentialBuilding> collapsedBuildings;
	private ArrayList<Disaster> executedDisasters;
	private ArrayList<Disaster> activeDisasters;
	private Unit unitSelected;
	private ArrayList<JButton> unitsSelected;
	@SuppressWarnings("unused")
	private ArrayList<Unit> emergencyUnits;
	private Address previousTargetLocation = null; 
	private Rescuable previousTarget = null; 
	private JPanel empty = new JPanel();
	public CommandCenter() throws Exception {
		engine = new Simulator(this);
		visibleBuildings = new ArrayList<ResidentialBuilding>();
		visibleCitizens = new ArrayList<Citizen>();
		emergencyUnits = engine.getEmergencyUnits();
		rescueSimulationView = new RescueSimulationView();
		deadCitizens = new ArrayList<Citizen>();
		collapsedBuildings = new ArrayList<ResidentialBuilding>();
		executedDisasters = new ArrayList<Disaster>();
		activeDisasters = new ArrayList<Disaster>();
		unitsSelected = new ArrayList<JButton>();
		rescueSimulationView.getNextCycle().addActionListener(this);
		unitSelected = null;
		for(int i = 0; i < engine.getEmergencyUnits().size();i++) {
			
			if(engine.getEmergencyUnits().get(i) instanceof Ambulance) {
				JButton amb = new JButton();
				amb.addActionListener(this);
				amb.setToolTipText("Ambulance");
				amb.setOpaque(true);
				amb.setPreferredSize(new Dimension(110,60));
				Border bored = BorderFactory.createLineBorder(Color.DARK_GRAY);
				amb.setBorder(bored);
				amb.setBackground(Color.DARK_GRAY);
				amb.setCursor(new Cursor(Cursor.HAND_CURSOR));
				 amb.addMouseListener(new java.awt.event.MouseAdapter() {
					    public void mouseEntered(java.awt.event.MouseEvent evt) {
					    	amb.setBackground(Color.BLACK);
					    }
					    public void mouseExited(java.awt.event.MouseEvent evt) {
					    	amb.setBackground(Color.DARK_GRAY);
					    }
					});
				ImageIcon iconAmb = new ImageIcon(getClass().getResource("ambulance (2).png"));
				Image img = iconAmb.getImage() ;  
				Image newimg = img.getScaledInstance( 100, 50,  java.awt.Image.SCALE_SMOOTH ) ;  
				iconAmb = new ImageIcon( newimg );
				amb.setIcon(iconAmb);
				rescueSimulationView.addUnit(amb);
				unitsSelected.add(amb);
			}
			
			else if(engine.getEmergencyUnits().get(i) instanceof DiseaseControlUnit) {
				
				JButton Disease = new JButton();
				Disease.addActionListener(this);
				Disease.setToolTipText("Disease");
				Border bored = BorderFactory.createLineBorder(Color.DARK_GRAY);
				Disease.setPreferredSize(new Dimension(110,60));
				Disease.setBorder(bored);
				Disease.setOpaque(true);
				Disease.setBackground(Color.DARK_GRAY);
				Disease.setCursor(new Cursor(Cursor.HAND_CURSOR));
				Disease.addMouseListener(new java.awt.event.MouseAdapter() {
					    public void mouseEntered(java.awt.event.MouseEvent evt) {
					    	Disease.setBackground(Color.BLACK);
					    }
					    public void mouseExited(java.awt.event.MouseEvent evt) {
					    	Disease.setBackground(Color.DARK_GRAY);
					    }
					});
				ImageIcon iconDis = new ImageIcon(getClass().getResource("Disease (2).png"));
				Image img2 = iconDis.getImage() ;  
				Image newimg2 = img2.getScaledInstance( 50, 50,  java.awt.Image.SCALE_SMOOTH ) ;  
				iconDis = new ImageIcon( newimg2 );
				Disease.setIcon(iconDis);
				rescueSimulationView.addUnit(Disease);
				unitsSelected.add(Disease);
			}
			
			else if(engine.getEmergencyUnits().get(i) instanceof Evacuator) {
				
				JButton Evacuator = new JButton();
				Evacuator.addActionListener(this);
				Evacuator.setToolTipText("Evacuator");
				Evacuator.setPreferredSize(new Dimension(110,60));
				Border bored = BorderFactory.createLineBorder(Color.DARK_GRAY);
				Evacuator.setBorder(bored);
				Evacuator.setOpaque(true);
				Evacuator.setBackground(Color.DARK_GRAY);
				Evacuator.setCursor(new Cursor(Cursor.HAND_CURSOR));
				Evacuator.addMouseListener(new java.awt.event.MouseAdapter() {
					    public void mouseEntered(java.awt.event.MouseEvent evt) {
							Evacuator.setBackground(Color.BLACK);
					    }
					    public void mouseExited(java.awt.event.MouseEvent evt) {
							Evacuator.setBackground(Color.DARK_GRAY);
					    }
					});
				ImageIcon iconEv = new ImageIcon(getClass().getResource("truck.png"));
				Image img3 = iconEv.getImage() ;  
				Image newimg3 = img3.getScaledInstance( 90, 50,  java.awt.Image.SCALE_SMOOTH ) ;  
				iconEv = new ImageIcon( newimg3 );
				Evacuator.setIcon(iconEv);
				rescueSimulationView.addUnit(Evacuator);
				unitsSelected.add(Evacuator);
				
			}
			
			else if(engine.getEmergencyUnits().get(i) instanceof FireTruck) {
				
				
				JButton Fire = new JButton();
				Fire.addActionListener(this);
				Fire.setToolTipText("Fire");
				Fire.setPreferredSize(new Dimension(110,60));
				Border bored = BorderFactory.createLineBorder(Color.DARK_GRAY);
				Fire.setBorder(bored);
				Fire.setOpaque(true);
				Fire.setBackground(Color.DARK_GRAY);
				Fire.setCursor(new Cursor(Cursor.HAND_CURSOR));
				Fire.addMouseListener(new java.awt.event.MouseAdapter() {
					    public void mouseEntered(java.awt.event.MouseEvent evt) {
							Fire.setBackground(Color.BLACK);
					    }
					    public void mouseExited(java.awt.event.MouseEvent evt) {
							Fire.setBackground(Color.DARK_GRAY);
					    }
					});
				ImageIcon iconF = new ImageIcon(getClass().getResource("fire.png"));
				Image img4 = iconF.getImage() ;  
				Image newimg4 = img4.getScaledInstance( 100, 50,  java.awt.Image.SCALE_SMOOTH ) ;  
				iconF = new ImageIcon( newimg4 );
				Fire.setIcon(iconF);
				rescueSimulationView.addUnit(Fire);
				unitsSelected.add(Fire);
			}
			
			else {
				
				
				JButton Gas = new JButton();
				Gas.addActionListener(this);
				Gas.setToolTipText("Gas");
				Gas.setPreferredSize(new Dimension(110,60));
				Border bored = BorderFactory.createLineBorder(Color.DARK_GRAY);
				Gas.setBorder(bored);
				Gas.setOpaque(true);
				Gas.setBackground(Color.DARK_GRAY);
				Gas.setCursor(new Cursor(Cursor.HAND_CURSOR));
				Gas.addMouseListener(new java.awt.event.MouseAdapter() {
					    public void mouseEntered(java.awt.event.MouseEvent evt) {
							Gas.setBackground(Color.BLACK);
					    }
					    public void mouseExited(java.awt.event.MouseEvent evt) {
							Gas.setBackground(Color.DARK_GRAY);
					    }
					});
				ImageIcon iconF = new ImageIcon(getClass().getResource("gas.png"));
				Image img4 = iconF.getImage() ;  
				Image newimg4 = img4.getScaledInstance( 100, 50,  java.awt.Image.SCALE_SMOOTH ) ;  
				iconF = new ImageIcon( newimg4 );
				Gas.setIcon(iconF);
				rescueSimulationView.addUnit(Gas);
				unitsSelected.add(Gas);
			}
			
			
		}
		for (int i = 0; i < visibleCitizens.size(); i++) {
			
			JButton b = new JButton ();
			b.addActionListener(this);
			b.setText("C");
			
 			rescueSimulationView.addSimulatable(visibleCitizens.get(i).getLocation().getX(), visibleCitizens.get(i).getLocation().getY(), this, b);
			
		}
		
		for (int i = 0; i < visibleBuildings.size(); i++) {
			
			JButton b = new JButton ();
			b.addActionListener(this);
			b.setText("B");
			
			rescueSimulationView.addSimulatable(visibleBuildings.get(i).getLocation().getX(), visibleBuildings.get(i).getLocation().getY(), this, b);
			
		}
		
		
		rescueSimulationView.setVisible(true);
	}


	@Override
	public void receiveSOSCall(Rescuable r) {
		
		if (r instanceof ResidentialBuilding) {
			
			if (!visibleBuildings.contains(r))
				visibleBuildings.add((ResidentialBuilding) r);
			
			
		} else {
			
			if (!visibleCitizens.contains(r))
				visibleCitizens.add((Citizen) r);
		}
		
		

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton b = (JButton) e.getSource();
		if(b.getToolTipText() == "C") {
		int index = rescueSimulationView.getBtnsCit().indexOf(b);
		if(unitSelected != null) {
//			try {
//				unitSelected.respond(visibleCitizens.get(index));
//				
//			} catch (UnitException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
			try {
				unitSelected.respond(visibleCitizens.get(index));
			} catch (UnitException e1) {
				if(e1 instanceof IncompatibleTargetException)
				JOptionPane.showMessageDialog(null, "This Unit Only Responds To Buildings");
				else
					JOptionPane.showMessageDialog(null, "No Need For This Unit To Respond To The Target");
			}
			
			rescueSimulationView.getTxtCells().setText("");
			if(previousTargetLocation != null   &&  previousTarget != null && previousTarget.getDisaster().isActive() ) {
				int x = previousTargetLocation.getX();
				int y = previousTargetLocation.getY();
				rescueSimulationView.getGrid()[x][y].setBackground(Color.DARK_GRAY);
				rescueSimulationView.getGrid()[x][y].setCursor(new Cursor(Cursor.HAND_CURSOR));
				rescueSimulationView.getGrid()[x][y].addMouseListener(new java.awt.event.MouseAdapter() {
					    public void mouseEntered(java.awt.event.MouseEvent evt) {
					    	rescueSimulationView.getGrid()[x][y].setBackground(Color.getHSBColor(16, 63, 94));
					    }
					    public void mouseExited(java.awt.event.MouseEvent evt) {
					    	rescueSimulationView.getGrid()[x][y].setBackground(Color.DARK_GRAY);
					    }
					});
			}
			previousTargetLocation = visibleCitizens.get(index).getLocation();
			unitSelected = null;
		}
			
			
		else {	
		rescueSimulationView.updateInfo(visibleCitizens.get(index));
		}
		
		}
		
		else if(b.getToolTipText() == "B") {
			
			int index = rescueSimulationView.getBtnsBuild().indexOf(b);
			if(unitSelected != null) {
				try {
					unitSelected.respond(visibleBuildings.get(index));
					
				} catch (UnitException e1) {
					if(e1 instanceof IncompatibleTargetException)
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, "This Unit Only Responds To Buildings");
					else
						JOptionPane.showMessageDialog(null, "No Need For This Unit To Respond To The Target");
				}
				rescueSimulationView.getTxtCells().setText("");
				if(previousTargetLocation != null   &&  previousTarget != null && previousTarget.getDisaster().isActive() ) {
					int x = previousTargetLocation.getX();
					int y = previousTargetLocation.getY();
					rescueSimulationView.getGrid()[x][y].setBackground(Color.DARK_GRAY);
					rescueSimulationView.getGrid()[x][y].setCursor(new Cursor(Cursor.HAND_CURSOR));
					rescueSimulationView.getGrid()[x][y].addMouseListener(new java.awt.event.MouseAdapter() {
						    public void mouseEntered(java.awt.event.MouseEvent evt) {
						    	rescueSimulationView.getGrid()[x][y].setBackground(Color.getHSBColor(16, 63, 94));
						    }
						    public void mouseExited(java.awt.event.MouseEvent evt) {
						    	rescueSimulationView.getGrid()[x][y].setBackground(Color.DARK_GRAY);
						    }
						});
				}
				previousTargetLocation = visibleBuildings.get(index).getLocation();
				unitSelected = null;
			}
			else {
			rescueSimulationView.updateInfo(visibleBuildings.get(index));
			}
		}
		
		else if(b.getText() == "Next Cycle") {
			unitSelected = null;
			executedDisasters.clear();
			activeDisasters.clear();
			deadCitizens.clear();
			collapsedBuildings.clear();
			try {
				engine.nextCycle();
			} catch (DisasterException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			rescueSimulationView.getBtnsCit().clear();
			rescueSimulationView.getBtnsBuild().clear();
			rescueSimulationView.getTxtCells().setText("");
			if(engine.checkGameOver()) {
				
				JOptionPane.showMessageDialog(null, "The Game Is Over \n" + "Number Of Casualties: " + engine.calculateCasualties());
				rescueSimulationView.setContentPane(empty);

			}
			else {
			for (int i = 0; i < visibleCitizens.size(); i++) {
			
				JButton b2 = new JButton ();
				b2.addActionListener(this);
				b2.setText("C");
				
	 			rescueSimulationView.addSimulatable(visibleCitizens.get(i).getLocation().getX(), visibleCitizens.get(i).getLocation().getY(), this, b2);
				if(visibleCitizens.get(i).getState() == CitizenState.DECEASED) {
					
					rescueSimulationView.Dead(visibleCitizens.get(i), visibleCitizens.get(i).getLocation().getX(), visibleCitizens.get(i).getLocation().getY());
					deadCitizens.add(visibleCitizens.get(i));
				}
				if(visibleBuildings.get(i).getDisaster() != null) {
				executedDisasters.add(visibleCitizens.get(i).getDisaster());
				if(visibleCitizens.get(i).getDisaster().isActive()) {
					activeDisasters.add(visibleCitizens.get(i).getDisaster());
				}
				
				}
			}
			
			for (int i = 0; i < visibleBuildings.size(); i++) {
				
				JButton b2 = new JButton ();
				b2.addActionListener(this);
				b2.setText("B");
				
				rescueSimulationView.addSimulatable(visibleBuildings.get(i).getLocation().getX(), visibleBuildings.get(i).getLocation().getY(), this, b2);
				if(visibleBuildings.get(i).getStructuralIntegrity() == 0) {
					
					rescueSimulationView.Dead(visibleBuildings.get(i), visibleBuildings.get(i).getLocation().getX(), visibleBuildings.get(i).getLocation().getY());
					collapsedBuildings.add(visibleBuildings.get(i));
				}
				if(visibleBuildings.get(i).getDisaster() != null) {
				executedDisasters.add(visibleBuildings.get(i).getDisaster());
				if(visibleBuildings.get(i).getDisaster().isActive()) {
					activeDisasters.add(visibleBuildings.get(i).getDisaster());
				}
			}
			}	
			
			rescueSimulationView.updateLog(deadCitizens, collapsedBuildings, executedDisasters, activeDisasters);
			
			for(int i = 0; i < unitsSelected.size(); i++) {
				
				if(((Unit) engine.getEmergencyUnits().get(i)).getState() == UnitState.TREATING) {
					if( (engine.getEmergencyUnits().get(i).getTarget() instanceof Citizen &&  (((Citizen) engine.getEmergencyUnits().get(i).getTarget()).getHp() != 0)) || (engine.getEmergencyUnits().get(i).getTarget() instanceof ResidentialBuilding &&  (((ResidentialBuilding) engine.getEmergencyUnits().get(i).getTarget()).getStructuralIntegrity() != 0))) {
					int x = engine.getEmergencyUnits().get(i).getTarget().getLocation().getX();
					int y = engine.getEmergencyUnits().get(i).getTarget().getLocation().getY();
					rescueSimulationView.getGrid()[x][y].setBackground(Color.GREEN);
					rescueSimulationView.getGrid()[x][y].setCursor(new Cursor(Cursor.HAND_CURSOR));
					rescueSimulationView.getGrid()[x][y].addMouseListener(new java.awt.event.MouseAdapter() {
						    public void mouseEntered(java.awt.event.MouseEvent evt) {
						    	rescueSimulationView.getGrid()[x][y].setBackground(Color.getHSBColor(16, 63, 94));
						    }
						    public void mouseExited(java.awt.event.MouseEvent evt) {
						    	rescueSimulationView.getGrid()[x][y].setBackground(Color.GREEN);
						    }
						});
					
				}
				}
				
				else if( (engine.getEmergencyUnits().get(i).getTarget() != null) && ((Unit) engine.getEmergencyUnits().get(i)).getLocation() == engine.getEmergencyUnits().get(i).getTarget().getLocation()) {
					if( (engine.getEmergencyUnits().get(i).getTarget() instanceof Citizen &&  (((Citizen) engine.getEmergencyUnits().get(i).getTarget()).getHp() != 0)) || (engine.getEmergencyUnits().get(i).getTarget() instanceof ResidentialBuilding &&  (((ResidentialBuilding) engine.getEmergencyUnits().get(i).getTarget()).getStructuralIntegrity() != 0))) {
					int x = engine.getEmergencyUnits().get(i).getTarget().getLocation().getX();
					int y = engine.getEmergencyUnits().get(i).getTarget().getLocation().getY();
					rescueSimulationView.getGrid()[x][y].setBackground(Color.ORANGE);
					rescueSimulationView.getGrid()[x][y].setCursor(new Cursor(Cursor.HAND_CURSOR));
					rescueSimulationView.getGrid()[x][y].addMouseListener(new java.awt.event.MouseAdapter() {
						    public void mouseEntered(java.awt.event.MouseEvent evt) {
						    	rescueSimulationView.getGrid()[x][y].setBackground(Color.getHSBColor(16, 63, 94));
						    }
						    public void mouseExited(java.awt.event.MouseEvent evt) {
						    	rescueSimulationView.getGrid()[x][y].setBackground(Color.ORANGE);
						    }
						});
					
				}
				}
				
			}
			
			rescueSimulationView.updateBase(engine.getEmergencyUnits());
			rescueSimulationView.updateCycle(engine.getCurrentCycle());
			}
		}
		
		else if(b.getToolTipText() == "Ambulance" || b.getToolTipText() == "Evacuator" || b.getToolTipText() == "Disease" || b.getToolTipText() == "Fire" || b.getToolTipText() == "Gas") {
				
				int index = unitsSelected.indexOf(b);
				unitSelected = engine.getEmergencyUnits().get(index);
				previousTarget = engine.getEmergencyUnits().get(index).getTarget();
				rescueSimulationView.updateInfo(engine.getEmergencyUnits().get(index));
				
		
		}
	}
	
	public static void main(String[] args) throws Exception {
		
		new CommandCenter();
		
	}

}
