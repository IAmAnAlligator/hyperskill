
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReadabilityScore {
    public static int wordCount = 0;
    public static int sentCount = 0;
    public static int charCount = 0;
    public static int syllableCount = 0;
    public static int polysyllableCount = 0;

    public static void main(String[] args) throws FileNotFoundException {
        File file = new File(args[0]);
        Scanner scanner = new Scanner(file);
        Scanner scannerIn = new Scanner(System.in);

        StringBuilder sb = new StringBuilder();
        String text = "";

        while (scanner.hasNext()) {
            text = sb.append(scanner.nextLine()).toString();
        }

        String[] words = text.split(" ");
        wordCount = words.length;
        String[] sentences = text.split("[!?.]+");
        sentCount = sentences.length;
        charCount = text.replaceAll(" ", "").length();
        syllableCount = countSyllables(text);
        polysyllableCount = countPolysyllables(text);

        System.out.println("Words: " + wordCount);
        System.out.println("Sentences: " + sentences.length);
        System.out.println("Characters: " + charCount);
        System.out.println("Syllables: " + syllableCount);
        System.out.println("Polysyllables: " + polysyllableCount);

        System.out.println("Enter the score you want to calculate (ARI, FK, SMOG, CL, all): ");
        String input = scannerIn.nextLine();

        switch (input) {
            case "ARI" -> System.out.printf("Automated Readability Index: %.2f (about %d-year-olds). ",
                    calcARI(), showAge(calcARI()));
            case "FK" -> System.out.printf("Flesch-Kincaid readability tests: %.2f (about %d-year-olds). ",
                    calcFK(), showAge(calcFK()));
            case "SMOG" -> System.out.printf("Simple Measure of Gobbledygook: %.2f (about %d-year-olds). ",
                    calcSMOG(), showAge(calcSMOG()));
            case "CL" -> System.out.printf("Coleman-Liau index: %.2f (about %d-year-olds). ",
                    calcCL(), showAge(calcCL()));
            case "all" -> {
                System.out.printf("Automated Readability Index: %.2f (about %d-year-olds). \n",
                        calcARI(), showAge(calcARI()));
                System.out.printf("Flesch-Kincaid readability tests: %.2f (about %d-year-olds). \n",
                        calcFK(), showAge(calcFK()));
                System.out.printf("Simple Measure of Gobbledygook: %.2f (about %d-year-olds). \n",
                        calcSMOG(), showAge(calcSMOG()));
                System.out.printf("Coleman-Liau index: %.2f (about %d-year-olds). \n\n",
                        calcCL(), showAge(calcCL()));
                System.out.printf("This text should be understood in average by %.2f-year-olds.", calcAverage());
            }
            default -> System.out.println("Incorrect input!");
        }
    }

    static public int countSyllables(String s) {
        int counter = 0;
        s = s.toLowerCase(); // converting all string to lowercase
        if (s.contains("the ")) {
            counter++;
        }

        if (s.contains("you")) {
            counter++;
        }

        if (s.contains("simple")) {
            counter++;
        }

        String[] split = s.split("e!$|e[?]$|e,|e |e[),]|e$");
        ArrayList<String> al = new ArrayList<>();
        Pattern tokSplitter = Pattern.compile("[aeiouy0-9]+");

        for (String value : split) {
            String s1 = value.replaceAll(",", "");
            Matcher m = tokSplitter.matcher(s1);

            while (m.find()) {
                al.add(m.group());
            }
        }
        counter += al.size();
        return counter;
    }

    public static int countPolysyllables(String text) {
        int counter = 0;
        String[] split = text.split(" ");
        for (String s : split) {
            if (countSyllables(s.replaceAll("\\.", "")) > 2) {
                counter++;
            }
        }
        return counter;
    }

    public static double calcAverage() {
        return (showAge(calcARI()) + showAge(calcFK()) + showAge(calcSMOG()) + showAge(calcCL())) / 4.0;
    }

    public static double calcARI() {
        return 4.71 * charCount / wordCount + 0.5 * wordCount / sentCount - 21.43;
    }

    public static double calcFK() {
        return 0.39 * wordCount / sentCount + 11.8 * syllableCount / wordCount - 15.59;
    }

    public static double calcSMOG() {
        return 1.043 * Math.sqrt(polysyllableCount * 30.0 / sentCount) + 3.1291;
    }

    public static double calcCL() {
        double l = (double) charCount / wordCount * 100;
        double s = (double) sentCount / wordCount * 100;
        return 0.0588 * l - 0.296 * s - 15.8;
    }

    public static int showAge(double score) {
        double result = Math.ceil(score);
        return switch ((int) result) {
            case 1 -> 6;
            case 2 -> 7;
            case 3 -> 8;
            case 4 -> 9;
            case 5 -> 10;
            case 6 -> 11;
            case 7 -> 12;
            case 8 -> 13;
            case 9 -> 14;
            case 10 -> 15;
            case 11 -> 16;
            case 12 -> 17;
            case 13 -> 18;
            case 14 -> 22;
            default -> 0;
        };
    }
}
