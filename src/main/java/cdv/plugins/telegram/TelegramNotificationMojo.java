package cdv.plugins.telegram;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;

/**
 * Mojo for sending Telegram messages
 *
 * @author Dmitry Coolga
 *         19.11.2016 16:35
 */
@Mojo(name = "send-message", threadSafe = true, requiresOnline = true)
public class TelegramNotificationMojo extends AbstractMojo {

    public void execute() throws MojoExecutionException, MojoFailureException {
        getLog().info("Hello from telegram plugin");
    }

}
