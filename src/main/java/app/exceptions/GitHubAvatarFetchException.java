package app.exceptions;

public class GitHubAvatarFetchException extends RuntimeException {

    public GitHubAvatarFetchException(Throwable cause) {
        super("GitHub avatar fetch error.", cause);
    }
}
