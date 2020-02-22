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
public class RollbackOperation extends VcsOperation {
    /**
     * Rollback operation constructor.
     *
     * @param type          type of the operation
     * @param operationArgs the arguments of the operation
     */
    public RollbackOperation(OperationType type, ArrayList<String> operationArgs) {
        super(type, operationArgs);
    }

    /**
     * Executes the rollback operation.
     *
     * @param vcs the active file system snapshot
     * @return return code
     */

    @Override
    public int execute(Vcs vcs) {
        Branch activeBranch = vcs.getActiveBranch();
        ArrayList<String> staging = vcs.getStaging();

        /**
         * Empty the staging
         */
        staging.removeAll(staging);

        /**
         * Restore the file system snapshot to the last commit created
         */
        ArrayList<Commit> commits = activeBranch.getCommits();
        vcs.setActiveSnapshot(commits.get(commits.size() - 1).getActiveSnapshot());

        return ErrorCodeManager.OK;
    }
}
