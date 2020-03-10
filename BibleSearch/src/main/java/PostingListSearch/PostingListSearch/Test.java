package PostingListSearch.PostingListSearch;

/*

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;



public class Test 
{
	
    public static void main( String[] args ) throws IOException
    {
    	String s = "word&&djp3.net/";
    	System.out.println(s.indexOf("&&"));
    	String[] test = s.split("&&");
    	System.out.println(test[0]);
    	System.out.println(test[1]);
    	
    }

}
*/
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.text.*;

import org.json.simple.parser.ParseException;

import javax.swing.event.*;
import javax.swing.GroupLayout.*;

public class Test{
	
  public static void main(String[] args) {

	  //*
    JFrame f = new JFrame("A JFrame");
    f.setSize(600, 300);
    f.setLocation(300,200);
    JPanel searchPanel = new JPanel();
    searchPanel.setLayout(new FlowLayout());
    JTextField textField = new JTextField(20);
    searchPanel.add(textField);
    final JButton submit = new JButton("Enter Search");
    searchPanel.add(submit);
    f.getContentPane().add(searchPanel, BorderLayout.NORTH);
    final JTextArea textArea = new JTextArea(10, 40);
    f.getContentPane().add(BorderLayout.CENTER, textArea);
    
    submit.addActionListener(new ActionListener() {
    	@Override
        public void actionPerformed(ActionEvent e) {
    		
    		textArea.setText("");
            String query = textField.getText();
            try {
            	String[] output = new String[1];
            	output[0]=query;
				ArrayList<String> results = App.main(output);
				if (results.size()==0) {
					textArea.append("No results found... Try a different search");
				}
				else {
					textArea.append("Your search for "+query+" has returned the following "+results.size()+" results:\n");
				}
				for (String url : results) {
					textArea.append(url+"\n");
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
            
            
        }
    });
    f.setVisible(true);
    //*/
	

  }

}
