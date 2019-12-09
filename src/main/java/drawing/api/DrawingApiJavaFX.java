package drawing.api;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

import static drawing.api.DrawingUtils.HEIGHT;
import static drawing.api.DrawingUtils.WIDTH;

public class DrawingApiJavaFX implements DrawingApi {

    private static List<Shape> shapes = null;

    public DrawingApiJavaFX() {
        shapes = new ArrayList<>();
    }

    @Override
    public double getDrawingAreaWidth() {
        return WIDTH;
    }

    @Override
    public double getDrawingAreaHeight() {
        return HEIGHT;
    }

    @Override
    public void drawCircle(Point p, double radius) {
        shapes.add(new Circle(p.x, p.y, radius));
    }

    @Override
    public void drawLine(Point p1, Point p2) {
        shapes.add(new Line(p1.x, p1.y, p2.x, p2.y));
    }

    @Override
    public void visualize() {
        Application.launch(GraphApplication.class);
    }

    public static class GraphApplication extends Application {

        @Override
        public void start(Stage stage) {
            stage.setTitle("JavaFx Graph");
            Group root = new Group();

            shapes.forEach(shape -> root.getChildren().add(shape));

            stage.setScene(new Scene(root, WIDTH, HEIGHT));
            stage.show();
        }
    }

}
