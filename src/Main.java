import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
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
            //branched code

            //I added a boolean so I can add multiple entries on one run. The only way to end the program is to press "3" exactly to quit
            boolean running = true;

            //MENU options, dependent upon user input
            while (running) {
                IO.println("MENU:");
                IO.println("Type 1 to Calculate Dimensions for a New project");
                IO.println("Type 2 to Input Data for a Finished Project");
                IO.println("Type 3 to Quit");
                String userChoice = IO.readln();

                switch (userChoice) {
                    case "1" -> calculateProject();
                    case "2" -> inputFinishedProject();
                    case "3" -> {
                        running = false;
                        IO.println("Goodbye!");
                    }
                    default -> IO.println("Invalid choice. Please enter 1, 2, or 3");
                }

            }
    }

    //PROJECT CALCULATIONS (dimension, thread, etc.)
    private static void calculateProject () {
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

            if (aidaCount <= 0) {
                IO.println("Number has to be larger than 0");
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

                if (costSkein < 0) {
                    IO.println("Number cannot be negative");
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
                if (bufferVariable < 0) {
                    IO.println("Number cannot be negative");
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

        //time estimation prediction using data from data.txt
        double pastHours = 0;
        double pastCountArea = 0;
        int pastProjects = 0;

        try (BufferedReader br = new BufferedReader(new FileReader("data.txt"))) {
            while (true) {
                String savedLine = br.readLine();
                if (savedLine == null) {
                    break;
                }
                String[] parts = savedLine.split(",");
                if (parts.length != 7) {
                    continue;
                }

                try {
                    double savedHours = Double.parseDouble(parts[1]);
                    int savedWidth = Integer.parseInt(parts[2]);
                    int savedHeight = Integer.parseInt(parts[3]);
                    int savedAida = Integer.parseInt(parts[4]);

                    if (savedHours <= 0 || savedWidth <= 0 || savedHeight <= 0 || savedAida <= 0) {
                        continue;
                    }

                    //size of the completed project in square inches
                    pastCountArea += ((double) savedWidth / savedAida) * ((double) savedHeight / savedAida);
                    pastHours += savedHours;
                    pastProjects++;
                } catch (NumberFormatException e) {
                    //a line with bad numbers or values is not considered in time estimation
                }
            }
        } catch (IOException e) {

        }

        if (pastProjects == 0) {
            IO.println("No past projects saved to estimate time with.");
        } else {
            double hoursPerSquareInch = pastHours / pastCountArea;
            double predictedHours = hoursPerSquareInch * widthInches * heightInches;
            IO.println("Estimated time to complete: " + String.format("%.2f", predictedHours) + " hours (based on "
                    + pastProjects + " past project(s))");
        }
    }

    //WRITING FINISHED DATA TO FILE
    private static void inputFinishedProject () {
        IO.println("Name of project (Do not put comma in name): ");
        String name = IO.readln();
        if (name.isEmpty()) {
            name = "Untitled";
        }

        double hours = 0;
        while (true) {
            IO.println("Time spent on project (in hours)");
            String orighours = IO.readln();

            try {
                hours = Double.parseDouble(orighours);
            } catch (NumberFormatException e) {
                IO.println("Not an amount.");
                continue;
            }

            if (hours < 0) {
                IO.println("Number cannot be negative");
                continue;
            }

            break;
        }

        int countWidth = 0;
        while (true) {
            IO.println("Enter width of project (in counts): ");
            String origWidth = IO.readln();

            try {
                countWidth = Integer.parseInt(origWidth);
            } catch (NumberFormatException e) {
                IO.println("Not an amount.");
                continue;
            }
            if (countWidth <= 0) {
                IO.println("Number must be larger than 0");
                continue;
            }

            break;
        }

        int countHeight = 0;
        while (true) {
            IO.println("Enter height of project (in counts): ");
            String origHeight = IO.readln();

            try {
                countHeight = Integer.parseInt(origHeight);
            } catch (NumberFormatException e) {
                IO.println("Not an amount.");
                continue;
            }

            if (countHeight <= 0) {
                IO.println("Number must be larger than 0");
                continue;
            }

            break;
        }


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

            if (aidaCount <= 0) {
                IO.println("Number must be larger than 0");
                continue;
            }

            break;
        }

        int skeinsUsed = 0;
        while (true) {
            IO.println("How many skeins of thread did you actually use?");
            String origskeinsThread = IO.readln();

            try {
                skeinsUsed = Integer.parseInt(origskeinsThread);
            } catch (NumberFormatException e) {
                IO.println("Not an amount.");
                continue;
            }

            if (skeinsUsed < 0) {
                IO.println("Number cannot be negative");
                continue;
            }

            break;
        }

        double actualCost = 0;
        while (true) {
            IO.println("What was the total cost of the project? ");
            String origcost = IO.readln();

            try {
                actualCost = Double.parseDouble(origcost);
            } catch (NumberFormatException e) {
                IO.println("Not an amount.");
                continue;
            }

            break;
        }

        saveFinishedProject(name, hours, countWidth, countHeight, aidaCount, skeinsUsed, actualCost);
    }


    private static void saveFinishedProject(String name, double hours, int countWidth, int countHeight, int aidaCount, int skeinsUsed, double actualCost) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("data.txt", true))) {
            //true in bw appends to end instead of rewriting the file
            bw.write(name + "," + hours + "," + countWidth + "," + countHeight + "," + aidaCount + "," + skeinsUsed + "," + actualCost);
            bw.newLine();
            IO.println("Saved to data.txt");
        } catch (IOException e) {
            IO.println("Could not save project: " + e.getMessage());
        }
    }
}
