package miniproject.utils;

public final class Utils {
    // Empêche l'instanciation de cette classe
    private Utils() {}


    /**
     * Lance une exception de type {@code IllegalArgumentException}
     * si la condition donnée n'est pas satisfaite.
     *
     * @param message Le message de l'erreur
     * @param condition La condition qui, si évaluée à {@code false}
     *                  provoque l'exception {@code IllegalArgumentException}
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
     * Possède le même comportement que {@link #require(String, boolean)}
     * mais sans message détaillé de l'erreur.
     *
     * @param condition La condition qui, si est évaluée à {@code false}
     *                  provoque l'exception {@code IllegalArgumentException}
     *
     * @see #require(String, boolean)
     */
    public static void require(boolean condition) {
        require(null, condition);
    }

    /**
     * Lance une exception de type {@code IllegalArgumentException}
     * si la l'objet {@code toTest} est {@code null}.
     *
     * @param message Le message détaillé de l'erreur
     * @param toTest L'objet qui, si vaut {@code null}, provoque
     *               l'exception {@code IllegalArgumentException}
     */
    public static void requireNonNull(String message, Object toTest) {
        if(toTest == null) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Possède le même comportement que {@link #requireNonNull(String, Object)}
     * mais sans message détaillé de l'erreur.
     *
     * @param toTest L'objet qui, si vaut {@code null}, provoque
     *               l'exception {@code IllegalArgumentException}
     *
     * @see #requireNonNull(String, Object)
     */
    public static void requireNonNull(Object toTest) {
        if(toTest == null) {
            throw new IllegalArgumentException();
        }
    }

    
}
