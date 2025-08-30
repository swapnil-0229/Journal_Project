package com.sbprojects.journal_app.entity;

import java.time.LocalDateTime;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mongodb.lang.NonNull;

import lombok.Data;   // this alone gives us getters, setters, requiredargsconstructor, tostring, valus, equalshashcode
// import lombok.Getter; // lombok used to  decrease  boilerplate code by generating these things at compilation time
// import lombok.Setter;
import lombok.NoArgsConstructor;


@Document(collection = "journal_entries")
// @Getter
// @Setter

@Data  
@NoArgsConstructor
public class journalEntry {

    @Id
    private ObjectId id;  // we are using an object id  type instead of string or int
    @NonNull
    private String title;

    private String content;

    private LocalDateTime date;
}
