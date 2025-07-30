package com.naotenhorgb.skyFight.data;

import com.esotericsoftware.yamlbeans.YamlConfig;
import com.esotericsoftware.yamlbeans.YamlReader;
import com.esotericsoftware.yamlbeans.YamlWriter;
import com.naotenhorgb.skyFight.SkyFight;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Objects;

@SuppressWarnings("unused")
public class SkyfightConfig {

    public String debug;
    public String bungee;

    private static SkyfightConfig skyfightConfig;

    public static SkyfightConfig getSkyfightConfig() {
        if (skyfightConfig == null) {
            skyfightConfig = load();
        }
        return skyfightConfig;
    }

    public static SkyfightConfig load() {
        try {
            File folder = new File("plugins/SkyFight");
            folder.mkdir();

            File messagesFile = new File(folder, "skyfight.yml");

            if (!messagesFile.exists()) {
                URL url = SkyFight.class.getResource("/skyfight.yml");
                FileUtils.copyURLToFile(Objects.requireNonNull(url), messagesFile);
            }

            InputStream inputStream = Files.newInputStream(messagesFile.toPath());
            Reader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));

            YamlReader yamlReader = new YamlReader(reader);
            SkyfightConfig config = yamlReader.read(SkyfightConfig.class);
            skyfightConfig = config;
            return config;

        } catch (IOException exception) {
            throw new RuntimeException("Não foi possível carregar o skyfight.yml", exception);
        }
    }

    public void save() {
        try {
            File file = new File("plugins/SkyFight/skyfight.yml");
            Writer writer = new BufferedWriter(new OutputStreamWriter(
                    Files.newOutputStream(file.toPath()), StandardCharsets.UTF_8
            ));

            YamlWriter yamlWriter = new YamlWriter(writer);
            yamlWriter.getConfig().writeConfig.setWriteClassname(YamlConfig.WriteClassName.NEVER);
            yamlWriter.write(this);
            yamlWriter.close();
        } catch (IOException e) {
            throw new RuntimeException("Não foi possível salvar o skyfight.yml", e);
        }
    }

}
