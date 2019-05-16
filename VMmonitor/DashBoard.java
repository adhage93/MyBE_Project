package VMmonitor;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;
import javax.swing.border.LineBorder;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import java.awt.Rectangle;
import javax.swing.JComboBox;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.JProgressBar;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dimension;
import java.awt.event.ItemListener;
import java.util.Iterator;
import java.awt.event.ItemEvent;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import java.awt.Canvas;

import org.opennebula.client.Client;
import org.opennebula.client.user.User;
import org.opennebula.client.user.UserPool;

public class DashBoard {

	public static String mainIP = "192.168.0.16";
	
	private JFrame frmCloudForensicData;
	private JTextField login_textField;
	private JPasswordField pass_textField;
	private JTextField vm_name_textField;
	private JTextField vm_id_textField;
	
	public VmOperations VMop1 = new VmOperations();
	String items[] = {"General","Network","Memory"};
	public Client oneClient = null;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) 
	{
		SocketServer s1 = new SocketServer();
		s1.start();
		
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try 
				{
					DashBoard window = new DashBoard();
					window.frmCloudForensicData.setVisible(true);
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
	public DashBoard() 
	{
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() 
	{	
		frmCloudForensicData = new JFrame();
		frmCloudForensicData.setResizable(false);
		frmCloudForensicData.setSize(new Dimension(1200, 516));
		frmCloudForensicData.setBounds(new Rectangle(0, 0, 1200, 675));
		frmCloudForensicData.setFont(new Font("Courier New", Font.BOLD, 12));
		frmCloudForensicData.setTitle("Cloud Forensic Data Collection");
		frmCloudForensicData.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmCloudForensicData.getContentPane().setLayout(null);
		
		JPanel login_panel = new JPanel();
		login_panel.setBackground(new Color(0, 0, 51));
		login_panel.setForeground(Color.BLACK);
		login_panel.setBorder(null);
		login_panel.setBounds(0, 0, 520, 200);
		frmCloudForensicData.getContentPane().add(login_panel);
		login_panel.setLayout(null);
		
		login_textField = new JTextField();
		login_textField.setBorder(new LineBorder(new Color(171, 173, 179), 3));
		login_textField.setBounds(100, 83, 150, 40);
		login_textField.setFont(new Font("Courier 10 Pitch", Font.BOLD, 16));
		login_panel.add(login_textField);
		login_textField.setColumns(10);
		
		pass_textField = new JPasswordField();
		pass_textField.setBorder(new LineBorder(new Color(171, 173, 179), 3));
		pass_textField.setColumns(10);
		pass_textField.setBounds(356, 83, 150, 40);
		pass_textField.setFont(new Font("Courier 10 Pitch", Font.BOLD, 16));
		login_panel.add(pass_textField);
		
		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setForeground(Color.WHITE);
		lblUsername.setFont(new Font("Courier New", Font.BOLD, 15));
		lblUsername.setBounds(10, 83, 90, 40);
		login_panel.add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setForeground(Color.WHITE);
		lblPassword.setFont(new Font("Courier New", Font.BOLD, 15));
		lblPassword.setBounds(266, 83, 90, 40);
		login_panel.add(lblPassword);
		
		JLabel lblCloud = new JLabel(" Cloud Forensic Data Collection ");
		lblCloud.setForeground(Color.WHITE);
		lblCloud.setFont(new Font("Courier New", Font.BOLD, 27));
		lblCloud.setHorizontalAlignment(SwingConstants.CENTER);
		lblCloud.setBounds(0, 0, 520, 70);
		login_panel.add(lblCloud);
		
		JButton btnLogin = new JButton("Login");
		
		btnLogin.setBounds(266, 146, 242, 40);
		login_panel.add(btnLogin);
		btnLogin.setBorder(new LineBorder(new Color(0, 0, 0), 3));
		btnLogin.setFont(new Font("Courier New", Font.BOLD, 22));
		
		JSeparator separator = new JSeparator();
		separator.setBounds(0, 70, 520, 2);
		login_panel.add(separator);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(0, 136, 520, 2);
		login_panel.add(separator_1);
		
		JPanel host_panel = new JPanel();
		host_panel.setBackground(new Color(192, 192, 192));
		host_panel.setBorder(null);
		host_panel.setBounds(520, 0, 680, 200);
		frmCloudForensicData.getContentPane().add(host_panel);
		host_panel.setLayout(null);
		
		JLabel lblHostDetails = new JLabel("Host Details:");
		lblHostDetails.setFont(new Font("Courier New", Font.BOLD, 23));
		lblHostDetails.setBounds(12, 13, 200, 30);
		host_panel.add(lblHostDetails);
		
		/*
		 * JTextPane jtxt=new JTextPane();
		jtxt.setFont(new Font("Courier 10 Pitch", Font.BOLD, 16));
		JScrollPane host_textPane = new JScrollPane(jtxt);
		host_textPane.setFont(new Font("Courier 10 Pitch", Font.BOLD, 16));
		host_textPane.setBounds(12, 48, 300, 139);
		temp_panel.add(host_textPane);
		 * */
		
		JTextPane host_textPane = new JTextPane();
		host_textPane.setFont(new Font("Courier 10 Pitch", Font.BOLD, 16));
		host_textPane.setBounds(12, 48, 300, 139);
		host_panel.add(host_textPane);
		
		JSeparator separator_6 = new JSeparator();
		separator_6.setBackground(new Color(0, 0, 0));
		separator_6.setOrientation(SwingConstants.VERTICAL);
		separator_6.setBounds(324, 0, 2, 200);
		host_panel.add(separator_6);
		//System.out.println(Math.round(((100*usedmem)/maxmem)));
		JLabel lblMemory = new JLabel("Memory:");
		lblMemory.setFont(new Font("Courier New", Font.BOLD, 23));
		lblMemory.setBounds(338, 13, 100, 30);
		host_panel.add(lblMemory);
		
		JLabel lblCpu = new JLabel("CPU:");
		lblCpu.setFont(new Font("Courier New", Font.BOLD, 23));
		lblCpu.setBounds(338, 101, 100, 30);
		host_panel.add(lblCpu);
		
		JProgressBar mem_progressBar = new JProgressBar();
		mem_progressBar.setForeground(new Color(25, 25, 112));
		mem_progressBar.setBounds(338, 48, 330, 40);
		host_panel.add(mem_progressBar);
		
		JProgressBar cpu_progressBar = new JProgressBar();
		cpu_progressBar.setForeground(new Color(25, 25, 112));
		cpu_progressBar.setBounds(338, 135, 330, 40);
		host_panel.add(cpu_progressBar);
		
		JPanel vm_list_panel = new JPanel();
		vm_list_panel.setBackground(new Color(192, 192, 192));
		vm_list_panel.setBorder(null);
		vm_list_panel.setBounds(0, 200, 520, 477);
		frmCloudForensicData.getContentPane().add(vm_list_panel);
		vm_list_panel.setLayout(null);
		
		JButton btnGetVmInformation = new JButton("Get VM Information");
		
		btnGetVmInformation.setFont(new Font("Courier New", Font.BOLD, 22));
		btnGetVmInformation.setBorder(new LineBorder(new Color(0, 0, 0), 3));
		btnGetVmInformation.setBounds(117, 13, 260, 60);
		vm_list_panel.add(btnGetVmInformation);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setBackground(Color.BLACK);
		separator_2.setForeground(Color.GRAY);
		separator_2.setOrientation(SwingConstants.VERTICAL);
		separator_2.setBounds(260, 84, 8, 393);
		vm_list_panel.add(separator_2);
		
		JLabel lblLiveVms = new JLabel("Live VMs:");
		lblLiveVms.setFont(new Font("Courier New", Font.BOLD, 23));
		lblLiveVms.setBounds(22, 98, 200, 30);
		vm_list_panel.add(lblLiveVms);
		
		JLabel lblDeadVms = new JLabel("Dead VMs:");
		lblDeadVms.setFont(new Font("Courier New", Font.BOLD, 23));
		lblDeadVms.setBounds(282, 98, 200, 30);
		vm_list_panel.add(lblDeadVms);
		
		JList<String> live_list = new JList<String>();
		live_list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		live_list.setFont(new Font("Courier New", Font.BOLD, 22));
		live_list.setBounds(22, 141, 220, 323);
		vm_list_panel.add(live_list);
		
		JSeparator separator_3 = new JSeparator();
		separator_3.setBackground(Color.BLACK);
		separator_3.setForeground(Color.GRAY);
		separator_3.setBounds(0, 83, 520, 2);
		vm_list_panel.add(separator_3);
		
		JList<String> dead_list = new JList<String>();
		dead_list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		dead_list.setFont(new Font("Courier New", Font.BOLD, 22));
		dead_list.setBounds(280, 141, 220, 323);
		vm_list_panel.add(dead_list);
		
		JPanel vm_panel = new JPanel();
		vm_panel.setBackground(new Color(0, 0, 51));
		vm_panel.setBorder(null);
		vm_panel.setBounds(520, 200, 680, 477);
		frmCloudForensicData.getContentPane().add(vm_panel);
		vm_panel.setLayout(null);
		
		JTextPane mainDisplay_textPane = new JTextPane();
		mainDisplay_textPane.setFont(new Font("Courier 10 Pitch", Font.BOLD, 25));
		mainDisplay_textPane.setBounds(14, 144, 645, 320);
		vm_panel.add(mainDisplay_textPane);
		mainDisplay_textPane.setEditable(false);
		
		JLabel lblVmName = new JLabel("VM Name:");
		lblVmName.setForeground(Color.WHITE);
		lblVmName.setFont(new Font("Courier New", Font.BOLD, 18));
		lblVmName.setBounds(12, 13, 120, 30);
		vm_panel.add(lblVmName);
		
		vm_name_textField = new JTextField();
		vm_name_textField.setFont(new Font("Courier New", Font.BOLD, 18));
		vm_name_textField.setEditable(false);
		vm_name_textField.setBounds(128, 15, 183, 30);
		vm_panel.add(vm_name_textField);
		vm_name_textField.setColumns(40);
		
		JSeparator separator_4 = new JSeparator();
		separator_4.setOrientation(SwingConstants.VERTICAL);
		separator_4.setBackground(Color.GRAY);
		separator_4.setBounds(325, 13, 2, 120);
		vm_panel.add(separator_4);
		
		JLabel lblVmId = new JLabel("VM ID:");
		lblVmId.setForeground(Color.WHITE);
		lblVmId.setFont(new Font("Courier New", Font.BOLD, 18));
		lblVmId.setBounds(12, 56, 120, 30);
		vm_panel.add(lblVmId);
		
		vm_id_textField = new JTextField();
		vm_id_textField.setFont(new Font("Courier New", Font.BOLD, 18));
		vm_id_textField.setEditable(false);
		vm_id_textField.setColumns(40);
		vm_id_textField.setBounds(128, 58, 183, 30);
		vm_panel.add(vm_id_textField);
		
		JComboBox<String> info_comboBox = new JComboBox<String>(items);
		info_comboBox.setFont(new Font("Courier New", Font.BOLD, 18));
		info_comboBox.setBounds(128, 99, 183, 30);
		vm_panel.add(info_comboBox);
		info_comboBox.setEnabled(false);
		
		JLabel lblSelectInfo = new JLabel("Select :");
		lblSelectInfo.setForeground(Color.WHITE);
		lblSelectInfo.setFont(new Font("Courier New", Font.BOLD, 18));
		lblSelectInfo.setBounds(12, 99, 120, 30);
		vm_panel.add(lblSelectInfo);
		
		/*
		JLabel lblSmartAgentStatus = new JLabel("Smart Agent Status:");
		lblSmartAgentStatus.setForeground(Color.WHITE);
		lblSmartAgentStatus.setFont(new Font("Courier New", Font.BOLD, 18));
		lblSmartAgentStatus.setBounds(339, 99, 220, 30);
		vm_panel.add(lblSmartAgentStatus);
		
		Canvas status_light_canvas = new Canvas();
		status_light_canvas.setBounds(554, 99, 30, 30);
		vm_panel.add(status_light_canvas);
		
		JLabel label = new JLabel("ON");
		label.setForeground(Color.WHITE);
		label.setFont(new Font("Courier New", Font.BOLD, 25));
		label.setBounds(601, 101, 58, 30);
		vm_panel.add(label);
		*/
		//BUTTON FUNCTIONS HERE-----
				
				btnLogin.addActionListener(new ActionListener() 
				{
					public void actionPerformed(ActionEvent e) 
					{
						//JFrame login_msg = new JFrame();
						String secret = login_textField.getText()+":"+pass_textField.getPassword();
						if(secret.length()>1)
						{
							oneClient = VMop1.login(secret);
							User us=new User(4, oneClient);
							System.out.println(User.login(oneClient,"temp123","asd",60).getMessage());
							UserPool up=new UserPool(oneClient);
							Iterator<User>it=up.iterator();
							while(it.hasNext())
							{
								System.out.println("1");
								User u=it.next();
								System.out.println(u.info().getMessage());
								
								
							}
							if(oneClient!=null)
							{
								HostOperations temp=new HostOperations();
								host_textPane.setText(temp.getDetails(oneClient));
								mem_progressBar.setStringPainted(true); 
								mem_progressBar.setValue(temp.getUsedMem());
								cpu_progressBar.setStringPainted(true);
								cpu_progressBar.setValue(temp.getUsedCPU());
								VMop1.createMongo(oneClient);	
							}
							else
							{
								JOptionPane.showMessageDialog(null,"Incorrect Username or password!");
							}
						}
						else
						{
							JOptionPane.showMessageDialog(null,"Username or password cannot be null!");
						}
					}
				});
				
				btnGetVmInformation.addActionListener(new ActionListener() 
				{
					public void actionPerformed(ActionEvent e)
					{			
						btnGetVmInformation.setText("Refresh VM Info");
						live_list.removeAll();
						dead_list.removeAll();
						//status_light_canvas.setBackground(new Color(255, 255, 255));
						
						
						DefaultListModel<String> dlive = new DefaultListModel<String>();
						DefaultListModel<String> ddead = new DefaultListModel<String>();
						
						String []s = VMop1.getLiveVms(oneClient);
						String []d = VMop1.getDeadVms(oneClient);
						
						for(int i=0; i<s.length; i++)
						{
							dlive.addElement(s[i]);
						}
						for(int i=0; i<d.length; i++)
						{
							ddead.addElement(d[i]);
						}
						
						live_list.setModel(dlive);
						dead_list.setModel(ddead);
						
						mainDisplay_textPane.setText(null);
						vm_name_textField.setText(null);
						vm_id_textField.setText(null);
						
						info_comboBox.setEnabled(true);
					}
				});
				
				live_list.addListSelectionListener(new ListSelectionListener() 
				{
					public void valueChanged(ListSelectionEvent e) 
					{
						String val = live_list.getSelectedValue();
						if(val!=null)
						{	
							int id = Integer.parseInt(val.substring(val.indexOf("-")+1,val.length()));
							vm_name_textField.setText(val);
							vm_id_textField.setText(val.substring(val.indexOf("-")+1,val.length()));
							mainDisplay_textPane.setText(VmOperations.getGeneralInfo(id));
							/*
							if(AgentComm.RESULT == true)
							{
								status_light_canvas.setBackground(new Color(0, 255, 0));
							}
							else
							{
								status_light_canvas.setBackground(new Color(255, 0, 0));
							}
							*/
						}
					}
				});
				
				dead_list.addListSelectionListener(new ListSelectionListener() 
				{
					public void valueChanged(ListSelectionEvent e) 
					{
						String val = dead_list.getSelectedValue();
						if(val!=null)
						{	
							int id = Integer.parseInt(val.substring(val.indexOf("-")+1,val.length()));
							vm_name_textField.setText(val);
							vm_id_textField.setText(val.substring(val.indexOf("-")+1,val.length()));
							mainDisplay_textPane.setText(VmOperations.getGeneralInfo(id));
							/*
							if(AgentComm.RESULT == true)
							{
								status_light_canvas.setBackground(new Color(0, 255, 0));
							}
							else
							{
								status_light_canvas.setBackground(new Color(255, 0, 0));
							}
							*/
						}
					}
				});
				
				info_comboBox.addItemListener(new ItemListener() 
				{
					public void itemStateChanged(ItemEvent e) 
					{
						String vname = vm_name_textField.getText();
						if(vname!=null)
						{
							String val = (String) info_comboBox.getSelectedItem();
							int id = Integer.parseInt(vname.substring(vname.indexOf("-")+1,vname.length()));
							if(val.equals("General"))
							{
								mainDisplay_textPane.setText(VmOperations.getGeneralInfo(id));
							}
							else if(val.equals("Network"))
							{
								mainDisplay_textPane.setText(VmOperations.getNetworkInfo(id));
							}
							else if(val.equals("Memory"))
							{
								mainDisplay_textPane.setText(VmOperations.getMemoryInfo(id));
							}
						}
					}
				});
	}
}