import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;

public class TQBR_Client{
    public static void main(String Args[]) throws IOException, UnknownHostException, UnsupportedOperationException{
        HashMap<String, ArrayList<Question>> questionsAsked = new HashMap<String, ArrayList<Question>>();
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
            questionsAsked.put(playernames[i], new ArrayList<Question>());
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
            ArrayList<String> playersbutyou = new ArrayList<String>();
            for(int thej=0; thej<Playersin.size(); thej++){
                playersbutyou.add(Playersin.get(thej));
            }
            playersbutyou.remove(yourusername);
            for(int i = 0; i < playersbutyou.size(); i++){
                System.out.println("Questions asked so far to "+playersbutyou.get(i)+":");
                for(int j = 0; j < questionsAsked.get(playersbutyou.get(i)).size(); j++){
                    System.out.println((j+1)+". "+questionsAsked.get(playersbutyou.get(i)).get(j));
                }
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
                4. Correct (the player guessed the thing exactly)""");
                String response0st;
                do{
                    response0st = Scan.nextLine();
                }while(!(response0st.equals("1") || response0st.equals("2") || response0st.equals("3") || response0st.equals("4")));
                int response0 = Integer.parseInt(response0st);
                if(response0==4){
                    System.out.println("You have been eliminated. Still, finish all questions.");
                    stillin = false;
                }
                ps.println(Answers[response0-1]);
                ps.flush();
            }
            ArrayList<String> newplayersin=new ArrayList<String>();
            for(int thej=0; thej<Playersin.size(); thej++){
                newplayersin.add(Playersin.get(thej));
            }
            for(int i = 0; i<Playersin.size(); i++){
                boolean eliminatedMessageDisplayed = false;
                for(int j = 0; j<Playersin.size(); j++){
                    if(i!=j){
                        String Q = br.readLine();
                        String A = br.readLine();
                        if(A.equals("Correct")){
                            if(!Playersin.get(i).equals(yourusername)){
                                if(eliminatedMessageDisplayed){
                                    System.out.println("They also answered \"Correct\" to the following question by "+Playersin.get(j)+": "+Q);
                                }
                                else{
                                    System.out.println(Playersin.get(i) + " has been eliminated!");
                                    System.out.println("They answered \"Correct\" to the following question by "+Playersin.get(j)+": "+Q);
                                    eliminatedMessageDisplayed = true;
                                }
                            }
                            newplayersin.set(i,"");
                        }
                        //System.out.println(Playersin.get(j)+" asked "+Playersin.get(i)+": "+Q+"\n"+Playersin.get(i)+" responded with: "+A+".");
                        questionsAsked.get(Playersin.get(i)).add(new Question(Playersin.get(j), Q, A));
                    }
                }
            }
            Playersin.clear();
            for(int thej=0; thej<newplayersin.size(); thej++){
                if(newplayersin.get(thej)!=""){
                    Playersin.add(newplayersin.get(thej));
                }
            }
            if(Playersin.size() <= 1){
                stillin = false;
            }
            else{
                System.out.println(Playersin.size()+" players remain.");
            }
        }
        System.out.println("Game is still going on...");
        String[] things = new String[playercount];
        for(int i = 0; i < playercount; i++){
            things[i] = br.readLine();
        }
        System.out.println("Game has finished!");
        for(int i = 0; i < playercount; i++){
            if(playernames[i] != yourusername){
                System.out.println(playernames[i]+"'s thing: "+things[i]);
            }
        }
        String winnerName = br.readLine();
        if(winnerName.equals(yourusername)){
            System.out.println("You won!");
        }
        else{
            System.out.println(winnerName+" won!");
        }
        s.close();
        Scan.close();
    }
}