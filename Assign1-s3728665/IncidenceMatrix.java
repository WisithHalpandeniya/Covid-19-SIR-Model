import java.io.PrintWriter;

/**
 * Incidence matrix implementation for the GraphInterface interface.
 *
 * Your task is to complete the implementation of this class. You may add
 * methods, but ensure your modified class compiles and runs.
 *
 * @author Jeffrey Chan, 2021.
 */
public class IncidenceMatrix extends AbstractGraph {

	public class Node {
		public String label = "";
		public String state = "S";

		public Node(String l) {
			this.label = l;
		}
	}

	public int[][] matrix;
	public Node[] nodes = null;

	public IncidenceMatrix() {
		matrix = new int[0][0];
		nodes = new Node[0];
	} // end of IncidenceMatrix()

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
		int hl = vl == 0 ? 0 : matrix[0].length;
		int[][] t = new int[vl + 1][hl];
		for (i = 0; i < vl; i++) {
			t[i] = matrix[i];

		}
		t[i] = new int[hl];
		matrix = t;
	} // end of addVertex()

	public void addEdge(String srcLabel, String tarLabel) {

		int vl = matrix.length;
		int hl = vl == 0 ? 0 : matrix[0].length;
		for (int i = 0; i < vl; i++) {
			int[] t = new int[hl + 1];
			for (int j = 0; j < hl; j++) {
				t[j] = matrix[i][j];
			}
			matrix[i] = t;
		}

		int srcIndex = 0, tarIndex = 0;
		for (int i = 0; i < vl; i++) {
			if (nodes[i].label.equals(srcLabel))
				srcIndex = i;
			else if (nodes[i].label.equals(tarLabel))
				tarIndex = i;
		}

		matrix[srcIndex][hl] = 1;
		matrix[tarIndex][hl] = 1;

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
		int nl = matrix.length;
		int el = nl == 0 ? 0 : matrix[0].length;
		int srcIndex = 0, tarIndex = 0;
		for (int i = 0; i < nl; i++) {
			if (nodes[i].label.equals(srcLabel))
				srcIndex = i;
			else if (nodes[i].label.equals(tarLabel))
				tarIndex = i;
		}
		int indexEdge = 0;
		for (int i = 0; i < el; i++) {
			if (matrix[srcIndex][i] == 1 && matrix[tarIndex][i] == 1) {
				indexEdge = i;
				break;
			}
		}
		deleteColumnByIndex(indexEdge);

	} // end of deleteEdge()

	public void deleteColumnByIndex(int indexEdge) {
		int nl = matrix.length;
		int el = nl == 0 ? 0 : matrix[0].length;
		for (int i = 0; i < nl; i++) {
			int[] tmp = new int[el - 1];
			int z = 0;
			for (int j = 0; j < el; j++) {
				if (j != indexEdge) {
					tmp[z] = matrix[i][j];
					z++;
				}
			}
			matrix[i] = tmp;
		}
	}

	public void deleteRowByIndex(int index) {
		int nl = matrix.length;
		int el = nl == 0 ? 0 : matrix[0].length;
		int[][] tmp = new int[nl - 1][el];
		int z = 0;
		for (int i = 0; i < nl; i++) {
			if (i != index) {
				tmp[z] = matrix[i];
				z++;
			}
		}
		matrix = tmp;
	}

	public void deleteNodeByIndex(int index) {
		int nl = nodes.length;
		Node[] tmp = new Node[nl - 1];
		int z = 0;
		for (int i = 0; i < nl; i++) {
			if (i != index) {
				tmp[z] = nodes[i];
				z++;
			}
		}
		nodes = tmp;
	}

	public void deleteVertex(String vertLabel) {
		int nl = nodes.length;

		for (int i = 0; i < nl; i++) {
			if (nodes[i].label.equals(vertLabel)) {
				boolean a = true;
				while (a) {
					a = false;
					for (int j = 0; j < matrix[i].length; j++) {
						if (matrix[i][j] == 1) {
							a = true;
							deleteColumnByIndex(j);
							break;
						}
					}
				}

				deleteRowByIndex(i);
			}
		}
		for (int i = 0; i < nl; i++) {
			if (nodes[i].label.equals(vertLabel)) {
				deleteNodeByIndex(i);
				break;
			}
		}

	} // end of deleteVertex()

	public String[] kHopNeighbours(int k, String vertLabel) {
		if (k > matrix[0].length)
			k = matrix[0].length;
		for (int i = 0; i < nodes.length; i++) {
			if (nodes[i].label.equals(vertLabel)) {
				String[] s = new String[0];
				for (int j = 0; j < matrix[i].length; j++) {
					if (matrix[i][j] == 1) {
						for (int z = 0; z < matrix.length; z++) {
							if (matrix[z][j] == 1 && !nodes[z].label.equals(nodes[i].label)) {
								String[] tmp = new String[s.length + 1];
								int w = 0;
								for (w = 0; w < s.length; w++) {
									tmp[w] = s[w];
								}
								tmp[w] = nodes[z].label;
								s = tmp;
								if (k > 1) {
									String[] kn = kHopNeighbours(k - 1, nodes[z].label);
									if (kn.length > 1) {
										tmp = new String[s.length + kn.length - 1];
										int p = 0;
										for (p = 0; p < s.length; p++) {
											tmp[p] = s[p];
										}
										int o = 0;
										for (o = 0; o < kn.length; o++) {
											if (!kn[o].equals(vertLabel)) {
												tmp[p] = kn[o];
												p++;
											}
										}
										s = tmp;
									}

								}
							}
						}
					}

				}
				return s;
			}
		}

		// please update!
		return new String[0];
	} // end of kHopNeighbours()

	public void printVertices(PrintWriter os) {
		String s = "";
		for (int i = 0; i < nodes.length; i++) {
			s += "(" + nodes[i].label + " , " + nodes[i].state + ")";
		}
		os.println(s);
	} // end of printVertices()

	public void printEdges(PrintWriter os) {
		int nl = matrix.length;
		int el = nl == 0 ? 0 : matrix[0].length;

		for (int i = 0; i < nl; i++) {

			for (int j = 0; j < el; j++) {
				if (matrix[i][j] == 1) {
					String s = "";
					s += nodes[i].label + " ";
					for (int z = 0; z < nl; z++) {
						if (i != z && matrix[z][j] == 1) {
							s += nodes[z].label + " ";
						}
					}
					os.println(s);
				}
			}

		}

	} // end of printEdges()

} // end of class IncidenceMatrix
