package net.bmac.intellij.settings;

import com.google.common.collect.Lists;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@State(name = "pluginDownloader", storages = @Storage(file = "downloader.xml"))
public class PluginSettings implements PersistentStateComponent<PluginSettings> {

    public static PluginSettings getInstance() {
        return ServiceManager.getService(PluginSettings.class);
    }

    private List<String> pluginIds = Lists.newArrayList();
    private List<String> pluginRepos = Lists.newArrayList();

    public List<String> getPluginIds() {
        return pluginIds;
    }

    public void addPluginId(String pluginId) {
        this.pluginIds.add(pluginId);
    }

    public void setPluginIds(List<String> pluginIds) {
        this.pluginIds = pluginIds;
    }

    public List<String> getPluginRepos() {
        return pluginRepos;
    }

    public void addPluginRepo(String repo) {
        this.pluginRepos.add(repo);
    }

    public void setPluginRepos(List<String> pluginRepos) {
        this.pluginRepos = pluginRepos;
    }

    @Nullable
    @Override
    public PluginSettings getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull PluginSettings state) {
        XmlSerializerUtil.copyBean(state, this);
    }
}
