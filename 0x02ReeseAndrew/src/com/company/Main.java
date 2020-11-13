package com.company;

import java.lang.*;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.net.BindException;
import java.util.Random;

public class Main {
    public static int number = 249932;
    public static int maximum = 100000;

    public static String[] GenerateTestList(int N, int k, int minV, int maxV) {

        //creates a new random generator
        Random rand = new Random();

        //solution is where we will add the new string array
        String[] solution = new String[N];
        //rand-char will be where we create the individual random chars
        char[] randChar = new char[k + 1];

        //initial for loop to get the amount of string[] needed for the solutions.
        for (int i = 0; i < N; i++) {
            //nested loop to create the individual strings.
            for (int z = 0; z < k; z++) {
                //at the z place of the randChar array, will look for the random char values between the two
                //variables and creates the output for the char;
                randChar[z] = (char) (rand.nextInt(maxV - minV) + minV);

            }
            randChar[k] = '\0';
            //Converts randChar to a string and adds it to the solution[] and
            //adds the string to the solutions[] at i
            solution[i] = String.valueOf(randChar);
        }
        //returns solution to main main function.
        return solution;
    }

    //creating a selection algorithm.
    public static String[] selSort(String[] randomString, int N) {
        int z = 0;
        String[] solution = new String[N + 1];
        String[] temp = new String[N + 1];

        //creates a for loop that will run through the entire randomString array
        for (int q = 0; q < N; q++) {
            //Creates the initial string
            String start = randomString[q];
            int i = q;

            for (z = 0; z < N; z++) {
                //checks to see if the initial string is larger than the next string over
                if (randomString[z].compareTo(start) < 0) {
                    //if "larger" than string gets replaced
                    start = randomString[z];
                }
            }
            String x = randomString[i];
            randomString[i] = randomString[q];
            randomString[i] = x;

        }
        return solution;
    }

    public static String[] mSort(String[] randomString, int low, int high) {
        int middle = (high + low) / 2;
        if (high < low) {
            //if the high variable is less than the lower variable then it will
            //send resort the string
            mSort(randomString, low, middle);
            mSort(randomString, middle, high);
        }
        //creates a temporary string to store variables.
        String[] temporary = new String[(randomString.length)+1];
        int i = 0;
        int initial = low;
        int finale = high;

        //if the initial is less than the middle and finale is less than the low then
        //it will add to the temporary variable @ i
        while (initial <= middle && finale <= low) {
            i++;
            temporary[i] = randomString[low];
        }
        // if initial is less than middle
        // then it will add the random string to temp[i]
        //this is where the first error occurs.
        while (initial < middle) {
            temporary[i++] = randomString[high--];
        }
        //same with high
        while (finale <= high) {
            temporary[i++] = randomString[low++];
        }
        i = 0;
        while (initial <= high) {
            temporary[i++] = randomString[i++];
        }
        //returns the temporary[]
        return temporary;
    }

    //quick sort function
    public static void qSort(String[] randomString, int low, int high) {
        //if hye string length == 0 or if the low will be less than high it will return to variable
        if ((randomString.length == 0) || (low >= high)) {
            return;
        }
        //creates a middle variable to look between the two arrays
        int mid = (low + high) / 2;
        String pivot = randomString[mid];
        String temp;
        boolean pPoint = false;
        int lowNum = low + 1;
        int highNum = high - 1;

        //the program will iterate until the point is no longer false
        while (!pPoint) {
            //looking for ways to add to the low num or subtract to the high num
            while (lowNum < highNum && randomString[lowNum].compareTo(pivot) < 0) {
                lowNum++;
            }
            while (highNum > lowNum && randomString[highNum].compareTo(pivot) > 0) {
                highNum--;
            }
            if (lowNum >= highNum) {
                //when lowNum becomes high num then the while loop will end.
                pPoint = true;
            } else {
                //this is used to add the strings to temp string[]
                temp = randomString[lowNum];
                randomString[lowNum] = randomString[highNum];
                randomString[highNum] = temp;
                highNum--;
                lowNum++;
            }

            randomString[low] = randomString[highNum];
            randomString[highNum] = pivot;
            //will sort again.
            qSort(randomString, low, high);
            qSort(randomString, highNum + 1, high);
        }
        return;

    }

    public static void radixSort(String[] randomString, int num) {
        String[] radix = new String[256];
        for (int i = num - 1; i >= 0; i--) {
            int[] newRadixCounter = new int[257];

            for (int p = 0; p < randomString.length; p++) {
                newRadixCounter[randomString[i].charAt(p) + 1]++;
            }
            for (int p = 0; p < 256; p++) {
                newRadixCounter[p + 1] += newRadixCounter[p];
            }
            for (int p = 0; p < randomString.length; p++) {
                radix[newRadixCounter[randomString[p].charAt(i)]++] = randomString[p];
            }
            for (int p = 0; p < randomString.length; p++) {
                randomString[p] = radix[p];
            }

        }

        return;
    }

    public static long getCpuTime() {
        ThreadMXBean bean = ManagementFactory.getThreadMXBean();
        return bean.isCurrentThreadCpuTimeSupported() ?
                bean.getCurrentThreadCpuTime() : 0L;
    }


    public static void main(String[] args) {
        {

            //creating necessary variables.
            String[] randomList;
            double doubleRatio;
            double expDoubleRatio;
            long start, end, run;
            long pSelSort = 0;
            long pQSort = 0;
            long pMSort = 0;
            long pRSort = 0;


            boolean mTime = false;
            long mTimeReached = 10000000L;

            System.out.printf("%12s%24s%19s%19s%24s%19s%19s%24s%19s%19s24s%19s%19s\n", "", "Selection ", "", "", "Merge ", "", "", "Quick", "", "Radix ", "", "");
            System.out.printf("%12s%24s%19s%19s%24s%19s%19s%24s%19s%19s%24s%19s%19s\n", "N", "Time", "DblRatio", "ExpDblRatio", "Time", "DblRatio", "ExpDblRatio", "Time", "DblRatio", "ExpDblRatio", "Time", "DblRatio", "ExpDblRatio");

            for (int i = 2; i <= number; i = i * 2) {
                System.out.printf("%12s", i);
                randomList = GenerateTestList(100, 12, 1, 45);

                start = getCpuTime();
                selSort(randomList, 100);
                end = getCpuTime();
                run = end - start;
                System.out.printf("%24s", run);

                if (pSelSort == 0) {
                    System.out.printf("%19s%19s", "N/a", "N/a");
                } else {
                    doubleRatio = (double) run / pSelSort;
                    expDoubleRatio = (double) Math.pow(run, 3) / Math.pow(pSelSort, 3);
                    System.out.printf("%19f%19f", doubleRatio, expDoubleRatio);
                }
                pSelSort = run;
                if (run > maximum) {
                    mTime = true;
                }

                start = getCpuTime();
                mSort(randomList, 1, 40);
                end = getCpuTime();
                run = end - start;
                System.out.printf("%24s", run);

                if (pMSort == 0) {
                    System.out.printf("%19s%19s", "N/a", "N/a");
                } else {
                    doubleRatio = (double) run / pMSort;
                    expDoubleRatio = (Math.pow(run, 2) * Math.log((double) run)) / (Math.pow(pMSort, 2) * Math.log((double) pMSort));
                    System.out.printf("%19f%19f", doubleRatio, expDoubleRatio);
                }
                pMSort = run;
                if (run > maximum) {
                    mTime = true;
                }

                start = getCpuTime();
                qSort(randomList, 1, 100);
                end = getCpuTime();
                run = end - start;
                System.out.printf("%24s", run);

                if (pQSort == 0) {
                    System.out.printf("%19s%19s", "N/a", "N/a");
                } else {
                    doubleRatio = (double) run / pQSort;
                    expDoubleRatio = (Math.pow(run, 2) * Math.log((double) run)) / (Math.pow(pQSort, 2) * Math.log((double) pQSort));
                    System.out.printf("%19f%19f", doubleRatio, expDoubleRatio);
                }
                pMSort = run;
                if (run > maximum) {
                    mTime = true;
                }

                start = getCpuTime();
                radixSort(randomList, 100);
                end = getCpuTime();
                run = end - start;
                System.out.printf("%24s", run);

                if (pRSort == 0) {
                    System.out.printf("%19s%19s", "N/a", "N/a");
                } else {
                    doubleRatio = (double) run / pRSort;
                    expDoubleRatio = Math.pow(run, 2) / Math.pow(pRSort, 2);
                    System.out.printf("%19f%19f", doubleRatio, expDoubleRatio);
                }
                pRSort = run;
                if (run > maximum) {
                    mTime = true;
                }
                System.out.println("");

            }
        }
    }
}


