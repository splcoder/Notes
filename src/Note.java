import java.io.Serializable;
import java.util.Calendar;
import java.util.concurrent.atomic.AtomicLong;

public class Note implements Comparable<Note>, Serializable {
	private static final long serialVersionUID = 1L;

	//-------------------------------------------------------------
	private static AtomicLong lastId = new AtomicLong( 0 );
	private static long getNewId() {
		return lastId.getAndIncrement();
	}
	// Special functions to be used only by NotesManager
	public static void setLastId( long id ) {
		lastId.set( id );
	}
	public static long getLastId() {
		return lastId.get();
	}
	//-------------------------------------------------------------

	private long id;
	private long creationDate;
	private long modificationDate;
	private String text;
	private Priority priority;

	public static long getTimeMilis() {
		return Calendar.getInstance().getTimeInMillis();
	}
	private String getDate( long date, boolean showHour ) {
		String sDate = "";
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis( date );
		int month = c.get( Calendar.MONTH ) + 1;
		int day = c.get( Calendar.DATE );
		sDate += c.get( Calendar.YEAR ) + "-" + (month < 10 ? "0" + month : month) + "-" + (day < 10 ? "0" + day : day);
		if( showHour ) {
			int hours = c.get( Calendar.HOUR_OF_DAY );
			int minutes = c.get( Calendar.MINUTE );
			int seconds = c.get( Calendar.SECOND );
			sDate += " " + (hours < 10 ? "0" + hours : hours) + ":" + (minutes < 10 ? "0" + minutes : minutes) + ":" + (seconds < 10 ? "0" + seconds : seconds);
		}
		return sDate;
	}
	/*public final int getElapsedYearsTillOpeningDate() {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis( openingDate );
		return Calendar.getInstance().get( Calendar.YEAR ) - c.get( Calendar.YEAR );
	}*/
	public Note() {
		id = getNewId();
		this.creationDate = getTimeMilis();
		this.modificationDate = getTimeMilis();
	}
	public Note( String text, Priority priority ) {
		this();
		this.text = text;
		this.priority = priority;
	}
	public long getId() {
		return id;
	}
	public long getCreationDate() {
		return creationDate;
	}
	public long getModificationDate() {
		return modificationDate;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
		this.modificationDate = getTimeMilis();
	}
	public Priority getPriority() {
		return priority;
	}
	public void setPriority(Priority priority) {
		this.priority = priority;
		this.modificationDate = getTimeMilis();
	}
	@Override
	public Note clone() {
		return new Note( text, priority );
	}
	@Override
	public String toString() {
		return "Note [id=" + id + ", creationDate=" + getDate( creationDate, true ) + ", modificationDate=" + getDate( modificationDate, true )
				+ ", text=" + text + ", priority=" + priority + "]";
	}
	@Override
	public int compareTo( Note note ) {
		return (int)(creationDate - note.creationDate);		// UPS long to int !!!
	}
	@Override
	public boolean equals( Object obj ){
		if( this == obj )	return true;
		if( obj == null || getClass() != obj.getClass() )	return false;

		Note other = (Note) obj;
		if( text == null ){
			if( other.text != null )	return false;
		} else if( ! text.equals( other.text ) )
			return false;
		return true;
	}	
	
}
