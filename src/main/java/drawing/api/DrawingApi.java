package drawing.api;

public interface DrawingApi {

    double getDrawingAreaWidth();

    double getDrawingAreaHeight();

    void drawCircle(Point p, double radius);

    void drawLine(Point p1, Point p2);

    void visualize();
}