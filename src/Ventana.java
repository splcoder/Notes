import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import java.awt.Component;
import java.awt.Color;
import javax.swing.SwingConstants;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JList;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.GridBagConstraints;
import javax.swing.ListSelectionModel;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JMenu;

public class Ventana extends JFrame {
	
	private static NotesManager notesManager = null;
	private static void startNotesManager() {
		try {
			notesManager = new NotesManager();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if( notesManager != null ) {
			Note n1 = new Note( "Nota 1", Priority.URGENT );
			Note n2 = new Note( "Nota 2", Priority.IMPORTANT );
			Note n3 = new Note( "Nota 3", Priority.NORMAL );
			notesManager.add( n1 );
			notesManager.add( n2 );
			notesManager.add( n3 );
			ArrayList<Note> aNotes = notesManager.getAllNotes();
			for( int i = 0; i < aNotes.size(); i++ ) {
				System.out.println( "NOTE " + i + ":" + aNotes.get( i ).toString() );
			}
		}
	}

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Ventana frame = new Ventana();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Ventana() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmCreateNewNote = new JMenuItem("Create new note");
		mnFile.add(mntmCreateNewNote);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JList list = new JList();
		list.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		GridBagConstraints gbc_list = new GridBagConstraints();
		gbc_list.fill = GridBagConstraints.BOTH;
		gbc_list.gridx = 0;
		gbc_list.gridy = 0;
		contentPane.add(list, gbc_list);
		
		//-----------------------------------------
		// Close
		WindowListener exitListener = new WindowAdapter() {
			@Override
			public void windowClosing( WindowEvent e ){
				/*int confirm = JOptionPane.showOptionDialog( null, "Are You Sure to Close Application ?", "Exit Confirmation", JOptionPane.YES_NO_OPTION, 
																JOptionPane.QUESTION_MESSAGE, null, null, null );
				if( confirm == JOptionPane.YES_OPTION ){
					System.exit( 0 );
				}*/
				if( notesManager != null ) {
					// CLOSE IT !!!. It is required for saving the data to a file
					try {
						notesManager.close();
					} catch ( IOException ioe ) {
						// TODO Auto-generated catch block
						ioe.printStackTrace();
					}
				}
			}
		};
		this.addWindowListener( exitListener );
		//-----------------------------------------
		
		startNotesManager();
	}

}
