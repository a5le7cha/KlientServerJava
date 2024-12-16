package Klient;

/*
Класс описания Клиента
Содержит, поле Name с get и set
 */

public class Klient {
    private String Name = null;
    private String value = null;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        if(name != null) Name = name;
    }
}
