package it.unibo.oop.exceptions;

public class SpritesNotSplittableException extends Exception {

    private static final long serialVersionUID = -5160337647169133194L;

    public SpritesNotSplittableException() {
        super();
    }

    public SpritesNotSplittableException(final String message) {
        super(message);
    }
}