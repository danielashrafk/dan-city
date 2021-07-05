package view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SpringLayout;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;

import controller.CommandCenter;
import model.disasters.Collapse;
import model.disasters.Disaster;
import model.disasters.Fire;
import model.disasters.GasLeak;
import model.disasters.Infection;
import model.disasters.Injury;
import model.infrastructure.ResidentialBuilding;
import model.people.Citizen;
import model.people.CitizenState;
import model.units.Ambulance;
import model.units.DiseaseControlUnit;
import model.units.Evacuator;
import model.units.FireTruck;
import model.units.PoliceUnit;
import model.units.Unit;
import model.units.UnitState;
import simulation.Address;
import simulation.Simulatable;

public class RescueSimulationView extends JFrame {
	private JPanel pnlCells;
	private ArrayList<JButton> btnsCit;
	private ArrayList<JButton> btnsBuild;
	private JScrollPane logPnl1;
	private JScrollPane logPnl2;
	private JScrollPane logPnl3;
	private JScrollPane logPnl4;
	private JTextArea txtLog3;
	private JTextArea txtLog4;
	private JButton[][] grid = new JButton[10][10];
	private JLabel cycle;

	private JPanel unitsPnl;
	private JScrollPane txtCellsScroll;
	private JButton nextCycle;
	private JTextArea txtCells;
	private JTextArea txtLog;
	private JPanel infoPnl;
	JScrollPane infoScroll;
	private JTextArea txtLog2;
	public RescueSimulationView() {
		txtLog3 = new JTextArea();
		txtLog2 = new JTextArea();
		setLayout(null);
		getContentPane().setBackground(Color.DARK_GRAY);
		 this.setExtendedState(JFrame.MAXIMIZED_BOTH);
//		infoPnl = new JPanel();
//		infoPnl.setLayout(null);
//		infoPnl.setBounds(950,50,152,500);
//		infoPnl.setOpaque(true);
//		infoPnl.setBackground(Color.DARK_GRAY);
		
//		add(infoPnl);
		btnsCit= new ArrayList<>();
		btnsBuild= new ArrayList<>();
		
		setTitle("Rescue Simulation");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		pnlCells = new JPanel();
		pnlCells.setBackground(Color.darkGray);
		pnlCells.setLayout(new GridLayout(0, 10));
		pnlCells.setBounds(300,50,600,600);
		nextCycle = new JButton();
		nextCycle.setText("Next Cycle");
		nextCycle.setForeground(Color.getHSBColor(16, 63, 94));
		nextCycle.setBounds(90,90,100,50);
		nextCycle.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
		nextCycle.setBackground(Color.DARK_GRAY);
		Border nbored = BorderFactory.createLineBorder(Color.getHSBColor(16, 63, 94));
		nextCycle.setBorder(nbored);
		nextCycle.setCursor(new Cursor(Cursor.HAND_CURSOR));
		 nextCycle.addMouseListener(new java.awt.event.MouseAdapter() {
			    public void mouseEntered(java.awt.event.MouseEvent evt) {
			    	nextCycle.setBackground(Color.BLACK);
			    }
			    public void mouseExited(java.awt.event.MouseEvent evt) {
			    	nextCycle.setBackground(Color.DARK_GRAY);
			    }
			});
		add(nextCycle);
		add(pnlCells);
		txtCells = new JTextArea();
		
		txtCells.setLayout(null);
		txtCells.setEditable(false);
		txtCells.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
		txtCells.setBounds(0, 0, 140, 200);
		txtCells.setBackground(Color.DARK_GRAY);
		txtCells.setForeground(Color.getHSBColor(16, 63, 94));
		
		
		
        
		JLabel info =  new JLabel();
		info.setLayout(null);
		info.setText("Information Panel:");
		info.setBounds(950, 50, 150, 10);
		info.setForeground(Color.getHSBColor(16, 63, 94));
		add(info);
//		infoPnl.add(txtCells);
		infoScroll = new JScrollPane(txtCells);
		infoScroll.setBounds(950,80,160,370);
		Border bored2 = BorderFactory.createLineBorder(Color.DARK_GRAY);
		infoScroll.setBorder(bored2);
		infoScroll.setVisible(true);
		this.getContentPane().add(infoScroll);
		
		

		unitsPnl = new JPanel();
		unitsPnl.setSize(200,100);
		unitsPnl.setBackground(Color.DARK_GRAY);
		//Color.getHSBColor(16, 63, 94)
		unitsPnl.setBounds(30,200,210,400);
		JLabel unitsLab = new JLabel("Available Units:");
		unitsLab.setForeground(Color.getHSBColor(16, 63, 94));
		unitsLab.setBounds(5,5,100,10);
		unitsPnl.add(unitsLab);
		JLabel unitsTxt = new JLabel();
		unitsTxt.setBounds(10,10,80,100);
		unitsPnl.add(unitsTxt);
		add(unitsPnl);
		
		txtLog = new JTextArea();
		txtLog.setLayout(null);
		txtLog.setEditable(false);
		txtLog.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
		txtLog.setBounds(0, 0, 140, 200);
		txtLog.setBackground(Color.DARK_GRAY);
		txtLog.setForeground(Color.getHSBColor(16, 63, 94));
		
		logPnl1 = new JScrollPane(txtLog);
		logPnl1.setBounds(1130,110,140,100);
		Border bored3 = BorderFactory.createLineBorder(Color.DARK_GRAY);
		logPnl1.setBorder(bored3);
		logPnl1.setVisible(true);
		this.getContentPane().add(logPnl1);
		
		JLabel Notifications =  new JLabel();
		Notifications.setLayout(null);
		Notifications.setText("Notifications Panel:");
		Notifications.setBounds(1130, 50, 150, 10);
		Notifications.setForeground(Color.getHSBColor(16, 63, 94));
		add(Notifications);
		JLabel deadCitizens =  new JLabel();
		deadCitizens.setLayout(null);
		deadCitizens.setText("Dead Citizens:");
		deadCitizens.setBounds(1130, 80, 100, 20);
		deadCitizens.setForeground(Color.getHSBColor(16, 63, 94));
		add(deadCitizens);
		
		txtLog2.setLayout(null);
		txtLog2.setEditable(false);
		txtLog2.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
		txtLog2.setBounds(0, 0, 600, 200);
		txtLog2.setBackground(Color.DARK_GRAY);
		txtLog2.setForeground(Color.getHSBColor(16, 63, 94));
		JLabel executedDisasters =  new JLabel();
		executedDisasters.setLayout(null);
		executedDisasters.setText("Executed Disasters:");
		executedDisasters.setBounds(1130, 220, 200, 20);
		executedDisasters.setForeground(Color.getHSBColor(16, 63, 94));
		add(executedDisasters);
		logPnl2 = new JScrollPane(txtLog2);
		logPnl2.setBounds(1130,250,200,200);
		Border bored4 = BorderFactory.createLineBorder(Color.DARK_GRAY);
		logPnl2.setBorder(bored4);
		logPnl2.setVisible(true);
		this.getContentPane().add(logPnl2);
		
		
		
		txtLog3.setLayout(null);
		txtLog3.setEditable(false);
		txtLog3.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
		txtLog3.setBounds(0, 0, 600, 200);
		txtLog3.setBackground(Color.DARK_GRAY);
		txtLog3.setForeground(Color.getHSBColor(16, 63, 94));
		JLabel activeDisasters =  new JLabel();
		activeDisasters.setLayout(null);
		activeDisasters.setText("Active Disasters:");
		activeDisasters.setBounds(1130, 470, 200, 20);
		activeDisasters.setForeground(Color.getHSBColor(16, 63, 94));
		add(activeDisasters);
		logPnl3 = new JScrollPane(txtLog3);
		logPnl3.setBounds(1130,500,200,200);
		Border bored5 = BorderFactory.createLineBorder(Color.DARK_GRAY);
		logPnl3.setBorder(bored5);
		logPnl3.setVisible(true);
		this.getContentPane().add(logPnl3);
		txtLog4 = new JTextArea();
		txtLog4.setLayout(null);
		txtLog4.setEditable(false);
		txtLog4.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
		txtLog4.setBounds(0, 0, 600, 200);
		txtLog4.setBackground(Color.DARK_GRAY);
		txtLog4.setForeground(Color.getHSBColor(16, 63, 94));
		JLabel Base =  new JLabel();
		Base.setLayout(null);
		Base.setText("Base:");
		Base.setBounds(950, 470, 200, 20);
		Base.setForeground(Color.getHSBColor(16, 63, 94));
		add(Base);
		logPnl4 = new JScrollPane(txtLog4);
		logPnl4.setBounds(950,490,160,150);
		Border bored6 = BorderFactory.createLineBorder(Color.DARK_GRAY);
		logPnl4.setBorder(bored6);
		logPnl4.setVisible(true);
		this.getContentPane().add(logPnl4);
		
		
		
		 cycle = new JLabel();
		 cycle.setBounds(35,600,200,20);
		 cycle.setForeground(Color.getHSBColor(16, 63, 94));
		
		 add(cycle);
		
		
		
		
		
		
		
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				if(i == 0 && j == 0) {
					
					JButton b = new JButton();
					b.setBackground(Color.yellow);
					b.setText("Base");
					Border bored = BorderFactory.createLineBorder(Color.LIGHT_GRAY);
					b.setBorder(bored);
					int i1 = i;
					int j1 = j;
					grid[i][j] = b;
					grid[i][j].setCursor(new Cursor(Cursor.HAND_CURSOR));
					 grid[i][j].addMouseListener(new java.awt.event.MouseAdapter() {
						    public void mouseEntered(java.awt.event.MouseEvent evt) {
						    	grid[i1][j1].setBackground(Color.getHSBColor(16, 63, 94));
						    }
						    public void mouseExited(java.awt.event.MouseEvent evt) {
						    	grid[i1][j1].setBackground(Color.yellow);
						    }
						});
					pnlCells.add(b);
					
				}
				
				else {
				JButton b = new JButton();
				b.setBackground(Color.darkGray);
				Border bored = BorderFactory.createLineBorder(Color.LIGHT_GRAY);
				b.setBorder(bored);
				int i1 = i;
				int j1 = j;
				grid[i][j] = b;
				grid[i][j].setCursor(new Cursor(Cursor.HAND_CURSOR));
				 grid[i][j].addMouseListener(new java.awt.event.MouseAdapter() {
					    public void mouseEntered(java.awt.event.MouseEvent evt) {
					    	grid[i1][j1].setBackground(Color.getHSBColor(16, 63, 94));
					    }
					    public void mouseExited(java.awt.event.MouseEvent evt) {
					    	grid[i1][j1].setBackground(Color.DARK_GRAY);
					    }
					});
				pnlCells.add(b);

			}
			}
		}

	}

	public JTextArea getTxtCells() {
		return txtCells;
	}

	public JButton getNextCycle() {
		return nextCycle;
	}

	public void addSimulatable( int x, int y,CommandCenter c, JButton b) {
		
		
		 grid[x][y].addActionListener(c);
		  grid[x][y].setToolTipText(b.getText());
		  if(b.getText() == "C") { 
			  btnsCit.add(grid[x][y]);
			 
			  grid[x][y].setOpaque(true);
			  ImageIcon iconCit = new ImageIcon(getClass().getResource("dead.png"));
				Image img = iconCit.getImage() ;  
				Image newimg = img.getScaledInstance(48, 48,  java.awt.Image.SCALE_SMOOTH ) ;  
				iconCit = new ImageIcon( newimg );
				grid[x][y].setIcon(iconCit);
				
			  
		  }
		  
		  else if(b.getText() == "B") {
			  btnsBuild.add(grid[x][y]);
			  grid[x][y].setOpaque(true);
			  ImageIcon iconBuild = new ImageIcon(getClass().getResource("buildings.png"));
				Image img = iconBuild.getImage() ;  
				Image newimg = img.getScaledInstance(48, 48,  java.awt.Image.SCALE_SMOOTH ) ;  
				iconBuild = new ImageIcon( newimg );
				grid[x][y].setIcon(iconBuild);
				
		  }
		
	}

	public JButton[][] getGrid() {
		return grid;
	}

	public ArrayList<JButton> getBtnsCit() {
		return btnsCit;
	}

	public ArrayList<JButton> getBtnsBuild() {
		return btnsBuild;
	}

	public void updateInfo(Simulatable s) {
		String info = "";
		if (s instanceof ResidentialBuilding) {

			
			info += "" + "Location: " + "(" + ((ResidentialBuilding) s).getLocation().getX() + "," + ((ResidentialBuilding) s).getLocation().getY() + ")" + "\n";
			info += "" + "Structural Integrity: " + ((ResidentialBuilding) s).getStructuralIntegrity() + "\n";
			info += "" + "Fire Damage: " + ((ResidentialBuilding) s).getFireDamage() + "\n";
			info += "" + "Gas Level: " + ((ResidentialBuilding) s).getGasLevel() + "\n";
			info += "" + "Foundation Damage: " + ((ResidentialBuilding) s).getFoundationDamage() + "\n";
			if (((ResidentialBuilding) s).getDisaster() != null) {
				if(((ResidentialBuilding) s).getDisaster() instanceof Collapse)
				info += "Disaster: " + "Collapse" + "\n";
				else if(((ResidentialBuilding) s).getDisaster() instanceof Fire)
					info += "Disaster: " + "Fire" + "\n";
				else if(((ResidentialBuilding) s).getDisaster() instanceof GasLeak)
					info += "Disaster: " + "GasLeak" + "\n";
			}
			info += "" + "Number Of Occupants: " + ((ResidentialBuilding) s).getOccupants().size() + "\n";
			info += "-------------------------------- \n";

			for (int i = 0; i < ((ResidentialBuilding) s).getOccupants().size(); i++) {
				Citizen c = ((ResidentialBuilding) s).getOccupants().get(i);
				int h = i + 1;
				info += "" + "Occupant " + h + " info: " + "\n";
				info += "" + "Location: " + "(" + ((Citizen) c).getLocation().getX() + "," + ((Citizen) c).getLocation().getY() + ")" + "\n";
				info += "Name: " + c.getName() + "\n";
				info += "Age: " + c.getAge() + "\n";
				info += "National ID: " + c.getNationalID() + "\n";
				info += "HP: " + c.getHp() + "\n";
				info += "Blood Loss: " + c.getBloodLoss() + "\n";
				info += "Toxicity: " + c.getToxicity() + "\n";
				if(((Citizen) c).getState() == CitizenState.DECEASED)
					info += "State: " + "Deceased" + "\n";
				else if(((Citizen) c).getState() == CitizenState.RESCUED)
					info += "State: " + "Rescued" + "\n";
				else if(((Citizen) c).getState() == CitizenState.IN_TROUBLE)
					info += "State: " + "In Trouble" + "\n";
				else if(((Citizen) c).getState() == CitizenState.SAFE)
					info += "State: " + "Safe" + "\n";
				if (c.getDisaster() != null) {
					if(c.getDisaster() instanceof Infection)
					info += "Disaster: " + "Infection" + "\n";
					else if(c.getDisaster() instanceof Injury)
						info += "Disaster: " + "Injury" + "\n";
					
				}
				
				info += "-------------------------------- \n";

				

				
				}
			

			

		}

		else if (s instanceof Citizen) {
			
			info += "" + "Location: " + "(" + ((Citizen) s).getLocation().getX() + "," + ((Citizen) s).getLocation().getY() + ")" + "\n";
			info += "Name: " + ((Citizen) s).getName() + "\n";
			info += "Age: " + ((Citizen) s).getAge() + "\n";
			info += "National ID: " + ((Citizen) s).getNationalID() + "\n";
			info += "HP: " + ((Citizen) s).getHp() + "\n";
			info += "Blood Loss: " + ((Citizen) s).getBloodLoss() + "\n";
			info += "Toxicity: " + ((Citizen) s).getToxicity() + "\n";
			if(((Citizen) s).getState() == CitizenState.DECEASED)
				info += "State: " + "Deceased" + "\n";
			else if(((Citizen) s).getState() == CitizenState.RESCUED)
				info += "State: " + "Rescued" + "\n";
			else if(((Citizen) s).getState() == CitizenState.IN_TROUBLE)
				info += "State: " + "In Trouble" + "\n";
			else if(((Citizen) s).getState() == CitizenState.SAFE)
				info += "State: " + "Safe" + "\n";
			if (((Citizen) s).getDisaster() != null) {
				if(((Citizen) s).getDisaster() instanceof Infection)
				info += "Disaster: " + "Infection" + "\n";
				else if(((Citizen) s).getDisaster() instanceof Injury)
					info += "Disaster: " + "Injury" + "\n";
				
			}
			
			info += "-------------------------------- \n";


		}
		
		else if(s instanceof Unit) {
			
				info+= "ID: " + ((Unit) s).getUnitID() + "\n";
				if(s instanceof Ambulance)
					info+= "Unit Type: Ambulance" + "\n";
				else if(s instanceof DiseaseControlUnit)
					info+= "Unit Type: Disease Control Unit" + "\n";
				else if(s instanceof Evacuator)
					info+= "Unit Type: Evacuator" + "\n";
				else if(s instanceof FireTruck)
					info+= "Unit Type: Fire Truck" + "\n";
				else
					info+= "Unit Type: Gas Control Unit" + "\n";
				info+= "Location: " + "(" + ((Unit) s).getLocation().getX() + "," + ((Unit) s).getLocation().getY() + ")" + "\n";
				info+= "Steps Per Cycle: " + ((Unit) s).getStepsPerCycle() + "\n";
				if(((Unit) s).getState() == UnitState.IDLE)
					info += "State: " + "Idle" + "\n";
				else if(((Unit) s).getState() == UnitState.RESPONDING)
					info += "State: " + "Responding" + "\n";
				else if(((Unit) s).getState() == UnitState.TREATING)
					info += "State: " + "Treating" + "\n";
				
				if(((Unit) s).getTarget() != null) {
					info += "-------------------------------- \n";

				if(((Unit) s).getTarget() instanceof Citizen) {
					info+= "Target: " +  "\n";
					info += "Type: Citizen " +  "\n";
					info += "" + "Location: " + "(" + ((Citizen) ((Unit) s).getTarget()).getLocation().getX() + "," + ((Citizen) ((Unit) s).getTarget()).getLocation().getY() + ")" + "\n";
					info += "-------------------------------- \n";
				}
				else if(((Unit) s).getTarget() instanceof ResidentialBuilding) {
					info+= "Target: " +  "\n";
					info += "Type: Residential Building " +  "\n";
					info += "" + "Location: " + "(" + ((ResidentialBuilding) ((Unit) s).getTarget()).getLocation().getX() + "," + ((ResidentialBuilding) ((Unit) s).getTarget()).getLocation().getY() + ")" + "\n";
					info += "-------------------------------- \n";
				}
				}
				
				
			

				if(s instanceof Evacuator) {
					
					info += "Number of Passengers: " + ((PoliceUnit) s).getPassengers().size() + "\n";
					info += "-------------------------------- \n";

					
					for(int i = 0; i < ((PoliceUnit) s).getPassengers().size(); i++) {
						int h = i+1;
						info += "" + "Passenger " + h + " info: " + "\n";
						info += "" + "Location: " + "(" + ((Unit) s).getLocation().getX() + "," + ((Unit) s).getLocation().getY() + ")" + "\n";
						info += "Name: " + ((PoliceUnit) s).getPassengers().get(i).getName() + "\n";
						info += "Age: " + ((PoliceUnit) s).getPassengers().get(i).getAge()  + "\n";
						info += "National ID: " + ((PoliceUnit) s).getPassengers().get(i).getNationalID()  + "\n";
						info += "HP: " + ((PoliceUnit) s).getPassengers().get(i).getHp()  + "\n";
						info += "Blood Loss: " + ((PoliceUnit) s).getPassengers().get(i).getBloodLoss()  + "\n";
						info += "Toxicity: " + ((PoliceUnit) s).getPassengers().get(i).getToxicity() + "\n";
						if(((PoliceUnit) s).getPassengers().get(i).getState()  == CitizenState.DECEASED)
							info += "State: " + "Deceased" + "\n";
						else if(((PoliceUnit) s).getPassengers().get(i).getState() == CitizenState.RESCUED)
							info += "State: " + "Rescued" + "\n";
						else if(((PoliceUnit) s).getPassengers().get(i).getState()== CitizenState.IN_TROUBLE)
							info += "State: " + "In Trouble" + "\n";
						else if(((PoliceUnit) s).getPassengers().get(i).getState() == CitizenState.SAFE)
							info += "State: " + "Safe" + "\n";
						if (((PoliceUnit) s).getPassengers().get(i).getDisaster() != null) {
							if(((PoliceUnit) s).getPassengers().get(i).getDisaster() instanceof Infection)
							info += "Disaster: " + "Infection" + "\n";
							else if(((PoliceUnit) s).getPassengers().get(i).getDisaster() instanceof Injury)
								info += "Disaster: " + "Injury" + "\n";
						
						
					}
						info += "-------------------------------- \n";

				}
					

		}
		}

		txtCells.setText(info);
	}

	public void updateLog(ArrayList<Citizen> deadCitizens, ArrayList<ResidentialBuilding> collapsedBuildings, ArrayList<Disaster> executedDisasters, ArrayList<Disaster> activeDisasters) {
		String log = "";
		String log2 = "";
		String log3 = "";
		for(int i = 0; i < deadCitizens.size();i++) {
			log2 += "" + "Location: " + "(" + deadCitizens.get(i).getLocation().getX() + "," + deadCitizens.get(i).getLocation().getY() + ")" + "\n";
			log2 += "-------------------------------- \n";
			
			
		}
		
		for(int i = 0; i < collapsedBuildings.size();i++) {
			if(collapsedBuildings.get(i).getOccupants().size() != 0) {
			log2 += "" + "Location: " + "(" + collapsedBuildings.get(i).getLocation().getX() + "," + collapsedBuildings.get(i).getLocation().getY() + ")" + "\n";
			log2 += "-------------------------------- \n";
			
			}
			
		}
		
		
		
		for(int i = 0; i < executedDisasters.size();i++) {
			
			if(executedDisasters.get(i) instanceof Collapse)
				log += "Type: " + "Collapse" + "\n";
				else if(executedDisasters.get(i) instanceof Fire)
					log += "Type: " + "Fire" + "\n";
				else if(executedDisasters.get(i) instanceof GasLeak)
					log += "Type: " + "GasLeak" + "\n";
				else if(executedDisasters.get(i) instanceof Infection)
				log += "Type: " + "Infection" + "\n";
				else if(executedDisasters.get(i) instanceof Injury)
					log += "Type: " + "Injury" + "\n";
			if(executedDisasters.get(i).getTarget() instanceof Citizen) 
				log += "Target Name: " + ((Citizen) executedDisasters.get(i).getTarget()).getName() + "\n";
				
			else 
				log += "Target Name: Residential Building \n";
			log += "-------------------------------- \n";
			
			
		}
		
		
		
		for(int i = 0; i < activeDisasters.size();i++) {
			if((activeDisasters.get(i).getTarget() instanceof Citizen && ((Citizen) activeDisasters.get(i).getTarget()).getState() != CitizenState.DECEASED) || (activeDisasters.get(i).getTarget() instanceof ResidentialBuilding && ((ResidentialBuilding) activeDisasters.get(i).getTarget()).getStructuralIntegrity() != 0)) {
			if(activeDisasters.get(i) instanceof Collapse)
				log3 += "Type: " + "Collapse" + "\n";
				else if(activeDisasters.get(i) instanceof Fire)
					log3 += "Type: " + "Fire" + "\n";
				else if(activeDisasters.get(i) instanceof GasLeak)
					log3 += "Type: " + "GasLeak" + "\n";
				else if(activeDisasters.get(i) instanceof Infection)
				log3 += "Type: " + "Infection" + "\n";
				else if(activeDisasters.get(i) instanceof Injury)
					log3 += "Type: " + "Injury" + "\n";
			if(activeDisasters.get(i).getTarget() instanceof Citizen) 
				log3 += "Target Name: " + ((Citizen) activeDisasters.get(i).getTarget()).getName() + "\n";
				
			else 
				log3 += "Target Name: Residential Building \n";
			log3 += "-------------------------------- \n";
			}
			
		}
		
		txtLog3.setText(log3);
		txtLog2.setText(log);
		
		
		
		
		txtLog.setText(log2);
	}
	
	
	
	public void addUnit(JButton b) {
		
		unitsPnl.add(b);
		
	}
	
	public void Dead(Simulatable s, int x, int y) {
		
		if(s instanceof Citizen) {
			
			ImageIcon iconCit = new ImageIcon(getClass().getResource("RIP.png"));
			Image img = iconCit.getImage() ;  
			Image newimg = img.getScaledInstance(48, 48,  java.awt.Image.SCALE_SMOOTH ) ;  
			iconCit = new ImageIcon( newimg );
			grid[x][y].setIcon(iconCit);
			grid[x][y].setBackground(Color.BLACK);
			grid[x][y].setCursor(new Cursor(Cursor.HAND_CURSOR));
			 grid[x][y].addMouseListener(new java.awt.event.MouseAdapter() {
				    public void mouseEntered(java.awt.event.MouseEvent evt) {
				    	grid[x][y].setBackground(Color.getHSBColor(16, 63, 94));
				    }
				    public void mouseExited(java.awt.event.MouseEvent evt) {
				    	grid[x][y].setBackground(Color.BLACK);
				    }
				});
			
		}
		
		else if(s instanceof ResidentialBuilding) {
			
			ImageIcon iconCit = new ImageIcon(getClass().getResource("boom.png"));
			Image img = iconCit.getImage() ;  
			Image newimg = img.getScaledInstance(48, 48,  java.awt.Image.SCALE_SMOOTH ) ;  
			iconCit = new ImageIcon( newimg );
			grid[x][y].setIcon(iconCit);
			grid[x][y].setBackground(Color.BLACK);
			grid[x][y].setCursor(new Cursor(Cursor.HAND_CURSOR));
			 grid[x][y].addMouseListener(new java.awt.event.MouseAdapter() {
				    public void mouseEntered(java.awt.event.MouseEvent evt) {
				    	grid[x][y].setBackground(Color.getHSBColor(16, 63, 94));
				    }
				    public void mouseExited(java.awt.event.MouseEvent evt) {
				    	grid[x][y].setBackground(Color.BLACK);
				    }
				});
			
		}
		
		
	}
	
	public void updateBase(ArrayList<Unit> Units) {
		String baseLog = "";
		for(int i = 0; i < Units.size(); i++) {
			if(Units.get(i).getLocation().getX() == 0 && Units.get(i).getLocation().getX() == 0) {
				if(Units.get(i) instanceof Ambulance) {
					baseLog+= "Unit Type: Ambulance" + "\n";
				baseLog += "Unit ID: " + Units.get(i).getUnitID() + "\n";
				}
				else if(Units.get(i) instanceof DiseaseControlUnit) {
					baseLog+= "Unit Type: Disease Control Unit" + "\n";
				baseLog += "Unit ID: " + Units.get(i).getUnitID() + "\n";
				}
				else if(Units.get(i) instanceof Evacuator) {
					baseLog+= "Unit Type: Evacuator" + "\n";
				baseLog += "Unit ID: " + Units.get(i).getUnitID() + "\n";
				}
				else if(Units.get(i) instanceof FireTruck) {
					baseLog+= "Unit Type: Fire Truck" + "\n";
					baseLog += "Unit ID: " + Units.get(i).getUnitID() + "\n";
				}
				else {
					baseLog+= "Unit Type: Gas Control Unit" + "\n";
				baseLog += "Unit ID: " + Units.get(i).getUnitID() + "\n";
				}
				baseLog += "-------------------------------- \n";
			}
			
			

			
		}
		
		
		
		txtLog4.setText(baseLog);
		
	}
	
	public void updateCycle(int c) {

		String log = "Current Cycle: " + c;
		cycle.setText(log);
	}

}
