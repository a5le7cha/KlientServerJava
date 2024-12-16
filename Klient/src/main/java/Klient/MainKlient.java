package Klient;

import java.util.Scanner;
import java.io.*;
import java.net.*;

public class MainKlient {
    private static Klient klient = new Klient();

    private static Socket clientSocket;

    private static BufferedReader reader;
    private static BufferedWriter out;

    static int port = 12345;
    static String host = "localhost";

    public static void main(String[] args) throws InterruptedException, IOException {
        Scanner in = new Scanner(System.in);
        System.out.println("Input user name");
        klient.setName(in.nextLine());
        System.out.printf("Name: %s \n", klient.getName());

        try{
            try{
                clientSocket = new Socket(host, port);

                reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

                while(true){
                    System.out.print("Input command: \n");
                    String[] command = in.nextLine().split(" ");

                    if(command.length == 3 && command[0].equals("set")){
                        out.write(klient.getName()+" "+command[0]+" "+command[1]+" "+command[2]+"\n");
                        out.flush();
                    }
                    else if(command.length == 2 && command[0].equals("read")){
                        out.write(klient.getName()+" "+command[0]+" "+command[1]+"\n");
                        out.flush();
                    }
                    else if(command.length == 2 && command[0].equals("unset")){
                        out.write(klient.getName()+" "+command[0]+" "+command[1]+"\n");
                        out.flush();
                    }
                    else{
                        break;
                    }

                    String line = reader.readLine();
                    System.out.println(line);
                }
            }
            finally {
                clientSocket.close();
                out.close();
            }
        }
        catch (IOException e){
            System.err.println(e);
        }
    }
}