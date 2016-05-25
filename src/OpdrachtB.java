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

        graphWidth = width - 50;
        graphHeight = height - 50;

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
        drawGraph();
    }

    /**
     * getColor switches on the category and decides which color should be applied
     * @param cat Category object
     * @return int array with colorcode
     */
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
        return new int[]{0,0,0};
    }

    private void drawGraph(){
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