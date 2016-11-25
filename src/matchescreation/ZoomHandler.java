/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matchescreation;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.input.ScrollEvent;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.StackPane;
/**
 *
 * @author Emilia
 */
 

public class ZoomHandler implements EventHandler<ScrollEvent> {
    private static final double MAX_SCALE = 1.0d;
    private static final double MIN_SCALE = 0.5d;
    private StackPane nodeToZoom;
    private ScrollPane scrollPane;
    private Node node;

    public ZoomHandler(StackPane nodeToZoom, Node node) {
        this.nodeToZoom = nodeToZoom;
       // scrollPane = (ScrollPane) nodeToZoom.getParent();
    }

    @Override
    public void handle(ScrollEvent scrollEvent) {
        if (scrollEvent.isControlDown()) {
            final double scale = calculateScale(scrollEvent);
            nodeToZoom.setScaleX(scale);
            nodeToZoom.setScaleY(scale);
            
//            nodeToZoom.relocate(-1000, 0);
//            node.relocate(-200,-200);

            scrollEvent.consume();
            
            
            
             // runlater as we want to size the container after a layout pass has been performed on the scaled node.
    Platform.runLater(new Runnable() { 
      @Override public void run() {
//        nodeToZoom.setPrefSize(
//          Math.max(nodeToZoom.getBoundsInParent().getMaxX(), scrollPane.getViewportBounds().getWidth()),
//          Math.max(nodeToZoom.getBoundsInParent().getMaxY(), scrollPane.getViewportBounds().getHeight())
//        );
      }
    });
        }
    }

    private double calculateScale(ScrollEvent scrollEvent) {
        double scale = nodeToZoom.getScaleX() + scrollEvent.getDeltaY() / 300;

        if (scale <= MIN_SCALE) {
            scale = MIN_SCALE;
        } else if (scale >= MAX_SCALE) {
            scale = MAX_SCALE;
        }
        return scale;
    }
}


