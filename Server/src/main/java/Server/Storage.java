package Server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Storage {
    /*Хранилище для key value*/
    private Map<String, String> storage = new HashMap<String, String>();

    /**
     * Метод добавления key value
     * @param id
     * @param value
     * @return - void*/
    public void AddItem(String id, String value){
        storage.put(id, value);
    }

    /**
     * Свойство получения значения из хранилища по id
     * @param id
     * @return - соответствующее значение*/
    public String getValue(String id){
        return storage.get(id);
    }

    /**
     * Метод удаления key value по value
     * @param value
     * @return - void*/
    public void deleteValue(String value){
        Set<Map.Entry<String, String>> entrySet = storage.entrySet();
        for(Map.Entry<String,String> pair: entrySet){
            if(value.equals(pair.getValue())){
                storage.remove(pair.getKey());
                break;
            }
        }
    }

    /**
     * Метод проверки значения
     * @param value
     * @return - true or false*/
    public boolean ContainValue(String value){
        ArrayList<String> values = (ArrayList<String>) storage.values();

        return values.contains(value);
    }
}
