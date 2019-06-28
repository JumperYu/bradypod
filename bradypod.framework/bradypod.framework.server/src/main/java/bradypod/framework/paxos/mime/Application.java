package bradypod.framework.paxos.mime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * my paxos
 *
 * @author xiangmin.zeng@vistel.cn
 * @version: 1.0    2019/6/28
 */
public class Application {

    public static void main(String[] args) {
        List<Acceptor> acceptors = new ArrayList<>();
        int maxProposer = 5;

        for (int i = 0; i < 5; i++) {
            acceptors.add(new Acceptor());
        }

        ExecutorService pool = Executors.newFixedThreadPool(maxProposer);
        for (int i = 0; i < maxProposer; i++) {
            pool.submit(new Proposer(i, maxProposer, acceptors));
        }

        pool.shutdown();
    }
}