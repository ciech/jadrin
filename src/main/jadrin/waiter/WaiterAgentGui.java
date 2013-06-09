package main.jadrin.waiter;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;


public final class WaiterAgentGui extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5658252133971369582L;

	private WaiterAgent waiter;
	
	private JPanel p;
	private JTextArea question;
	private JTextArea response;
	
	WaiterAgentGui(WaiterAgent a) {
		super(a.getLocalName());
		waiter = a;
		p = new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.PAGE_AXIS));
		getContentPane().add(p);
		question = new JTextArea("", 1, 50);
		question.setLineWrap(true);
		question.requestFocusInWindow();
		question.addKeyListener(new KeyAdapter(){
			public void keyReleased(KeyEvent e) {
				if (KeyEvent.VK_ENTER == e.getKeyCode()) {
					if (question.isEditable())
					{
						JTextArea textArea = (JTextArea) e.getSource();
		            	String text = textArea.getText();
		            	text = text.substring(0,text.length()-1);
		               	if (!text.isEmpty() && !text.contains(new String("\n")))
						{
							response.append("Pytanie: " + text + "\n");
							question.setText("");
							setEditable(false);
							waiter.analizeQuestion(text);
						}
						else
						{
							question.setText("");
						}
					}
				}
	        }
		});
		
		JScrollPane  questionPane = new JScrollPane(question);
		questionPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		questionPane.setPreferredSize(new Dimension(600, 70));
		questionPane.setBorder(
			BorderFactory.createCompoundBorder(
				BorderFactory.createCompoundBorder(
					BorderFactory.createTitledBorder("Czego chciałbyś się dowiedzieć ?"),
	                BorderFactory.createEmptyBorder(5,5,5,5)),
	                questionPane.getBorder()));
	 
	   	response = new JTextArea("");
   	 	response.setLineWrap(true);
   	    response.setWrapStyleWord(false); 
   	    response.setEditable(false);
   	    
   	    JScrollPane  responsePane = new JScrollPane(response);
	   	responsePane.setPreferredSize(new Dimension(600, 500));
	   	responsePane.setBorder(
	   		BorderFactory.createCompoundBorder(
	   			BorderFactory.createCompoundBorder(
	   				BorderFactory.createTitledBorder("Historia rozmowy:"),
	   				BorderFactory.createEmptyBorder(5,5,5,5)),
	                responsePane.getBorder()));

	   	p.add(responsePane);
	   	p.add(questionPane);
	   
		
		setResizable(false);
	}
	
	public void setResponse(String response)
	{
	   this.response.append("Kelner: " + response + "\n");
	}
	
	public void setEditable(boolean editable)
	{
		this.question.setEnabled(editable);
		
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


