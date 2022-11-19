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
        ps.println(thing);
        ps.flush();
        boolean stillin = true;
        while(stillin){
            for(int i = 0; i < playercount; i++){
                System.out.println("ask a question for player "+i);
                ps.println(Scan.nextLine());
                ps.flush();
            }
            for(int i = 0; i < playercount-1; i++){
                System.out.println(br.readLine());
                System.out.println("""
                1. Yes
                2. No
                3. Sometimes
                4. Correct (the player guessed the thing exactly)
                """);
                int response0 = Scan.nextInt();
                if(response0==4){
                    System.out.println("you have been eliminated.");
                    stillin = false;
                }
                ps.println(Integer.toString(response0));
                ps.flush();
            }
            
        }
    }
}