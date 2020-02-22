package vcs;

import java.util.ArrayList;

import utils.Branch;
import utils.Commit;
import utils.ErrorCodeManager;
import utils.OperationType;

/**
 * @author Mares Catalin-Constantin, 322CD
 *
 */
public class CheckoutOperation extends VcsOperation {
    /**
     * Checkout operation constructor.
     *
     * @param type          type of the operation
     * @param operationArgs the arguments of the operation
     */
    public CheckoutOperation(OperationType type, ArrayList<String> operationArgs) {
        super(type, operationArgs);
    }

    /**
     * Executes the checkout operation.
     *
     * @param vcs the active file system snapshot
     * @return return code
     */

    @Override
    public int execute(Vcs vcs) {
        Branch activeBranch = vcs.getActiveBranch();
        ArrayList<String> staging = vcs.getStaging();

        /**
         * If the staging is not empty, there are staged operations
         * Checkout is not permitted
         */
        if (staging.size() != 0) {
            return ErrorCodeManager.VCS_STAGED_OP_CODE;
        }

        /**
         * If we are checking out on a previous commit
         */
        if (operationArgs.get(0).equals("-c")) {
            int commitId = Integer.parseInt(operationArgs.get(1));

            /**
             * Check if the commit ID is valid
             */
            boolean validId = activeBranch.checkCommitId(commitId);

            if (!validId) {
                return ErrorCodeManager.VCS_BAD_PATH_CODE;
            } else {
                /**
                 * Move to the commit given.
                 * Set the active file system snapshot to the
                 * file system snapshot of the commit we moved to
                 */
                activeBranch.moveTo(commitId);
                Commit checkoutCommit = activeBranch.getCommit(commitId);
                vcs.setActiveSnapshot(checkoutCommit.getActiveSnapshot());

                return ErrorCodeManager.OK;
            }
        } else {
            /**
             * If we are checking out on another branch
             */
            String branchName = operationArgs.get(0);
            boolean notValidName = vcs.checkBranchName(branchName);
            /**
             * Check if the branch with the given name exists
             */

            if (notValidName) {
                return ErrorCodeManager.VCS_BAD_CMD_CODE;
            } else {
                vcs.moveTo(branchName);

                return ErrorCodeManager.OK;
            }
        }
    }
}
