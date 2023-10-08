import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;


interface Eat{
    void serveRefreshments(List<Participant> participants);
    String toString();
}
interface SerializableToFile {
    void saveToFile(String fileName);
    String loadFromFile(String fileName);
}

class Meeting implements SerializableToFile {
    private String title;
    private Date date;
    private String location;
    private List<String> agendaItems;

    public Meeting(String title, Date date, String location) {
        this.title = title;
        this.date = date;
        this.location = location;
        this.agendaItems = new ArrayList<>();
    }

    public void addAgendaItem(String agendaItem) {
        agendaItems.add(agendaItem);
    }

    public void displayMeetingDetails() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("Meeting Title: " + title);
        System.out.println("Date and Time: " + sdf.format(date));
        System.out.println("Location: " + location);
        System.out.println("Agenda Items: " + agendaItems);
    }

    @Override
    public void saveToFile(String fileName) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            writer.println("Meeting Title: " + title);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            writer.println("Date and Time: " + sdf.format(date));
            writer.println("Location: " + location);
            writer.println("Agenda Items:");
            for (String item : agendaItems) {
                writer.println(item);
            }
            System.out.println("Meeting data saved to file: " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String loadFromFile(String fileName) {
        StringBuilder sb = new StringBuilder();
        try (Scanner scanner = new Scanner(new File(fileName))) {
            while (scanner.hasNextLine()) {
                sb.append(scanner.nextLine()).append("\n");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}

class Participant implements SerializableToFile {
    private int age;
    private String name;
    private String email;
    private Refreshment refreshment;

    public Participant(int age, String name, String email) {
        this.age = age;
        this.name = name;
        this.email = email;
    }

    public void consumeRefreshments(Refreshment refreshment) {
        this.refreshment = refreshment;
        System.out.println(name + " is enjoying refreshments: " + refreshment);
    }

    @Override
    public void saveToFile(String fileName) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            writer.println("Age: " + age);
            writer.println("Name: " + name);
            writer.println("Email: " + email);
            if (refreshment != null) {
                writer.println("Refreshments: " + refreshment);
            }
            System.out.println("Participant data saved to file: " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String loadFromFile(String fileName) {
        StringBuilder sb = new StringBuilder();
        try (Scanner scanner = new Scanner(new File(fileName))) {
            while (scanner.hasNextLine()) {
                sb.append(scanner.nextLine()).append("\n");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}


class Refreshment implements Eat {
    private int coffee;
    private int water;
    private int snacks;

    public Refreshment(int coffee, int water, int snacks) {
        this.coffee = coffee;
        this.water = water;
        this.snacks = snacks;
    }

    public void serveRefreshments(List<Participant> participants) {
        for (Participant participant : participants) {
            participant.consumeRefreshments(this);
        }
    }

    @Override
    public String toString() {
        return "Refreshments: Cup of Coffee=" + coffee + ", Bottles of Water=" + water + ", Snacks=" + snacks;
    }
}

class FileOperationThread extends Thread {
    private SerializableToFile object;
    private String fileName;
    private boolean saveToFile;

    public FileOperationThread(SerializableToFile object, String fileName, boolean saveToFile) {
        this.object = object;
        this.fileName = fileName;
        this.saveToFile = saveToFile;
    }

    @Override
    public void run() {
        if (saveToFile) {
            object.saveToFile(fileName);
        } else {
            String loadedData = object.loadFromFile(fileName);
            System.out.println("\n Loaded Data from " + fileName + ": \n");
            System.out.println(loadedData);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Meeting meeting = new Meeting("Project Kick-off Meeting", new Date(), "Department of Software Engineering");
        meeting.addAgendaItem("Project Scope Discussion");
        meeting.addAgendaItem("Team Roles and Responsibilities");
        meeting.addAgendaItem("Team work");

        List<Participant> participants = new ArrayList<>();
        participants.add(new Participant(19, "Muhammad Saif", "saif@example.com"));
        participants.add(new Participant(20, "Isra Memon", "isra@example.com"));
        participants.add(new Participant(30, "Babar Azam", "bob@example.com"));

        Refreshment refreshment = new Refreshment(1, 2, 10);
        refreshment.serveRefreshments(participants);

        FileOperationThread saveMeetingThread = new FileOperationThread(meeting, "meeting.txt", true);
        FileOperationThread saveParticipantThread1 = new FileOperationThread(participants.get(0), "participant1.txt", true);
        FileOperationThread saveParticipantThread2 = new FileOperationThread(participants.get(1), "participant2.txt", true);
        FileOperationThread saveParticipantThread3 = new FileOperationThread(participants.get(2), "participant3.txt", true);

        saveMeetingThread.start();
        saveParticipantThread1.start();
        saveParticipantThread2.start();
        saveParticipantThread3.start();

        try {
            saveMeetingThread.join();
            saveParticipantThread1.join();
            saveParticipantThread2.join();
            saveParticipantThread3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        FileOperationThread loadMeetingThread = new FileOperationThread(meeting, "meeting.txt", false);
        FileOperationThread loadParticipantThread1 = new FileOperationThread(participants.get(0), "participant1.txt", false);
        FileOperationThread loadParticipantThread2 = new FileOperationThread(participants.get(1), "participant2.txt", false);
        FileOperationThread loadParticipantThread3 = new FileOperationThread(participants.get(2), "participant3.txt", false);

        loadMeetingThread.start();
        loadParticipantThread1.start();
        loadParticipantThread2.start();
        loadParticipantThread3.start();
    }
}
