package com.sbprojects.journal_app.entity;

import java.time.LocalDateTime;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mongodb.lang.NonNull;
import com.sbprojects.journal_app.enums.Sentiment;

import lombok.AllArgsConstructor;
import lombok.Data;   // this alone gives us getters, setters, requiredargsconstructor, tostring, valus, equalshashcode
import lombok.NoArgsConstructor;


@Document(collection = "journal_entries")
@Data  
@NoArgsConstructor
@AllArgsConstructor
public class JournalEntry {
    @Id
    private ObjectId id;
    @NonNull
    private String title;
    private String content;
    private LocalDateTime date;
    private Sentiment sentiment;
}
