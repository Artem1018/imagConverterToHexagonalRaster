package com.example.imagconvertertohexagonalraster;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;



public class Application extends javafx.application.Application {
    private static final FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("mainScreen.fxml"));
    protected static Pane pane;
    protected static Pane workSpace = new Pane();
    protected static Pane rectangleSpace = new Pane();
    protected static Pane finalImageResult = new Pane();

    protected static Image image;
    protected static final FileChooser fileChooser = new FileChooser();
    protected static File file;

    protected static double lambda = 4;
    protected static int imageWidth;
    protected static int imageHeight;

    private final Scene scene = new Scene(pane);

    static {
        try {
            pane = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage stage){
        stage.setTitle("Конвертор зображень");
        stage.setScene(scene);
        stage.show();
        stage.setMinWidth(1200);
        stage.setMinHeight(440);

    }

    public static void drawHexagonalRaster(int width, int height){
        int shiftX = (int) (10/lambda);
        int shiftY = 0;
        Hexagon hexagon;

        for (int i = 1; i < height; i++) {
            for (int j = 1; j < width; j++) {
                hexagon = new Hexagon(lambda, shiftX, shiftY, j, i);

                Color aRGB = image.getPixelReader().getColor(j,i);

                hexagon.getPolygon().setFill(aRGB);

                shiftX += hexagon.getHexagonWidthForShift();
                if (j + 1 == width)
                    shiftY += hexagon.getHexagonHeightForShift();
            }
            if (i%2 == 0)
                shiftX = (int) (10/lambda);
            else
                shiftX = 0;
        }
    }

    public static void drawRectangleRaster(int width, int height){
        int shiftX = 0;
        int shiftY = 0;
        Rectangle rectangle;

        for (int i = 1; i < height; i++) {
            for (int j = 1; j < width; j++) {
                rectangle = new Rectangle(lambda, shiftX, shiftY, j, i);
                Color aRGB = image.getPixelReader().getColor(j,i);

                rectangle.getPolygon().setFill(aRGB);
                shiftX += rectangle.getRectangleShift();
                if (j + 1 == width)
                    shiftY += rectangle.getRectangleShift();
            }
                shiftX = 0;
        }
    }

    public static void setDefaultValue(){
        workSpace.getChildren().clear();
        rectangleSpace.getChildren().clear();
        finalImageResult.getChildren().clear();
    }


    public static void main(String[] args) {
        launch();
    }
}