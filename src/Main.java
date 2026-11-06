public class Main {
    public static void main(String[] args) {
        IO.println("Hello! Welcome to Cross Stitch Calculator!");

        int width = 0;
        while(true) {
            IO.println("Enter width of project (in counts): ");
            String originalWidth = IO.readln();

            try {
                width = Integer.parseInt(originalWidth);
            }catch (NumberFormatException e) {
                IO.println("Not an amount.");
                continue;
            }

            if (width <= 0) {
                IO.println("Number has to be larger than 0");
                continue;
            }
            break;
        }

        int height = 0;
        while(true) {
            IO.println("Enter height of project (in counts): ");
            String originalHeight = IO.readln();

            try {
                height = Integer.parseInt(originalHeight);
            }catch (NumberFormatException e) {
                IO.println("Not an amount.");
                continue;
            }

            if (height <= 0) {
                IO.println("Number has to be larger than 0");
                continue;
            }
            break;
        }

        int aidaCount = 0;
        while(true) {
            IO.println("Enter AIDA count of fabric: ");
            String origAidaCount = IO.readln();

            try {
                aidaCount = Integer.parseInt(origAidaCount);
            }catch (NumberFormatException e) {
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

        int totalSkeins = (int) Math.ceil((double)totalAreaStitches / skeinsForStitches);
            //rounds up to nearest whole number


        IO.println("Number of skeins of thread needed: " + totalSkeins);
    }

}
