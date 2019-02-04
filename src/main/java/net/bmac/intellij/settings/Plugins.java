package net.bmac.intellij.settings;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Plugins {

    private List<Plugin> plugins = Lists.newArrayList();
    private List<String> pluginRepos = Lists.newArrayList();

    public List<Plugin> getPlugins() {
        return plugins;
    }

    public void setPlugins(List<Plugin> plugins) {
        this.plugins = plugins;
    }

    public List<String> getPluginRepos() {
        return pluginRepos;
    }

    public void setPluginRepos(List<String> pluginRepos) {
        this.pluginRepos = pluginRepos;
    }

    public static class Plugin {
        String id;
        String repo;
        String path;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getRepo() {
            return repo;
        }

        public void setRepo(String repo) {
            this.repo = repo;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }
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
