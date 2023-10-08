import java.io.*;
import java.util.ArrayList;

class Participant {
    private int age;
    private String name;
    private String email;

    public Participant(int age, String name, String email) {
        this.age = age;
        this.name = name;
        this.email = email;
    }

    @Override
    public String toString() {
        return "Age: " + age + ", Name: " + name + ", Email: " + email;
    }
}

public class Participants  {
    private static ArrayList<Participant> participants = new ArrayList<>();

    public static void addParticipant(int age, String name, String email) {
        Participant participant = new Participant(age, name, email);
        participants.add(participant);
    }

    public static void saveToFile(String fileName) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(participants);
            System.out.println("Participants data saved to file: " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}