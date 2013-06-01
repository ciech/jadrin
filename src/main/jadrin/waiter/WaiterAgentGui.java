package main.jadrin.waiter;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class WaiterAgentGui extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private WaiterAgent myAgent;
	
	WaiterAgentGui(WaiterAgent a) {
		super(a.getLocalName());
		
		myAgent = a;
		
		JPanel p = new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.PAGE_AXIS));
	
		getContentPane().add(p);
		
		JTextArea question = new JTextArea(
	                "Zadaj kelnerowi pytanie.."
	        );
		 question.setFont(new Font("Serif", Font.ITALIC, 12)); 
		 question.setBorder(
	            BorderFactory.createCompoundBorder(
	                BorderFactory.createCompoundBorder(
	                                BorderFactory.createTitledBorder("Czego chciałbyś się dowiedzieć ?"),
	                                BorderFactory.createEmptyBorder(5,5,5,5)),
	                                question.getBorder()));
	 
	   	 JTextArea response = new JTextArea(
	                ""
	        );
	   	 	response.setFont(new Font("Serif", Font.ITALIC, 12));
	   	 	response.setLineWrap(true);
	   	    response.setWrapStyleWord(true);      
	   	    response.setPreferredSize(new Dimension(250, 250));
	   	    response.setBorder(
	            BorderFactory.createCompoundBorder(
	                BorderFactory.createCompoundBorder(
	                                BorderFactory.createTitledBorder("Kelner mówi:"),
	                                BorderFactory.createEmptyBorder(5,5,5,5)),
	                                response.getBorder()));
	   	    response.setEditable(false);
	   	  p.add(response);
	   	    p.add(question);
	   
		
		JButton addButton = new JButton("Wyślij");
		addButton.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				try {

						// agent action
				}
				catch (Exception e) {
					JOptionPane.showMessageDialog(WaiterAgentGui.this, "Invalid values. "+e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE); 
				}
			}
		} );
		p = new JPanel();
		p.add(addButton);
		getContentPane().add(p, BorderLayout.SOUTH);
		
		addWindowListener(new	WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				myAgent.doDelete();
			}
		} );
		
		setResizable(false);
	}
	
	public void showGui() {
		pack();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int centerX = (int)screenSize.getWidth() / 2;
		int centerY = (int)screenSize.getHeight() / 2;
		setLocation(centerX - getWidth() / 2, centerY - getHeight() / 2);
		super.setVisible(true);
	}	
}


