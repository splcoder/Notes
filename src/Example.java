import java.io.IOException;
import java.util.ArrayList;

public class Example {

	public static void main(String[] args) {
		try {
			NotesManager nm = new NotesManager();
			Note n1 = new Note( "Nota 1", Priority.URGENT );
			Note n2 = new Note( "Nota 2", Priority.IMPORTANT );
			Note n3 = new Note( "Nota 3", Priority.NORMAL );
			nm.add( n1 );
			nm.add( n2 );
			nm.add( n3 );
			ArrayList<Note> aNotes = nm.getAllNotes();
			for( int i = 0; i < aNotes.size(); i++ ) {
				System.out.println( "NOTE " + i + ":" + aNotes.get( i ).toString() );
			}
			// CLOSE IT !!!. It is required for saving the data to a file
			nm.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
