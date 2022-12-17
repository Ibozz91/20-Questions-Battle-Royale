import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.ArrayList;

public class TQBR_Client{
    public static void main(String Args[]) throws IOException, UnknownHostException, UnsupportedOperationException{
        String[] Answers = {"Yes", "No", "Sometimes", "Correct"};
        Scanner Scan = new Scanner(System.in);
        System.out.println("20 Questions Battle Royale\nPlease enter the IP address of the game you want to join.");
        Socket s = new Socket(Scan.nextLine(), 20200);
        BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
        PrintStream ps = new PrintStream(s.getOutputStream());
        int playercount=Integer.parseInt(br.readLine());
        System.out.println("There are "+playercount+" players. Select a username for yourself.");
        String yourusername=Scan.nextLine();
        ps.println(yourusername);
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
        ArrayList<String> Playersin = new ArrayList<String>();
        for(int i = 0; i < playernames.length; i++){
            Playersin.add(playernames[i]);
        }
        while(stillin){
            ArrayList<String> playersbutyou = Playersin;
            playersbutyou.remove(yourusername);
            for(int i = 0; i < playersbutyou.size(); i++){
                System.out.println("Ask a question for "+playersbutyou.get(i));
                ps.println(Scan.nextLine());
                ps.flush();
            }
            for(int i = 0; i < playersbutyou.size(); i++){
                System.out.println("From "+playersbutyou.get(i)+": "+br.readLine());
                System.out.println("""
                1. Yes
                2. No
                3. Sometimes
                4. Correct (the player guessed the thing exactly)
                """);
                int response0 = Scan.nextInt();
                Scan.nextLine();
                if(response0>4){
                    response0=4;
                }
                else if(response0<1){
                    response0=1;
                }
                if(response0==4){
                    System.out.println("you have been eliminated.");
                    stillin = false;
                }
                ps.println(Answers[response0-1]);
                ps.flush();
            }
            for(int i = 0; i<Playersin.size(); i++){
                for(int j = 0; j<Playersin.size(); j++){
                    if(i!=j){
                        String Q = br.readLine();
                        String A = br.readLine();
                        if(A.equals("Correct")){
                            Playersin.remove(i);
                        }
                        System.out.println(Playersin.get(j)+" asked "+Playersin.get(i)+": "+Q+"\n"+Playersin.get(i)+" responded with: "+A+".");
                    }
                }
            }
        }
        System.out.println("Game is still going on...");
        System.out.println(br.readLine()+" won!");
    }
}