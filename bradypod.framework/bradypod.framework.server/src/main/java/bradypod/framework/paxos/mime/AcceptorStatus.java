package bradypod.framework.paxos.mime;

/**
 * @author xiangmin.zeng@vistel.cn
 * @version: 1.0    2019/6/28
 */
public enum AcceptorStatus {
    ACCEPTED, //	接受
    PROMISED, //	承诺
    NONE    //	未处理过任何提案
}
