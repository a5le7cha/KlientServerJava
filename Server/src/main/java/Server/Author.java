package Server;

import java.util.ArrayList;

public class Author {
    private String Name;

    /*Массив id записей*/
    private ArrayList<String> records = new ArrayList<String>();

    /*Объект класса Storage - хранилище*/
    private Storage storege = new Storage();

    public Author(){}
    public Author(String name){
        Name = name;
    }

    /**
     * Свойство получения имени автора*/
    public String getName(){
        return Name;
    }

    /**
    * Метод проверяет введенный Id в массиве записей
     * @param Id - Id int
     * @return true, если такой уже есть и fals, если такого нет*/
    public boolean ContainId(String Id){
        return records.contains(Id);
    }

    /**
     * Метод добавляет Id и Value
     * @param Id
     * @param value
     * @return void*/
    public void AddRecord(String Id, String value){
        storege.AddItem(Id, value);
        records.add(Id);
    }

    /**
     * get получение значения из словаря Storage
     * @param id
     * @return - соответсвующее значение*/
    public String getValueStorege(String id){
        return storege.getValue(id);
    }

    /**
     * Метод удаления значения
     * @param value
     * @return - void*/
    public void deleteValue(String value){
        storege.deleteValue(value);
    }

    /**
     * Метод проверки существования значения
     * @param value
     * @return true or false*/
    public boolean ContainValue(String value){
        return storege.ContainValue(value);
    }
}
