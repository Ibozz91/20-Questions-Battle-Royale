import java.io.*;
import java.net.*;
public class Player{
    private Socket sock;
    private String username;
    private String thing;
    private BufferedReader br;
    private PrintStream ps;
    private boolean stillalive;
    public Player(Socket socketaccepted) throws IOException{
        sock = socketaccepted;
        br = new BufferedReader(new InputStreamReader(sock.getInputStream()));
        ps = new PrintStream(sock.getOutputStream());
        stillalive = true;
    }
    public void assignusername(String usn){
        username = usn;
    }
    public String usrn(){
        return username;
    }
    public void send(String whattosend){
        ps.println(whattosend);
        ps.flush();
    }
    public String read() throws IOException{
        return br.readLine();
    }
    public void assthing(String whattheysaid){
        thing = whattheysaid;
    }
    public String readthing(){
        return thing;
    }
    public void eliminate(){
        stillalive = false;
    }
    public boolean isAlive(){
        return stillalive;
    }
}