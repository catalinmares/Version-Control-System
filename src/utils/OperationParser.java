package utils;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Mares Catalin-Constantin, 322CD
 *
 */
public final class OperationParser {
    private static final int SECOND = 2;
    private static final int THIRD = 3;
    private static OperationFactory operationFactory = OperationFactory.getInstance();

    /**
     * Parses an operation.
     *
     * @param newOperation operation string
     * @return the operation
     */
    public AbstractOperation parseOperation(String newOperation) {
        ArrayList<String> operationArgs
                = new ArrayList<>(Arrays.asList(newOperation.split("\\s+")));

        if (operationArgs.get(0).toLowerCase().equals("vcs")) {
            operationArgs.remove(0);
            return parseVcsOperation(newOperation, operationArgs);
        } else {
            return parseFileSystemOperation(newOperation, operationArgs);
        }
    }

    /**
     * Parses the vcs operation and calls the specific method for creating it.
     * @param newOperation the operation name
     * @param operationArgs the operation arguments
     * @return the created operation
     */
    private AbstractOperation parseVcsOperation(String newOperation,
                                                ArrayList<String> operationArgs) {

        String specificCommand = operationArgs.remove(0);

        switch (specificCommand.toLowerCase()) {
            case "status":
                return operationFactory.createOperation(OperationType.STATUS, operationArgs);
            case "branch":
                return operationFactory.createOperation(OperationType.BRANCH, operationArgs);
            case "commit":
                return operationFactory.createOperation(OperationType.COMMIT, operationArgs);
            case "checkout":
                return operationFactory.createOperation(OperationType.CHECKOUT, operationArgs);
            case "log":
                return operationFactory.createOperation(OperationType.LOG, operationArgs);
            case "rollback":
                return operationFactory.createOperation(OperationType.ROLLBACK, operationArgs);
            default:
                return operationFactory
                        .createOperation(OperationType.VCS_INVALID_OPERATION, operationArgs);
        }
    }

    private AbstractOperation parseFileSystemOperation(String newOperation,
                                                       ArrayList<String> operationArgs) {
        ArrayList<String> parsedArgs = null;
        String specificCommand = operationArgs.remove(0);

        switch (specificCommand.toLowerCase()) {
            case "cat":
                return operationFactory.createOperation(OperationType.CAT, operationArgs);
            case "cd":
                return operationFactory.createOperation(OperationType.CHANGEDIR, operationArgs);
            case "ls":
                return operationFactory.createOperation(OperationType.LIST, operationArgs);
            case "mkdir":
                if (operationArgs.size() == 1) {
                    parsedArgs = parsePathAndName(operationArgs.get(0));
                }

                return operationFactory.createOperation(OperationType.MAKEDIR, parsedArgs);
            case "rm":
            case "rmdir":
                operationArgs.add(0, specificCommand);

                return operationFactory.createOperation(OperationType.REMOVE, operationArgs);
            case "touch":
                if (operationArgs.size() == 1) {
                    parsedArgs = parsePathAndName(operationArgs.get(0));
                }

                return operationFactory.createOperation(OperationType.TOUCH, parsedArgs);
            case "writetofile":
                parsedArgs = new ArrayList<String>();

                parsedArgs.add(operationArgs.remove(0));
                parsedArgs.add(newOperation.split("\\s+", THIRD)[SECOND]);

                return operationFactory.createOperation(OperationType.WRITETOFILE, parsedArgs);
            case "print":
                return operationFactory.createOperation(OperationType.PRINT, operationArgs);
            default:
                return operationFactory
                        .createOperation(OperationType.FILESYSTEM_INVALID_OPERATION, operationArgs);
        }
    }

    private ArrayList<String> parsePathAndName(String toParse) {
        ArrayList<String> parsedArgs = new ArrayList<String>();

        if (toParse.contains("/")) {
            parsedArgs.add(toParse.substring(0, toParse.lastIndexOf("/")));
            parsedArgs.add(toParse.substring(toParse.lastIndexOf("/") + 1));
        } else {
            parsedArgs.add("");
            parsedArgs.add(toParse);
        }

        return parsedArgs;
    }
}
