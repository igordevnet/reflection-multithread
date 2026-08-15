import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Main {

    public static void main(String[] args) throws Exception {

        var result = execute(Reflection.class);

        if (result == 2)
            System.out.println("Congrats! You got it: " + result);
        else
            System.out.println("Eh... You did your best: " + result);
    }

    public static int execute(Class clazz) throws Exception {
        int failedCount = 0;
        ExecutorService service = Executors.newFixedThreadPool(10);

        Object instance = Class.forName(clazz.getName()).newInstance();
        List<Future<Integer>> resultList = new ArrayList<>();

        for (Method m : clazz.getDeclaredMethods()) {
            if (m.getName().startsWith("test")) {
                if (m.getGenericReturnType() == boolean.class) {
                    Future<Integer> resultThread = service.submit(new Callable<Integer>() {
                        @Override
                        public Integer call () throws Exception {
                            try {
                                Object result = m.invoke(instance);

                                if (!(boolean) result)
                                    return 1;

                                return 0;
                            } catch (Exception e) {
                                return 0;
                            }
                        }
                    });

                    resultList.add(resultThread);
                }
            }
        }

        for(var r : resultList)
            failedCount += r.get();

        service.shutdownNow();

        return failedCount;
    }
}

