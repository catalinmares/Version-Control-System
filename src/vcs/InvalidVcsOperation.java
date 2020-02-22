package vcs;

import java.util.ArrayList;
import utils.ErrorCodeManager;
import utils.OperationType;

/**
 * @author Mares Catalin-Constantin, 322CD
 *
 */
public class InvalidVcsOperation extends VcsOperation {
    /**
     * Invalid vcs operation constructor.
     *
     * @param type          type of the operation
     * @param operationArgs the arguments of the operation
     */
    public InvalidVcsOperation(OperationType type, ArrayList<String> operationArgs) {
        super(type, operationArgs);
    }

    /**
     * Execute the invalid vcs operation.
     *
     * @param fileSystemSnapshot the active file system snapshot
     * @return return code
     */
    @Override
    public int execute(Vcs vcs) {
        return ErrorCodeManager.VCS_BAD_CMD_CODE;
    }
}
