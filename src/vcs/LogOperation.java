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
public class LogOperation extends VcsOperation {
    /**
     * Log operation constructor.
     *
     * @param type          type of the operation
     * @param operationArgs the arguments of the operation
     */
    public LogOperation(OperationType type, ArrayList<String> operationArgs) {
        super(type, operationArgs);
    }

    /**
     * Executes the log operation.
     *
     * @param vcs the active file system snapshot
     * @return return code
     */

    @Override
    public int execute(Vcs vcs) {
        Branch activeBranch = vcs.getActiveBranch();
        ArrayList<Commit> commits = activeBranch.getCommits();

        /**
         * Print to file the list of commits of the active branch
         */
        for (int i = 0; i < commits.size(); i++) {
            vcs.getOutputWriter().write("Commit id: " + commits.get(i).getCommitId() + "\n");
            vcs.getOutputWriter().write("Message: " + commits.get(i).getMessage() + "\n");

            /**
             * For output-ref matching
             */
            if (i != commits.size() - 1) {
                vcs.getOutputWriter().write("\n");
            }
        }

        return ErrorCodeManager.OK;
    }
}
