package gui;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import com.fazecast.jSerialComm.SerialPort;

import arduino.ArduinoCOM;
import instrument.Constants;
import instrument.Instrument;
import instrument.Settings;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;

import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import javax.swing.JCheckBox;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.awt.event.ItemEvent;

public class SerialConfig extends JDialog {
	/*TODO: FIX Port comboboxes to remove selected items
	*/
	
	private final JLabel lblInstrument = new JLabel("Instrument");
	private final JLabel lblPort = new JLabel("Port");
	private final JLabel lblSendTest = new JLabel("Send Test");
	private final JComboBox<String> portSnare = new JComboBox<String>();
	private final JComboBox<String> portCHiHat = new JComboBox<String>();
	private final JComboBox<String> portOHiHat = new JComboBox<String>();
	private final JComboBox<String> portBass = new JComboBox<String>();
	private final JComboBox<String> portToms = new JComboBox<String>();
	private final JComboBox<String> portCymbals = new JComboBox<String>();
	private final JButton sendTestSnare = new JButton("SEND");
	private final JButton sendTestCHiHat = new JButton("SEND");
	private final JButton sendTestOHiHat = new JButton("SEND");
	private final JButton sendTestBass = new JButton("SEND");
	private final JButton sendTestToms = new JButton("SEND");
	private final JButton sendTestCymbals = new JButton("SEND");
	private final JPanel buttonPane = new JPanel();
	private final JButton ok = new JButton("OK");
	private final JButton cancel = new JButton("Cancel");
	private final JLabel lblSnare = new JLabel("Snare");
	private final JLabel lblCHiHat = new JLabel("Closed Hi-Hat");
	private final JLabel lblOHiHat = new JLabel("Open Hi-Hat");
	private final JLabel lblBass = new JLabel("Bass");
	private final JLabel lblToms = new JLabel("Low Tom (A) High Tom (B)");
	private final JLabel lblRideaCrash = new JLabel("Ride (A) Crash (B)");
	private final JButton autoFind = new JButton("AUTO SELECT");
	
	private  List<Instrument> instruments = new ArrayList<Instrument>();
	private Boolean isAPIUpdate;
	
	public SerialConfig() {
		initialize();
	}
	
	private void initialize() {
		setTitle("Serial Port Configuration");
		setBounds(100, 100, 430, 280);
		setResizable(false);
		getContentPane().setLayout(new MigLayout("", "[198.00][110.00][]", "[][30.00][][][][][][][]"));
		lblSnare.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		getContentPane().add(lblSnare, "cell 0 1,alignx center,aligny center");
		lblCHiHat.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		getContentPane().add(lblCHiHat, "cell 0 2,alignx center,aligny center");
		lblOHiHat.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		getContentPane().add(lblOHiHat, "cell 0 3,alignx center,aligny center");
		lblBass.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		getContentPane().add(lblBass, "cell 0 4,alignx center,aligny center");
		lblToms.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		getContentPane().add(lblToms, "cell 0 5,alignx center,aligny center");
		lblRideaCrash.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		getContentPane().add(lblRideaCrash, "cell 0 6,alignx center");
		getContentPane().add(buttonPane, "cell 0 8,alignx left,aligny top");
		getContentPane().add(lblInstrument, "cell 0 0,alignx center,aligny center");
		getContentPane().add(lblPort, "cell 1 0,alignx center,aligny center");
		getContentPane().add(lblSendTest, "cell 2 0,alignx center,aligny center");
		getContentPane().add(portSnare, "cell 1 1,growx");
		getContentPane().add(portCHiHat, "cell 1 2,growx");
		getContentPane().add(portOHiHat, "cell 1 3,growx");
		getContentPane().add(portBass, "cell 1 4,growx");
		getContentPane().add(portToms, "cell 1 5,growx");
		getContentPane().add(portCymbals, "cell 1 6,growx");
		getContentPane().add(sendTestSnare, "cell 2 1");
		getContentPane().add(sendTestCHiHat, "cell 2 2");
		getContentPane().add(sendTestOHiHat, "cell 2 3");
		getContentPane().add(sendTestBass, "cell 2 4");
		getContentPane().add(sendTestToms, "cell 2 5");
		getContentPane().add(sendTestCymbals, "cell 2 6");
		getContentPane().add(autoFind, "cell 1 8,alignx center,aligny center");
		
		lblInstrument.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblPort.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblSendTest.setFont(new Font("Tahoma", Font.BOLD, 11));
		portSnare.setEnabled(true);
		portCHiHat.setEnabled(true);
		portOHiHat.setEnabled(true);
		portBass.setEnabled(true);
		portToms.setEnabled(true);
		portCymbals.setEnabled(true);
		sendTestSnare.setEnabled(false);
		sendTestCHiHat.setEnabled(false);
		sendTestOHiHat.setEnabled(false);
		sendTestBass.setEnabled(false);
		sendTestToms.setEnabled(false);
		sendTestCymbals.setEnabled(false);
		
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		ok.setActionCommand("OK");
		buttonPane.add(ok);
		getRootPane().setDefaultButton(ok);
		cancel.setActionCommand("Cancel");
		buttonPane.add(cancel);
		
		portSnare.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if (isAPIUpdate) return;
				//fillComboBox(Constants.SNARE, cBoxPortSnare.getSelectedItem().toString());
				if (portSnare.getSelectedItem().toString().startsWith("COM")) {
					sendTestSnare.setEnabled(true);
				} else {
					sendTestSnare.setEnabled(false);
				}
			}
		});
		portCHiHat.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if (isAPIUpdate) return;
				//fillComboBox(Constants.CLOSED_HI_HAT,cBoxPortCHiHat.getSelectedItem().toString());
				if (portCHiHat.getSelectedItem().toString().startsWith("COM")) {
					sendTestCHiHat.setEnabled(true);
				} else {
					sendTestCHiHat.setEnabled(false);
				}
			}
		});
		portOHiHat.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if (isAPIUpdate) return;
				//fillComboBox(Constants.OPEN_HI_HAT,cBoxPortOHiHat.getSelectedItem().toString());
				if (portOHiHat.getSelectedItem().toString().startsWith("COM")) {
					sendTestOHiHat.setEnabled(true);
				} else {
					sendTestOHiHat.setEnabled(false);
				}
			}
		});
		portBass.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if (isAPIUpdate) return;
				//fillComboBox(Constants.BASS,cBoxPortBass.getSelectedItem().toString());
				if (portBass.getSelectedItem().toString().startsWith("COM")) {
					sendTestBass.setEnabled(true);
				} else {
					sendTestBass.setEnabled(false);
				}
			}
		});
		portToms.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if (isAPIUpdate) return;
				//fillComboBox(Constants.TOMS,cBoxPortToms.getSelectedItem().toString());
				if (portToms.getSelectedItem().toString().startsWith("COM")) {
					sendTestToms.setEnabled(true);
				} else {
					sendTestToms.setEnabled(false);
				}
			}
		});
		portCymbals.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if (isAPIUpdate) return;
				//fillComboBox(Constants.CYMBALS,cBoxPortCymbals.getSelectedItem().toString());
				if (portCymbals.getSelectedItem().toString().startsWith("COM")) {
					sendTestCymbals.setEnabled(true);
				} else {
					sendTestCymbals.setEnabled(false);
				}
			}
		});
		
		sendTestSnare.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (portSnare.getSelectedItem().toString().startsWith("COM")
						) {
					sendCOMTest(portSnare.getSelectedItem().toString());
				}
			}
		});
		sendTestCHiHat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (portCHiHat.getSelectedItem().toString().startsWith("COM")) {
					sendCOMTest(portCHiHat.getSelectedItem().toString());
				}
			}
		});
		sendTestOHiHat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (portOHiHat.getSelectedItem().toString().startsWith("COM")) {
					sendCOMTest(portOHiHat.getSelectedItem().toString());
				}
			}
		});
		sendTestBass.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (portBass.getSelectedItem().toString().startsWith("COM")){
					sendCOMTest(portBass.getSelectedItem().toString());
				}
			}
		});
		sendTestToms.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (portToms.getSelectedItem().toString().startsWith("COM")){
					sendCOMTest(portToms.getSelectedItem().toString());
				}
			}
		});
		sendTestCymbals.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (portCymbals.getSelectedItem().toString().startsWith("COM")){
					sendCOMTest(portCymbals.getSelectedItem().toString());
				}
			}
		});
		
		autoFind.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				findPorts();
			}
		});
		
		ok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				//Clear Instruments list to create new list
				getInstrumentList().clear();
				
				if (portSnare.getSelectedItem().toString().startsWith("COM")) {
					Instrument instrument = new Instrument(Constants.SNARE, portSnare.getSelectedItem().toString());
					getInstrumentList().add(instrument);
				}
				
				if (portCHiHat.getSelectedItem().toString().startsWith("COM")) {
					Instrument instrument = new Instrument(Constants.CLOSED_HI_HAT, portCHiHat.getSelectedItem().toString());
					getInstrumentList().add(instrument);
				}

				if (portOHiHat.getSelectedItem().toString().startsWith("COM")) {
					Instrument instrument = new Instrument(Constants.OPEN_HI_HAT, portOHiHat.getSelectedItem().toString());
					getInstrumentList().add(instrument);
				}
				
				if (portBass.getSelectedItem().toString().startsWith("COM")) {
					Instrument instrument = new Instrument(Constants.BASS, portBass.getSelectedItem().toString());
					getInstrumentList().add(instrument);
				}
				
				if (portToms.getSelectedItem().toString().startsWith("COM")) {
					Instrument instrument = new Instrument(Constants.TOMS, portToms.getSelectedItem().toString());
					getInstrumentList().add(instrument);
				}
				if (portCymbals.getSelectedItem().toString().startsWith("COM")) {
					Instrument instrument = new Instrument(Constants.CYMBALS, portCymbals.getSelectedItem().toString());
					getInstrumentList().add(instrument);
				}
				if (getInstrumentList().size() > 0) Settings.saveSettings(instruments);
				dispose();

			}
		});
		
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		
		importSavedInstruments();
		this.pack();
	}
	
	private JComboBox<String> getComboBox(Integer instrumentID) {
		switch (instrumentID) {
			case 0:
				return this.portSnare;
			case 1:
				return this.portCHiHat;
			case 2:
				return this.portOHiHat;
			case 3:
				return this.portBass;
			case 4:
				return this.portToms;
			case 5:
				return this.portCymbals;
			default:
				return null;
		}		
	}
	
	private JButton getTestButton(Integer instrumentID) {
		switch (instrumentID) {
			case 0:
				return this.sendTestSnare;
			case 1:
				return this.sendTestCHiHat;
			case 2:
				return this.sendTestOHiHat;
			case 3:
				return this.sendTestBass;
			case 4:
				return this.sendTestToms;
			case 5:
				return this.sendTestCymbals;
			default:
				return null;
		}		
	}
	
	private List<Instrument> getInstrumentList(){
		return this.instruments;
	}
	
	private void fillComboBox(Integer instrumentID, String selectedItem) {
		boolean matchFound = false;
		isAPIUpdate = true;
		getComboBox(instrumentID).removeAllItems();
		getComboBox(instrumentID).addItem("");
		for (SerialPort port : SerialPort.getCommPorts()) {
			getComboBox(instrumentID).addItem(port.getSystemPortName());
			if (port.getSystemPortName().equals(selectedItem)) matchFound = true;
		}
		if (matchFound) {
			getComboBox(instrumentID).setSelectedItem(selectedItem);
			getTestButton(instrumentID).setEnabled(true);
		}
		else {
			getComboBox(instrumentID).setSelectedItem("");
			getTestButton(instrumentID).setEnabled(false);
		}
		isAPIUpdate = false;
	}
	
	private void fillAllComboBoxes() {
		fillComboBox(Constants.SNARE,"");
		fillComboBox(Constants.CLOSED_HI_HAT,"");
		fillComboBox(Constants.OPEN_HI_HAT,"");
		fillComboBox(Constants.BASS,"");
		fillComboBox(Constants.TOMS,"");
		fillComboBox(Constants.CYMBALS,"");
	}
	
	private boolean isPortSelected(SerialPort port) {
		// Checks if a given port is already selected when re-populating port comboboxes
		if (portSnare.getSelectedItem().toString().equals(port.getSystemPortName())) {
			return true;
		}else if (portCHiHat.getSelectedItem().toString().equals(port.getSystemPortName())) {
			return true;
		}else if (portOHiHat.getSelectedItem().toString().equals(port.getSystemPortName())) {
			return true;
		}else if (portBass.getSelectedItem().toString().equals(port.getSystemPortName())) {
			return true;
		}else if (portToms.getSelectedItem().toString().equals(port.getSystemPortName())) {
			return true;
		}else if (portCymbals.getSelectedItem().toString().equals(port.getSystemPortName())) {
			return true;
		}else {
			return false;
		}
		
	}
	
	private void sendCOMTest(String portToTest) {
		ArduinoCOM port = new ArduinoCOM(portToTest);
		port.openConnection();
		port.serialWrite('A');
		port.closeConnection();
	}

	private void importSavedInstruments() {
		fillAllComboBoxes();
		
		for (Instrument instrument : Settings.loadSettings()) {
			switch (instrument.getInstrumentID()) {
				case 0: fillComboBox(Constants.SNARE,instrument.getPort().getPortDescription());
					break;
				case 1: fillComboBox(Constants.CLOSED_HI_HAT,instrument.getPort().getPortDescription());
					break;
				case 2: fillComboBox(Constants.OPEN_HI_HAT,instrument.getPort().getPortDescription());
					break;
				case 3: fillComboBox(Constants.BASS,instrument.getPort().getPortDescription());
					break;
				case 4: fillComboBox(Constants.TOMS,instrument.getPort().getPortDescription());
					break;
				case 5: fillComboBox(Constants.CYMBALS,instrument.getPort().getPortDescription());
					break;
			}
		}
	}
	
	private void findPorts() {
		String portReturn = null;
		String systemPortName = null;
		for (SerialPort port : SerialPort.getCommPorts()) {
			systemPortName = port.getSystemPortName();
			ArduinoCOM portToTest = new ArduinoCOM(systemPortName);
			portToTest.openConnection();
			portToTest.serialWrite('R');
			portReturn = portToTest.serialRead();
			portToTest.closeConnection();
			int instrumentID = Integer.parseInt(portReturn.substring(0, 1));
			if (instrumentID >= 0 && instrumentID <= 5) {
				fillComboBox(instrumentID, systemPortName);
			}
			
		}
	}
}
