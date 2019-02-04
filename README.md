# Plugin Downloader

Simple IntelliJ plugin to help distribute plugins. Allows specifying plugin repos as well as pluginIds.

Specify `distributionHelper.pluginFile` in idea.properties to specify the json plugin file

## Example

Example plugin file
```
{
    "pluginRepos": ["http://github.com/brian-mcnamara/pluginDownloader/pages/pluginUpdates.xml"],
    "plugins": [
        {
            "id" : "net.bmac.intellij.distributionHelper",
            "repo" : "http://github.com/brian-mcnamara/pluginDownloader/pages/pluginUpdates.xml",
            "path" : "./releases/distributionHelper-1.0.zip"
        }
    ]
}

```
