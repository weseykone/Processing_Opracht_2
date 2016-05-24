import processing.core.PApplet;
import processing.data.Table;
import processing.data.TableRow;

import java.util.ArrayList;
import java.util.List;

public class OpdrachtB extends PApplet {

    Table table;
    List<Category> scatterData = new ArrayList<>();

    double[] maxValues;

    int graphWidth;
    int graphHeight;

    public static void main(String args[]) {
        PApplet.main("OpdrachtB");
    }

    @Override
    public void settings() {
        size(1200, 675);
    }

    @Override
    public void setup() {

        graphWidth = width - 1000;
        graphHeight = height - 540;
        table = loadTable("data.csv", "header");

        for (TableRow row : table.rows()) {

            int id = row.getInt("CAT");
            double x = row.getFloat("EIG1");
            double y = row.getFloat("EIG2");

            scatterData.add(new Category(id, x, y));
        }
        maxValues = getHeighest(scatterData);

        println(getHeighest(scatterData));
        noLoop();
    }


    public double[] getHeighest(List<Category> scatterData) {
        double compareX = 0.0;
        double compareY = 0.0;
        for (Category cat : scatterData) {
            if (cat.x > compareX) {
                compareX = cat.x;
            }
            if (cat.y > compareY) {
                compareY = cat.y;
            }
        }
        return new double[]{compareX, compareY};
    }

    @Override
    public void draw() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                int y = 120 * i;
                int x = 240 * j;
                if (i != j) {
                    drawGraph(x, y);
                } else {
                    drawGraphString("Leeftijd", x, y);
                }
            }
        }

    }


    private int[] getColor(Category cat) {
        switch (cat.category) {
            case 1:
                return new int[]{0, 0,255};
            case 2:
                return new int[]{0, 255,0};
            case 3:
                return new int[]{255, 0,0};
            case 4:
                return new int[]{255, 0,255};
        }
        return null;
    }

    private void drawGraphString(String text,int x, int y) {
        pushMatrix();
        translate(x,y);
        fill(0,0,0);
        textSize(30);
        text(text, 70, 93);
        fill(0,0,0,0);
        rect(40,30, 175,100);
        popMatrix();
    }

    private void drawGraph(int x, int y){
        pushMatrix();
        translate(x,y);
        line(40, 30, 40, graphHeight);
        line(40, graphHeight, graphWidth, graphHeight);

        for (Category cat : scatterData) {
            int[] colorCode = getColor(cat);
            fill(colorCode[0], colorCode[1], colorCode[2]);
            drawScatters(cat.x, cat.y, maxValues);
        }

        fill(0, 0, 0);
        textSize(10);
        text(String.valueOf(maxValues[0]), (float) graphWidth - 23, (float) graphHeight + 10);
        text(String.valueOf(maxValues[1]), 1, 40);
        text("0", 38, (float) graphHeight + 10);

        popMatrix();
    }

    private void drawScatters(double x, double y, double[] max) {
        float[] items = getPositions(x,y, max);
        ellipse(items[0], items[1], 5, 5);
    }

    private float[] getPositions(double x, double y, double[] heights) {
        return new float[]{
                map((float) x, 0, (float) heights[0] + 10, 20, (float) graphWidth),
                map((float) y, 0, (float) heights[1] + 200, (float) graphHeight, 50)
        };
    }
}
