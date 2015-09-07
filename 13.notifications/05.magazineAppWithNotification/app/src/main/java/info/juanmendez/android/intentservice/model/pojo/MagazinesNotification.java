package info.juanmendez.android.intentservice.model.pojo;

import java.util.ArrayList;
import java.util.List;

import info.juanmendez.android.intentservice.model.pojo.Magazine;

/**
 * Created by Juan on 9/6/2015.
 */
public class MagazinesNotification {
    private ArrayList<Magazine> magazines;
    private int resultCode;

    public MagazinesNotification(){
        magazines = new ArrayList<Magazine>();
    }

    public MagazinesNotification(int resultCode, ArrayList<Magazine> magazines){
        this.resultCode = resultCode;
        this.magazines = magazines;
    }

    public ArrayList<Magazine> getMagazines(){
        return this.magazines;
    }

    public int getResultCode(){
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public void setMagazines(ArrayList<Magazine> magazines) {
        this.magazines = magazines;
    }
}
