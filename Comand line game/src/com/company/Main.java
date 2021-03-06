package com.company;

//Imports
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Random;

public class Main {

    //This is the grid array that will be used in the game to store all of the grids info.
    //0 = Not Taken, 1 = White Occupation, 2 = Black Occupation.
    private static int[][] Grid = new int[8][8];

    //This stores the last move made so that the programme can change the grid according to the move made.
    private static int[] LastMove = new int[2];

    //Sets up the middle pieces
    //SetUpBoard();

    public static void main(String[] args) {
        //This section of the code reads the user's choice as to the name of player 1
        String SPlayer1Name = ReadUser("Name of Player 1: ");

        //This is where the method that will run the secret bot training program will hide.
        if(SPlayer1Name == "Botsfordayz"){

        }

        String SPlayer2Name = "";
        //This section of the code reads the user's choice as to the name of player 2
        Boolean BNameTooShort = true;
        while(BNameTooShort == true){
            SPlayer2Name = ReadUser("Name of Player 2 (Make the prefix to the name 'AI:' should you want an AI to play against): ");
            if((SPlayer2Name.length()) > 4){
                BNameTooShort = false;
            }else{
                System.out.println("That name is too long.(sarcasm)");
            }
        }
        //This then checks if the user made the player 2 an AI.
        Boolean BPlayer2IsAI = false;
        if(SPlayer2Name.charAt(0) == 'A' && SPlayer2Name.charAt(1) == 'I' && SPlayer2Name.charAt(2) == ':'){
            BPlayer2IsAI = true;
            SPlayer2Name = SPlayer2Name.replace("AI:", "");
        }

        //This code initialises the game section.
        if(BPlayer2IsAI == true){
            AIVSUserGame(SPlayer1Name, SPlayer2Name);
        }else{

        }

    }
    public static void SetUpBoard(){

        //Fills the grid with 0's
        for(int i = 0; i > 8; i++){
            for(int j = 0; j > 8; j++){
                Grid[i][j] = 0;
            }
        }

        //Sets the four middle starting pieces
        Grid[3][3] = 1;
        Grid[4][3] = 2;
        Grid[3][4] = 2;
        Grid[4][4] = 1;
    }
    private static String ReadUser(String Message){

        //This is the function that reads all of the user's inputs.
        System.out.print(Message);
        Scanner UserInput = new Scanner(System.in);
        String UserChoice = UserInput.nextLine();
        return UserChoice;
    }
    //This function is responsible for printing the grid during a game.
    public static void PrintTable(String SPlayer1, String SPlayer2){

        //Prints out the player's names.
        System.out.println("Player 1: " + SPlayer1);
        System.out.println("Player 2: " + SPlayer2);

        //This prints a + followed by the numbers 0 to 7 at the top row of the grid.
        System.out.print("+" + '\t');
        for(int i = 0; i < 8; i++){
            System.out.print(i + '\t');
        }
        System.out.println("");

        //This prints out the grid with the numbers along the left vertical column.
        for(int i = 0; i < 8; i++){
            System.out.print(i + '\t');
            for(int j = 0; j < 8; j++){
                System.out.print(Grid[i][j]);
            }
            System.out.println("");
        }
    }

    private  static  void UserVSUserGame(String SPlayer1, String SPlayer2){

        //Gets which user will start from the user.
        String SPlayerStarting = ReadUser("Which Player Starts, " + SPlayer1 + " or " + SPlayer2 + ": ");
        //Acts on the user's decision.
        Boolean BPlayer1Starts = true;
        if(SPlayerStarting == SPlayer1 || SPlayerStarting == "1"){
            BPlayer1Starts = true;
        }else if(SPlayerStarting == SPlayer2 || SPlayerStarting == "2"){
            BPlayer1Starts = false;
            PrintTable(SPlayer1, SPlayer2);
            UserMove(2, SPlayer2);
        }
    }

    //This runs the user's game vs the AI.
    private static void AIVSUserGame(String SPlayer1, String SPlayer2){

        Boolean BDifficultyChosen = false;
        int IBotDifficulty = 0;

        while(BDifficultyChosen == false) {
            //Asks the user what difficulty they would like to play on.
            String SAiDifficulty = ReadUser("Select AI difficulty(Easy, Medium or Impossible): ");
            //Sets the bots difficulty and catches any user mistakes.
            if (SAiDifficulty.toLowerCase().equals("easy")) {
                System.out.println("This is essentially a random number generator you are playing against so it will act very stupidly.");
                BDifficultyChosen = true;
                IBotDifficulty = 0;
            }else{
                //catches all inputs that are unaccounted for above.
                System.out.println("This mode is either non-existent or hasn't been fully implemented yet please make another choice.");
            }
        }

        //This is 0 should the player go first and 1 should the bot go first.
        Boolean BPlayerStarting;
        Boolean BStartChosen = false;

        //This code collects the users choice on who should go first.
        while(BStartChosen == false){
            String UserInput = ReadUser("Would you like to start(enter: p) or would you like the bot to start(enter: b): ");
            if(UserInput.toLowerCase().equals("p")){
                BPlayerStarting = true;
            }else if(UserInput.toLowerCase().equals("b")){
                BPlayerStarting = false;
                AIMove(IBotDifficulty);

            }else{
                System.out.println("This is not a valid option enter either B or P!");
            }
        }

        Boolean BGameCompleted = false;

        //Runs the game until completion.
        while(BGameCompleted == false){
        }
    }

    public static int PostMoveModification(int[] LocalLastMove, int PlayerToken, boolean BDummy){
        int IChangeCount = 0;

        //Proceeds to find the nearest token of the player's nature in all directions.
        for(int i = 0; i <= 7; i++){
            Boolean BFoundEnd = false;
            boolean BValidEnd = false;
            //This if Statement works out the directional x and y modifiers based on the turn.
            int [] xyaddition = DirectionalModifiers(i);
            int xadition = xyaddition[0];
            int yadition = xyaddition[1];

            //Finds the cords inbetween the last cords and the next user token in the direction iterating.
            int[] IACurrentCords = LocalLastMove;
            List<int[]> LAInbetweenCords = new ArrayList<int[]>();
            
            while(BFoundEnd == false){
                IACurrentCords[0] = IACurrentCords[0] + xadition;
                IACurrentCords[1] = IACurrentCords[1] + yadition;
                //Checks if the edge of the grid is reached.
                if(IACurrentCords[0] > 7 || IACurrentCords[1] > 7 || IACurrentCords[0] < 0 || IACurrentCords[1] < 0 ){
                    BFoundEnd = true;
                }else if(Grid[IACurrentCords[0]][IACurrentCords[1]] == PlayerToken){
                    //Adds in all of the cords inbetween as the user's token.
                    for (int[] j : LAInbetweenCords){
                        Grid[j[0]][j[1]] = PlayerToken;
                    }
                    BFoundEnd = true;
                }
                LAInbetweenCords.add(IACurrentCords);
            }
        }
        return IChangeCount;
    }

    //Works out the directional modifiers
    public static int[] DirectionalModifiers(int turn){
        int[] IAModifications = {0 , 0};
        int counter = 0;
        for(int i = -1; i >= 1; i++){
            for(int j = -1; j >= 1; j++){
                if(counter == turn){
                    IAModifications[0] = i;
                    IAModifications[1] = j;
                }
            }
        }
        return IAModifications;
    }

    //This function checks the bots difficulty and moves accordingly.
    public static void AIMove(int IBotDifficulty){

        if(IBotDifficulty == 0){
            Boolean BBotMoveMade = false;
            int IVsUserToken = 2;
            while (BBotMoveMade == false) {
                    //creates a list of possible moves based on the pieces around it.
                    List<int[]> possiblecords = null;
                    for(int i = 0; i < 8; i++){
                        for(int j = 0; j < 8; j++){
                            for(int c = 0; c < 8; c++) {
                                int[] xymod = DirectionalModifiers(c);
                                int xmod = xymod[0];
                                int ymod = xymod[1];
                                if(i + xmod <= 7 || i + xmod >= 0 || i + ymod <= 7 || i + xmod >= 0) {
                                    boolean BCordsAdded = false;
                                    if (Grid[i + xmod][j + ymod] == 1) {
                                        if(BCordsAdded == false) {
                                            int[] xycords = {i, j};
                                            possiblecords.add(xycords);
                                            BCordsAdded = true;
                                        }
                                    }
                                }
                            }
                        }
                    }
                    for(int[] currentcords: possiblecords){
                        int ICords = PostMoveModification(currentcords, IVsUserToken, true);
                    }
                }
            }
        }

    //Reads an Int from the user.
    private  static Integer ReadUserInt(String SMessage){
        Boolean BUserChoiceValid = false;
        int IUserInput = 0;
        while(BUserChoiceValid == false) {
            System.out.println(SMessage);
            Scanner ReadUser = new Scanner(System.in);
            try {
                IUserInput = Integer.parseInt(ReadUser.nextLine());
                BUserChoiceValid = true;
            }catch (Exception e) {
                System.out.println(e);
                System.out.println("^ That Is Because Of YOU! ^ ENTER A NUMBER!");
            }
        }
        return  IUserInput;
    }

    //This handles all user moves.
    public static void UserMove(int UserToken, String PlayerName){
        Boolean BUserChoiceValid = false;
        int[] IAUserCords = {0, 0};
        int IVsUserToken = 0;
        if(UserToken == 1){
            IVsUserToken = 2;
        }else if(UserToken == 2){
            IVsUserToken = 1;
        }
        while (BUserChoiceValid == false) {
            IAUserCords[0] = ReadUserInt("Enter X Co-ordinate of the tile you want to place your piece on: ");
            IAUserCords[1] = ReadUserInt("Enter Y Co-ordinate of the tile you want to place your piece on: ");
            if(IAUserCords[0] < 8 && IAUserCords[0] >= 0 && IAUserCords[1] < 8 && IAUserCords[1] >= 0){
                if(Grid[IAUserCords[0] + 1][IAUserCords[1]] == IVsUserToken || Grid[IAUserCords[0]][IAUserCords[1] + 1] == IVsUserToken || Grid[IAUserCords[0] - 1][IAUserCords[1]] == IVsUserToken || Grid[IAUserCords[0]][IAUserCords[1] - 1] == IVsUserToken || Grid[IAUserCords[0] + 1][IAUserCords[1] + 1] == IVsUserToken || Grid[IAUserCords[0] - 1][IAUserCords[1] + 1] == IVsUserToken || Grid[IAUserCords[0] + 1][IAUserCords[1] - 1] == IVsUserToken || Grid[IAUserCords[0] - 1][IAUserCords[1] - 1] == IVsUserToken){
                    BUserChoiceValid = true;
                }
            }
        }
        Grid[IAUserCords[0]][IAUserCords[1]] = UserToken;
        LastMove = IAUserCords;

        PostMoveModification(LastMove, UserToken, false);
    }
}

