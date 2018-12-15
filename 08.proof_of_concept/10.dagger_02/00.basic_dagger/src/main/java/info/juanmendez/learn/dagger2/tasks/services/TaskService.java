package info.juanmendez.learn.dagger2.tasks.services;

/**
 * Created by musta on 2/2/2017.
 */
public class TaskService {

    public TaskService(){
        System.out.println( "Greeting has been created!" );
    }

    public String[] getList(){
        return new String[]{"Make Breakfast", "Make Coffee", "Turn up my computer", "Write code all day!"};
    }
}