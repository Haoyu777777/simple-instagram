
// hw 4 Haoyu Li hl6de

import java.util.ArrayList;

public abstract class PhotographContainer {

	protected String name; // the name of the album
	protected ArrayList<Photograph> photos; // the list of photos in the album

	/**
	 * constructor for PhotographContainer
	 * 
	 * @param name name of the photograph container to be created
	 */
	public PhotographContainer(String name) {
		this.name = name;
		this.photos = new ArrayList<Photograph>();
	}

	/**
	 * getName: accessor for name
	 * 
	 * @return the reference of its name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * setName: mutator for name in the album
	 * 
	 * @param name: new name to be changed to
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * getPhotos: accessor for photos
	 * 
	 * @return the reference of photo list
	 */
	public ArrayList<Photograph> getPhotos() {
		return this.photos;
	}

	/**
	 * addPhoto: add the Photograph to the list of photos if not present
	 * 
	 * @param p photograph to be added to the album
	 * @return true if the Photograph was added; false if it was not added or it is
	 *         null;
	 */
	public boolean addPhoto(Photograph p) {
		if (p == null) {
			return false; // return false when null
		} else if (!hasPhoto(p)) {
			this.photos.add(p);
			return true; // return true when added successfully
		}
		return false; // return false when not added
	}

	/**
	 * hasPhoto: test if the current obj has p in its list
	 * 
	 * @param p
	 * @return true if the current object has p in its list, false otherwise
	 */
	public boolean hasPhoto(Photograph p) {
		return this.photos.contains(p);
	}

	/**
	 * removePhoto: remove the photograph from the photo list
	 * 
	 * @param p photograph to be removed from the photo list
	 * @return true if removed successfully, else false
	 */
	public boolean removePhoto(Photograph p) {
		if (hasPhoto(p)) {
			this.photos.remove(p);
			return true; // return true after successfully removed
		}
		return false; // when not successfully removed
	}

	/**
	 * numPhotographs: no. of photographs in the album
	 * 
	 * @return number of Photographs in the current album
	 */
	public int numPhotographs() {
		return this.photos.size();
	}

	/**
	 * equals: to see if two obj have the same name as one another
	 *
	 * @param o obj to be tested if is equal to current album
	 * @return true if their name and photo list match
	 */
	public boolean equals(Object o) {
		if (o instanceof Album) {
			Album a = (Album) o; // cast the obj o to album a
			return this.name.equals(a.getName());
		} // test if their names are the same
		return false;
	}

	/**
	 * toString: strings to be printed out
	 * 
	 * @return name of the album in the first line, its list containing photos'
	 *         filename next
	 */
	public String toString() {
		ArrayList<String> filenameList = new ArrayList<String>();
		for (Photograph p : photos) {
			filenameList.add(p.getFilename());
		}
		return (this.name + ": " + "\n" + filenameList);
	}

	// from email instruction
	@Override
	public int hashCode() {
		return this.name.hashCode();
	}

	/**
	 * getPhotos: get the photos with rating higher or equal to the given rating
	 * 
	 * @param rating rating of photos to be compared with
	 * @return an ArrayList of photos from the photos feed that have a rating
	 *         greater than or equal to the given rating
	 */
	public ArrayList<Photograph> getPhotos(int rating) {
		ArrayList<Photograph> result = new ArrayList<Photograph>();
		if (!Photograph.isCorrectRatingFormat(rating)) {
			return null; // return null for incorrectly formatted rating
		}
		for (Photograph p : this.photos) {
			if (p.getRating() >= rating) {
				result.add(p); // add the photos with higher rating
			}
		}
		return result;
	}

	/**
	 * getPhotosInYear: get the photos taken in the year provided
	 * 
	 * @param year year when the photos to be taken
	 * @return an ArrayList of photos from the photos feed that were taken in the
	 *         year provided
	 */
	public ArrayList<Photograph> getPhotosInYear(int year) {
		ArrayList<Photograph> result = new ArrayList<Photograph>();
		String yearString = year + ""; // cast int to string for later comparison
		if (yearString.length() != 4) {
			return null; // return null if year is not formatted in 4 digit
		}
		for (Photograph p : this.photos) {
			String dateTaken = p.getDateTaken();
			String yearTaken = dateTaken.substring(0, 4); // get the year of each photo
			if (yearTaken.equals(yearString)) { // string comparison
				result.add(p); // add to the result only when year matches
			}
		}
		return result;
	}

	/**
	 * getPhotosInMonth: get the photos taken in the month, year provided
	 * 
	 * @param month month when the photos to be taken
	 * @param year  years when the photos to be taken
	 * @return an ArrayList of photos from the photos feed that were taken in the
	 *         month and year provided
	 */
	public ArrayList<Photograph> getPhotosInMonth(int month, int year) {
		ArrayList<Photograph> result = new ArrayList<Photograph>();
		if (month > 12 || month < 0) {
			return null; // when month is not formated correctly
		}
		String monthString = month + ""; // cast month from int to string
		if (month < 10) {
			monthString = 0 + monthString; // add 0 if month is single digit to correct format
		}
		for (Photograph p : getPhotosInYear(year)) { // for any photograph matching the year
			String dateTaken = p.getDateTaken();
			String monthTaken = dateTaken.substring(5, 7); // get the month of each photo
			if (monthTaken.equals(monthString)) {
				result.add(p); // add the element that matches the given month
			}
		}
		return result;
	}

	/**
	 * getPhotosBetween: get the photos taken between the two given date
	 * 
	 * @param beginDate starting date when the photographs are taken
	 * @param endDate   end date when the photographs are taken
	 * @return an ArrayList of photos from the photos feed that were taken between
	 *         beginDate and endDate (inclusive), null if formatted incorrectly or
	 *         beginDate > endDate
	 */
	public ArrayList<Photograph> getPhotosBetween(String beginDate, String endDate) {
		ArrayList<Photograph> result = new ArrayList<Photograph>();
		if (beginDate.compareTo(endDate) > 0 || !Photograph.isCorrectDateFormat(beginDate)
				|| !Photograph.isCorrectDateFormat(endDate)) {
			return null;
			// null when begin date is greater than end date, or either date is not
			// formatted correctly
		}
		for (Photograph p : this.photos) {
			if (p.getDateTaken().compareTo(endDate) <= 0 && p.getDateTaken().compareTo(beginDate) >= 0) {
				// string comparison
				result.add(p); // add only when the date is between begin and end date
			}
		}
		return result;
	}
}
