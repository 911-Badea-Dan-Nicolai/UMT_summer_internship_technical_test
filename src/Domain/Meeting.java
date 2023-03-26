package Domain;

import java.time.LocalTime;

import static java.time.temporal.ChronoUnit.MINUTES;

public class Meeting implements IMeeting{
    //I consider a meeting an interval between 2 LocalTimes
    LocalTime start;
    LocalTime end;

    public Meeting(LocalTime start, LocalTime end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public LocalTime get_start(){
        return this.start;
    }

    @Override
    public LocalTime get_end() {
        return this.end;
    }

    @Override
    public long get_duration() {
        return MINUTES.between(start, end);
    }

    @Override
    public void set_start(LocalTime start) {
        this.start = start;
    }

    @Override
    public void set_end(LocalTime end) {
        this.end =end;
    }

    @Override
    public String toString() {
        return "['" + start + "','" + end + "']";
    }
}
