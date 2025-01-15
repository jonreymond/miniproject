package miniproject.utils;

// From https://github.com/brogovia/antSimulation/blob/master/projet/ch/epfl/moocprog/
public final class Utils {
    // Empêche l'instanciation de cette classe
    private Utils() {}



    /**
     * This method checks if a given condition is true. If the condition is false, it throws an {@link IllegalArgumentException}.
     *
     * @param message A custom error message to be included in the exception if the condition is false.
     *                If this parameter is null, the exception will not include a custom error message.
     * @param condition The condition to be checked.
     *
     * @throws IllegalArgumentException If the condition is false. If a custom error message is provided,
     *                                  it will be included in the exception.
     */
    public static void require(String message, boolean condition) {
        if(!condition) {
            if (message == null) {
                throw new IllegalArgumentException();
            } else {
                throw new IllegalArgumentException(message);
            }
        }
    }


    /**
     * This method checks if a given condition is true. If the condition is false, it throws an {@link IllegalArgumentException}.
     * This method is a simplified version of the {@link #require(String, boolean)} method, where the custom error message is set to null.
     *
     * @param condition The condition to be checked.
     *
     * @throws IllegalArgumentException If the condition is false. Since no custom error message is provided,
     *                                  the exception will not include a custom error message.
     */
    public static void require(boolean condition) {
        require(null, condition);
    }

    /**
     * This method checks if a given object is not null. If the object is null, it throws an {@link IllegalArgumentException}.
     *
     * @param message A custom error message to be included in the exception if the object is null.
     *                If this parameter is null, the exception will not include a custom error message.
     * @param toTest The object to be checked for null.
     *
     * @throws IllegalArgumentException If the object is null. If a custom error message is provided,
     *                                  it will be included in the exception.
     */
    public static void requireNonNull(String message, Object toTest) {
        if(toTest == null) {
            throw new IllegalArgumentException(message);
        }
    }


    /**
     * This method checks if a given object is not null. If the object is null, it throws an {@link IllegalArgumentException}.
     * If no custom error message is provided, the exception will not include a custom error message.
     *
     * @param toTest The object to be checked for null.
     *
     * @throws IllegalArgumentException If the object is null. Since no custom error message is provided,
     *                                  the exception will not include a custom error message.
     */
    public static void requireNonNull(Object toTest) {
        if(toTest == null) {
            throw new IllegalArgumentException();
        }
    }

    
}
