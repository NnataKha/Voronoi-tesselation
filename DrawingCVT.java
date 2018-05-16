import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

@SuppressWarnings("serial")
public class DrawingCVT extends JFrame {
	public static final int CANVAS_WIDTH = 1200;
	public static final int CANVAS_HEIGHT = 600;
	private DrawCanvas canvas; // the custom drawing canvas (extends JPanel)

	class DrawCanvas extends JPanel {
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			int x, y;
			// drawing points
			g.setColor(Color.BLACK);
			for (int i = 0; i < m; i++) {
				x = points[i].getIntX();
				y = points[i].getIntY();
				g.fillOval(x - 3, y - 3, 6, 6);
				g.drawString("" + i, x, y-4);
			}

			// drawing Delaune Triangulation
			g.setColor(Color.GREEN);
			for (int i = 0; i < m; i++) {
				for (int j = i + 1; j < m; j++) {
					if (VoronoiCells[i][j].getP1() != null) {
						int x1 = points[i].getIntX();
						int x2 = points[j].getIntX();
						int y1 = points[i].getIntY();
						int y2 = points[j].getIntY();
						g.drawLine(x1, y1, x2, y2);
					}
				}
			}
			
			// drawing the bound points of the set
			g.setColor(Color.BLUE);
			for (int i = 0; i < boundPoints.size(); i++) {
				x = points[boundPoints.get(i)].getIntX();
				y = points[boundPoints.get(i)].getIntY();
				g.fillOval(x - 3, y - 3, 6, 6);				
			}
			
			// drawing Voronoi diagram
			g.setColor(Color.gray);
			for (int i = 0; i < m; i++) {
				for (int j = 0; j < m; j++) {
					if (VoronoiCells[i][j].getP1() != null) {
						x = VoronoiCells[i][j].getP1().getIntX();
						y = VoronoiCells[i][j].getP1().getIntY();
						int x1 = VoronoiCells[i][j].getP2().getIntX();
						int y1 = VoronoiCells[i][j].getP2().getIntY();
						g.drawLine(x1, y1, x, y);
					}
				}
			}
		}
	}

	/** Constructor to set up the GUI */
	public DrawingCVT() {
		dataInit();
		VoronoiCellsInit();
		// Set up a custom drawing JPanel
		canvas = new DrawCanvas();
		canvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));

		// Add the panel to this JFrame
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		cp.add(canvas, BorderLayout.CENTER);

		// "this" JFrame fires KeyEvent
		addKeyListener(new KeyAdapter() {
			int k = 0;
			@Override
			public void keyPressed(KeyEvent evt) {
				if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
					voronoi();
				}
				if (evt.getKeyCode() == KeyEvent.VK_SPACE){
					for (int i = 0; i < 20; i++){
						voronoi();
						centering();
						k++;
					}
					System.out.println("step " + k);
				}
				repaint();
			}
		});

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Handle the CLOSE
														// button
		setTitle("Centered Voronoi Tesselation");
		pack(); // pack all the components in the JFrame
		setVisible(true); // show it
		requestFocus(); // set the focus to JFrame to receive KeyEvent
	}

	int m = 100;
	Point[] points = new Point[m];

	// setting points randomly
	void dataInit() {
		for (int i = 0; i < m; i++) {
			Point p = new Point();
			p.setRandomPoint(CANVAS_WIDTH, CANVAS_HEIGHT);
			points[i] = p;
		}
	}

	Point[] points1 = new Point[m];
	Line[][] VoronoiCells = new Line[m][m];
	ArrayList<Integer> boundPoints = new ArrayList();
	ArrayList<Integer> innerPoints = new ArrayList();

	// ArrayList<Point> centeredPoints = new ArrayList();

	// setting an empty array VoronoiCells
	void VoronoiCellsInit() {
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < m; j++) {
				VoronoiCells[i][j] = new Line(null, null);
			}
		}
	}

	public double check(double x1, double x2, double y1, double y2, double x,
			double y) {
		return (y2 - y1) * (x - x1) - (x2 - x1) * (y - y1);
	}

	// Defining a ray that defines a side of Voronoi cell and is perpendicular to the side i j
	public Line buildRay(int i, int j, int k) {
		Point o1 = new Point();
		Circle c = new Circle(points[i], points[j], points[k]);
		o1.setPoint(c.getP());
		double x1, x2, x3, y1, y2, y3, x, y;
		x1 = points[i].getX();
		x2 = points[j].getX();
		x3 = points[k].getX();
		y1 = points[i].getY();
		y2 = points[j].getY();
		y3 = points[k].getY();
		x = CANVAS_WIDTH;
		if (y1 != y2) {
			y = y1 - (x2 - x1) * (x - x1) / (y2 - y1);
		} else {
			y = CANVAS_HEIGHT;
		}

		if (check(x1, x2, y1, y2, x3, y3) * check(x1, x2, y1, y2, x, y) > 0) {
			x = -CANVAS_WIDTH;
			if (y1 != y2) {
				y = y1 - (x2 - x1) * (x - x1) / (y2 - y1);
			}
		}
		Point o2 = new Point(x, y);
		Line l = new Line(o1, o2);
		return l;
	}

	// calculating voronoi tesselation
	public void voronoi() {
		VoronoiCellsInit();
		// building mxm array VoronoiCells which contain coordinates of Voronoi cells' bounds
		Point p1 = new Point();
		Point p2 = new Point();
		Point p3 = new Point();
		for (int i = 0; i < m; i++) {
			p1 = points[i];
			for (int j = i + 1; j < m; j++) {
				p2 = points[j];
				for (int k = j + 1; k < m; k++) {
					p3 = points[k];
					if (!p1.collinear(p2, p3) && !p1.coincide(p2, p3)) {
						Circle kolo = new Circle(p1, p2, p3);
						if (kolo.pointIn(i, j, k, points) == false) {
							if (VoronoiCells[i][j].getP1() == null) {
								VoronoiCells[i][j] = buildRay(i, j, k);
							} else {
								VoronoiCells[i][j].setP2(kolo.getP());
							}
							if (VoronoiCells[i][k].getP1() == null) {
								VoronoiCells[i][k] = buildRay(i, k, j);
							} else {
								VoronoiCells[i][k].setP2(kolo.getP());
							}
							if (VoronoiCells[k][j].getP1() == null) {
								VoronoiCells[k][j] = buildRay(k, j, i);
							} else {
								VoronoiCells[k][j].setP2(kolo.getP());
							}
							VoronoiCells[j][i] = VoronoiCells[i][j];
							VoronoiCells[k][i] = VoronoiCells[i][k];
							VoronoiCells[j][k] = VoronoiCells[k][j];

						}
					}
				}
			}
		}
		for (int i = 0; i < m; i++) {
			for (int j = i + 1; j < m; j++) {
			}
		}
	}

	public void bound() {
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < m; j++) {
				if (VoronoiCells[i][j].getP2() != null
						&& Math.abs(VoronoiCells[i][j].getP2().getX()) >= CANVAS_WIDTH) {
					boundPoints.add(i);
				}
			}
			if (!boundPoints.contains(i)) {
				innerPoints.add(i);
			}
		}
	}

	public void centering() {
		bound();
		for (int i = 0; i < m; i++) {
			points1[i] = new Point(points[i].getX(), points[i].getY());
		}
		int k;
		for (int i = 0; i < innerPoints.size(); i++) {
			double midX = 0;
			double midY = 0;
			double n = 0;
			k = innerPoints.get(i);
			for (int j = 0; j < m; j++) {
				if (VoronoiCells[k][j].getP1() != null) {
					midX = midX + points[j].getX();
					midY = midY + points[j].getY();
					n = n + 1;
				}
			}
			Point p = new Point(midX / n, midY / n);
			points1[k].setPoint(p);
			// centeredPoints.add(p);
		}
		for (int i = 0; i < m; i++) {
			points[i].setPoint(points1[i]);
		}
	}

	/** The entry main() method */
	public static void main(String[] args) {
		// Run GUI codes on the Event-Dispatcher Thread for thread safety
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				System.out.println("Press ENTER to draw Delaune triangulation");
				System.out.println("Press SPACE to draw centered points step by step");
				DrawingCVT d = new DrawingCVT(); 
			}
		});
	}
}