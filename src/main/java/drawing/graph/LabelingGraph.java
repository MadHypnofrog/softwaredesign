package drawing.graph;

import drawing.api.DrawingApi;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class LabelingGraph extends Graph {

    private final Set<Vertex> vertices;

    public LabelingGraph(DrawingApi drawingApi, Collection<Vertex> vertices) {
        super(drawingApi);
        this.vertices = new HashSet<>(vertices);
    }

    @Override
    public void doDraw() {
        vertices.forEach(vertex -> {
            drawVertex(vertex.getIndex());
            vertex.getEdges().forEach(this::drawEdge);
        });
    }

    @Override
    protected int getNumberOfVertices() {
        return vertices.size();
    }

}
