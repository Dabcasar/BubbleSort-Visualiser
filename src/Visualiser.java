import javafx.animation.PathTransition;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.HLineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



public class Visualiser extends Application {
    private final int WIDTH = 700, HEIGHT= 560;
    private final int SIZE = 100;
    private final float BAR_WIDTH = (float)WIDTH/SIZE;
    private float[] bar_height = new float[SIZE];
    PathTransition pathtransition1 = new PathTransition();
    PathTransition pathtransition2 = new PathTransition();
    public Group root = new Group();
    public List<List<Integer>> infoSwap = new ArrayList<>();
    public Rectangle [] sorted;
    public ArrayList<Float>copySorted = new ArrayList<>();
    public int numberOfSwaps = 0;
    public int numOfSorted = SIZE - 1;
    public Rectangle[] rect;
    @Override
    public void start(Stage stage) {
        stage.setResizable(false);
        fillArray();
        rect = createRectangles(bar_height);
        sorted = createRectangles(copySorted);
        Scene scene = new Scene(root, WIDTH, HEIGHT,Color.BLACK);
        swaps(rect);
        rect = new Rectangle[SIZE];
        rect = createRectangles(bar_height);
        root.getChildren().addAll(rect);
        stage.setScene(scene);
        stage.show();
        sort();
    }




    public void sort(){

        swap1(rect[infoSwap.get(numberOfSwaps).get(0)],rect[infoSwap.get(numberOfSwaps).get(1)],Duration.millis(5),infoSwap.get(numberOfSwaps).get(0),infoSwap.get(numberOfSwaps).get(1));

    }

    void swap2(Rectangle rect1, Rectangle rect2, Duration duration,int rect1Index, int rect2Index) {

        Path path1 = new Path(new MoveTo(rect2.getX() + BAR_WIDTH / 2,rect2.getY() + rect2.getHeight() / 2),new HLineTo(rect1.getX() - BAR_WIDTH/2));
        rect2.setFill(Color.RED);
        pathtransition1 = new PathTransition(duration, path1, rect2);
        pathtransition1.setOnFinished(event -> {
            rect2.setTranslateX(0);
            rect2.setTranslateY(0);
            rect2.setX(rect2.getX() - BAR_WIDTH);
            rect2.setFill(Color.AQUA);

            Rectangle temp = rect[rect1Index];
            rect[rect1Index] = rect[rect2Index];
            rect[rect2Index] = temp;

            if(rect[numOfSorted].getHeight() == sorted[numOfSorted].getHeight()){
                rect[numOfSorted].setFill(Color.PURPLE);
                numOfSorted--;
            }
            numberOfSwaps++;
            if(numberOfSwaps < infoSwap.size()){
                swap1(rect[infoSwap.get(numberOfSwaps).get(0)],rect[infoSwap.get(numberOfSwaps).get(1)],Duration.millis(5),infoSwap.get(numberOfSwaps).get(0),infoSwap.get(numberOfSwaps).get(1));
            }else{
                for(Rectangle r : rect){
                    r.setFill(Color.PURPLE);
                }
            }

        });

        pathtransition1.play();
    }


    void swap1( Rectangle rect1, Rectangle rect2, Duration duration,int rect1Index,int rect2Index) {
        Path path1 = new Path(new MoveTo(rect1.getX() + BAR_WIDTH / 2,rect1.getY() + rect1.getHeight()/2),new HLineTo(rect2.getX() + BAR_WIDTH/2));
        pathtransition2 = new PathTransition(duration, path1, rect1);
        rect1.setFill(Color.GOLD);
        pathtransition2.setOnFinished(event -> {
            rect1.setTranslateX(0);
            rect1.setTranslateY(0);
            rect1.setX(rect2.getX());
            rect1.setFill(Color.AQUA);
            swap2(rect1,rect2,Duration.millis(5),rect1Index,rect2Index);
        });
        pathtransition2.play();


    }
    private void swaps(Rectangle[] rect) {
        for(int i = 0; i<rect.length; i++){
            for(int j = 0; j<rect.length-1; j++){
                if(rect[j].getHeight() > rect[j+1].getHeight()){
                    ArrayList<Integer>oned = new ArrayList<>();
                    oned.add(j);
                    oned.add(j+1);
                    infoSwap.add(oned);
                    Rectangle temporary = rect[j];
                    rect[j] = rect[j+1];
                    rect[j+1] = temporary;
                }
            }
        }


    }


    private void fillArray() {
        ArrayList<Float> arr = new ArrayList<>();
        float step = (float)HEIGHT / SIZE;
        for(int i = 0; i <SIZE; i++){//I changed i from 0 to 1 and i<size to i<=size
            arr.add((float)i * step + 10);
            copySorted.add((float)i * step + 10);
        }

        Collections.shuffle(arr);
        for(int i = 0; i<arr.size(); i++){
            bar_height[i] = arr.get(i);
        }

    }
    private Rectangle[] createRectangles(float[] bar_height) {

        Rectangle [] rect = new Rectangle[bar_height.length];
        for(int i = 0; i<bar_height.length; i++){
            Rectangle rectangle = new Rectangle();
            rectangle.setX(i *  BAR_WIDTH);
            rectangle.setY(HEIGHT-bar_height[i]);
            rectangle.setWidth(BAR_WIDTH);
            rectangle.setHeight(bar_height[i]);
            rectangle.setFill(Color.AQUA);
            rect[i] = rectangle;
        }

        return rect;
    }

    private Rectangle[] createRectangles(ArrayList<Float> bar_height) {

        Rectangle [] rect = new Rectangle[bar_height.size()];
        for(int i = 0; i<bar_height.size(); i++){
            Rectangle rectangle = new Rectangle();
            rectangle.setX(i *  BAR_WIDTH);
            rectangle.setY(HEIGHT-bar_height.get(i));
            rectangle.setWidth(BAR_WIDTH);
            rectangle.setHeight(bar_height.get(i));
            rectangle.setFill(Color.AQUA);
            rect[i] = rectangle;
        }

        return rect;
    }

    public static void main(String[] args) {
        launch(args);
    }
}