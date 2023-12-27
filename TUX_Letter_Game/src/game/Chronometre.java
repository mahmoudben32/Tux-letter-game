package game;


// Chronometre : permet de verifier a chaque nouvelle frame du jeu si le temps ecouler pour deviner 
// le mot est ecoule
public class Chronometre {
    
    // lancement du chronometre
    private long begin;
    // temps de fin cu chronometres
    private long end;
    // represente le moment actuel du timer
    private long current;
    // temps pour trouve le mot
    private int limite;

    public Chronometre(int limite) {
       this.limite = limite;
       this.start();
       this.end = this.begin + limite;
       System.out.println("Debut chrono = " + begin );
       System.out.println("Fin chrono = " + end );
       System.out.println("temps restant = " + getSeconds() + "s");

    }
    
    public void start(){
        begin = System.currentTimeMillis();
    }
 
    public void stop(){
        end = System.currentTimeMillis();
    }
 
    public long getTime() {
        return end-begin;
    }
 
    public long getMilliseconds() {
        return end-begin;
    }
 
    public int getSeconds() {
        return (int)((end - begin) / 1000.0);
    }
 
    public double getMinutes() {
        return (end - begin) / 60000.0;
    }
 
    public double getHours() {
        return (end - begin) / 3600000.0;
    }
    
    /**
    * Method to know if it remains time.
    */
    public boolean remainsTime() {
        current = System.currentTimeMillis();
        int timeSpent;
        timeSpent = (int) ((   current - begin      )/1000.0);
        if(timeSpent < limite/1000)
        {
       return true;
        }
        else
            return false;
    }
    
    // retourne le temps que le joueur a passee sur la partie en cours
    public int timeSpent(){
        current = System.currentTimeMillis();
        int timeSpent;
        timeSpent = (int) ((   current - begin      )/1000.0);
        return timeSpent;
    }
     
}