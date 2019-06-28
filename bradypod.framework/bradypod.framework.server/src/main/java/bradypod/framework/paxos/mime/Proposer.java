package bradypod.framework.paxos.mime;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 提议者, 二阶提交
 *
 * @author xiangmin.zeng@vistel.cn
 * @version: 1.0    2019/6/28
 */
public class Proposer implements Runnable {

    private int id;
    private int proposalCount;

    private boolean voted = false;

    private static AtomicInteger seq = new AtomicInteger(0);

    private Proposal proposal;

    private List<Acceptor> acceptors;

    public Proposer(int id, int proposalCount, List<Acceptor> acceptors) {
        this.id = id;
        this.proposalCount = proposalCount;
        this.acceptors = acceptors;
        this.proposal = new Proposal(seq.getAndIncrement(), this.id + "#" + "最帅");
    }

    /**
     * 预提交
     */
    public synchronized boolean prepare() {

        int promiseCount = 0;

        List<Proposal> promisedProposals = new ArrayList<Proposal>();
        List<Proposal> acceptedProposals = new ArrayList<>();

        while (true) {
            for (Acceptor acceptor : acceptors) {
                PrepareResult prepareResult = acceptor.onPrepare(proposal);
                if (prepareResult.isPromised()) {
                    promiseCount++;
                } else {
                    //~	决策者已经给了更高id题案的承诺
                    if (prepareResult.getAcceptorStatus() == AcceptorStatus.ACCEPTED) {
                        promisedProposals.add(prepareResult.getProposal());
                    }
                    //~	决策者已经通过了一个题案
                    if (prepareResult.getAcceptorStatus() == AcceptorStatus.ACCEPTED) {
                        acceptedProposals.add(prepareResult.getProposal());
                    }
                }//~ end of if-else
            }//~ end of for

            if (promiseCount > (acceptors.size() / 2 + 1)) {
                break;
            }//~ 可以进行第二阶段：题案提交
        }

        Proposal maxAcceptedProposal = getMaxIdProposal(promisedProposals);

        //~	在已经被决策者通过题案中选择序列号最大的决策,作为自己的决策。
        if (maxAcceptedProposal != null) {
            proposal.setSeq(1);
            proposal.setVal("");
        } else {
            proposal.setSeq(1);
        }

        return false;
    }

    //~ prepare阶段接收到超过半数以上的同意则会向所有决策者发送此提议
    public boolean accept() {
        System.out.println(this.id + " accepted");
        return false;
    }

    @Override
    public void run() {
        prepare();
        accept();
    }

    //	获得序列号最大的提案
    private Proposal getMaxIdProposal(List<Proposal> acceptedProposals) {
        Proposal tmp = null;
        if (acceptedProposals != null) {
            for (Proposal proposal : acceptedProposals) {
                if (tmp == null || proposal.getSeq() > tmp.getSeq()) {
                    tmp = proposal;
                }
            }
        }
        return tmp;
    }

    //	是否已经有某个提案，被大多数决策者接受
    private Proposal votedEnd(List<Proposal> acceptedProposals) {
        Map<Proposal, Integer> proposalCount = countAcceptedProposalCount(acceptedProposals);
        for (Map.Entry<Proposal, Integer> entry : proposalCount.entrySet()) {
            if (entry.getValue() >= acceptors.size() / 2 + 1) {
                voted = true;
                return entry.getKey();
            }
        }
        return null;
    }

    //	计算决策者回复的每个已经被接受的提案计数
    private Map<Proposal, Integer> countAcceptedProposalCount(
            List<Proposal> acceptedProposals) {
        Map<Proposal, Integer> proposalCount = new HashMap<>();
        for (Proposal proposal : acceptedProposals) {
            //	决策者没有回复，或者网络异常
            int count = 1;
            if (proposalCount.containsKey(proposal)) {
                count = proposalCount.get(proposal) + 1;
            }
            proposalCount.put(proposal, count);
        }
        return proposalCount;
    }
}

/**
 * 提议
 */
@Data
class Proposal {
    private int seq; //~ 序列
    private String val; //~ 提议值

    public Proposal(int seq, String val) {
        this.seq = seq;
        this.val = val;
    }
}
