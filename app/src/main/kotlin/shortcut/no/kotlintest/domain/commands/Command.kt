package shortcut.no.kotlintest.domain.commands

/**
 * Created by Sijan Gurung on 31/03/16.
 * Shortcut AS
 * sijan.gurung@shortcut.no
 */

public interface Command<T>{
    fun execute():T
}