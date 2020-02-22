package vcs;

import java.util.ArrayList;

import filesystem.FileSystemOperation;
import filesystem.FileSystemSnapshot;
import utils.OutputWriter;
import utils.Visitor;
import utils.AbstractOperation;
import utils.Branch;
import utils.Commit;

/**
 * @author Mares Catalin-Constantin, 322CD
 *
 */
public final class Vcs implements Visitor {
    private final OutputWriter outputWriter;
    private FileSystemSnapshot activeSnapshot;
    private ArrayList<Branch> branches;
    private ArrayList<String> stagingOperations;

    /**
     * Vcs constructor.
     *
     * @param outputWriter the output writer
     */
    public Vcs(OutputWriter outputWriter) {
        this.outputWriter = outputWriter;
    }

    /**
     * Does initialisations.
     */
    public void init() {
        this.activeSnapshot = new FileSystemSnapshot(outputWriter);
        this.branches = new ArrayList<Branch>();
        this.branches.add(new Branch("master", activeSnapshot.cloneFileSystem(), true));

        Commit firstCommit = new Commit("First commit", activeSnapshot.cloneFileSystem());

        this.branches.get(0).addCommit(firstCommit);
        this.stagingOperations = new ArrayList<String>();
    }

    /**
     * Visits a file system operation.
     *
     * @param fileSystemOperation the file system operation
     * @return the return code
     */
    public int visit(FileSystemOperation fileSystemOperation) {
        return fileSystemOperation.execute(this.activeSnapshot);
    }

    /**
     * Visits a vcs operation.
     *
     * @param vcsOperation the vcs operation
     * @return return code
     */
    @Override
    public int visit(VcsOperation vcsOperation) {
        return vcsOperation.execute(this);
    }

    /**
     * Sets the active snapshot.
     *
     * @param activeSnapshot the new system snapshot
     */
    public void setActiveSnapshot(FileSystemSnapshot activeSnapshot) {
        this.activeSnapshot = activeSnapshot;
    }

    /**
     * Gets the active snapshot.
     *
     * @return the current file system snapshot
     */
    public FileSystemSnapshot getActiveSnapshot() {
        return this.activeSnapshot;
    }

    /**
     * Gets the staging operations.
     *
     * @return the array list of operations on stage
     */
    public ArrayList<String> getStaging() {
        return this.stagingOperations;
    }

    /**
     * Gets the output writer.
     *
     * @return the current output writer
     */
    public OutputWriter getOutputWriter() {
        return this.outputWriter;
    }

    /**
     * Puts the trackable operations in the staging list.
     *
     * @param operation the operation to be staged
     */
    public void trackOperation(AbstractOperation operation) {
        switch (operation.getType()) {
            case CHANGEDIR:
                this.stagingOperations.add("Changed directory to "
                + activeSnapshot.getCurrentDir());
                break;

            case MAKEDIR:
                this.stagingOperations.add("Created directory "
                + operation.getOperationArgs().get(1));
                break;

            case REMOVE:
                String arg1 = operation.getOperationArgs().get(1);

                if (arg1.equals("-r")) {
                    String arg2 = operation.getOperationArgs().get(2);
                    this.stagingOperations.add("Removed " + arg2);
                } else {
                    this.stagingOperations.add("Removed " + arg1);
                }

                break;

            case TOUCH:
                this.stagingOperations.add("Created file " + operation.getOperationArgs().get(1));
                break;
            case WRITETOFILE:
                String path = operation.getOperationArgs().get(0);
                String fileName = this.activeSnapshot.getEntity(path).getName();
                this.stagingOperations.add("Added " + '"' + operation.getOperationArgs().get(1)
                + '"' + " to file " + fileName);
                break;

            default: break;
        }
    }

    /**
     * Gets the active branch of the vcs.
     *
     * @return the instance of the active branch
     */
    public Branch getActiveBranch() {
        for (Branch branch : branches) {
            if (branch.isHead()) {
                return branch;
            }
        }

        return null;
    }

    /**
     * Method used for determining if the argument
     * of the operation branch is a valid name.
     *
     * @param name name of the branch to be created
     * @return true if no other branch has the given name
     *         false if there is another branch in the vcs with the given name
     */
    public boolean checkBranchName(String name) {
        for (Branch branch : branches) {
            if (branch.getName().equals(name)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Creates a new branch.
     *
     * @param name the name of the new branch
     */
    public void createBranch(String name) {
        Branch activeBranch = getActiveBranch();
        ArrayList<Commit> commits = activeBranch.getCommits();
        FileSystemSnapshot branchFileSystem = null;

        /**
         *  If the current branch has commits, the new branch will inherit the
         * file system of the last commit created on the current branch;
         * otherwise the branch will inherit the current system snapshot
         */
        if (commits.size() != 0) {
            Commit lastCommit = commits.get(commits.size() - 1);
            branchFileSystem = lastCommit.getActiveSnapshot();
        } else {
            branchFileSystem = activeSnapshot;
        }

        this.branches.add(new Branch(name, branchFileSystem, false));
    }

    /**
     * Method used for the checkout operation.
     * Moves the head pointer to the branch with the given name
     * and sets the active snapshot to the new branch's snapshot
     * @param name
     */
    public void moveTo(String name) {
        /**
         * The active branch must not be the head branch anymore.
         */
        Branch activeBranch = getActiveBranch();
        activeBranch.resetHead();

        for (Branch branch : branches) {
            if (branch.getName().equals(name)) {
                branch.setHead();
                setActiveSnapshot(branch.getActiveSnapshot());
                break;
            }
        }
    }
}
