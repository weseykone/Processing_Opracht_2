import processing.core.PApplet;
import processing.data.Table;
import processing.data.TableRow;


public class OpdrachtC extends PApplet {

    // Multidimensional array to store all the double arrays
    private double[][] plotData;

    // Width and height of the graph
    private int graphWidth;
    private int graphHeight;

    public static void main(String args[]) {
        PApplet.main("OpdrachtC");
    }

    @Override
    public void settings() {
        size(1200, 675);
    }

    @Override
    public void setup() {
        graphWidth = 200;
        graphHeight = 135;

        readPlotData();


        noLoop();
    }

    private void readPlotData() {
        // We load the table and use the count variable to instantiate the double arrays
        Table table = loadTable("studentcijfers.csv", "header");
        int count = table.getRowCount();

        double[] lftd = new double[count];
        double[] ANA = new double[count];
        double[] DEV = new double[count];
        double[] PRJ = new double[count];
        double[] SKL = new double[count];

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
    }

    /**
     * getHeighest get's the highest value from the sequence
     * @param scatterData sequence to check
     * @return highest value from scatterData
     */
    private double getHeighest(double[] scatterData) {
        double compare = 0.0;
        for (double data : scatterData) {
            if (data > compare) {
                compare = data;
            }
        }
        return compare;
    }

    /**
     * draw is responsible for drawing the content on the applet
     */
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


    /**
     * drawGraphString draws a string on the graph on a certain position
     * @param text the Text which needs to be drawn on the graph
     * @param x X position of the Text
     * @param y Y position of the Text
     */
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

    /**
     * drawGraph is responsible for drawing the ScatterGraph on the window
     * @param x x-coordinate for translation of the graph
     * @param y y-coordinate for translation of the graph
     * @param xList ScatterPlot data for X axis
     * @param yList ScatterPlot data for Y axis
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


    /**
     * drawScatters draw ellipses on the map to represent actual scatters
     * @param x x-position of the ellipse
     * @param y y-position of the ellipse
     * @param max actual position on the screen
     */
    public void drawScatters(double x, double y, double[] max) {
        float[] items = getPositions(x,y, max);
        ellipse(items[0], items[1], 5, 5);
    }


    /**
     * getPositions computes the positions on which something needs to be drawn.
     * @param x x-position
     * @param y y-position
     * @param heights relative to screen positions
     * @return float[] with the right pixels to draw on
     */
    public float[] getPositions(double x, double y, double[] heights) {
        return new float[]{
                map((float) x, 0, (float) heights[0], 40, (float) graphWidth),
                map((float) y, 0, (float) heights[1], (float) graphHeight, 50)
        };
    }
}
