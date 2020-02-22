package vcs;

import java.util.ArrayList;

import filesystem.FileSystemSnapshot;
import utils.Branch;
import utils.Commit;
import utils.ErrorCodeManager;
import utils.OperationType;

/**
 * @author Mares Catalin-Constantin, 322CD
 *
 */
public class CommitOperation extends VcsOperation {
    /**
     * Commit operation constructor.
     *
     * @param type          type of the operation
     * @param operationArgs the arguments of the operation
     */
    public CommitOperation(OperationType type, ArrayList<String> operationArgs) {
        super(type, operationArgs);
    }

    /**
     * Executes the commit operation.
     *
     * @param vcs the active file system snapshot
     * @return return code
     */

    @Override
    public int execute(Vcs vcs) {
        ArrayList<String> staging = vcs.getStaging();

        /**
         * If there are no staged operations, we cannot commit.
         */
        if (staging.size() == 0) {
            return ErrorCodeManager.VCS_BAD_CMD_CODE;
        }

        Branch activeBranch = vcs.getActiveBranch();
        operationArgs.remove(0); // Remove -m

        /**
         * Store the commit message
         */
        String message = operationArgs.remove(0);

        while (operationArgs.size() != 0) {
            message += " " + operationArgs.remove(0);
        }

        /**
         * Empty the staging
         */
        staging.removeAll(staging);

        /**
         * Add the new commit to the list of commits of the active branch.
         */
        FileSystemSnapshot activeSnapshot = vcs.getActiveSnapshot();
        activeBranch.addCommit(new Commit(message, activeSnapshot.cloneFileSystem()));

        return ErrorCodeManager.OK;
    }
}
