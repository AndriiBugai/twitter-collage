package app.exceptions;

public class GitHubIntegrationException extends RuntimeException {

    public GitHubIntegrationException(Throwable cause) {
        super("Failed to connect to GitHub API.", cause);
    }

}
