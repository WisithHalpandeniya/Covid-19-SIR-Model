import java.io.PrintWriter;

/**
 * Adjacency matrix implementation for the GraphInterface interface.
 *
 * Your task is to complete the implementation of this class. You may add
 * methods, but ensure your modified class compiles and runs.
 *
 * @author Jeffrey Chan, 2021.
 */
public class AdjacencyMatrix extends AbstractGraph {
	public class Node {
		public String label = "";
		public String state = "S";

		public Node(String l) {
			this.label = l;
		}
	}

	/**
	 * Contructs empty graph.
	 */
	public int[][] matrix;
	public Node[] nodes = null;

	public AdjacencyMatrix() {
		matrix = new int[0][0];
		nodes = new Node[0];
	} // end of AdjacencyMatrix()

	public void addVertex(String vertLabel) {
		Node tmp[] = new Node[nodes.length + 1];
		int i = 0;
		for (i = 0; i < nodes.length; i++) {
			tmp[i] = nodes[i];
		}
		Node n = new Node(vertLabel);
		tmp[i] = n;
		nodes = tmp;

		int vl = matrix.length;
		int hl = matrix.length;
		for (i = 0; i < vl; i++) {
			int[] t = new int[hl + 1];
			for (int j = 0; j < hl; j++) {
				t[j] = matrix[i][j];
			}
			matrix[i] = t;
		}
		int[][] t = new int[vl + 1][hl + 1];
		for (i = 0; i < vl; i++) {
			t[i] = matrix[i];

		}
		t[i] = new int[hl + 1];
		matrix = t;

	} // end of addVertex()

	public void addEdge(String srcLabel, String tarLabel) {
		int srcIndex = 0;
		int tarIndex = 0;
		for (int i = 0; i < nodes.length; i++) {
			if (nodes[i].label.equals(srcLabel)) {
				srcIndex = i;
			} else if (nodes[i].label.equals(tarLabel)) {
				tarIndex = i;
			}
		}
		matrix[srcIndex][tarIndex] = 1;
		matrix[tarIndex][srcIndex] = 1;

	} // end of addEdge()

	public void toggleVertexState(String vertLabel) {
		for (int i = 0; i < nodes.length; i++) {
			if (vertLabel.equals(nodes[i].label)) {
				if (nodes[i].state == "S")
					nodes[i].state = "I";
				else if (nodes[i].state == "I")
					nodes[i].state = "R";
			}
		}
	} // end of toggleVertexState()

	public void deleteEdge(String srcLabel, String tarLabel) {
		int srcIndex = 0;
		int tarIndex = 0;
		for (int i = 0; i < nodes.length; i++) {
			if (nodes[i].label.equals(srcLabel)) {
				srcIndex = i;
			} else if (nodes[i].label.equals(tarLabel)) {
				tarIndex = i;
			}
		}
		matrix[srcIndex][tarIndex] = 0;
		matrix[tarIndex][srcIndex] = 0;
	} // end of deleteEdge()

	public void deleteVertex(String vertLabel) {
		int nl = nodes.length;
		int[][] tmp1 = new int[nl - 1][nl - 1];
		Node[] tmp3 = new Node[nl - 1];
		int w = 0;
		for (int i = 0; i < nl; i++) {
			if (!nodes[i].label.equals(vertLabel)) {
				int[] tmp2 = new int[nl - 1];
				int z = 0;
				for (int j = 0; j < nl; j++) {
					if (!nodes[j].label.equals(vertLabel)) {
						tmp2[z] = matrix[i][j];
						z++;
					}
				}

				tmp1[w] = tmp2;
				tmp3[w] = nodes[i];
				w++;
			}
		}
		matrix = tmp1;
		nodes = tmp3;
	} // end of deleteVertex()

	public String[] kHopNeighbours(int k, String vertLabel) {
		for (int i = 0; i < nodes.length; i++) {
			if (nodes[i].label.equals(vertLabel)) {
				String[] s = new String[0];
				for (int j = i + 1; j < nodes.length; j++) {
					if (matrix[i][j] == 1) {
						String[] tmp = new String[s.length + 1];
						int z = 0;
						for (z = 0; z < s.length; z++) {
							tmp[z] = s[z];
						}
						tmp[z] = nodes[j].label;
						s = tmp;
						if (k > 1) {
							String[] kn = kHopNeighbours(k - 1, nodes[j].label);
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
				return s;
			}
		}

		// please update!
		return null;
	} // end of kHopNeighbours()

	public void printVertices(PrintWriter os) {
		String s = "";
		for (int i = 0; i < nodes.length; i++) {
			s += "(" + nodes[i].label + " , " + nodes[i].state + ")";
		}
		os.println(s);
	} // end of printVertices()

	public void printEdges(PrintWriter os) {
		for (int i = 0; i < matrix.length; i++) {
			String s = nodes[i].label + " ";
			for (int j = 0; j < matrix[0].length; j++) {
				if (matrix[i][j] == 1) {
					s += nodes[j].label;
					os.println(s);
					s = nodes[i].label + " ";
				}
			}

		}
	} // end of printEdges()

} // end of class AdjacencyMatrix
