package com.naotenhorgb.skyFight.managers;

import com.esotericsoftware.yamlbeans.YamlReader;
import com.esotericsoftware.yamlbeans.YamlWriter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;

public class LocationsConfig {

    public String lobby_spawn;
    public String game_spawn;
    public List<String> game_safezone;
    public List<String> game_boundaries;

    private static File FILE;

    /** Chame uma vez no onEnable, passando sua instância de plugin */
    public static void init(JavaPlugin plugin) {
        // garante dataFolder
        plugin.getDataFolder().mkdirs();

        File file = new File(plugin.getDataFolder(), "locations.yml");

        // se não existir OU estiver vazio, copia o default do JAR
        if (!file.exists() || file.length() == 0) {
            plugin.saveResource("locations.yml", true);
            plugin.getLogger().info("locations.yml criado/substituído a partir do recurso interno.");
        }

        FILE = file;
    }


    /** Carrega o conteúdo já copiado por saveResource */
    public static LocationsConfig load() {
        try (Reader reader = Files.newBufferedReader(FILE.toPath(), StandardCharsets.UTF_8)) {
            YamlReader yaml = new YamlReader(reader);
            return yaml.read(LocationsConfig.class);
        } catch (IOException e) {
            Bukkit.getLogger().severe("Erro ao ler locations.yml: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void save() {
        try (Writer writer = Files.newBufferedWriter(FILE.toPath(), StandardCharsets.UTF_8)) {
            YamlWriter yaml = new YamlWriter(writer);
            yaml.write(this);
        } catch (IOException e) {
            Bukkit.getLogger().severe("Erro ao salvar locations.yml: " + e.getMessage());
        }
    }
}
