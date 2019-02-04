package net.bmac.intellij.settings;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Plugins {

    private List<String> pluginIds = Lists.newArrayList();
    private List<String> pluginRepos = Lists.newArrayList();

    public List<String> getPluginIds() {
        return pluginIds;
    }

    public void setPluginIds(List<String> pluginIds) {
        this.pluginIds = pluginIds;
    }

    public List<String> getPluginRepos() {
        return pluginRepos;
    }

    public void setPluginRepos(List<String> pluginRepos) {
        this.pluginRepos = pluginRepos;
    }

    public static Plugins load(File from) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(from, Plugins.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
