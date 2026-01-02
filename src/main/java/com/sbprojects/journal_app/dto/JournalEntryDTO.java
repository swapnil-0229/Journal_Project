package com.sbprojects.journal_app.dto;

import com.sbprojects.journal_app.enums.Sentiment;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Data Transfer Object for Journal Entries")
public class JournalEntryDTO {

    @NotBlank(message = "title is required")
    @Schema(description = "The title or headline of the journal entry", example = "My First Trip to Manali")
    private String title;
    
    @Schema(description = "The main body/text of the journal entry", example = "Today was an amazing day. The weather was perfect...")
    private String content;
    
    @Schema(description = "Mood of the entry (HAPPY, SAD, ANGRY, ANXIOUS)", example = "HAPPY")
    private Sentiment sentiment;
}
