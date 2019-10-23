import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Driver {

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);

        /**
         * Getting user inputs
         */
        System.out.print("Length of Array: ");
        int arrayLength = in.nextInt();
        System.out.print("How many Threads: ");
        int numThreads = in.nextInt();


        /**
         * Creating a arraylist of the user desired size filled with random numbers
         */
        Random r = new Random();
        ArrayList<Integer> numbers = new ArrayList<>();
        while (numbers.size() != arrayLength) {
            Integer rand = r.nextInt(arrayLength + 10);
            if(!numbers.contains(rand)){
                numbers.add(rand);
            }
        }


        /**
         * creating an array of sorting threads and initializing them in the loop
         */
        SortingTool[] toolBox = new SortingTool[numThreads];
        for (int i = 0; i < numThreads; i++) {
            SortingTool temp = new SortingTool(numbers);
            toolBox[i] = temp;

        }

        /**
         * Variables that determine the functionality of the sorting
         */
        int sortingLength = arrayLength / numThreads; //determines the length that each thread will deal with
        int start; //starting index
        int end; //ending index
        boolean inOrder = false; //boolean that will determine when the program finishes
        boolean even = false; //boolean that determines if the next shift is an even or an odd shift
        ArrayList<Integer> orderedNumbers = new ArrayList<>(); //Array list that will be the end result organised
        int currentStart;//stating index to copy the results
        int currentEnd;//ending index to copy the results


        System.out.println();
        System.out.printf("%-15s %s %n %n", "Given Array:", numbers);


        /**
         * Loop that continues until all elements are sorted out
         */
        while (!inOrder) {

            /**
             * if were at an odd shift the start index is zero and the end index is the sorting length
             * else if its an even index, start is -1 and end is one less than the sorting length
             */
            if (!even) {
                start = 0;
                end = sortingLength;
            } else {
                start = -1;
                end = sortingLength - 1;
            }

            /**
             * Loop that sets all the ranges for each thread
             */
            for (int i = 0; i < numThreads; i++) {

                toolBox[i].setStart(start);//setting the starting index
                toolBox[i].setEnd(end); //setting the end index
                start = end; //start becomes end
                end += sortingLength; //end becomes end plus sorting length
            }

            /**
             * loop that starts each thread
             */
            for (int i = 0; i < numThreads; i++) {

                if (!toolBox[i].chekOrder()) {
                    toolBox[i].run();
                }
            }

            /**
             * loop that joins each thread
             */
            try {
                for (int i = 0; i < numThreads; i++) {
                    if (!toolBox[i].chekOrder()) {
                        toolBox[i].join();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            /**
             * Loop that gives the organise Array list its values from each thread
             */
            for (int i = 0; i < numThreads; i++) {

                //if we're at the first thread and the starting index is 1, currentStart is 0
                //else current start is the starting index if of the current thread
                if (i == 0 && (toolBox[i].getStart() == 1 ||toolBox[i].getStart() == -1) ) {
                    currentStart = 0;
                } else {
                    currentStart = toolBox[i].getStart();
                }

                //if we're at the last thread and the ending index is not the size of the array, currentEnd is the length of the array
                //else current end is the ending index if of the current thread
                if (i == (numThreads - 1) && toolBox[i].getEnd() != arrayLength) {
                    currentEnd = arrayLength;
                } else {
                    currentEnd = toolBox[i].getEnd();
                }

                //creating list object to hold the substring of the current ArrayList in the current thread
                List<Integer> sub = toolBox[i].getGivenNums().subList(currentStart, currentEnd);
                //converting that list to an ArrayList
                ArrayList<Integer> arraySub = new ArrayList<>(sub);
                //adding that sub ArrayList to the organised ArrayList
                orderedNumbers.addAll(arraySub);
            }

            /**
             * Printing results depending on the current type of shift
             * and then also changing the type of shift
             */
            if (!even) {
                System.out.printf("%-15s %s %n", "Odd Shift:", orderedNumbers);
                System.out.println("--------------------------------------------------------------------------------------------");
                even = true;
            } else {
                System.out.printf("%-15s %s %n", "Even Shift:", orderedNumbers);
                System.out.println("--------------------------------------------------------------------------------------------");
                even = false;
            }

            /**
             * loop that checks if all elements in the ordered ArrayList are from least to greatest and if not loop again
             */
            for (int i = 0; i < numbers.size() - 1; i++) {
                if (numbers.get(i) > numbers.get(i + 1)) {
                    inOrder = false;
                    orderedNumbers.clear();
                    break;
                } else {
                    inOrder = true;
                }
            }
        }

        System.out.println();
        System.out.printf("%-15s %s %n %n", "Results:", orderedNumbers);
        System.out.println();
        int totalTime = 0;
        System.out.println("Time Unit Stats:");
        System.out.println("-----------------");
        for (int i = 0; i < numThreads; i++) {
            System.out.println("Thread " + (i + 1) + ": " + toolBox[i].getTimeUnits());
            totalTime += toolBox[i].getTimeUnits();
        }
        System.out.println();
        System.out.println("Total Time Units: " + totalTime);

    }
}