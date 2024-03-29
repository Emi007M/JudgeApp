/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package judgeApp.model;

import javafx.event.EventHandler;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.StackPane;

/**
 *
 * @author Emilia
 */
public class ZoomHandler implements EventHandler<ScrollEvent> {

    private static final double MAX_SCALE = 1.0d;
    private static final double MIN_SCALE = 0.5d;
    private final StackPane nodeToZoom;

    public ZoomHandler(StackPane nodeToZoom) {
        this.nodeToZoom = nodeToZoom;
    }

    @Override
    public void handle(ScrollEvent scrollEvent) {
        if (scrollEvent.isControlDown()) {
            final double scale = calculateScale(scrollEvent);
            nodeToZoom.setScaleX(scale);
            nodeToZoom.setScaleY(scale);
            scrollEvent.consume();
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
