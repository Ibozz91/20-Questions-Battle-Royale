/*
   TWENTY QUESTIONS BATTLE ROYALE
   Rules:
   Each player thinks of one thing each
   During each turn, every uneliminated player asks a yes/no question to every other eliminated player.
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
        System.out.println("Please enter the amount of people playing exactly.");
        ServerSocket[] ServeSocks = new ServerSocket[Scan.nextInt()];
        for(int i = 0; i < ServeSocks.length; i++){
            ServeSocks[i] = new ServerSocket(20200);
        }
        System.out.println("Have players join at "+InetAddress.getLocalHost().getHostAddress()+" on the Client Side version.\nIf you want to join too, open a Client Side version in a new window and enter \"localhost\".");
        Socket[] Socks = new Socket[ServeSocks.length];
        for(int i = 0; i < Socks.length; i++){
            Socks[i]=ServeSocks[i].accept();
        }
    }
}