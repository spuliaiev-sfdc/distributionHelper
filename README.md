# Plugin Downloader

Simple IntelliJ plugin to help distribute plugins. Allows specifying plugin repos as well as pluginIds. Combined, the plugin
will download plugins that the shared settings specifies.

## Example

Example shared configuration (settings-repository)[https://github.com/JetBrains/intellij-community/blob/master/plugins/settings-repository/README.md] downloader.xml
```
<application>
  <component name="pluginDownloader">
    <option name="pluginIds">
      <list>
        <option value="net.bmac.intellij.pluginDownloader" />
      </list>
    </option>
    <option name="pluginRepos">
      <list>
        <option value="http://github.com/brian-mcnamara/pluginDownloader/pages/pluginUpdates.xml" />
      </list>
    </option>
  </component>
</application>
```
