
public class Timer {

    public long[] execTime = null;

    public Timer() {
        execTime = new long[0];
    }

    public void add(long time) {
        long tmp[] = new long[execTime.length + 1];
        int i = 0;
        for (i = 0; i < execTime.length; i++) {
            tmp[i] = execTime[i];
        }
        tmp[i] = time;
        execTime = tmp;
    }

    public long getTotal() {
        long res = 0;
        for (long e : execTime)
            res += e;

        return res;
    }

    public long getAvg() {
        long res = 0;
        for (long e : execTime)
            res += e;
        res = res / execTime.length;

        return res;
    }

}
