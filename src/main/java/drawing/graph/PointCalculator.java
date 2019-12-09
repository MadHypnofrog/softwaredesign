package drawing.graph;

import drawing.api.Point;

public class PointCalculator {

    private final double rotateAngle;
    private double zeroX;
    private double zeroY;
    private double width;
    private double height;

    public PointCalculator(double width, double height, int numberOfVertices) {
        this.width = width;
        this.height = height;
        this.rotateAngle = 2 * Math.PI / numberOfVertices;
        this.zeroX = width / 2;
        this.zeroY = height / 5;
    }

    public Point getPoint(int index) {
        Point currentPoint = Point.of(zeroX, zeroY);

        for (int i = 1; i < index; i++) {
            currentPoint = rotate(currentPoint);
        }

        return currentPoint;
    }

    private Point rotate(Point point) {
        double x1 = point.x - width / 2;
        double y1 = point.y - height / 2;
        double newX = Math.cos(rotateAngle) * x1 - Math.sin(rotateAngle) * y1 + width / 2;
        double newY = Math.sin(rotateAngle) * x1 + Math.cos(rotateAngle) * y1 + height / 2;
        return Point.of(newX, newY);
    }

}
