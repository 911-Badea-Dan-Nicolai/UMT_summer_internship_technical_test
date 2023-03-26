package Repository;

import Domain.IMeeting;

import java.util.ArrayList;

public interface ICalendar {

    void add(IMeeting m) throws Exception;

    String get_range_limits();

    IMeeting get_total_window();

    ArrayList<IMeeting> get_calendar();
}
