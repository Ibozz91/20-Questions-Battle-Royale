/*
   TWENTY QUESTIONS BATTLE ROYALE
   Rules:
   Each player thinks of one thing each
   During each turn, every uneliminated player asks a yes/no question to every other uneliminated player.
   After this, players answer questions honestly.
   Screenpeeking, and teams are not allowed.
   Last player remaining wins.
*/
import java.util.ArrayList;
import java.util.Random;
import java.io.*;
import java.net.*;
import java.util.Scanner;
public class TQBR_Host{
    static final String GAME_VERSION = "1.0";
    public static void main(String Args[]) throws IOException, UnknownHostException{
        Scanner Scan = new Scanner(System.in);
        ServerSocket ss = new ServerSocket(20200);
        System.out.println("20 Questions Battle Royale\nVersion "+GAME_VERSION+"\nPlease enter the amount of people playing exactly.");
        Player[] players = new Player[Scan.nextInt()];
        Scan.nextLine();
        System.out.println("Have players join at "+InetAddress.getLocalHost().getHostAddress()+" on the Client Side version.\nIf you want to join too, open a Client Side version in a new window and enter \"localhost\".\nMake sure everybody is on the same wifi.");
        for(int i = 0; i < players.length; i++){
            System.out.println(i+" player(s) have joined.");
            players[i] = new Player(ss.accept());
        }
        System.out.println("Everyone has joined.");
        for(Player i: players){
            i.send(Integer.toString(players.length));
        }
        ArrayList<String> usrns = new ArrayList<String>();
        for(Player i: players){
            String version = i.read();
            String usernamee = i.read();
            if(usrns.contains(usernamee) || usernamee.equals("")){
                System.out.println("Duplicate or blank username detected: "+usernamee);
                System.exit(0);
            }
            else if(!GAME_VERSION.equals(version)){
                System.out.println("Version mismatch: "+usernamee+" is playing on version "+version);
                System.exit(0);
            }
            else{
                i.assignusername(usernamee);
                usrns.add(usernamee);
            }
        }
        System.out.println("Current players:");
        for(Player i: players){
            System.out.println(i.usrn());
        }
        for(Player i: players){
            for(Player ii: players){
                i.send(ii.usrn());
            }
        }
        System.out.println("Accepting responses for things from the players.");
        for(Player i: players){
            i.assthing(i.read());
        }
        int playersin=players.length;
        while(playersin>1){
            String[][] Questions = new String[players.length][players.length];
            String[][] Answers = new String[players.length][players.length];
            //Get questions from every player and send them to every other player
            for(int i = 0; i < players.length; i++){
                if(players[i].isAlive()){
                    for(int ii = 0; ii < players.length; ii++){
                        if(players[ii].isAlive() && i!=ii){
                            String q = players[i].read();
                            players[ii].send(q);
                            Questions[ii][i]=q;
                        }
                    }
                }
                players[i].setle(false);
            }
            //Get the answers to the questions
            for(int i = 0; i < players.length; i++){
                if(players[i].isAlive()){
                    for(int ii = 0; ii < players.length; ii++){
                        if(players[ii].isAlive() && i!=ii){
                            String a = players[i].read();
                            Answers[i][ii]=a;
                        }
                    }
                }
            }
            //Send Q&A to everybody
            for(int i = 0; i < players.length; i++){
                if(players[i].isAlive()){
                    for(int ii = 0; ii < players.length; ii++){
                        if(players[ii].isAlive()){
                            for(int iii = 0; iii < players.length; iii++){
                                if(players[iii].isAlive() && ii!=iii){
                                    players[i].send(Questions[ii][iii]);
                                    players[i].send(Answers[ii][iii]);
                                }
                            }
                        }
                    }
                }
            }
            //Eliminate players if necessary
            for(int i = 0; i < players.length; i++){
                if(players[i].isAlive()){
                    for(int ii = 0; ii < players.length; ii++){
                        if(Answers[i][ii] != null && Answers[i][ii].equals("Correct")){
                            players[i].eliminate();
                            players[i].setle(true);
                        }
                    }
                }
            }
            playersin=0;
            for(int i = 0; i < players.length; i++){
                if(players[i].isAlive()){
                    playersin++;
                }
            }
            System.out.println("After this round, there are "+playersin+" players still in.");
        }
        for(int i = 0; i < players.length; i++){
            for(int ii = 0; ii < players.length; ii++){
                players[i].send(players[ii].readthing());
            }
        }
        if(playersin==1){
            int winner = -1;
            for(int i = 0; i < players.length; i++){
                //System.out.println("Is "+players[i].usrn()+" alive?");
                if(players[i].isAlive()){
                    winner = i;
                    //System.out.println("Yes");
                }
            }
            //System.out.println("Sending "+players[winner].usrn());
            for(int i = 0; i < players.length; i++){
                players[i].send(players[winner].usrn());
            }
        }
        else{
            ArrayList<Integer> winners = new ArrayList<Integer>();
            for(int i = 0; i < players.length; i++){
                if(players[i].isle()){
                    winners.add(i);
                }
            }
            Random tiebreaker = new Random();
            int winnerindex = tiebreaker.nextInt(winners.size());
            int winner = winners.get(winnerindex);
            for(int i = 0; i < players.length; i++){
                players[i].send(players[winner].usrn());
            }
        }
        Scan.close();
        ss.close();
    }
}