package utils;

import java.util.ArrayList;

import filesystem.FileSystemSnapshot;

/**
 * @author Mares Catalin-Constantin, 322CD
 *
 */
public final class Branch {
    private String name;
    private boolean head;
    private ArrayList<Commit> commits;
    private FileSystemSnapshot activeSnapshot;

    /**
     * Branch constructor.
     *
     * @param name the name of the new branch
     * @param activeSnapshot the system snapshot of the new branch
     * @param head whether the branch is head or not
     */
    public Branch(String name, FileSystemSnapshot activeSnapshot, boolean head) {
        this.name = name;
        this.activeSnapshot = activeSnapshot;
        this.head = head;
        this.commits = new ArrayList<Commit>();
    }

    /**
     * Gets the branch name.
     *
     * @return the branch name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets the commits made on this branch.
     *
     * @return list of commits made on the current branch
     */
    public ArrayList<Commit> getCommits() {
        return this.commits;
    }

    /**
     * Gets the active system snapshot.
     *
     * @return the active file system snapshot
     */
    public FileSystemSnapshot getActiveSnapshot() {
        return this.activeSnapshot;
    }

    /**
     * Checks if the branch is active.
     *
     * @return true if the branch is active
     *         false otherwise
     */
    public boolean isHead() {
        return this.head;
    }
    /**
     * Sets the head of the branch to false.
     */
    public void resetHead() {
        this.head = false;
    }

    /**
     * Sets the head of the branch to true.
     */
    public void setHead() {
        this.head = true;
    }

    /**
     * Adds a new commit to the list.
     *
     * @param newCommit the commit to be added
     */
    public void addCommit(Commit newCommit) {
        commits.add(newCommit);
    }

    /**
     * Method used for the checkout operation.
     *
     * @param commitId the id of the commit to checkout to
     * @return true if the commit with the given ID exists
     *         false otherwise
     */
    public boolean checkCommitId(int commitId) {
        for (Commit commit : commits) {
            if (commit.getCommitId() == commitId) {
                return true;
            }
        }

        return false;
    }

    /**
     * Method used for the checkout operation.
     * Removes all the commits created after the commit with
     * the given ID
     *
     * @param commitId the ID of the commit to checkout to
     */
    public void moveTo(int commitId) {
        for (int i = 0; i < commits.size(); i++) {
            if (commits.get(i).getCommitId() > commitId) {
                commits.remove(i);
                i--;
            }
        }
    }

    /**
     * Gets the commit with the given ID from the list of commits.
     *
     * @param commitId the ID of the commit needed
     * @return the commit with the given ID
     */
    public Commit getCommit(int commitId) {
        for (Commit commit : commits) {
            if (commit.getCommitId() == commitId) {
                return commit;
            }
        }

        return null;
    }
}
