package drawing.api;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

import static drawing.api.DrawingUtils.HEIGHT;
import static drawing.api.DrawingUtils.WIDTH;

public class DrawingApiAwt implements DrawingApi {

    private final List<Shape> shapes;

    public DrawingApiAwt() {
        this.shapes = new ArrayList<>();
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
        double x = radius * 2;
        shapes.add(new Ellipse2D.Double(p.x - radius, p.y - radius, x, x));
    }

    @Override
    public void drawLine(Point p1, Point p2) {
        shapes.add(new Line2D.Double(p1.x, p1.y, p2.x, p2.y));
    }

    @Override
    public void visualize() {
        Frame frame = new GraphFrame();
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });
        frame.setSize(WIDTH, HEIGHT);
        frame.setVisible(true);
    }

    private class GraphFrame extends Frame {

        public GraphFrame() {
            super("Awt Graph");
        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);
            Graphics2D g2 = (Graphics2D) g;
            shapes.forEach(shape -> {
                if (shape instanceof Line2D.Double) {
                    g2.setPaint(Color.BLACK);
                    g2.draw(shape);
                } else {
                    g2.setPaint(Color.BLACK);
                    g2.fill(shape);
                }
            });
        }
    }

}
