public class Line {
	Point p1;
	Point p2;

	// Constructing a line given by two points
	public Line(Point ap, Point bp) {
		p1 = ap;
		p2 = bp;
	}

	// Constructing a line given by x1, y1, x2, y2
	public Line(double x1, double y1, double x2, double y2) {
		p1.x = x1;
		p1.y = y1;
		p2.x = x2;
		p2.y = y2;
	}

	// Default construction
	public Line() {
		p1 = null;
		p2 = null;
	}
	
	// Set the first point in line
	public void setP1(Point a) {
		p1.setPoint(a);
	}

	// Set the second point in line
	public void setP2(Point a) {
		p2.setPoint(a);
	}

	// Get the first point in line
	public Point getP1() {
		return p1;
	}

	// Get the second point in line
	public Point getP2() {
		return p2;
	}
	
	// Get the nearest integer value of the x- coordinate of point p1 of the line
	public int getIntP1X() {
		return (int) Math.floor(this.p1.x);
	}
	
	// Get the nearest integer value of the y- coordinate of point p1 of the line
	public int getIntP1Y() {
		return (int) Math.floor(this.p1.y);
	}
	
	// Get the nearest integer value of the x- coordinate of point p2 of the line
	public int getIntP2X() {
		return (int) Math.floor(this.p2.x);
	}
	
	// Get the nearest integer value of the y- coordinate of point p2 of the line
	public int getIntP2Y() {
		return (int) Math.floor(this.p2.y);
	}

}
