import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SimpleService implements Service {
    @Override
    public List<String> run(String item, double value, Date date) {
        try {
            TimeUnit.MILLISECONDS.sleep(1500);
        } catch (InterruptedException ignore) {}
        List<String> list = new ArrayList<>();
        for(int i = 0; i < item.length(); i++)
            list.add(item + ' ' + value + ' ' + date);
        return list;
    }

    @Override
    public List<String> work(String item) {
        try {
            TimeUnit.MILLISECONDS.sleep(1500);
        } catch (InterruptedException ignore) {}
        List<String> list = new ArrayList<>();
        for(int i = 0; i < item.length(); i++)
            list.add(item);
        return list;
    }
}
