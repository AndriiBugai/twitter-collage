package app.exceptions;

public class GitHubResourceNotFoundException extends RuntimeException {

    public GitHubResourceNotFoundException(Throwable cause) {
        super("GitHub resource not found.", cause);
    }

}
