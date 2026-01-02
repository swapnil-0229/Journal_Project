package com.sbprojects.journal_app.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sbprojects.journal_app.entity.ConfigJournalAppEntity;
import com.sbprojects.journal_app.enums.AppCacheKey;
import com.sbprojects.journal_app.repository.ConfigJournalAppRepo;

import jakarta.annotation.PostConstruct;

@Component
public class AppCache {

    private Map<String, String> configCache;

    @Autowired
    private ConfigJournalAppRepo configJournalAppRepo;

    @PostConstruct
    public void init() {
        configCache = new HashMap<>();
        List<ConfigJournalAppEntity> list = configJournalAppRepo.findAll();
        for(ConfigJournalAppEntity configJournalAppEntity : list){
            configCache.put(configJournalAppEntity.getKey(), configJournalAppEntity.getValue());
        }
    }

    public String get(AppCacheKey key){
        return configCache.get(key.toString());
    }
}
