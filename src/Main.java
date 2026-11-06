public class Main {
    public static void main(String[] args) {
        IO.println("Hello! Welcome to Cross Stitch Calculator!");

        //width of project
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

        //height of project
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

        //aida count
        int aidaCount = 0;
        while (true) {
            IO.println("Enter AIDA count of fabric: ");
            String origAidaCount = IO.readln();

            try {
                aidaCount = Integer.parseInt(origAidaCount);
            } catch (NumberFormatException e) {
                IO.println("Not an amount.");
                continue;
            }

            if (!(aidaCount == 6 || aidaCount == 8 || aidaCount == 11 || aidaCount == 14 || aidaCount == 16 || (aidaCount > 17 && aidaCount < 23))) {
                IO.println("AIDA count " + aidaCount + " is not an accepted count of fabric. Choose a different AIDA count size.");
                continue;
            }
            break;
        }

        double widthInches = (double) width / aidaCount;
        double heightInches = (double) height / aidaCount;

        IO.println("Your finished project size is: " + String.format("%.2f", widthInches) + " x " + String.format("%.2f", heightInches) + " inches");

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

        int totalSkeins = (int) Math.ceil((double) totalAreaStitches / skeinsForStitches);
        //rounds up to nearest whole number


        IO.println("Number of skeins of thread needed: " + totalSkeins);


        double costSkein = 0.84;
        IO.println("Do you have a specific price of thread that you want to input? (yes/no)");
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

        double costOfFabric = 15.00;
        //find more efficient way to have the user input price or compare other sizes, but for now just constant
        double totalCost = (costOfFabric + (costSkein * totalSkeins));
        IO.println("Total estimated cost of project: $" + String.format("%.2f", totalCost));


        //buffer variable
        double bufferVariable = 5.0;
        IO.println("Do you want to change the buffer variable from 5 inches (type yes if so)");
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



        IO.println("Your finished fabric size needed is: " + String.format("%.2f", (widthInches + bufferVariable)) + " x " +  String.format("%.2f", (heightInches + bufferVariable)) + " inches");
    }
}
