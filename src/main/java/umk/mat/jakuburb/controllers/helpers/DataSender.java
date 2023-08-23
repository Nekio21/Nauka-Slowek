package umk.mat.jakuburb.controllers.helpers;

import java.util.HashMap;

public class DataSender {

    private static DataSender dataSender;
    private HashMap<String, Object> mapa;

    private DataSender(){
        mapa = new HashMap<>();
    }

    public static DataSender initDataSender(){
        if(dataSender == null){
            dataSender = new DataSender();
        }

        return dataSender;
    }

    public Object add(Object obj, String key){
        return mapa.put(key, obj);
    }

    public Object remove(String key){
        return mapa.remove(key);
    }

    public Object get(String key){
        return mapa.get(key);
    }

    public HashMap<String, Object> getMapa(){
        return mapa;
    }
}
