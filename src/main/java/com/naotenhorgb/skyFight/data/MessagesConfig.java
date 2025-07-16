package com.naotenhorgb.skyFight.data;

import com.esotericsoftware.yamlbeans.YamlReader;
import com.naotenhorgb.skyFight.SkyFight;
import com.naotenhorgb.skyFight.utils.MessagesUtils;
import org.apache.commons.io.FileUtils;
import org.bukkit.command.CommandSender;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Objects;

@SuppressWarnings("unused")
public class MessagesConfig {

    public String prefix;
    public String no_permission;

    public String setup_0;
    public String setup_1;
    public String setup_2;
    public String setup_3;
    public String setup_4;
    public String setup_5;
    public String setup_6;
    public String setup_7;
    public String setup_reset;
    public String setup_same_world;
    public String unknown_error;
    public String no_setup;

    private static MessagesConfig instance;

    public static MessagesConfig get() {
        if (instance == null) {
            instance = load();
        }
        return instance;
    }

    public static MessagesConfig load() {
        try {
            File folder = new File("plugins/SkyFight");
            folder.mkdir();

            File messagesFile = new File(folder, "messages.yml");

            if (!messagesFile.exists()) {
                URL url = SkyFight.class.getResource("/messages.yml");
                FileUtils.copyURLToFile(Objects.requireNonNull(url), messagesFile);
            }

            InputStream inputStream = Files.newInputStream(messagesFile.toPath());
            Reader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));

            YamlReader yamlReader = new YamlReader(reader);
            MessagesConfig config = yamlReader.read(MessagesConfig.class);
            instance = config;
            return config;

        } catch (IOException exception) {
            throw new RuntimeException("Não foi possível carregar o messages.yml", exception);
        }
    }

    public static String get(String key) {
        try {
            String raw = (String) instance.getClass().getField(key).get(instance);
            return MessagesUtils.format(raw);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            return MessagesUtils.format("&cMensagem não encontrada: &e" + key);
        }
    }

    public static void send(CommandSender sender, String key) {
        String msg = get(key);
        for (String line : msg.split("\n")) {
            sender.sendMessage(MessagesUtils.getPrefix() + " " + line);
        }
    }
}
