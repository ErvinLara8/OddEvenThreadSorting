import java.util.ArrayList;

public class SortingTool extends Thread{


    /**
     * Class Variables
     */
    private ArrayList<Integer> givenNums;
    private int start;
    private int end;
    private int timeUnits;

    //default constructor
    public SortingTool(){
        this.givenNums = new ArrayList<>();
    }

    //main constructor
    public SortingTool(ArrayList<Integer> givenNums){
        this.givenNums = givenNums;
        this.timeUnits = 0;
    }

    /**
     * run method where the shifting happens
     */
    public void run() {

        //incrementing timeUnits
        timeUnits ++;
        //if the starting index is -1 convert it to 1
        if(start == -1) {
            start = 1;
        }

        //loop that compares each element from its given start to end
        for (int i = start; i < end; i += 2) {

            //variables to hold the elements
            int first = this.givenNums.get(i);
            int second = this.givenNums.get(i + 1);

            //comparing
            if (first > second) {
                this.givenNums.set(i, second);
                this.givenNums.set(i + 1, first);

            }
            //if statement to check were not stepping our of bound
            if ((i + 2) >= this.givenNums.size()) {
                break;
            }
        }
    }

    /**
     * getters and setters
     * @return
     */
    public ArrayList<Integer> getGivenNums() {
        return givenNums;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public int getTimeUnits() {
        return timeUnits;
    }

    public boolean chekOrder(){

        boolean arereadyOrdered = false;

        int fakeStart;

        if(start == -1){
            fakeStart = 0;
        }
        else{
            fakeStart = start;
        }

        for (int i = fakeStart; i < end; i++) {

            if(givenNums.get(i) < givenNums.get(i + 1)){
                arereadyOrdered = true;
            }
            else{
                arereadyOrdered = false;
                break;
            }

            if((i+1) == (end-1)){
                break;
            }
        }
        return arereadyOrdered;
    }
}
