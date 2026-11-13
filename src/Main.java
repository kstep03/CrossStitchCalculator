/**
 * A program that calculates and predicts size, cost, skein count, and more.
 *
 *
 * Purdue University -- CS19300 -- Fall 2026 -- Final Project -- Cross Stitch Calculator
 *
 * @author Katie Stephenson
 * @version November 6, 2026
 */
public class Main {
    public static void main(String[] args) {
        IO.println("Hello! Welcome to Cross Stitch Calculator!");
        //testing branching
        //Asking user to input the width of project in counts, and refusing until answer is in correct format
        int width = 0;
        while (true) {
            IO.println("Enter width of project (in counts): ");
            String originalWidth = IO.readln();

            try {
                width = Integer.parseInt(originalWidth);
            } catch (NumberFormatException e) {
                IO.println("Not an amount.");
                continue;
            }

            if (width <= 0) {
                IO.println("Number has to be larger than 0");
                continue;
            }
            break;
        }

        //Asking user to input the height of project in counts, and refusing until answer is in correct format
        int height = 0;
        while (true) {
            IO.println("Enter height of project (in counts): ");
            String originalHeight = IO.readln();

            try {
                height = Integer.parseInt(originalHeight);
            } catch (NumberFormatException e) {
                IO.println("Not an amount.");
                continue;
            }

            if (height <= 0) {
                IO.println("Number has to be larger than 0");
                continue;
            }
            break;
        }

        //Asking user the AIDA Count, and refusing until answer is one of the accepted counts
        int aidaCount = 0;
        while (true) {
            IO.println("Enter AIDA count of fabric (e.g. 14): ");
            String origAidaCount = IO.readln();

            try {
                aidaCount = Integer.parseInt(origAidaCount);
            } catch (NumberFormatException e) {
                IO.println("Not an amount.");
                continue;
            }

            if (!(aidaCount == 6 || aidaCount == 8 || aidaCount == 11 || aidaCount == 14 || aidaCount == 16 || (aidaCount >= 18 && aidaCount < 23))) {
                IO.println("AIDA count " + aidaCount + " is not an accepted count of fabric. Choose a different AIDA count size.");
                continue;
            }
            break;
        }

        //calculates the weight and height of design in inches according to the count of fabric
        double widthInches = (double) width / aidaCount;
        double heightInches = (double) height / aidaCount;

        //estimates how many stitches are possible with one skein of thread
        int totalAreaStitches = width * height;
        int skeinsForStitches = 0;
        if (aidaCount > 18) {
            skeinsForStitches = 2300;
        } else if (aidaCount == 16) {
            skeinsForStitches = 2000;
        } else if (aidaCount == 14) {
            skeinsForStitches = 1700;
        } else if (aidaCount == 11) {
            skeinsForStitches = 1500;
        } else {
            skeinsForStitches = 1300;
        }

        //calculates the number of skeins that will be used with the AIDA Count considered
        int totalSkeins = (int) Math.ceil((double) totalAreaStitches / skeinsForStitches);
            //rounds up to nearest whole number, so you don't run out of thread while in the middle of the project

        //estimating cost: skein cost.
            //asks user if they want to input a specific cost of skeins, or if they want to use the set cost of 0.84 cents
        double costSkein = 0.84;
        IO.println("Do you have a specific price of thread that you want to input? (type yes if so)");
        String answerPrice = IO.readln();
        String lowerAnswerPrice = answerPrice.toLowerCase();
        if (lowerAnswerPrice.equals("yes")) {
            while (true) {
                IO.println("Enter price per skein: ");
                String origAidaCount = IO.readln();

                try {
                    costSkein = Double.parseDouble(origAidaCount);
                } catch (NumberFormatException e) {
                    IO.println("Not an amount.");
                    continue;
                }

                break;
            }
        }

        //estimating cost: fabric
            //preestablished, general cost because the cost of fabric varies widely across brands and stores
        double costOfFabric = 15.00;

        //calculating total cost
        double totalCost = (costOfFabric + (costSkein * totalSkeins));


        /*adding buffer variable for the fabric around the borders of the actual design
            buffer is used to know how big the finished product will be, if you have enough fabric, and the amount of
            fabric necessary for the frame
                if the user has no preference, the preestablished, general buffer of 5 inches will be used*/
        double bufferVariable = 5.0;
        IO.println("Do you want to change the buffer variable from 5 inches/side (type yes if so)");
        String answerBuffer = IO.readln();
        String lowerAnswerBuffer = answerBuffer.toLowerCase();
        if (lowerAnswerBuffer.equals("yes")) {
            while (true) {
                IO.println("Inches of buffer wanted around the outside of the project: ");
                String origBufferVariable = IO.readln();

                try {
                    bufferVariable = Double.parseDouble(origBufferVariable);
                } catch (NumberFormatException e) {
                    IO.println("Not an amount.");
                    continue;
                }

                break;
            }
        }

        //program outputs the proper information after the calculations
        IO.println("Finished project size: " + String.format("%.2f", widthInches) + " x " + String.format("%.2f", heightInches) + " inches");
        IO.println("Size of fabric needed: " + String.format("%.2f", (widthInches + (2 * bufferVariable))) + " x " + String.format("%.2f", (heightInches + (2 * bufferVariable))) + " inches");
        IO.println("Skeins of thread needed: " + totalSkeins);
        IO.println("Total estimated cost: $" + String.format("%.2f", totalCost));
    }
}
