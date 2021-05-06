import java.io.PrintWriter;
// import java.util.*;

/**
 * Adjacency list implementation for the AssociationGraph interface.
 *
 * Your task is to complete the implementation of this class. You may add
 * methods, but ensure your modified class compiles and runs.
 *
 * @author Jeffrey Chan, 2019.
 */

public class AdjacencyList extends AbstractGraph {

	public class Node {
		public String label = "";
		public String state = "S";

		public Node(String l) {
			this.label = l;
		}
	}

	public class LinkedList {
		public Node src = null;
		public Node[] targets = null;

		public LinkedList(Node s) {
			this.src = s;
			targets = new Node[0];
		}

		public void addTarget(Node n) {
			Node[] tmp = new Node[targets.length + 1];
			int i = 0;
			for (i = 0; i < targets.length; i++) {
				tmp[i] = targets[i];
			}
			tmp[i] = n;
			targets = tmp;
		}

		public void deleteTarget(String vertLabel) {
			for (int i = 0; i < targets.length; i++) {
				if (vertLabel.equals(targets[i].label)) {
					Node[] tmp = new Node[targets.length - 1];
					int z = 0;
					for (int j = 0; j < targets.length; j++) {
						if (j != i) {
							tmp[z] = targets[j];
							z++;
						}
					}
					targets = tmp;
				}
			}
		}
	}

	public Node[] vertices = null;
	public LinkedList[] ll = null;

	/**
	 * Contructs empty graph.
	 */
	public AdjacencyList() {
		vertices = new Node[0];
		ll = new LinkedList[0];
	} // end of AdjacencyList()

	public void addVertex(String vertLabel) {
		// add vertex to list of vertices
		Node tmp[] = new Node[vertices.length + 1];
		int i = 0;
		for (i = 0; i < vertices.length; i++) {
			tmp[i] = vertices[i];
		}
		Node n = new Node(vertLabel);
		tmp[i] = n;
		vertices = tmp;

		// add new LinkedList
		LinkedList[] tmp2 = new LinkedList[ll.length + 1];
		for (i = 0; i < ll.length; i++) {
			tmp2[i] = ll[i];
		}
		tmp2[i] = new LinkedList(n);
		ll = tmp2;

	} // end of addVertex()

	public void addEdge(String srcLabel, String tarLabel) {

		Node tar = null;
		Node src = null;
		for (int i = 0; i < vertices.length; i++) {
			if (tarLabel.equals(vertices[i].label)) {
				tar = vertices[i];
			}

		}

		for (int i = 0; i < ll.length; i++) {
			if (srcLabel.equals(ll[i].src.label)) {
				ll[i].addTarget(tar);
			}

		}

	} // end of addEdge()

	public void toggleVertexState(String vertLabel) {
		for (int i = 0; i < vertices.length; i++) {
			if (vertLabel.equals(vertices[i].label)) {
				if (vertices[i].state == "S")
					vertices[i].state = "I";
				else if (vertices[i].state == "I")
					vertices[i].state = "R";
			}
		}
	} // end of toggleVertexState()

	public void deleteEdge(String srcLabel, String tarLabel) {
		for (int i = 0; i < ll.length; i++) {

			if (srcLabel.equals(ll[i].src.label)) {
				ll[i].deleteTarget(tarLabel);
			}
		}
	} // end of deleteEdge()

	public void deleteVertex(String vertLabel) {

		for (int i = 0; i < ll.length; i++) {
			ll[i].deleteTarget(vertLabel);
			if (vertLabel.equals(ll[i].src.label)) {
				LinkedList[] tmp = new LinkedList[ll.length - 1];
				int z = 0;
				for (int j = 0; j < ll.length; j++) {
					if (j != i) {
						tmp[z] = ll[j];
						z++;
					}
				}
				ll = tmp;
			}
		}

		for (int i = 0; i < ll.length; i++) {
			ll[i].deleteTarget(vertLabel);
		}

		for (int i = 0; i < vertices.length; i++) {
			if (vertLabel.equals(vertices[i].label)) {
				Node[] tmp = new Node[vertices.length - 1];
				int z = 0;
				for (int j = 0; j < vertices.length; j++) {
					if (j != i) {
						tmp[z] = vertices[j];
						z++;
					}
				}
				vertices = tmp;
			}
		}

	} // end of deleteVertex()

	public String[] kHopNeighbours(int k, String vertLabel) {

		String s[] = new String[0];
		for (int i = 0; i < ll.length; i++) {
			if (vertLabel.equals(ll[i].src.label)) {
				if (ll[i].targets.length > 0 && k > 0) {
					for (int j = 0; j < ll[i].targets.length; j++) {
						String tmp[] = new String[s.length + 1];
						int z = 0;
						for (z = 0; z < s.length; z++) {
							tmp[z] = s[z];
						}
						tmp[z] = ll[i].targets[j].label;
						s = tmp;
						if (k > 1) {
							String[] kn = kHopNeighbours(k - 1, ll[i].targets[j].label);
							tmp = new String[s.length + kn.length];
							z = 0;
							for (z = 0; z < s.length; z++) {
								tmp[z] = s[z];
							}
							int w = 0;
							for (w = 0; w < kn.length; w++) {
								tmp[z] = kn[w];
								z++;
							}
							s = tmp;
						}

					}

				}

			}
		}

		return s;
	} // end of kHopNeighbours()

	public void printVertices(PrintWriter os) { // USE os.print !!!
		String s = "";
		for (int i = 0; i < vertices.length; i++) {
			s += "(" + vertices[i].label + " , " + vertices[i].state + ")";
		}
		os.println(s);
	} // end of printVertices()

	public void printEdges(PrintWriter os) {

		for (int i = 0; i < ll.length; i++) {
			String s = ll[i].src.label + " ";
			String s1 = ll[i].src.label;
			for (int j = 0; j < ll[i].targets.length; j++) {
				s += ll[i].targets[j].label;
				s1 = ll[i].targets[j].label + " " + s1;
				os.println(s);
				os.println(s1);
				s = ll[i].src.label + " ";
				s1 = ll[i].src.label;
			}
		}

	} // end of printEdges()

} // end of class AdjacencyList
