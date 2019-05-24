import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NotesManager {
	public static final String FILE_NAME_FOR_ID = "notes_manager_id.txt";
	public static final String FILE_NAME = "notes_manager.txt";

	private Map<Long, Note> mapNotes = new HashMap<Long, Note>();

	// Reader
	private FileInputStream fis;
	private ObjectInputStream ois;
	// Writer
	private FileOutputStream fos;
	private ObjectOutputStream oos;
	
	//---------------------------------------------
	// Id
	private static void writeIdToFile() throws IOException {
		FileOutputStream fosId = new FileOutputStream( FILE_NAME_FOR_ID, false );
		fosId.write( Converter.longToBytes( Note.getLastId() ) );
		fosId.close();
	}
	private static long readIdFromFile() throws IOException {
		byte[] bytes = new byte[ Long.BYTES ];
		FileInputStream fisId = new FileInputStream( FILE_NAME_FOR_ID );
		fisId.read( bytes );
		fisId.close();
		return Converter.bytesToLong( bytes );
	}
	//---------------------------------------------

	private Note readNextNote() throws IOException, ClassNotFoundException {
		try {
			if( ois != null )	return (Note)ois.readObject();
		} catch( EOFException e ) {
			// The EOFException must be catch here and don't thrown
		}
		return null;
	}

	/*private void appendNoteToFile( Note note ) throws IOException {
		fos = new FileOutputStream( FILE_NAME, true );
		oos = new ObjectOutputStream( fos );
		if( oos != null ) {
			oos.writeObject( note );
			oos.close();
		}
	}*/
	
	private void writeAllToFile() throws IOException {
		fos = new FileOutputStream( FILE_NAME, false );
		oos = new ObjectOutputStream( fos );
		if( oos != null ) {
			for( long key : mapNotes.keySet() ) {
				oos.writeObject( mapNotes.get( key ) );
			}
			oos.close();
		}
	}
	/**
	 * The constructor will read all the notes saved
	 * @throws IOException 
	 */
	public NotesManager() throws IOException {
		try {
			fis = new FileInputStream( FILE_NAME );
			ois = new ObjectInputStream( fis );
			//
			Note note = null;
			while( (note = readNextNote()) != null ) {
				mapNotes.put( note.getId(), note );
			}
		} catch( FileNotFoundException e ) {
			Console.show( "The file must be created", true );
		} catch( Exception e ) {
			e.printStackTrace();
		}
		// Close
		if( ois != null )	ois.close();
	}
	/**
	 * Before turning off the app all the data must be saved to file
	 * So it is required the call of this function
	 * @throws IOException
	 */
	public void close() throws IOException {
		writeAllToFile();
	}
	/**
	 * Write a new note
	 * @param note
	 * @throws IOException
	 */
	public void add( Note note ){
		mapNotes.put( note.getId(), note );
	}
	/**
	 * Delete an existing note
	 * @param note
	 * @return
	 * @throws IOException
	 */
	public boolean del( Note note ){
		if( mapNotes.containsKey( note.getId() ) ) {
			mapNotes.remove( note.getId() );
			return true;
		}
		return false;
	}
	public boolean update( Note note ){
		if( mapNotes.containsKey( note.getId() ) ) {
			mapNotes.put( note.getId(), note );
			return true;
		}
		// It is new !!!
		mapNotes.put( note.getId(), note );
		return false;
	}
	public ArrayList<Note> getAllNotes(){
		ArrayList<Note> aNotes = new ArrayList<>();
		for( long key : mapNotes.keySet() ) {
			aNotes.add( mapNotes.get( key ) );
		}
		return aNotes;
	}
}
