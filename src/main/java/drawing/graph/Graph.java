package drawing.graph;

import drawing.api.DrawingApi;
import drawing.api.Point;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static drawing.api.DrawingUtils.CIRCLE_RADIUS;

public abstract class Graph {

    private final Map<Integer, Point> drawnVertices;
    private final Set<Edge> drawnEdges;
    private final DrawingApi drawingApi;
    private PointCalculator calculator;

    public Graph(DrawingApi drawingApi) {
        this.drawingApi = drawingApi;
        this.drawnVertices = new HashMap<>();
        this.drawnEdges = new HashSet<>();
    }

    public void drawGraph() {
        calculator = new PointCalculator(
                drawingApi.getDrawingAreaWidth(),
                drawingApi.getDrawingAreaHeight(),
                getNumberOfVertices()
        );
        doDraw();
        drawingApi.visualize();
    }

    protected Point drawVertex(int index) {
        if (drawnVertices.containsKey(index)) {
            return drawnVertices.get(index);
        }
        Point p = calculator.getPoint(index);
        drawnVertices.put(index, p);
        drawingApi.drawCircle(p, CIRCLE_RADIUS / 2);

        return p;
    }

    protected void drawEdge(Edge edge) {
        if (drawnEdges.contains(edge))
            return;

        Point p1 = drawVertex(edge.getFrom());
        Point p2 = drawVertex(edge.getTo());

        drawingApi.drawLine(p1, p2);
        drawnEdges.add(edge);
    }

    protected abstract void doDraw();

    protected abstract int getNumberOfVertices();

}
