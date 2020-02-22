package vcs;

import java.util.ArrayList;

import utils.Branch;
import utils.ErrorCodeManager;
import utils.OperationType;
import utils.OutputWriter;

/**
 * @author Mares Catalin-Constantin, 322CD
 *
 */
public class StatusOperation extends VcsOperation {
    /**
     * Status operation constructor.
     *
     * @param type          type of the operation
     * @param operationArgs the arguments of the operation
     */
    public StatusOperation(OperationType type, ArrayList<String> operationArgs) {
        super(type, operationArgs);
    }

    /**
     * Executes the status operation.
     *
     * @param vcs the active file system snapshot
     * @return return code
     */

    @Override
    public int execute(Vcs vcs) {
        Branch activeBranch = vcs.getActiveBranch();
        OutputWriter outputWriter = vcs.getOutputWriter();

        /**
         * Print to file the current branch and the staged changes
         */
        outputWriter.write("On branch: " + activeBranch.getName() + "\n");
        outputWriter.write("Staged changes:\n");

        ArrayList<String> stagingOperations = vcs.getStaging();
        for (String operation : stagingOperations) {
            outputWriter.write("\t" + operation + "\n");
        }

        return ErrorCodeManager.OK;
    }
}
