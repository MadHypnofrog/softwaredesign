package drawing;

import drawing.api.DrawingApi;
import drawing.api.DrawingApiAwt;
import drawing.api.DrawingApiJavaFX;
import drawing.graph.AdjacencyGraph;
import drawing.graph.Edge;
import drawing.graph.Graph;
import drawing.graph.LabelingGraph;
import drawing.graph.Vertex;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        if (args.length == 1 && args[0].equals("help")) {
            System.err.println("awt/javafx <path-to-graph> labeling/adjacency");
        } else if (args.length != 3) {
            System.err.println("Usage: <drawing-api> <path-to-graph> <graph-presenting>");
        } else {
            DrawingApi api = getDrawingApi(args[0]);
            Graph graph = readGraph(api, args[1], args[2].equals("labeling"));
            graph.drawGraph();
        }
    }

    private static DrawingApi getDrawingApi(String apiName) {
        if ("awt".equals(apiName)) {
            return new DrawingApiAwt();
        } else if ("javafx".equals(apiName)) {
            return new DrawingApiJavaFX();
        } else {
            throw new IllegalArgumentException("Invalid draw api name " + apiName);
        }
    }

    private static Graph readGraph(DrawingApi api, String pathToGraph, boolean isLabeling) {
        Collection<String> graphStr = readGraph(pathToGraph);
        if (isLabeling) {
            return new LabelingGraph(api, readLabeling(graphStr));
        } else {
            return new AdjacencyGraph(api, readAdjacency(graphStr));
        }
    }

    private static Collection<String> readGraph(String pathToGraph) {
        try {
            return Files.readAllLines(Paths.get(pathToGraph));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public static Collection<Vertex> readLabeling(Collection<String> graphStr) {
        List<int[]> graph = graphStr.stream()
                .map(line -> line.split(" "))
                .map(line -> Arrays.stream(line).mapToInt(Integer::parseInt).toArray())
                .collect(Collectors.toList());

        Map<Integer, Vertex> mapVertex = new HashMap<>();
        for (int i = 0; i < graph.size(); i++) {
            int index = i + 1;
            mapVertex.put(index, new Vertex(index));
            for (int other = 0; other < graph.get(i).length; other++) {
                if (graph.get(i)[other] == 1) {
                    mapVertex.get(index).addNeighbour(other + 1);
                }
            }
        }

        return mapVertex.values();
    }

    public static Collection<Edge> readAdjacency(Collection<String> graphStr) {
        return graphStr.stream()
                .map(line -> line.split(" "))
                .map(indices -> new Edge(Integer.valueOf(indices[0]), Integer.valueOf(indices[1])))
                .collect(Collectors.toList());
    }

}
