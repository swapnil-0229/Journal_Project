package com.sbprojects.journal_app.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sbprojects.journal_app.entity.ConfigJournalAppEntity;
import com.sbprojects.journal_app.repository.ConfigJournalAppRepo;

@Component
public class AppCache {

    public enum keys {
        WEATHER_API;
    }
    
    public Map<String, String> appCache;

    @Autowired
    public ConfigJournalAppRepo configJournalAppRepo;

    @PostConstruct
    public void init() {
        appCache = new HashMap<>();
        List<ConfigJournalAppEntity> list = configJournalAppRepo.findAll();
        for(ConfigJournalAppEntity configJournalAppEntity : list){
            appCache.put(configJournalAppEntity.getKey(), configJournalAppEntity.getValue());
        }
    }
}
