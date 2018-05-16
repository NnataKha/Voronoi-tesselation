public class Point {
	double x;
	double y;

	public Point(double ax, double ay) {
		x = ax;
		y = ay;
	}

	public Point() {
		x = 0;
		y = 0;
	}

	public void setPoint(Point ap) {
		x = ap.x;
		y = ap.y;
	}

	public void setXY(double ax, double ay) {
		x = ax;
		y = ay;
	}

	public void copy(Point ap) {
		this.x = ap.x;
		this.y = ap.y;
	}


	//Get the first coordinate
	public double getX() {
		return this.x;
	}

	//Get the second coordinate
	public double getY() {
		return this.y;
	}

	// setting WPoint randomly
	public void setRandomPoint(int maxX, int maxY) {
		this.setXY(Math.floor(Math.random() * maxX), Math.floor(Math.random() * maxY));
	}

	
	// check if any two of three points coincide
	public boolean coincide(Point b, Point c) {
		boolean co = false;
		if ((this.x == b.x) && (this.y == b.y)) {
			co = true;
		}
		if ((c.x == b.x) && (c.y == b.y)) {
			co = true;
		}
		if ((this.x == c.x) && (this.y == c.y)) {
			co = true;
		}
		return co;
	}

	// three points belong to one line
	public boolean collinear(Point b, Point c) {
		boolean col = false;
		if ((b.x - this.x) * (c.y - b.y) == (c.x - b.x) * (b.y - this.y)) {
			col = true;
		}
		return col;
	}

	// Check if a point is inside the given circle
	public boolean pointInCircle(Circle c) {
		double px, py, ox, oy, r;
		px = this.x;
		py = this.y;
		ox = c.getP().x;
		oy = c.getP().y;
		r = c.getR();
		if ((ox - px) * (ox - px) + (oy - py) * (oy - py) < r * r) {
			return true;
		}
		return false;
	}
	
	// Get the nearest integer value of the x- coordinate of the point
	public int getIntX() {
		return (int) Math.floor(this.x);
	}
	
	// Get the nearest integer value of the y- coordinate of the point
	public int getIntY() {
		return (int) Math.floor(this.y);
	}
	

}
