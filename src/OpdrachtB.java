import processing.core.PApplet;
import processing.data.Table;
import processing.data.TableRow;

import java.util.ArrayList;
import java.util.List;

public class OpdrachtB extends PApplet {

    Table table;
    List<Category> scatterData = new ArrayList<>();

    // Double arrays to store the data
    double[] ANA;
    double[] lftd;
    double[] DEV;
    double[] PRJ;
    double[] SKL;

    // Multidimensional array to store all the double arrays
    double[][] plotData;

    // Width and height of the graph
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
        graphWidth = 200;
        graphHeight = 135;

        // We load the table and use the count variable to instantiate the double arrays
        table = loadTable("studentcijfers.csv", "header");
        int count = table.getRowCount();

        lftd = new double[count];
        ANA = new double[count];
        DEV = new double[count];
        PRJ = new double[count];
        SKL = new double[count];

        int i = 0;

        // The values of the rows are put in the arrays on the place of i
        for(TableRow row : table.rows()) {
            double l = row.getDouble("lftd");
            double a = row.getDouble("ANA");
            double d = row.getDouble("DEV");
            double p = row.getDouble("PRJ");
            double s = row.getDouble("SKL");

            lftd[i] = l;
            ANA[i] = a;
            DEV[i] = d;
            PRJ[i] = p;
            SKL[i] = s;
            i++;
        }

        // This array will be used to loop over and get the data from
        plotData = new double[][] {lftd, ANA, DEV, PRJ, SKL};

        noLoop();
    }

    // This method will return the heigest value of the data in the array that is given as a parameter
    public double getHeighest(double[] scatterData) {
        double compare = 0.0;
        for (double data : scatterData) {
            if (data > compare) {
                compare = data;
            }
        }
        return compare;
    }

    // Automatically generated method by processing
    @Override
    public void draw() {
        // String array for the column names
        String[] columns = {"Leeftijd", "Analyse", "Development", "Project", "Skills"};

        //This for loop is used to draw the graphs and position them
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                // These 2 variables are used to position the graph
                int y = 120 * i;
                int x = 240 * j;

                if (i != j) {
                    drawGraph(x, y, plotData[j], plotData[i]);
                } else {
                    drawGraphString(columns[i], x, y);
                }
            }
        }

        // The text at the top of the matrix
        fill(0,0,0);
        textSize(20);
        text("Matrix plot of leeftijd, analyse, development, project and skills", (width / 2) - (textWidth("Matrix plot of leeftijd, analyse, development, project and skills") / 2), 18);

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

    // Method to draw the boxes and the text of the name of the columns
    private void drawGraphString(String text,int x, int y) {
        pushMatrix();
        translate(x,y);
        fill(0,0,0);
        textSize(25);
        text(text, 50, 93);
        fill(0,0,0,0);
        rect(40,30, 175,100);
        popMatrix();
    }

    /*
    The actual method that draws the graph. Takes 2 int parameters x and y to use for translation of the graph.
    It also takes 2 double arrays as parameters to use as the data which needs to be drawn on the scatterplots.
    */

    private void drawGraph(int x, int y, double[] xList, double[] yList){
        pushMatrix();
        translate(x,y);
        line(40, 30, 40, graphHeight);
        line(40, graphHeight, graphWidth, graphHeight);

        double maxValueX = getHeighest(xList);
        double maxValueY = getHeighest(yList);


        for (int i = 0; i < xList.length; i++) {
            //int[] colorCode = getColor(cat);
            //fill(colorCode[0], colorCode[1], colorCode[2]);
            fill(0,0,255);
            drawScatters(xList[i], yList[i], new double[]{maxValueX,maxValueY});
        }

        fill(0, 0, 0);
        textSize(10);
        text(String.valueOf(maxValueX), (float) graphWidth - 20, (float) graphHeight + 10);
        text(String.valueOf(maxValueX / 2), (float) graphWidth - 90, (float) graphHeight + 10);
        text(String.valueOf(maxValueY), 15, 40);
        text(String.valueOf(maxValueY / 2), 15, 90);
        text("0", 38, (float) graphHeight + 10);

        popMatrix();
    }

    /*
    This method is used to draw the data on the map as ellipses.
    The method takes 2 double parameters which are used to compute the positions of the ellipses.
    This is the same for the double array.
    */
    private void drawScatters(double x, double y, double[] max) {
        float[] items = getPositions(x,y, max);
        ellipse(items[0], items[1], 5, 5);
    }

    /*
    This method computes the positions on which something needs to be drawn.
    The method takes 2 doubles as parameters which are used to compute the position.
    This is the same for the double array.
    */
    private float[] getPositions(double x, double y, double[] heights) {
        return new float[]{
                map((float) x, 0, (float) heights[0], 40, (float) graphWidth),
                map((float) y, 0, (float) heights[1], (float) graphHeight, 50)
        };
    }
}
