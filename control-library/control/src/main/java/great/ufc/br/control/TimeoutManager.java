/*
* File: TimeoutManager.java 
* Created: 2017-07-01
* Authors:
*   - Ismayle de Sousa Santos
*   - Erick Barros dos Santos
*   - Rossana Maria de Castro Andrade
*   - Pedro de Alcântara dos Santos Neto
*/

package great.ufc.br.control;

import java.util.Timer;
import java.util.TimerTask;

import great.ufc.br.control.annotations.ControlStatusAdapted;

public class TimeoutManager {
    private Timer mTimer;
    private long mTimeoutPeriod = 10000;
    private boolean timeouted;

    public TimeoutManager(long timeoutPeriod) {
        mTimer = new Timer();
        if (timeoutPeriod > 0) {
            mTimeoutPeriod = timeoutPeriod;
        }
    }

    class CancelCurrentTestCase extends TimerTask {
        public void run() {
            timeouted = true;
            testCaseTimout();
            mTimer.cancel();
        }
    }

    public void startTimer() {
        mTimer.schedule(new CancelCurrentTestCase(), mTimeoutPeriod);
    }

    public void stopTimer() {
        mTimer.cancel();
    }

    @ControlStatusAdapted
    public String testCaseTimout() {
        return "ControlTestCaseTimeout";
    }

    public boolean isTimeouted() {
        return this.timeouted;
    }
}