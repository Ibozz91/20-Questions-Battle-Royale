import java.io.*;
import java.net.*;
import java.util.Scanner;

public class TQBR_Client{
    public static void main(String Args[]) throws IOException, UnknownHostException, UnsupportedOperationException{
        Scanner Scan = new Scanner(System.in);
        System.out.println("20 Questions Battle Royale\nPlease enter the IP address of the game you want to join.");
        Socket s = new Socket(Scan.nextLine(), 20200);
        BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
        PrintStream ps = new PrintStream(s.getOutputStream());
        int playercount=Integer.parseInt(br.readLine());
        System.out.println("There are "+playercount+" players. Select a username for yourself.");
        ps.println(Scan.nextLine());
        ps.flush();
        System.out.println("what would you like your players to guess?");
        String thing = Scan.nextLine();
        ps.flush(thing);
    }
}