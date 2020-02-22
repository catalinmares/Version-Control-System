package utils;

import filesystem.FileSystemSnapshot;

/**
 * @author Mares Catalin-Constantin, 322CD
 *
 */
public final class Commit {
    private int id;
    private String message;
    private FileSystemSnapshot fileSystem;

    /**
     * Commit constructor.
     *
     * @param message the message of the commit
     * @param fileSystem the file system of the commit
     */
    public Commit(String message, FileSystemSnapshot fileSystem) {
        this.id = IDGenerator.generateCommitID();
        this.message = message;
        this.fileSystem = fileSystem;
    }

    /**
     * Gets the commit ID.
     *
     * @return the commit ID
     */
    public int getCommitId() {
        return this.id;
    }

    /**
     * Gets the file system of the commit.
     *
     * @return the file system of the commit
     */
    public FileSystemSnapshot getActiveSnapshot() {
        return this.fileSystem;
    }

    /**
     * Gets the message of the commit.
     *
     * @return the message of the commit
     */
    public String getMessage() {
        return this.message;
    }
}
