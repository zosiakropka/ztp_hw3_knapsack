
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

/**
 *
 * @author Zofia Sobocinska
 */
public class Knapsack {

	final int N;
	Element.List elements;

	/**
	 *
	 * @param n
	 * @param inputFile
	 * @throws Knapsack.Element.Reader.UnevenNumbersCountException
	 * @throws IOException
	 */
	public Knapsack(int n, String inputFile)
			throws Element.Reader.UnevenNumbersCountException, IOException, Element.Reader.ElementTooBigException {
		this.N = n;
		elements = Element.Reader.readFromFile(inputFile, N, N);

		for (Element element : elements) {
			System.out.println(element);
		}
	}

	public int pack() {
		return 0;
	}

	public static class Element implements Comparable<Element> {

		private int w, h;

		public Element(int w, int h) {
			this.w = w;
			this.h = h;
		}

		public int getL() {
			return (w > h) ? w : h;
		}

		public int getS() {
			return (w < h) ? w : h;
		}

		public int getW() {
			return w;
		}

		public int getH() {
			return h;
		}

		public int getSize() {
			return w * h;
		}

		public void rotate() {
			int tmp = w;
			w = h;
			h = tmp;
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
			return compareSize(this, t);
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

		public static class Reader {

			/**
			 *
			 * @param filename
			 * @param maxW
			 * @param maxH
			 * @return
			 * @throws Knapsack.Element.Reader.UnevenNumbersCountException
			 * @throws IOException
			 * @throws Knapsack.Element.Reader.ElementTooBigException
			 */
			public static List readFromFile(String filename, int maxW, int maxH)
					throws UnevenNumbersCountException, IOException, ElementTooBigException {

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

					if (w > maxW || h > maxH) {
						throw new ElementTooBigException(new Element(w, h));
					}
					if (w != 0 && h != 0) {
						elements.add(new Element(w, h));
					}
				}
				Collections.sort(elements);
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

			public static class ElementTooBigException
					extends Exception {

				public ElementTooBigException(Element element) {
					super(MSG_A + element + MSG_B);
				}
				static final long serialVersionUID = 2L;
				static final String MSG_A = "Element ";
				static final String MSG_B = " too big to be cut off slug.";
			}
		}

		public static class List extends ArrayList<Element> {

			public void sort() {
				Collections.sort(this);
			}
		}
	}
}
