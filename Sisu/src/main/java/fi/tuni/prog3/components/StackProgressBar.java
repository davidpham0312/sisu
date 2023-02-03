package fi.tuni.prog3.components;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;

public class StackProgressBar extends Canvas {
    private GraphicsContext graphicsContext;
    private final double horizontalOffset = 3.0;
    private final double vertialOffset = 5.0;
    private final double arcWidth = 10.0;
    private final double arcHeight = 10.0;

    private final Color borderBarColor = Color.rgb(170, 170, 170);
    private final Color outerGradientColor = Color.rgb(240, 240, 240);
    private final Color centralGradientColor = Color.WHITE;
    private final Color completedBarColor = Color.rgb(0, 162, 237);
    private final Color registeredBarColor = Color.rgb(255, 153, 0);
    private double completedPercent;
    private double registeredPercent;


    public StackProgressBar() {
        this.graphicsContext = getGraphicsContext2D();
        this.completedPercent = 0.0;
        this.registeredPercent = 0.0;

        widthProperty().addListener(evt -> drawProgressBar());
        heightProperty().addListener(evt -> drawProgressBar());
    }

    public void setProgress(double newCompletedPercent, double newRegisteredPercent) {
        completedPercent = newCompletedPercent;
        registeredPercent = newRegisteredPercent;
        drawProgressBar();
    }


    private void drawProgressBar() {
        double canvasWidth = getWidth();
        double canvasHeight = getHeight();
        drawBarBackground(canvasWidth, canvasHeight);
        drawBar(canvasWidth, canvasHeight);
    }

    private void drawBarBackground(double canvasWidth, double canvasHeight) {
        graphicsContext.clearRect(0, 0, canvasWidth, canvasHeight);

        Stop[] stops = {new Stop(0, outerGradientColor),
                new Stop(0.5, centralGradientColor),
                new Stop(1, outerGradientColor)};

        LinearGradient linearGradient = new LinearGradient(0, 0,
                0, 1, true, CycleMethod.NO_CYCLE, stops);

        graphicsContext.setFill(linearGradient);
        graphicsContext.fillRoundRect(0.0, 0.0, canvasWidth, canvasHeight, arcWidth, arcHeight);
        graphicsContext.setStroke(borderBarColor);
        graphicsContext.strokeRoundRect(0.0, 0.0, canvasWidth, canvasHeight, arcWidth, arcHeight);
    }

    private void drawBar(double canvasWidth, double canvasHeight) {
        double barMaxLength = canvasWidth - 2 * horizontalOffset;
        double barHeight = canvasHeight - 2 * vertialOffset;
        double completedBarLength = completedPercent * barMaxLength;
        double registeredBarLength = registeredPercent * barMaxLength;

        if (completedBarLength + registeredBarLength <= barMaxLength) {
            drawCompletedBar(completedBarLength, barHeight);
            drawRegisteredBar(registeredBarLength, completedBarLength, barHeight);
        }
        else if (completedBarLength > barMaxLength) {
            drawCompletedBar(barMaxLength, barHeight);
        }
        else {
            drawCompletedBar(completedBarLength, barHeight);
            drawRegisteredBar(barMaxLength - completedBarLength, completedBarLength, barHeight);
        }
    }

    private void drawCompletedBar(double completedBarLength, double barHeight) {
        graphicsContext.setFill(completedBarColor);
        graphicsContext.fillRect(horizontalOffset,
                vertialOffset,
                completedBarLength,
                barHeight);
    }

    private void drawRegisteredBar(double registeredBarLength, double completedBarLength, double barHeight) {
        graphicsContext.setFill(registeredBarColor);
        graphicsContext.fillRect(horizontalOffset + completedBarLength,
                vertialOffset,
                registeredBarLength,
                barHeight);
    }

    public double getCompletedPercent() {
        return completedPercent;
    }

    public double getRegisteredPercent() {
        return registeredPercent;
    }

    public Color getCompletedBarColor() {
        return completedBarColor;
    }

    public Color getRegisteredBarColor() {
        return registeredBarColor;
    }
}
