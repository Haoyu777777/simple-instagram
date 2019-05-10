
// hw 4 Haoyu Li, hl6de

import java.util.ArrayList;
import java.util.HashSet;

public class PhotoLibrary extends PhotographContainer {
	private int id; // int containing the person's ID
	private HashSet<Album> albums; // set of album, each contains photos from this user's photos stream

	/**
	 * constructor for a PhotoLibrary object
	 * 
	 * @param name the name of the library
	 * @param id   id referring the library
	 */
	public PhotoLibrary(String name, int id) {
		super(name);
		this.id = id;
		this.photos = new ArrayList<Photograph>();
		this.albums = new HashSet<Album>();
	}

	/**
	 * getId: accessor
	 * 
	 * @return the person's id
	 */
	public int getId() {
		return this.id;
	}

	/**
	 * getAlbums: accessor
	 * 
	 * @return the reference to the set of photos
	 */
	public HashSet<Album> getAlbums() {
		return this.albums;
	}

	/**
	 * setName: change the person's name
	 * 
	 * @param name the new name to change for the person
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * equals: test if two photograph obj are the same
	 * 
	 * @param o the object to be tested if equal to current person
	 * @return true if current person's id is equal to the id passed in, false
	 *         otherwise
	 */
	public boolean equals(Object o) {
		if (o instanceof PhotoLibrary) {
			PhotoLibrary p = (PhotoLibrary) o;
			return this.id == p.id;
		}
		return false;
	}

	/**
	 * commonPhotos: to see the common photograph between two person
	 * 
	 * @param a person a to test if they have common photos
	 * @param b person b to test if they have common photos
	 * @return a list of common photos that both a and b have
	 */
	public static ArrayList<Photograph> commonPhotos(PhotoLibrary a, PhotoLibrary b) {
		ArrayList<Photograph> commonResult = new ArrayList<Photograph>();
		for (Photograph p : a.photos) { // go through each photo in a
			for (Photograph p2 : b.photos) { // go through each photo in b
				if (p2.equals(p)) { // add it to the result if the two photos are equal
					commonResult.add(p);
				}
			}
		}
		return commonResult;
	}

	/**
	 * similarity: to see the similarity of photos bewteen two person
	 * 
	 * @param a person a to test for their similarity in photos
	 * @param b person b to test for their similarity in photos
	 * @return how similar the photo feed are between a and b, from 0 to 1
	 */
	public static double similarity(PhotoLibrary a, PhotoLibrary b) {
		if (a.photos.isEmpty() || b.photos.isEmpty()) { // when either has posted any
			return 0.0; // similarity is 0.0
		}
		float numCommonPhotos = commonPhotos(a, b).size(); // parse it to float
		int smallerNumOfPhotos = Math.min(a.photos.size(), b.photos.size());
		return numCommonPhotos / smallerNumOfPhotos;
	}

	/**
	 * createAlbum: create an album with the name to the photolibrary obj
	 * 
	 * @param albumName name of the album to be created in current obj iff not
	 *                  already exist
	 * @return true if the add was successful, false otherwise
	 */
	public boolean createAlbum(String albumName) {
		Album a = new Album(albumName);
		if (!this.albums.contains(a)) { // if not already exists
			this.albums.add(a);
			return true; // true when successfully added
		}
		return false;
	}

	/**
	 * removeAlbum: remove the album with name given from the current obj
	 * 
	 * @param albumName name of the album to be removed
	 * @return true if the remove was successful, false otherwise
	 */
	public boolean removeAlbum(String albumName) {
		Album a = new Album(albumName);
		if (this.albums.contains(a)) {
			this.albums.remove(a);
			return true;
		}
		return false;
	}

	/**
	 * addPhotoToAlbum: add a Photograph to the Album when not already in it and it
	 * is in the photolibrary list
	 * 
	 * @param p         photograph to be added to the album
	 * @param albumName name of the album that the photograph is to be added
	 * @return true if the Photograph was added; return false if it was not added
	 */
	public boolean addPhotoToAlbum(Photograph p, String albumName) {
		Album a = getAlbumByName(albumName);
		if (this.hasPhoto(p) && a != null) {
			return a.addPhoto(p);
		}
		return false;
	}

	/**
	 * removePhotoFromAlbum: remove the photograph from the album in the set of
	 * albums that has name albumName
	 * 
	 * @param p         photograph to be removed
	 * @param albumName name of album which photograph is removed from
	 * @return true if the photo was successfully removed, Otherwise return false.
	 */
	public boolean removePhotoFromAlbum(Photograph p, String albumName) {
		Album a = getAlbumByName(albumName);
		if (a != null) { // if a exists
			return a.removePhoto(p); // try remove the photo from the album
		}
		return false;
	}

	/**
	 * getAlbumByName: get the album in the album set with the same provided
	 * 
	 * @param albumName the name of album to get
	 * @return the album with that name, null if not found.
	 */
	private Album getAlbumByName(String albumName) {
		for (Album a : this.albums) {
			if (a.getName().equals(albumName)) {
				return a;
			}
		}
		return null; // return null if not found
	}

	/**
	 * removePhoto: remove the Photograph p from the PhotoLibrary list of photos,
	 * and remove the Photograph from any Albums in the list of albums
	 * 
	 * @param p photograph to be removed
	 * @return true if the photograph was successfully removed, false otherwise
	 */
	public boolean removePhoto(Photograph p) {
		if (hasPhoto(p)) {
			this.photos.remove(p);
			for (Album a : this.albums) {
				a.removePhoto(p); // remove p from any album that has it
			}
			return true;
		}
		return false; // false otherwise
	}

	/**
	 * toString: to print out the obj with a list of Album names contained in the
	 * album list
	 * 
	 * @return a list of Album names contained in the album list
	 */
	public String toString() {
		ArrayList<String> albumName = new ArrayList<String>();
		for (Album a : this.albums) {
			albumName.add(a.getName());
		}
		return ("name: " + this.name + ", id: " + this.id + ", photo: " + this.photos + ", albums: " + albumName);
	}

}