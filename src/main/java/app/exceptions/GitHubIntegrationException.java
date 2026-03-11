package app.exceptions;

public class GitHubIntegrationException extends RuntimeException {

    public GitHubIntegrationException(Throwable cause) {
        super("GitHub integration error.", cause);
    }

}
