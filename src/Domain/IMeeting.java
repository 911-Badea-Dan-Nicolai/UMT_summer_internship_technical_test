package Domain;

import java.time.LocalTime;

public interface IMeeting {

    LocalTime get_start();

    LocalTime get_end();

    long get_duration();

    void set_start(LocalTime start);

    void set_end(LocalTime end);
}
