package bradypod.framework.paxos.mime;

/**
 * 决策者
 *
 * @author xiangmin.zeng@vistel.cn
 * @version: 1.0    2019/6/28
 */
public class Acceptor {

    public PrepareResult onPrepare(Proposal proposal) {
        return new PrepareResult(true);
    }

    public void onAccept(Proposal proposal) {

    }
}
