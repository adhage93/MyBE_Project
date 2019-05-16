package EvidenceMngr;

import java.awt.EventQueue;
import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class EvidenceMngr {

	private JFrame frame;
	MngrOperations op1 = new MngrOperations();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) 
	{
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try 
				{
					EvidenceMngr window = new EvidenceMngr();
					window.frame.setVisible(true);
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public EvidenceMngr() 
	{
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() 
	{
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel select_vm_lbl = new JLabel("Select VM :");
		select_vm_lbl.setBounds(20, 20, 100, 30);
		frame.getContentPane().add(select_vm_lbl);
		
		JComboBox<String> select_vm_cb = new JComboBox<String>();
		select_vm_cb.setBounds(125, 20, 200, 30);
		frame.getContentPane().add(select_vm_cb);
		
		JComboBox<String> collections_cb = new JComboBox<String>();
		collections_cb.setBounds(125, 60, 200, 30);
		frame.getContentPane().add(collections_cb);
		
		JLabel lblCollections = new JLabel("Collections :");
		lblCollections.setBounds(20, 60, 100, 30);
		frame.getContentPane().add(lblCollections);
		
		
		Iterator<String> i1 = op1.getDatabases();
		while(i1.hasNext())
		{
			select_vm_cb.addItem(i1.next().toString());
			
		}
		
//METHODS EVENT HANDLERS--------------------------------------------------------------		
		
		select_vm_cb.addItemListener(new ItemListener() 
		{
			public void itemStateChanged(ItemEvent e) 
			{
				Iterator<String> i1 = op1.getCollections(select_vm_cb.getSelectedItem().toString());
				collections_cb.removeAllItems();
				while(i1.hasNext())
				{
					collections_cb.addItem(i1.next().toString());
				}
			}
		});
		
	}
}
