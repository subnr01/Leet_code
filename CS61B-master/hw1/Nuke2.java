import java.io.*;


class Nuke2 {
    public static void main (String[] args) throws Exception{
        BufferedReader keyboard;
        String inputLine;
        keyboard = new BufferedReader(new InputStreamReader(System.in));
        inputLine = keyboard.readLine();
        String firstLetter;
        firstLetter = inputLine.substring(0,1);
        String restOfLetters;
        restOfLetters = inputLine.substring(2, inputLine.length());
        System.out.println(firstLetter + restOfLetters);
    }
}
