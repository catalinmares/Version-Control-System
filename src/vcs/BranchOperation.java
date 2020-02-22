package vcs;

import java.util.ArrayList;

import utils.ErrorCodeManager;
import utils.OperationType;

/**
 * @author Mares Catalin-Constantin, 322CD
 *
 */
public class BranchOperation extends VcsOperation {
    /**
     * Branch operation constructor.
     *
     * @param type          type of the operation
     * @param operationArgs the arguments of the operation
     */
    public BranchOperation(OperationType type, ArrayList<String> operationArgs) {
        super(type, operationArgs);
    }

    /**
     * Executes the branch operation.
     *
     * @param vcs the active file system snapshot
     * @return return code
     */

    @Override
    public int execute(Vcs vcs) {
        String newBranchName = operationArgs.remove(0);

        /**
         * Check if the given branch name is valid
         * (if no other existent branch has that name)
         */
        boolean validName = vcs.checkBranchName(newBranchName);

        if (!validName) {
            return ErrorCodeManager.VCS_BAD_CMD_CODE;
        } else {
            vcs.createBranch(newBranchName);
            return ErrorCodeManager.OK;
        }
    }
}
