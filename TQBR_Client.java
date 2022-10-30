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
        ps.send(Scan.nextLine());
        String[] playernames = new String[playercount];
        for(int i = 0; i < playercount; i++){
            playernames[i]=br.readLine();
        }
        System.out.println("All player usernames:");
        for(String i: playernames){
            System.out.println(i);
        }
        System.out.println("Choose something for other players to guess. Remember to keep it secret and not have anybody peek.");
        String thing = Scan.nextLine();
        ps.send(thing);
        int pnumber = Integer.parseInt(br.readLine());
        boolean stillin=true;
        boolean gamestillgoingon=true;
        while(gamestillgoingon){
            for(int i = 0; i < playercount; i++){
                System.out.println("ask a question for player "+i);
                ps.send(Scan.nextLine());
            }
            for(int i = 0; i < playercount; i++){
                read();
                System.out.println("""
                1. Yes
                2. No
                3. Sometimes
                4. Correct (the player guessed the thing exactly)
                """);
                ps.send(Scan.nextLine());
            }
        }
        /*
        for(int x = 0; x<playercount; x++){
            br.readLine();
            System.out.println("""
            1. Yes
            2. No
            3. Sometimes
            4. Correct (the player guessed the thing exactly)
            """);
            ps.println(Scan.nextLine());
            ps.flush();
        }
        */
    }
}