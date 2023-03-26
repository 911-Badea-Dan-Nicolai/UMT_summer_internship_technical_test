package Repository;

import Domain.IMeeting;

import java.util.ArrayList;

public class Calendar implements ICalendar{
    //A calendar would be an array of meetings
    ArrayList<IMeeting> calendar;
    //Seeing the maximum ranges of a calendar is a useful thing to have,
    //so we don't schedule meetings at unavailable times
    IMeeting total_window;

    public Calendar(IMeeting total_window) {
        this.calendar = new ArrayList<>();
        this.total_window = total_window;
    }

    @Override
    public String toString() {
        String output = "Booked: [";

        for (IMeeting elem: calendar){
            output = output + elem.toString();
        }

        output = output + "]";

        return output;
    }

    @Override
    public void add(IMeeting m) throws Exception {
        if(m.get_start().isBefore(this.total_window.get_start()) ||
        m.get_end().isAfter(this.total_window.get_end()))
            throw new Exception("Meeting at unavailable time");
        calendar.add(m);
    }

    @Override
    public String get_range_limits() {
        return "Range limits: " + total_window.toString();
    }

    @Override
    public IMeeting get_total_window() {
        return total_window;
    }

    @Override
    public ArrayList<IMeeting> get_calendar() {
        return calendar;
    }


}
