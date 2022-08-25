package carrillodev.ae.tool;

import java.time.LocalTime;

import carrillodev.ae.core.Game;

public class Timer
{
    private LocalTime newCurrentTime;
    private LocalTime oldCurrentTime;

    public Timer()
    {
        oldCurrentTime = java.time.LocalTime.now();
    }

    public boolean checkDifferenceMili(int time)
    {
        newCurrentTime = java.time.LocalTime.now();
        Game.log(newCurrentTime.getSecond() - oldCurrentTime.getSecond());
        if (newCurrentTime.getSecond() - oldCurrentTime.getSecond() >= time - 1)
        {
            oldCurrentTime = newCurrentTime;
            return true;
        }

        return false;
    }
}