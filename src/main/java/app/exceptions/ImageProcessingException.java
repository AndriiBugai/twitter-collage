package app.exceptions;

public class ImageProcessingException extends RuntimeException {

    public ImageProcessingException(Throwable cause) {
        super("Image Processing Error.", cause);
    }

}
