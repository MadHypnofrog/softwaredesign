package drawing.graph;

import drawing.api.DrawingApi;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class AdjacencyGraph extends Graph {

    private final List<Edge> edges;

    public AdjacencyGraph(DrawingApi drawingApi, Collection<Edge> edges) {
        super(drawingApi);
        this.edges = new ArrayList<>(edges);
    }

    @Override
    public void doDraw() {
        for (int vertice = 1; vertice <= getNumberOfVertices(); vertice++) {
            drawVertex(vertice);
        }
        edges.forEach(this::drawEdge);
    }

    @Override
    protected int getNumberOfVertices() {
        return edges.stream()
                .flatMap(e -> Stream.of(e.getFrom(), e.getTo()))
                .max(Comparator.naturalOrder()).get();
    }

}
