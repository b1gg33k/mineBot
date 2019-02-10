package com.b1gg33k.services;

import org.cfg4j.source.ConfigurationSource;
import org.cfg4j.source.classpath.ClasspathConfigurationSource;
import org.cfg4j.source.context.filesprovider.ConfigFilesProvider;

import java.io.File;
import java.util.Arrays;

public class ConfigurationService {
    private ConfigurationSource configurationSource = null;

    public ConfigurationService() {
        ConfigFilesProvider configFilesProvider = () -> Arrays.asList(new File("system.yaml").toPath(), new File("system.yaml").toPath());

        ConfigurationSource source = new ClasspathConfigurationSource(configFilesProvider);
    }
}
