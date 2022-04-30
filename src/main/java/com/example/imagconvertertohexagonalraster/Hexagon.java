package com.example.imagconvertertohexagonalraster;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;


public class Hexagon {
    private final double[] points;
    private final Polygon polygon;


    Hexagon(double lambda, int shiftX, int shiftY, int cordX, int cordY){


        points = new double[]{
                10 / lambda + shiftX, 10 / lambda + shiftY,
                20 / lambda + shiftX, 5  / lambda + shiftY,          //......
                30 / lambda + shiftX, 10 / lambda + shiftY,         //......
                30 / lambda + shiftX, 25 / lambda + shiftY,         //......
                20 / lambda + shiftX, 30 / lambda + shiftY,         //......
                10 / lambda + shiftX, 25 / lambda + shiftY};

        polygon = new Polygon(points);
        polygon.setStroke(Color.TRANSPARENT);
        polygon.setFill(Color.TRANSPARENT);

        Application.workSpace.getChildren().add(polygon);
    }

    Hexagon(double lambda){


        points = new double[]{
                10 / lambda, 10 / lambda,
                20 / lambda, 5  / lambda,          //......
                30 / lambda, 10 / lambda,         //......
                30 / lambda, 25 / lambda,         //......
                20 / lambda, 30 / lambda,         //......
                10 / lambda, 25 / lambda};

        polygon = new Polygon(points);
        polygon.setStroke(Color.BLACK);
        polygon.setFill(Color.TRANSPARENT);
    }

    protected int getHexagonWidthForShift(){

        return (int) (this.points[4] - this.points[0]);
    }
    protected int getHexagonHeightForShift(){
        return (int) (this.points[9] - this.points[1]);
    }


    public Polygon getPolygon() {
        return polygon;
    }

}
