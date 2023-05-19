import java.io.*;
import java.net.*;
import java.util.*;

// การทำงานของ server
public class Server {
    private static ArrayList<cilentManage> clients = new ArrayList<>();
    private static int clientCount = 0;

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(9001);
        System.out.println("Server is running...");
        while (true) {
            Socket clientSocket = server.accept();
            cilentManage client = new cilentManage(clientSocket, clients, clientCount);
            clientCount += 1;
            System.out.println("Student " + clientCount + " connected.");
            clients.add(client);
            new Thread(client).start();
        }
    }
}

// จัดการการทำงาน Thread ใน 1 Thread ของแต่ละ Client
class cilentManage implements Runnable {
    private Socket clientSocket;
    private BufferedReader input;
    private PrintWriter output;
    private ArrayList<cilentManage> clients;
    private int id;
       
    public cilentManage(Socket clientSocket, ArrayList<cilentManage> clients, int id) throws IOException {
        this.clientSocket = clientSocket;
        this.clients = clients;
        this.id = id;
        input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        output = new PrintWriter(clientSocket.getOutputStream(), true);

        if (id == 0) {
            output.println("-------------------------------------------------------- You are the first student. -----------------------------------------------------------------");
        } else {
            id += 1;
            output.println("------------------------------------------------------------- You are student " + id + ". -----------------------------------------------------------------"); ;
            id -= 1;
        }

       
    }
    
    // การทำงานของ Thread 
    public void run() {
    int count = 0;
    int point = 0;
        String[][] quiz = {
            // คำถามทั้งหมด
                {"______ acts as a bridge between the applications and data processing performed \n at hardware using the interprocess communication \n and the system calls. The ______ does tasks like disk management, task management \nand file management.", "kernel"},
                {"______ memory is a memory management technique for letting processes execute \noutside of memory. This is very useful especially is an executing program \n cannot fit in the physical memory.", "virtual"},
                {"The main objective of ______ programming is to have a process running \nat all times. With this design, CPU utilization is said to be maximized.", "multi"},
                {"In a Time-sharing system, the CPU executes multiple jobs by switching \namong them, also known as ______. This process happens so fast that\n users can interact with each program while it is running.", "multitasking"},
                {"______ It is one type of scheduling algorithm. In this scheme, the process\n that requests the CPU first is allocated the CPU first. Implementation is \nmanaged by a FIFO queue", "fcfs"},
                {"______ scheduling algorithm is primarily aimed for time-sharing systems.\n A circular queue is a setup in such a way that the CPU scheduler goes around \nthat queue, allocating CPU to each process for a time interval of up to around 10 to 100 milliseconds.", "rr"},
                {"______ situations occur when four conditions occur simultaneously in a system:\n Mutual exclusion; Hold and Wait; No preemption; and Circular wait.", "deadlock"},
                {"Caching is the processing of utilizing a region of fast memory for a limited data and process. \nA cache memory is usually much efficient because of \nits high ______ speed.", "access"},
                {"______ is used to enable a process to be larger than the amount of memory allocated to it. \nThe basic idea of this is that only instructions and data \n that are needed at any given time are kept in memory.", "overlay"},
                {"A ______ provides a connection between two applications. Each endpoint of a communication is a ______.", "socket"},
                {"Demand ______ is referred when not all of a process’s pages are in the RAM, \nthen the OS brings the missing(and required) pages from the disk into the RAM.", "paging"},
                {"The ______ is available upon booting and supports hardware’s core functioning, including support for I/O \ndevices, internal and external storage retrieval,\n and more. It is an essential part of computer and server functioning", "os"},
        };
        String message;
       
        try {
            while (count < quiz.length){
                Random random = new Random();
                int index = random.nextInt(12);
                String[] question = quiz[index];
                output.println(question[0]);
                message = input.readLine();
                if (count < quiz.length-1){
                    count += 1;
                    if ( message.equalsIgnoreCase(question[1]) ){
                        point += 1;
                        clients.get(id).output.println("\n-----You are answer " + message + "." );
                        clients.get(id).output.println("-----Correct , you have " + (point) + " point.");
                        clients.get(id).output.println("--------------------------------------------------------------Next question.------------------------------------------------------------------");
                    } else {
                        if ( message.equalsIgnoreCase("") ){
                            clients.get(id).output.println("\n-----You are answer nothing.");
                            clients.get(id).output.println("-----You have " + (point) + " point.\n");
                            clients.get(id).output.println("--------------------------------------------------------------Next question.------------------------------------------------------------------");
                        } else {
                        clients.get(id).output.println("\n-----You are answer " + message + "." );
                        clients.get(id).output.println("-----Wrong , you have " + (point) + " point.");
                        clients.get(id).output.println("-----The answer is " + question[1] + "." + "\n");
                        clients.get(id).output.println("--------------------------------------------------------------Next question.------------------------------------------------------------------");
                    } 
                }
                } else {
                    count += 1;
                    if ( message.equalsIgnoreCase(question[1]) ){
                        point += 1;
                        clients.get(id).output.println("\n-----You are answer " + message + ". " );
                        clients.get(id).output.println("-----Correct , you have " + (point) + " point.");
                        clients.get(id).output.println("----------------------------------------------------------------------------------------------------------------------------------------------");
                    
                    } else {
                        if ( message.equalsIgnoreCase("") ){
                            clients.get(id).output.println("\n-----You are answer nothing. ");
                            clients.get(id).output.println("-----You have " + (point) + " point.\n");
                            clients.get(id).output.println("-----------------------------------------------------------------------------------------------------------------------------------------------");
                        } else {
                        clients.get(id).output.println("\n-----You are answer " + message + "." );
                        clients.get(id).output.println("-----Wrong , you have " + 0 + " point. ");
                        clients.get(id).output.println("-----The answer is " + question[1] + "\n");
                        clients.get(id).output.println("---------------------------------------------------------------------------------------------------------------------------------------------");
                        }
                    } 
                }
            } 

            clients.get(id).output.println("\nYou got " + point + " point.");
            if (point >= 10){
                clients.get(id).output.println("You got Grade A ");
                clients.get(id).output.println("Good job!!! You can learn CN410.");
            } else if (point >= 8){
                clients.get(id).output.println("You got Grade B ");
                clients.get(id).output.println("Nice!!! You can learn CN410.");
            } else if (point >= 6){
                clients.get(id).output.println("You got Grade C ");
                clients.get(id).output.println("Good! You can learn CN410.");
            } else if (point >= 4){
                clients.get(id).output.println("You got Grade D+ ");
                clients.get(id).output.println("Good! You need to practice more.");
            } else if (point >= 2){
                clients.get(id).output.println("You got Grade D ");
                clients.get(id).output.println("You need to try harder you are good one.");
            } else {
                clients.get(id).output.println("You got Grade F ");
                clients.get(id).output.println("You need to try harder one day you will be the best.");
            }
            System.out.println("Student " + (clients.get(id).id + 1)  + " got " + point + " point" );
        } catch (IOException e) {
            System.out.println("Student " + (clients.get(id).id + 1)  + " disconnected.");
            clients.remove(this);
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }                  
    }
}
