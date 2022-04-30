package com.example.imagconvertertohexagonalraster;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;



public class Rectangle {
    private final double[] points;
    private final Polygon polygon;

    Rectangle(double lambda, int shiftX, int shiftY, int cordX, int cordY){

        points = new double[]{
                10 / lambda + shiftX, 5 / lambda + shiftY,
                30 / lambda + shiftX, 5 / lambda + shiftY,          //......
                30 / lambda + shiftX, 25 / lambda + shiftY,         //......
                10 / lambda + shiftX, 25 / lambda + shiftY};

        polygon = new Polygon(points);
        polygon.setStroke(Color.TRANSPARENT);
        polygon.setFill(Color.TRANSPARENT);
        Application.rectangleSpace.getChildren().add(polygon);

    }
    protected int getRectangleShift(){

        return (int) (this.points[4] - this.points[0]);
    }
    public Polygon getPolygon() {
        return polygon;
    }
}
