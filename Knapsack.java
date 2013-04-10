
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Scanner;
//import java.util.logging.Level;
//import java.util.logging.Logger;

/**
 *
 * @author Zofia Sobocinska
 */
public class Knapsack {

	final int N;
	int filledSize = 0;
	Node startNode = null;
	Element.List elements;

	/**
	 *
	 * @param n Slug size (width == height)
	 * @param inputFile File from which Elements should be scanned
	 * @throws Knapsack.Element.Reader.UnevenNumbersCountException
	 * @throws IOException
	 */
	public Knapsack(int n, String inputFile)
			throws Element.Reader.UnevenNumbersCountException,
			IOException {
		N = n;
		startNode = new Node(new Element(0, 0, N, N));
		elements = Element.Reader.readFromFile(inputFile, n, n);
	}

	/**
	 * Method tries to cut elements form slug the way that produces liest amount
	 * of waste.
	 *
	 * @return Waste amount
	 */
	public int pack() {
		Iterator<Element> elementIt = elements.iterator();
		while (elementIt.hasNext()) {
			Element next = elementIt.next();
			try {
				cut(next);
			} catch (Element.ElementTooBigException ex) {
				//Logger.getLogger(Knapsack.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		return getWaste();
	}

	/**
	 * @return Total slug's area
	 */
	int getSize() {
		return N * N;
	}

	/**
	 * @return Filled area
	 */
	int getFilled() {
		return filledSize;
	}

	/**
	 * @return Waste's area
	 */
	int getWaste() {
		return getSize() - getFilled();
	}

	/*
	 * Class holding best node to cut slug from
	 */
	static class Node {

		Node left = null;
		Node right = null;
		Element element = null;
		boolean filled = false;

		@Override
		public String toString() {
			return "Node"
					+ ", element: " + element
					+ ", " + (filled ? "+" : "-")
					+ ", left: " + left
					+ ", right: " + right;
		}

		/**
		 * @return Element from this node
		 */
		Element getElement() {
			return element;
		}

		/**
		 * @return Element to the left from this node
		 */
		Node getLeft() {
			return left;
		}

		/**
		 * @return Element to the right from this node
		 */
		Node getRight() {
			return right;
		}

		/**
		 * Sets element as node's element
		 *
		 * @param elem
		 */
		void setElement(Element elem) {
			element = elem;
		}

		Node() {
		}

		/**
		 * Constructor that sets element as node's element
		 *
		 * @param elem
		 */
		Node(Element elem) {
			element = elem;
		}

		/**
		 * Method tries to find best place for element somewhere in the picture.
		 * Returns null, if cannot match.
		 *
		 * @param elem
		 * @return Node, into which element matches best. If element doesn't
		 * match, returns null;
		 */
		Node match(Element elem) {
			if (left != null) {
				Node tmp = left.match(elem);
				if (tmp != null) {
					return tmp;
				} else {
					return right.match(elem);
				}
			}
			if (filled || !elem.fits(element)) {
				return null;
			}

			if (elem.same(element)) {
				filled = true;
				return this;
			}
			left = new Node();
			right = new Node();


			int w_diff = element.getW() - elem.getW();
			int h_diff = element.getH() - elem.getH();

			Element mine = element;

			if (w_diff > h_diff) {
				left.setElement(new Element(mine.getX(), mine.getY(), elem.getW(), mine.getH()));
				right.setElement(new Element(mine.getX() + elem.getW(), mine.getY(), mine.getW() - elem.getW(), mine.getH()));
			} else {
				// split into top and bottom, putting element on top.
				left.setElement(new Element(mine.getX(), mine.getY(), mine.getW(), elem.getH()));
				right.setElement(new Element(mine.getX(), mine.getY() + elem.getH(), mine.getW(), mine.getH() - elem.getH()));
			}
			return left.match(elem);
		}
	}

	/**
	 * Method tries to cut element from slug. If cannot match it anywhere,
	 * throws exception.
	 *
	 * @param element
	 * @throws Knapsack.Element.ElementTooBigException Cannot match element
	 */
	void cut(Element element) throws Element.ElementTooBigException {
		Node node = startNode.match(element);
		if (node != null) {
			filledSize += node.getElement().getSize();
		}
		if (getSize() <= filledSize) {
			throw new Element.ElementTooBigException(element);
		}
	}

	/**
	 * Element to be cut off slug
	 */
	static class Element implements Comparable<Element> {

		private int w, h, x, y;
		private boolean used;

		/**
		 *
		 * @return position on X axis
		 */
		int getX() {
			return x;
		}

		/**
		 *
		 * @return position on Y axis
		 */
		int getY() {
			return y;
		}

		/**
		 *
		 * @return width
		 */
		int getW() {
			return w;
		}

		/**
		 *
		 * @return height
		 */
		int getH() {
			return h;
		}

		/**
		 * Constructor
		 *
		 * @param x position on X axis
		 * @param y position on Y axis
		 * @param w width
		 * @param h height
		 */
		public Element(int x, int y, int w, int h) {
			this.x = x;
			this.y = y;
			this.w = w;
			this.h = h;
		}

		/**
		 * @return element's size
		 */
		public int getSize() {
			return w * h;
		}

		/**
		 * Rotates element
		 */
		public void rotate() {
			int tmp = w;
			w = h;
			h = tmp;
		}

		boolean fits(Element space) {
			return space != null && space.getW() >= getW() && space.getH() >= getH();
		}

		@Override
		public String toString() {
			return "(" + w + ", " + h + ")";
		}

		/**
		 * Element's comparism mechanism. Here is implemented compareTo() method
		 * that is ought to allow sorting elements in the most efficient manner
		 * to be used by the implemented knapsac algorithm.
		 *
		 * @param t
		 * @return
		 */
		@Override
		public int compareTo(Element t) {
			return compareHeight(this, t);
		}

		public static int compareWidth(Element a, Element b) {
			return (a.w != b.w) ? (a.w - b.w) : (a.h - b.h);
		}

		public static int compareHeight(Element a, Element b) {
			return (a.h != b.h) ? (a.h - b.h) : (a.w - b.w);
		}

		public static int compareSize(Element a, Element b) {
			return a.getSize() - b.getSize();
		}

		public boolean same(Element t) {
			return t.w == w && t.h == h;
		}

		public static class Reader {

			/**
			 *
			 * @param filename
			 * @param maxW
			 * @param maxH
			 * @return
			 * @throws Knapsack2.Element.Reader.UnevenNumbersCountException
			 * @throws IOException
			 * @throws Knapsack2.Element.Reader.ElementSizeException
			 */
			public static List readFromFile(String filename, int maxW, int maxH)
					throws UnevenNumbersCountException, IOException {

				String regexp = "\\D+";

				List elements = new List();

				File file = new File(filename);
				Scanner sc = new Scanner(file).useDelimiter(regexp);
				while (sc.hasNext()) {
					int w = sc.nextInt();
					if (!sc.hasNext()) {
						throw new UnevenNumbersCountException();
					}
					int h = sc.nextInt();

					try {
						if (w > maxW || h > maxH || w < 0 || h < 0) {
							throw new ElementSizeException(new Element(0, 0, w, h));
						}
//						elements.add(new Element(0, 0, (w > h) ? h : w, (w > h) ? w : h));
						elements.add(new Element(0, 0, w, h));
					} catch (ElementSizeException ex) {
//						Logger.getLogger(Knapsack.class.getName()).log(Level.SEVERE, null, ex);
					}

				}
//				Collections.sort(elements);
				return elements;
			}

			/**
			 * May be thrown when count of numbers in the input file isn't even
			 */
			public static class UnevenNumbersCountException
					extends Exception {

				static final long serialVersionUID = 1L;
				static final String MSG = "Uneven count of numbers in the input file.";

				public UnevenNumbersCountException() {
					super(MSG);
				}
			}

			public static class ElementSizeException
					extends Exception {

				public ElementSizeException(Element element) {
					super(MSG_A + element + MSG_B);
				}
				static final long serialVersionUID = 2L;
				static final String MSG_A = "Element ";
				static final String MSG_B = " has wrong size.";
			}
		}

		public static class List extends ArrayList<Element> {

			public void sort() {
				Collections.sort(this);
			}

			public int getSize() {
				int size = 0;
				Iterator<Element> iterator = iterator();
				while (iterator.hasNext()) {
					Element next = iterator.next();
					size += next.getSize();
				}
				return size;
			}
		}

		public static class ElementTooBigException
				extends Exception {

			public ElementTooBigException(Element element) {
				super(MSG_A + element + MSG_B);
			}
			static final long serialVersionUID = 3L;
			static final String MSG_A = "Element ";
			static final String MSG_B = " too big to be cut off.";
		}
	}
}