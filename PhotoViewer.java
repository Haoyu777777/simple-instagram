
// hw 5 Haoyu Li hl6de

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class PhotoViewer extends JFrame {

	private static final long serialVersionUID = 1; // instruction from piazza

	private PhotographContainer imageAlbum;

	private JPanel ui; // panel contains left, right, bot, top, center

	private JPanel left; // panel for prev button
	private JPanel center; // panel for big images
	private JPanel right; // panel for next button
	private JPanel top; // panel for form control
	private JPanel bot; // panel for rating

	private JPanel thumbnails; // label with thumbnail image, caption, date and rating
	private ArrayList<JLabel> tnList;

	// form control to sort images in thumbnail
	private JRadioButton orderByCaption;
	private JRadioButton orderByDate;
	private JRadioButton orderByRating;

	// advance to previous or next image when clicked
	private JButton previous;
	private JButton next;
	private JLabel displayPic;
	private int imgIndex; // index for image to be displayed

	// rate the image
	private JRadioButton rate1;
	private JRadioButton rate2;
	private JRadioButton rate3;
	private JRadioButton rate4;
	private JRadioButton rate5;
	private ButtonGroup rateGroup;

	/***
	 * constructor for photoviewer
	 * 
	 * @param p photolibrary to be displayed in the window
	 */
	public PhotoViewer(PhotoLibrary p) {
		this.imageAlbum = p;
		this.imgIndex = 0;
		this.rateGroup = new ButtonGroup();
		tnList = new ArrayList<>(); // initialize the list
		initialize();
	}

	/***
	 * create the group of rating radio button at bot
	 */
	public void createRatingButton() {
		// button panel and its component
		rate1 = new JRadioButton("1");
		rate2 = new JRadioButton("2");
		rate3 = new JRadioButton("3");
		rate4 = new JRadioButton("4");
		rate5 = new JRadioButton("5");

		// group the radio button
		rateGroup.add(rate1);
		rateGroup.add(rate2);
		rateGroup.add(rate3);
		rateGroup.add(rate4);
		rateGroup.add(rate5);

		// add the radio button to the panel
		bot.add(rate1);
		bot.add(rate2);
		bot.add(rate3);
		bot.add(rate4);
		bot.add(rate5);

		// add button action to each radio button
		rate1.setActionCommand("radio");
		rate1.addActionListener(new ButtonListener());
		rate2.setActionCommand("radio");
		rate2.addActionListener(new ButtonListener());
		rate3.setActionCommand("radio");
		rate3.addActionListener(new ButtonListener());
		rate4.setActionCommand("radio");
		rate4.addActionListener(new ButtonListener());
		rate5.setActionCommand("radio");
		rate5.addActionListener(new ButtonListener());

	}

	/***
	 * find the rating of any pic
	 * 
	 * @param group button group where ratings are grouped
	 * @param p     photograph whose rating is to be found
	 */
	public void findRating(ButtonGroup group, Photograph p) {
		// loop over the button group to set current rating checked, idea from
		// java2s.com
		Enumeration<AbstractButton> elements = group.getElements();
		while (elements.hasMoreElements()) {
			AbstractButton button = (AbstractButton) elements.nextElement();
			if (button.getText().equals("" + p.getRating())) { // if the rating is found
				button.setSelected(true); // select the rate of displayed img
			}
		}
	}

	/***
	 * create the group of sort option at top
	 */
	public void createSortButton() {

		// 3 buttons
		orderByCaption = new JRadioButton("Caption");
		orderByDate = new JRadioButton("Date", true); // selected by default
		orderByRating = new JRadioButton("Rating");

		// group the order radio button
		ButtonGroup orderGroup = new ButtonGroup();
		orderGroup.add(orderByCaption);
		orderGroup.add(orderByDate);
		orderGroup.add(orderByRating);

		// add the button to top panel
		top.add(orderByDate);
		top.add(orderByCaption);
		top.add(orderByRating);

		// set and add button listener
		orderByCaption.setActionCommand("sort");
		orderByCaption.addActionListener(new ButtonListener());

		orderByDate.setActionCommand("sort");
		orderByDate.addActionListener(new ButtonListener());

		orderByRating.setActionCommand("sort");
		orderByRating.addActionListener(new ButtonListener());

	}

	/***
	 * create thumbnails at left grid with the list of jlabels
	 */
	public void createThumbnails() {

		for (int i = 0; i < this.imageAlbum.getPhotos().size(); i++) { // loop over the list of photographs
			// add required info to the arraylist element
			Photograph p = imageAlbum.getPhotos().get(i);

			try {
				// get all relevant info
				BufferedImage pic = ImageIO.read(p.getImageFile());
				String date = p.getDateTaken();
				int rating = p.getRating();
				String caption = p.getCaption();

				JLabel iconHolder = new JLabel();

				iconHolder.setIcon(new ImageIcon(pic.getScaledInstance(100, 100, Image.SCALE_DEFAULT)));
				iconHolder.setText(
						"<html>Taken: " + date + "		Rate: " + rating + "	Caption: " + caption + "</html>");

				// add the mouse click event to pic label
				// idea from stack overflow
				iconHolder.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						// change the current img index to the one on thumbnail if clicked
						imgIndex = imageAlbum.getPhotos().indexOf(p);
						// set the img to the newer one
						setPic(imgIndex);
					}
				});
				tnList.add(iconHolder);

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// add each thumb nail into the thumbnails panel
		for (JLabel l : tnList) {
			thumbnails.add(l);
		}
	}

	/***
	 * set a thumb nail label given one photo to a given label
	 * 
	 * @param p photo with newer info that label should change to
	 * @param l label whose content needs changing
	 */
	public void setTNLabel(Photograph p, JLabel l) {
		try {
			// read information from the photograph
			BufferedImage pic = ImageIO.read(p.getImageFile());
			String date = p.getDateTaken();
			int rating = p.getRating();
			String caption = p.getCaption();

			// change the content of the labels
			l.setIcon(new ImageIcon(pic.getScaledInstance(100, 100, Image.SCALE_DEFAULT)));
			l.setText("<html><p>Taken: " + date + "		Rate: " + rating + "	Caption: " + caption + "</p></html>");

			// add the event
			l.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					// change the current img index to the one on thumbnail if clicked
					imgIndex = imageAlbum.getPhotos().indexOf(p);
					// set the img to the newer one
					setPic(imgIndex);
				}
			});
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	/***
	 * create main display for big picture at the center and buttons at its side
	 */
	public void createDisplayPic() {
		// add buttons
		previous = new JButton("Prev");
		next = new JButton("Next");

		// add big image
		displayPic = new JLabel();
		setPic(imgIndex);
		center.add(displayPic, BorderLayout.CENTER);

		// event for previous button
		previous.setActionCommand("prev");
		previous.addActionListener(new ButtonListener());

		// event for next button
		next.setActionCommand("next");
		next.addActionListener(new ButtonListener());

		left.add(previous, BorderLayout.CENTER);
		right.add(next, BorderLayout.CENTER);
	}

	/***
	 * display the big image given index
	 * 
	 * @param index index of image to be set on displayPic panel
	 */
	public void setPic(int index) {
		try {
			// read the img file in the container
			BufferedImage pic = ImageIO.read(imageAlbum.getPhotos().get(index).getImageFile());
			displayPic.setIcon(new ImageIcon(pic.getScaledInstance(400, 400, Image.SCALE_DEFAULT)));
		} catch (IOException err) {
			err.printStackTrace();
		}
		findRating(rateGroup, imageAlbum.getPhotos().get(index)); // find the rating of the shown pic
	}

	/***
	 * Initialize the contents of the frame
	 */
	private void initialize() {

		// set initial size (minW minH maxW maxH)
		setBounds(100, 100, 600, 600);

		// Set what happens when you click the close button
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// specify a gap between components (horiz, vert)
		getContentPane().setLayout(new GridLayout(1, 2)); // 2*1 grid layout
		ui = new JPanel(new BorderLayout(5, 5)); // border layout for ui display, rate

		thumbnails = new JPanel(new GridLayout(5, 1)); // 1*5 grid for thumbnail
		thumbnails.getPreferredSize();

		left = new JPanel();
		right = new JPanel();
		top = new JPanel();
		bot = new JPanel();
		center = new JPanel();

		// bottom panel and its components
		createRatingButton();

		// top panel and its components
		createSortButton();

		// left panel and its components
		createThumbnails();

		// right panel and its components
		createDisplayPic();

		// add the four panel to the content window
		ui.add(left, BorderLayout.WEST);
		ui.add(right, BorderLayout.EAST);
		ui.add(top, BorderLayout.NORTH);
		ui.add(bot, BorderLayout.SOUTH);
		ui.add(center, BorderLayout.CENTER);

		// add all layout together
		getContentPane().add(thumbnails);
		getContentPane().add(ui);
	}

	/**
	 * main test
	 * 
	 */
	public static void main(String[] args) {

		// relative path for Macs/Linux:
		String imageDirectory = "images/";

		// add the photos to a photo library
		Photograph p1 = new Photograph("1", imageDirectory + "1.jpg", "2019-01-30", 5);
		Photograph p2 = new Photograph("2", imageDirectory + "2.jpg", "2019-01-30", 4);
		Photograph p3 = new Photograph("3", imageDirectory + "3.jpg", "2019-02-01", 3);
		Photograph p4 = new Photograph("4", imageDirectory + "4.jpg", "2015-06-30", 4);
		Photograph p5 = new Photograph("5", imageDirectory + "5.jpg", "2015-06-30", 2);

		PhotoLibrary photoLib = new PhotoLibrary("Test Library", 1);

		photoLib.addPhoto(p1);
		photoLib.addPhoto(p2);
		photoLib.addPhoto(p3);
		photoLib.addPhoto(p4);
		photoLib.addPhoto(p5);

		// sort the list as default
		Collections.sort(photoLib.photos);

		PhotoViewer myViewer = new PhotoViewer(photoLib);

		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					// Resize to fit content
					myViewer.pack();

					// Display the Window
					myViewer.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private class ButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			// prev button, decrease the index by 1
			if (e.getActionCommand().equals("prev")) {
				imgIndex = (imgIndex - 1 + 5) % 5; // add 5 to avoid negative index

				// set the new display img after prev is clicked
				setPic(imgIndex);
			}

			// next button, increase the index by 1
			else if (e.getActionCommand().equals("next")) {
				imgIndex = (imgIndex + 1) % 5;

				// set the new img after next is clicked
				setPic(imgIndex);
			}

			// rating button
			else if (e.getActionCommand().equals("radio")) {

				// get current displayed img
				Photograph p = imageAlbum.getPhotos().get(imgIndex);

				// change the rating accordingly
				if (rate1.isSelected()) {
					p.setRating(1);
				} else if (rate2.isSelected()) {
					p.setRating(2);
				} else if (rate3.isSelected()) {
					p.setRating(3);
				} else if (rate4.isSelected()) {
					p.setRating(4);
				} else if (rate5.isSelected()) {
					p.setRating(5);
				}

				// update thumbnail rating
				for (int i = 0; i < tnList.size(); i++) {
					JLabel l = tnList.get(i); // get each label in label list
					Photograph p2 = imageAlbum.getPhotos().get(i); // get the photo
					setTNLabel(p2, l); // set the thumb nail label
				}
			}

			// sort button, change the current index to 0 after sorting
			else if (e.getActionCommand().equals("sort")) {

				ArrayList<Photograph> pList = imageAlbum.getPhotos(); // get the photo list
				imgIndex = 0; // new index is 0 by default

				// sort with respective comparator
				if (orderByCaption.isSelected()) {
					pList.sort(new CompareByCaption());

				} else if (orderByDate.isSelected()) {
					Collections.sort(pList); // default sort

				} else if (orderByRating.isSelected()) {
					pList.sort(new CompareByRating());

				}
				// set the img to the first in the list
				setPic(imgIndex);

				// change the thumb nail imgs
				for (int i = 0; i < tnList.size(); i++) {
					JLabel l = tnList.get(i); // get each label in label list
					Photograph p = pList.get(i); // get the photo
					setTNLabel(p, l); // set the thumb nail label
				}
			}
		}
	}
}
