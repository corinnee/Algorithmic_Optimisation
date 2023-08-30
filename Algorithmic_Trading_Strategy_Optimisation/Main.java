import java.io.IOException;

//import GA;

public class Main {
    public Main (){

    }
    public static void main(String[] args){
            GA ga1 = new GA();
            try {
                ga1.main("Unilever.csv");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
    }
    
}