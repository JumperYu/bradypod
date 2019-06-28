package bradypod.framework.paxos.mime;

/**
 * @author xiangmin.zeng@vistel.cn
 * @version: 1.0    2019/6/28
 */
public class PrepareResult {

    private boolean promised;
    private AcceptorStatus acceptorStatus;
    private Proposal proposal;

    public PrepareResult(boolean promised) {
        this.promised = promised;
    }

    public boolean isPromised() {
        return promised;
    }

    public PrepareResult setPromised(boolean promised) {
        this.promised = promised;
        return this;
    }

    public AcceptorStatus getAcceptorStatus() {
        return acceptorStatus;
    }

    public PrepareResult setAcceptorStatus(AcceptorStatus acceptorStatus) {
        this.acceptorStatus = acceptorStatus;
        return this;
    }

    public Proposal getProposal() {
        return proposal;
    }

    public PrepareResult setProposal(Proposal proposal) {
        this.proposal = proposal;
        return this;
    }
}
