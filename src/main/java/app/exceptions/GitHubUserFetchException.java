package app.exceptions;

public class GitHubUserFetchException extends RuntimeException {

    public GitHubUserFetchException(Throwable cause) {
        super("GitHub user not found.", cause);
    }

}
