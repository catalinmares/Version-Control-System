package utils;

import filesystem.FileSystemOperation;
import vcs.VcsOperation;

/**
 * @author Mares Catalin-Constantin, 322CD
 *
 */
public interface Visitor {
    /**
     * Visits a file system operation.
     *
     * @param fileSystemOperation the file system operation
     * @return the return code
     */
    int visit(FileSystemOperation fileSystemOperation);


    /**
     * Visits a vcs operation.
     *
     * @param vcsOperation the vcs operation
     * @return the return code
     */
    int visit(VcsOperation vcsOperation);
}
