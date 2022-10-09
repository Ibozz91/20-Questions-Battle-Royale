import java.io.*;
import java.net.*;
import java.util.Scanner;

public class TQBR_Client{
    public static void main(String Args[]) throws IOException{
        Scanner Scan = new Scanner(System.in);
        System.out.println("20 Questions Battle Royale\nPlease enter the IP address of the game you want to join.");
        String ip = Scan.nextLine();
        Socket s = new Socket(ip, 20200);
        BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
        PrintStream ps = new PrintStream(s.getOutputStream());
        if(br.readLine().equals("a")){
            System.out.println("what would you like your opponents to try and guess? (person, place, thing, etc)");
            String item = scan.nextLine();
            ps.println(item);
            ps.flush();
        }
    }
}