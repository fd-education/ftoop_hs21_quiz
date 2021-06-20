package ch.ffhs.quiz.client.ui.components.interfaces;

/**
 * Interface for text components that can be provided with a
 * String to complement/ adapt the output
 */
public interface DynamicUIComponent {
    /**
     * Get the required text component as a String
     *
     * @param complement the String to complement the desired output
     * @return a complemented String text component
     */
     String getComponent(final String complement);
}
