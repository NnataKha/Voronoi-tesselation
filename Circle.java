public class Circle {
	Point o; // Center
	double r; // Radius

	// Constructing a circle with given center and radius
	public Circle(Point a, double ar) {
		o.setPoint(a);
		r = ar;
	}

	// Constructing a default circle
	public Circle() {
		o = null;
		r = 0;
	}
	

	public void setC(double x, double y, double ar) {
		o.setXY(x, y);
		r = ar;
	}

	//For given 3 point calculate the circle center and radius 
	public Circle(Point a, Point b, Point c) {
		double r, ax, ay, bx, by, cx, cy, x, y;
		o = new Point(0.0, 0.0);
		ax = a.getX();
		ay = a.getY();
		bx = b.getX();
		by = b.getY();
		cx = c.getX();
		cy = c.getY();
		
		if (ax == bx){
			x = (Math.pow(cx, 2) - Math.pow(ax, 2) + (cy -ay) * (cy - by)) / (2 * (cx - ax));
			y = (ay + by) / 2;
			r = Math.sqrt((x - ax)*(x - ax) + (y - ay)*(y - ay));
			} else {
				double g1 = Math.pow(ax, 2) - Math.pow(bx, 2) + Math.pow(ay, 2) - Math.pow(by, 2);
				double g2 = Math.pow(ax, 2) - Math.pow(cx, 2) + Math.pow(ay, 2) - Math.pow(cy, 2);
				
				y = 0.5 * (g2 * (ax - bx) - g1 * (ax - cx)) / ((ay - cy) * (ax - bx) - (ay - by) * (ax - cx));
				x = (0.5 * g1 - y * (ay - by)) / (ax - bx);
				
				r = Math.sqrt(Math.pow(x - ax, 2) + Math.pow(y-ay, 2));
			}
		setC(x, y, r);
	}

	// Get circle center
	public Point getP() {
		return this.o;
	}

	// Get circle radius
	public double getR() {
		return this.r;
	}

	// Check if any of the points from the array is inside the given circle
	public boolean pointIn(int i1, int i2, int i3, Point[] ap) {
		for (int i = 0; i < ap.length; i++) {
			if (i != i1 && i != i2 && i != i3) {
				if (ap[i].pointInCircle(this)) {
					return true;
				}
			}
		}
		return false;
	}

}
