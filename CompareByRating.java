
// hw4 Haoyu Li hl6de

import java.util.Comparator;

public class CompareByRating implements Comparator<Photograph> {

	public int compare(Photograph p1, Photograph p2) {
		if (p1.getRating() == p2.getRating()) {
			return p1.getCaption().compareTo(p2.getCaption());
			// compare by caption if they have identical rating
		}
		return -(p1.getRating() - p2.getRating());
		// return positive if p2 has higher rating, negative if p1 has higher rating
		// higher rating goes before lower rating, i.e. higher rating is smaller
	}
}
