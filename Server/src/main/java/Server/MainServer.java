package Server;

import javax.swing.text.AbstractDocument;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.*;
import java.time.*;

public class MainServer {

    private static Socket clientSocket;
    private static ServerSocket server;

    private static BufferedReader in;
    private static BufferedWriter out;

    private static Logger logger = Logger.getLogger(String.valueOf(Logger.class.getName()));

    private static int port = 12345;


    private static Map<String, Author> NameAuthor = new HashMap<String, Author>();

    private static Map<String, Author> deleteRecords = new HashMap<String, Author>();

    public static void main(String[] args) throws IOException {
        try{
            FileHandler fh = new FileHandler("logging.log", true);
            logger.addHandler(fh);
            fh.setFormatter(new SimpleFormatter());
            try{
                server = new ServerSocket(port);

                logger.log(Level.INFO, "Start server!");

                logger.log(Level.INFO, "Server waiting connection...");

                clientSocket = server.accept();

                logger.log(Level.INFO, "Client connection!");

                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

                try{
                    while(!clientSocket.isClosed()){
                        String data = in.readLine();

                        /*spliteDate[0] - Author.Name
                        * splitData[1] - command
                        * splitData[2] - id (set), id (other)
                        * splitData[3] - value (set)
                        * */
                        String[] splitData = data.split(" ");

                        Author auth;

                        String key = splitData[0];

                        if(NameAuthor.containsKey(key)){
                            auth = NameAuthor.get(key);
                        }
                        else{
                            auth = new Author(key);
                            NameAuthor.put(key, auth);
                        }

                        if(splitData[1].equals("set")){
                            String Id = splitData[2];
                            String Value = splitData[3];

                            if(!auth.ContainId(Id)){
                                auth.AddRecord(Id, Value);
                                logger.log(Level.INFO, "Command: SET, params: Id Value");
                                out.write("success\n");
                                out.flush();
                            }
                            else{
                                System.out.println("Key is already present.");
                                logger.log(Level.INFO, "Command SET. Key is already present.");
                                out.write("IS ALREADY KEY!\n");
                                out.flush();
                            }
                        }
                        else if(splitData[1].equals("read")){
                            String id = splitData[2];

                            if(deleteRecords.containsKey(id)){
                                String authName = deleteRecords.get(id).getName();
                                logger.log(Level.INFO, "Command: READ, Is ID delete!");

                                out.write(String.format("delete by %s\n", authName));
                                out.flush();
                            }
                            else if(!auth.ContainId(id)){
                                logger.log(Level.INFO, "Command: READ, ID not found!");
                                out.write(String.format("no such %s\n", id));
                                out.flush();
                            }
                            else{
                                String NameAuthor = auth.getName();

                                String value = auth.getValueStorege(id);

                                out.write(String.format("%s: %s\n", NameAuthor, value));
                                out.flush();
                            }
                        }
                        else if(splitData[1].equals("unset")){
                            String value = splitData[2];

                            if(auth.ContainId(value)){
                                auth.deleteValue(value);

                                deleteRecords.put(value, auth);
                                logger.log(Level.INFO, "Command: UNSET, value is delete!");

                                out.write("success\n");
                                out.flush();
                            }
                            else{
                                logger.log(Level.INFO, "Command: UNSET, value not found!");
                                out.write("success\n");
                                out.flush();
                            }
                        }
                        else{
                            System.out.println("Command NOT FOUND!");
                            logger.log(Level.INFO, "Command NOT FOUND!");
                        }
                    }
                }
                finally {
                    in.close();
                    out.close();
                    logger.log(Level.INFO, "Input and output stream close!");
                }
            }
            finally {
                clientSocket.close();
                server.close();
                logger.log(Level.INFO, "Server closed!");
            }
        }
        catch(IOException e) {
            logger.log(Level.INFO, String.format("%s", e));
        }
    }
}