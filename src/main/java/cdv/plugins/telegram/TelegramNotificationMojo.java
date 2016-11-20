package cdv.plugins.telegram;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;

/**
 * Mojo for sending Telegram messages
 *
 * @author Dmitry Coolga
 *         19.11.2016 16:35
 */
@Mojo(name = "send-message", threadSafe = true, requiresOnline = true, aggregator = true)
public class TelegramNotificationMojo extends AbstractMojo {

    /**
     * Telegram Bot API token
     */
    @Parameter(property = "bot.token", required = true)
    private String botToken;

    /**
     * Chat identifier
     */
    @Parameter(property = "chat.id", required = true)
    private String chatId;

    /**
     * Message text
     */
    @Parameter(property = "text", required = true)
    private String text;

    public void execute() throws MojoExecutionException {
        Log log = getLog();
        log.info("Sending notification to Telegram...");
        log.debug("Message text: " + text);
        try {
            String response = ClientBuilder.newClient()
                    .target("https://api.telegram.org")
                    .path("bot" + botToken)
                    .path("sendMessage")
                    .queryParam("chat_id", chatId)
                    .queryParam("text", text)
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .get(String.class);
            log.info("Notification successfully sent");
            log.debug("Response from Telegram: " + response);
        } catch (Exception ex) {
            throw new MojoExecutionException("Notification failure", ex);
        }
    }

}
