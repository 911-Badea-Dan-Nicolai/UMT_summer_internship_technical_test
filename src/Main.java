import Controller.Controller;
import Controller.IController;
import Domain.IMeeting;
import Domain.Meeting;
import Repository.Calendar;
import Repository.ICalendar;

import java.time.LocalTime;

public class Main {
    public static void main(String[] args) throws Exception {

        //Constructing the first calendar
        IMeeting meet1 = new Meeting(LocalTime.of(9, 00), LocalTime.of(10, 30));
        IMeeting meet2 = new Meeting(LocalTime.of(12, 00), LocalTime.of(13, 00));
        IMeeting meet3 = new Meeting(LocalTime.of(16, 00), LocalTime.of(18, 00));

        LocalTime lower_limit1 = LocalTime.of(9, 00);
        LocalTime upper_limit1 = LocalTime.of(20, 00);

        IMeeting total_window1 = new Meeting(lower_limit1, upper_limit1);

        ICalendar calendar1 = new Calendar(total_window1);

        calendar1.add(meet1);
        calendar1.add(meet2);
        calendar1.add(meet3);

        //Constructing the second calendar
        IMeeting meet4 = new Meeting(LocalTime.of(10, 00), LocalTime.of(11, 30));
        IMeeting meet5 = new Meeting(LocalTime.of(12, 30), LocalTime.of(14, 30));
        IMeeting meet6 = new Meeting(LocalTime.of(14, 30), LocalTime.of(15, 00));
        IMeeting meet7 = new Meeting(LocalTime.of(16, 00), LocalTime.of(17, 00));

        LocalTime lower_limit2 = LocalTime.of(10, 00);
        LocalTime upper_limit2 = LocalTime.of(18, 30);

        IMeeting total_window2 = new Meeting(lower_limit2, upper_limit2);

        Calendar calendar2 = new Calendar(total_window2);

        calendar2.add(meet4);
        calendar2.add(meet5);
        calendar2.add(meet6);
        calendar2.add(meet7);

        IController controller = new Controller(calendar1, calendar2, 30);

        System.out.println("Calendar 1: " + calendar1.toString());
        System.out.println("Calendar 2: " + calendar2.toString());

        //Call the function that calculates the common available time windows for meetings
        System.out.print("Available meeting times: ");
        controller.find_availability();
    }
}