public class Player{
    private Socket sock;
    private String username;
    private BufferedReader br;
    private PrintStream ps;
    public Player(Socket socketaccepted){
        sock = socketaccepted;
        br = new BufferedReader(new InputStreamReader(sock.getInputStream()));
        ps = new PrintStream(sock.getOutputStream());
    }
    public void assignusername(String usn){
        username = usn;
    }
    public void send(String whattosend){
        pw.println(whattosend));
        pw.flush();
    }
    public Socket ineedthesock(){
        return sock;
    }
}