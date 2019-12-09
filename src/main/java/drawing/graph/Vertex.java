package drawing.graph;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Vertex {

    private int index;
    private List<Integer> neighbours;

    public Vertex(int index) {
        this.index = index;
        neighbours = new ArrayList<>();
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void addNeighbour(Integer other) {
        neighbours.add(other);
    }

    public List<Edge> getEdges() {
        return neighbours.stream().map(x -> new Edge(this.index, x)).collect(Collectors.toList());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vertex)) return false;
        Vertex that = (Vertex) o;
        return index == that.index;
    }
}
