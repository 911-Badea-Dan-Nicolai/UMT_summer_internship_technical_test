package Controller;

import Domain.IMeeting;
import Domain.Meeting;
import Repository.Calendar;
import Repository.ICalendar;

import java.time.LocalTime;

public class Controller implements IController{
    //In the controller I put the logic of the program
    ICalendar calendar1;
    ICalendar calendar2;
    long meet_time;

    public Controller(ICalendar calendar1, ICalendar calendar2, long meet_time) {
        this.calendar1 = calendar1;
        this.calendar2 = calendar2;
        this.meet_time = meet_time;
    }

    @Override
    public String toString() {
        return this.calendar1.toString() + "\n" + this.calendar1.get_range_limits() +
                "\n\n" + this.calendar2.toString() + "\n" + this.calendar2.get_range_limits() + "\n\n" +
                "Meeting Time Minutes: " + this.meet_time;
    }

    LocalTime get_max(LocalTime start1, LocalTime start2){
        //Function for getting the latest of the 2 LocalTimes
        if(start1.isAfter(start2)){
            return start1;
        }
        return start2;
    }

    LocalTime get_min(LocalTime end1, LocalTime end2){
        //Function for getting the earliest of the 2 LocalTimes
        if(end1.isBefore(end2)){
            return end1;
        }
        return end2;
    }

    @Override
    public void find_availability() throws Exception {
        //This is the main algorithm for solving the problem

        //I calculate the endpoints of the common interval of the calendar
        LocalTime lower_limit = this.get_max(
                calendar1.get_total_window().get_start(),
                calendar2.get_total_window().get_start()
        );

        LocalTime upper_limit = this.get_min(
                calendar1.get_total_window().get_end(),
                calendar2.get_total_window().get_end()
        );

        //Now I have the interval in which both calendars intersect
        IMeeting total_window = new Meeting(lower_limit, upper_limit);

        //And so I make a new calendar in which I am going to store the possible meetings
        ICalendar common_calendar = new Calendar(total_window);

        //At first, we have to check if there are available meeting times before the first meeting in both calendars
        LocalTime first_meeting_start_time = this.get_min(
                this.calendar1.get_calendar().get(0).get_start(),
                this.calendar2.get_calendar().get(0).get_start()
        );

        IMeeting free_time_before_first_meetings = new Meeting(common_calendar.get_total_window().get_start(),
                first_meeting_start_time);

        if(free_time_before_first_meetings.get_start().isBefore(free_time_before_first_meetings.get_end()))
            common_calendar.add(free_time_before_first_meetings);

        //After that, we need to check if there are any other available slots in the calendar, between all the meetings
        for (int i = 1; i < this.calendar1.get_calendar().size(); i++){
            //I iterate through all the meetings in calendar 1 to see where there is free time and how much
            IMeeting free_time1 = new Meeting(this.calendar1.get_calendar().get(i-1).get_end(),
                    this.calendar1.get_calendar().get(i).get_start());

            for (int j = 1; j < this.calendar2.get_calendar().size(); j++){
                //The same iteration is done for the second calendar
                IMeeting free_time2 = new Meeting(
                        this.calendar2.get_calendar().get(j-1).get_end(),
                        this.calendar2.get_calendar().get(j).get_start());

                //Now we can create a new meeting by considering the 2 available meet times in the calendars
                IMeeting common_free_time = new Meeting(
                        this.get_max(free_time1.get_start(), free_time2.get_start()),
                        this.get_min(free_time1.get_end(), free_time2.get_end())
                );

                //Here we want to see if the common free time for the meeting is actually valid
                if(common_free_time.get_end().isBefore(calendar1.get_calendar().get(i).get_end()) &&
                common_free_time.get_end().isBefore(calendar2.get_calendar().get(j).get_end()) &&
                common_free_time.get_start().isBefore(common_free_time.get_end())){
                    //And if it is we add it to the common calendar
                    common_calendar.add(common_free_time);
                }

            }
        }

        //Now we also have to check if there is any available time for meetings
        //after the last meetings in both calendars
        LocalTime last_meeting_end_time = this.get_max(
                this.calendar1.get_calendar().get(this.calendar1.get_calendar().size() - 1).get_end(),
                this.calendar2.get_calendar().get(this.calendar2.get_calendar().size() - 1).get_end()
        );

        IMeeting free_time_after_all_meetings = new Meeting(last_meeting_end_time,
                common_calendar.get_total_window().get_end());

        if(free_time_after_all_meetings.get_start().isBefore(free_time_after_all_meetings.get_end()))
            common_calendar.add(free_time_after_all_meetings);

        //Finally, we print the result
        System.out.println(common_calendar.toString());;
    }
}
