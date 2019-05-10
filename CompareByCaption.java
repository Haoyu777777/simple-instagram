
// hw 5 Haoyu Li hl6de

import java.util.Comparator;

public class CompareByCaption implements Comparator<Photograph> {

	public int compare(Photograph p1, Photograph p2) {
		if (p1.getCaption().equals(p2.getCaption())) {
			return -(p1.getRating() - p2.getRating());
			// compare by rating if their captions are identical
			// higher rating goes before lower rating, i.e. higher rating is smaller
		}
		return (p1.getCaption().compareTo(p2.getCaption()));
		// compare the captions in alphabetic order in string
	}
}
