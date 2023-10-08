import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Meeting implements Information {
    private String title;
    private Date date;
    private String location;
//    private List<String> participants;
    private List<String> agendaItems;

    public Meeting(String title, Date date, String location) {
        this.title = title;
        this.date = date;
        this.location = location;
//        this.participants = new ArrayList<>();
        this.agendaItems = new ArrayList<>();
    }

//    public void addParticipant(String participant) {
//        participants.add(participant);
//    }

    public void addAgendaItem(String agendaItem) {
        agendaItems.add(agendaItem);
    }

    public void displayMeetingDetails() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("Meeting Title: " + title);
        System.out.println("Date and Time: " + sdf.format(date));
        System.out.println("Location: " + location);
//        System.out.println("Participants: " + participants);
        System.out.println("Agenda Items: " + agendaItems);
    }
}