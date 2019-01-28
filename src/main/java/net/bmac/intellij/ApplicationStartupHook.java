package net.bmac.intellij;

import com.google.common.collect.Lists;
import com.intellij.ide.ApplicationLoadListener;
import com.intellij.ide.plugins.*;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.application.impl.ApplicationImpl;
import com.intellij.openapi.extensions.PluginDescriptor;
import com.intellij.openapi.extensions.PluginId;
import com.intellij.openapi.progress.DumbProgressIndicator;
import com.intellij.openapi.updateSettings.impl.UpdateSettings;
import com.intellij.util.Restarter;
import net.bmac.intellij.settings.PluginSettings;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ApplicationStartupHook implements ApplicationLoadListener {
    @Override
    public void beforeComponentsCreated() {
        addAllRepos();
        List<PluginNode> pluginNodes = getPluginsToInstall();

        if (!pluginNodes.isEmpty()) {
            List<IdeaPluginDescriptor> pluginDescriptors = getPlugins();
            try {
                ApplicationManager.getApplication().executeOnPooledThread(() -> {
                    boolean installed = PluginInstaller.prepareToInstall(pluginNodes, pluginDescriptors, new PluginManagerMain.PluginEnabler.HEADLESS(), DumbProgressIndicator.INSTANCE);
                    if (installed) {
                        PluginSettings.getInstance().setPluginIds(Lists.newArrayList());
                        if (Restarter.isSupported()) {
                            ((ApplicationImpl) ApplicationManager.getApplication()).exit(true, true, true);
                        }
                    }
                }).get();
            } catch (InterruptedException | ExecutionException e) {
                //TODO fixme;
                throw new RuntimeException(e);
            }
        }
    }

    private void addAllRepos() {
        List<String> repos = UpdateSettings.getInstance().getStoredPluginHosts();
        for (String repo : PluginSettings.getInstance().getPluginRepos()) {
            if (!repos.contains(repo)) repos.add(repo);
        }
    }

    private List<PluginNode> getPluginsToInstall() {
        List<String> pluginIds = PluginSettings.getInstance().getPluginIds();

        if (pluginIds.isEmpty()) {
            return Collections.emptyList();
        }
        List<PluginId> installedIds = Stream.of(PluginManagerCore.getPlugins()).map(PluginDescriptor::getPluginId).collect(Collectors.toList());
        List<PluginNode> plugins = Lists.newArrayListWithCapacity(pluginIds.size());
        for (String pluginId : pluginIds) {
            PluginId id = PluginId.getId(pluginId);
            if (installedIds.contains(id)) {
                continue;
            }
            PluginNode node = new PluginNode(id);
            node.setName(pluginId);
            plugins.add(node);
        }
        return plugins;
    }

    private List<IdeaPluginDescriptor> getPlugins() {
        try {
            return RepositoryHelper.loadCachedPlugins();
        } catch (IOException e) {

        }
        return Collections.emptyList();
    }
}
