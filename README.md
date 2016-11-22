# telegram-maven-plugin
Maven plugin for notifications via Telegram messenger

## Downloads

The plugin is available at Bintray:
```xml
<pluginRepository>
    <id>cdv-repo</id>
    <url>http://dl.bintray.com/coolgadv/cdv-maven-repository</url>
</pluginRepository>
```
Plugin dependency:
```xml
<plugin>
    <groupId>cdv.plugins</groupId>
    <artifactId>telegram-maven-plugin</artifactId>
    <version>1.0</version>
</plugin>
```

##Configuration

Telegram Maven plugin has the following configuration parameters:
* `botToken` - Telegram Bot API token
* `chatId` - chat identifier
* `text` - notification message text

Example:
```
<plugin>
    <groupId>cdv.plugins</groupId>
    <artifactId>telegram-maven-plugin</artifactId>
    <version>1.0</version>
    <executions>
        <execution>
            <id>send-message</id>
            <phase>post-integration-test</phase>
            <goals>
                <goal>send-message</goal>
            </goals>
            <configuration>
                <botToken>1234567890:aBcDeFg</botToken>
                <chatId>1234567890</chatId>
                <text>Hello, world !!!</text>
            </configuration>
        </execution>
    </executions>
</plugin>
```
