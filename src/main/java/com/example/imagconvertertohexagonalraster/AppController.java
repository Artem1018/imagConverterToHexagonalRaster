package com.example.imagconvertertohexagonalraster;



import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import javax.imageio.ImageIO;


public class AppController implements Initializable {

    public ScrollPane haxaScrollPane;
    public ScrollPane rectScrollPane;
    public Button chooseFile;
    public Slider lambdaSlider;
    public Rectangle precisionLevelOne;
    public Rectangle precisionLevelTwo;
    public Rectangle precisionLevelThree;
    public ScrollPane previewPane;
    public Text messageOnScreen;
    public ToggleButton langUABtn;
    public ToggleButton langENGBtn;
    public Text previewText;
    public Button readMeButton;
    public Button precisionButton;
    public Button startButton;
    public Text  sliderText;



    public void onStartButtonClick(){
        rectScrollPane.setContent(null);
        haxaScrollPane.setContent(null);
        saveAsPng(previewPane, "img-preview");

        Application.image = new Image(new File("img-preview.png").toURI().toString());
        Application.imageWidth = (int) Application.image.getWidth() - 15;
        Application.imageHeight = (int) Application.image.getHeight() - 15;
        Application.setDefaultValue();

        execute();
    }

    public void onChooseFileButtonClick(){
         Application.setDefaultValue();
         Application.file = Application.fileChooser.showOpenDialog(Application.pane.getScene().getWindow());
         if (Application.file != null){
             messageOnScreen.setVisible(false);
             System.out.println(Application.file.getPath());

             Application.image = new Image(new File(Application.file.getPath()).toURI().toString());
             Application.imageWidth = (int) Application.image.getWidth();
             Application.imageHeight = (int) Application.image.getHeight();
             previewPane.setContent(new ImageView(Application.image));
         }
         if (Application.image == null)
             messageOnScreen.setVisible(true);

    }

    public void onLambdaSliderAction(){
            Application.lambda = lambdaSlider.getValue();
            haxaScrollPane.setContent(new Hexagon(lambdaSlider.getValue()).getPolygon());
    }

    public void onPrecisionButtonClick(){

            if (precisionLevelThree.getOpacity() == 1 ){
                precisionLevelThree.setOpacity(0);
                precisionLevelTwo.setOpacity(0);
                lambdaSlider.setMin(0.2);
                lambdaSlider.setMax(1);
                lambdaSlider.setMajorTickUnit(0.2);
                lambdaSlider.setMinorTickCount(1);
                lambdaSlider.setValue(0.2);
            }
            else if (precisionLevelOne.getOpacity() == 1 &&
                    precisionLevelTwo.getOpacity() != 1){
                precisionLevelTwo.setOpacity(1);
                lambdaSlider.setMin(1);
                lambdaSlider.setMax(10);
                lambdaSlider.setMajorTickUnit(2);
                lambdaSlider.setMinorTickCount(1);
                lambdaSlider.setValue(1);
            }
            else if (precisionLevelTwo.getOpacity() == 1){
                precisionLevelThree.setOpacity(1);
                lambdaSlider.setMin(10);
                lambdaSlider.setMax(20);
                lambdaSlider.setMajorTickUnit(2);
                lambdaSlider.setMinorTickCount(1);
                lambdaSlider.setValue(10);
            }
    }

    public void saveAsPng(Node node, String fname) {
        saveAsPng(node, fname, new SnapshotParameters());
    }

    public void saveAsPng(Node node, String fname, SnapshotParameters ssp) {
        WritableImage image = node.snapshot(ssp, null);
        File file = new File(fname+".png");
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
        } catch (IOException e) {
            System.out.println("Помилка збереження результату у форматі <png>");
        }
    }

    public void execute(){

        HBox hBox = new HBox();

        Application.drawRectangleRaster(Application.imageWidth, Application.imageHeight);
        saveAsPng(Application.rectangleSpace, "img-original");
        hBox.getChildren().add(new ImageView(new Image(new File("img-original.png").toURI().toString())));
        Application.setDefaultValue();
        rectScrollPane.setContent(new ImageView(new Image(new File("img-original.png").toURI().toString())));

        Application.drawHexagonalRaster(Application.imageWidth, Application.imageHeight);
        saveAsPng(Application.workSpace, "img-converted");
        hBox.getChildren().add(new ImageView(new Image(new File("img-converted.png").toURI().toString())));
        Application.setDefaultValue();
        haxaScrollPane.setContent(new ImageView(new Image(new File("img-converted.png").toURI().toString())));

        Application.finalImageResult.getChildren().add(hBox);

        saveAsPng(Application.finalImageResult, "img-compare");



        Application.setDefaultValue();

    }

    public void onHelpButtonClick() throws IOException, URISyntaxException {
        Desktop.getDesktop().browse(new URI("https://github.com/Artem1018/imagConverterToHexagonalRaster"));
    }

    public void onLangUAButtonClick(){
        langENGBtn.setSelected(false);
        langUABtn.setSelected(true);

        Application.getStage().setTitle("Конвертер зображень");

        previewText.setText("Попередній перегляд");
        precisionButton.setText("Точність");
        chooseFile.setText("Обрати зображення");
        startButton.setText("Пуск");
        sliderText.setText("Коефіціент розміру пікселя");
        readMeButton.setText("Довідка");
        messageOnScreen.setText("Оберіть зображення");
    }

    public void onLangENGButtonClick(){
        langUABtn.setSelected(false);
        langENGBtn.setSelected(true);

        Application.getStage().setTitle("Image converter");

        previewText.setText("Preview");
        precisionButton.setText("Accuracy");
        chooseFile.setText("Choose an image");
        startButton.setText("Start");
        sliderText.setText("Pixel size ratio");
        readMeButton.setText("Readme");

        messageOnScreen.setText("Select an image");

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        precisionLevelOne.setOpacity(1);
        precisionLevelTwo.setOpacity(1);
        precisionLevelThree.setOpacity(0);
    }
}