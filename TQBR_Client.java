import java.io.*;
import java.net.*;
import java.util.Scanner;

public class TQBR_Client{
    public static void main(String Args[]) throws IOException{
        Socket s = null;
        BufferedReader br = null;
        PrintStream ps = null;
        Scanner scan = new Scanner(System.in);
        System.out.print("enter an IP address.");
        String ip = scan.nextLine();
        s = new Socket(ip, 20200);
        br = new BufferedReader(new InputStreamReader(s.getInputStream()));
        ps = new PrintStream(s.getOutputStream());


        if(s.isConnected()){
            System.out.println("what would you like your opponents to try and guess? (person, place, thing, etc)");
            String item = scan.nextLine();
            ps.println(item);
            ps.flush();
        }







    }
}