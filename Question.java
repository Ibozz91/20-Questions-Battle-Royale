public class Question {
    private String playerAsking;
    private String content;
    private String response;
    public Question(String playerAsking, String content, String response){
        this.playerAsking = playerAsking;
        this.content = content;
        this.response = response;
    }
    public String getPlayer(){
        return playerAsking;
    }
    public String getContent(){
        return content;
    }
    public String getResponse(){
        return response;
    }
    @Override
    public String toString(){
        return playerAsking+": "+content+" "+response;
    }
}
