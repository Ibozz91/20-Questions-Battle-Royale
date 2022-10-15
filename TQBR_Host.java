/*
   TWENTY QUESTIONS BATTLE ROYALE
   Rules:
   Each player thinks of one thing each
   During each turn, every uneliminated player asks a yes/no question to every other uneliminated player.
   After this, players answer questions honestly.
   Screenpeeking, and teams are not allowed.
   Last player remaining wins.
*/
import java.io.*;
import java.net.*;
import java.util.Scanner;
public class TQBR_Host{
    public static void main(String Args[]) throws IOException, UnknownHostException{
        Scanner Scan = new Scanner(System.in);
        ServerSocket ss = new ServerSocket(20200);
        System.out.println("20 Questions Battle Royale\nPlease enter the amount of people playing exactly.");
        Player[] players = new Player[Scan.nextInt()];
        System.out.println("Have players join at "+InetAddress.getLocalHost().getHostAddress()+" on the Client Side version.\nIf you want to join too, open a Client Side version in a new window and enter \"localhost\".");
        for(int i = 0; i < players.length; i++){
            System.out.println(i+" player(s) have joined.");
            players[i] = new Player(ss.accept());
        }
        System.out.println("Everyone has joined.");
        for(Player i: players){
            i.send(Integer.toString(players.length));
        }
        for(Player i: players){
            i.assignusername(i.read());
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
    }
}