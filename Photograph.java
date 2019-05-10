
//hw 5 Haoyu Li, hl6de

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Photograph implements Comparable<Photograph> {

	private String caption; // caption of the photograph
	private String filename; // filename of the photograph
	private String dateTaken; // string containing the date the photograph was taken, in YYYY-MM-DD
	private int rating; // the rating of the photograph on a scale from 0 to 5
	private File imageFile;  // the file of images

	/**
	 * constructor
	 * 
	 * @param caption  the caption to be added to the photograph
	 * @param filename the file name input from the user
	 */
	public Photograph(String caption, String filename) {
		this.caption = caption;
		this.filename = filename;
		this.dateTaken = "0000-00-00";
		this.rating = 0;
		this.imageFile = new File(filename);
	}

	/**
	 * constructor
	 * 
	 * @param filename
	 * @param caption
	 * @param dateTaken
	 * @param rating
	 */
	public Photograph(String caption, String filename, String dateTaken, int rating) {
		if (isCorrectRatingFormat(rating) && isCorrectDateFormat(dateTaken)) {
			// validate all the input first before constructing the obj
			this.filename = filename;
			this.caption = caption;
			this.dateTaken = dateTaken;
			this.rating = rating;
			this.imageFile = new File(filename);
		} 
	}

	/**
	 * getCaption: accessor for caption
	 * 
	 * @return reference to caption
	 */
	public String getCaption() {
		return this.caption;
	}

	/**
	 * getFilename: accessor for filename
	 * 
	 * @return: reference to filename
	 */
	public String getFilename() {
		return this.filename;
	}

	/**
	 * getDateTaken: accessor for date taken
	 * 
	 * @return reference to date taken
	 */
	public String getDateTaken() {
		return this.dateTaken;
	}

	/**
	 * getRating: accessor for rating
	 * 
	 * @return reference to the rating of a photo
	 */
	public int getRating() {
		return this.rating;
	}
	
	/**
	 * getImageFile: accessor for the image file
	 * 
	 * @return reference to the image file
	 */
	public File getImageFile() {
		return this.imageFile;
	}

	/**
	 * isCorrectRatingFormat: helper function to test if the input rating int is in
	 * correct format
	 * 
	 * @param rating input rating to be tested
	 * @return true if the rating input is 0-5, inclusive
	 */
	public static boolean isCorrectRatingFormat(int rating) {
		return (rating >= 0 && rating <= 5);
	}

	/**
	 * correctDateFormat: helper function to test if the input date string is in
	 * correct format
	 * 
	 * @param date input date string to be tested
	 * @return true if the input matches yyyy-MM-dd, false otherwise
	 */
	public static boolean isCorrectDateFormat(String inputDate) {
		// idea from slack
		Date date = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			date = sdf.parse(inputDate);
			if (!inputDate.equals(sdf.format(date))) { // if two date are different
				date = null;
			}
		} catch (ParseException ex) {
			ex.printStackTrace();
		}
		return date != null;
		// Invalid date format when equal
	}

	/**
	 * setDateTaken: mutator for date taken, date in yyyy-mm-dd
	 * 
	 * @param dateTaken new date to be changed to
	 */
	public void setDateTaken(String dateTaken) {
		if (isCorrectDateFormat(dateTaken)) { // update date only when format correctly
			this.dateTaken = dateTaken;
		}
	}

	/**
	 * setRating: mutator for rating
	 * 
	 * @param rating new rating to be changed to
	 */
	public void setRating(int rating) {
		if (isCorrectRatingFormat(rating)) {
			this.rating = rating;
		}
	}

	/**
	 * setCaption: mutator for caption
	 * 
	 * @param caption new caption to be changed to
	 */
	public void setCaption(String caption) {
		this.caption = caption;
	}
	
	/**
	 * setImageFile: mutator for image file
	 * 
	 * @param newFile new image file to be changed to
	 */
	public void setImageFile(File newFile) {
		this.imageFile = newFile;
	}

	/**
	 * equals: test for equality between two photographs
	 * 
	 * @param o object to be tested if equals to current photograph
	 * @return true if the testing object has the same caption and filename as the
	 *         current one, false otherwise
	 */
	public boolean equals(Object o) {
		if (o instanceof Photograph) {
			Photograph p = (Photograph) o;
			return (p.caption == this.caption && p.filename == this.filename);
		}
		return false;
	}

	/**
	 * toString: to string of this obj
	 * 
	 * @return values of caption and filename
	 */
	public String toString() {
		return "Caption: " + this.caption + " Filename: " + this.filename;

	}

	// email instruction
	@Override
	public int hashCode() {
		return (this.caption + "---" + this.filename).hashCode();
	}

	/**
	 * compareTo: compare dateTaken of the current Photograph object with the
	 * parameter p
	 * 
	 * @param p obj to be compared with
	 * @return negative if current obj is earlier, positive if p is earlier; if they
	 *         are equal, compare by caption
	 */
	public int compareTo(Photograph p) {
		if (this.getDateTaken().equals(p.getDateTaken())) {
			return this.getCaption().compareTo(p.getCaption());
			// compare their caption in string if they are taken in the same date
		}
		return this.getDateTaken().compareTo(p.getDateTaken());
		// return the string comparison of dateTaken
	}
	


}
