package com.sbprojects.journal_app.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Data Transfer Object for User operations (Signup, Login, Update)")
public class UserDTO {
    
    @NotBlank(message = "Username is required")
    @Schema(description = "Unique username for the user", example = "swapnil_02")
    private String username;

    @NotBlank(message = "Password is required")
    @Schema(description = "Secure password (min 6 chars recommended)", example = "SecurePass@123")
    private String password;

    @Email(message = "Invalid email format")
    @Schema(description = "User's email address", example = "zbc@example.com")
    private String email;
    
    @Schema(description = "Opt-in for AI-powered weekly sentiment analysis emails", example = "true")
    private boolean sentimentAnalysis;


}
