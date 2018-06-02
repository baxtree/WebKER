package jackteng.util;

public class TimeWatch {

    long time;

    public TimeWatch() {
	time = System.currentTimeMillis();
    }

    public void reset() {
	time = System.currentTimeMillis();
    }

    public void display(String desc) {
	long ntime;
	ntime = System.currentTimeMillis();
	System.err.println(desc + (ntime - time) + " ms");
	time = ntime;
    }
}
