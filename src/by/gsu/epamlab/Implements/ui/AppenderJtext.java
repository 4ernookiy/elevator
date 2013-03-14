package by.gsu.epamlab.Implements.ui;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.spi.LoggingEvent;

public class AppenderJtext extends AppenderSkeleton{

	private JTextArea text;
	private JScrollPane pane;
	
	public AppenderJtext(JTextArea text) {
		super();
		this.text = text;
		setLayout(new PatternLayout("[%d{mm:ss,SSS}] [%-12.12t] - %m%n"));
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean requiresLayout() {
		// TODO Auto-generated method stub
		return false;
	}

	public void setScrollPane(JScrollPane pane){
		this.pane = pane;
	}
	
	@Override
	protected void append(LoggingEvent arg0) {
		text.append(layout.format(arg0));
		int m = pane.getVerticalScrollBar().getMaximum();
		pane.getVerticalScrollBar().setValue(m+1000);
	}

}
