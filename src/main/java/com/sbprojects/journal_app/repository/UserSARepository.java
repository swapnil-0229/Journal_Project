package com.sbprojects.journal_app.repository;

import java.util.List;
import com.sbprojects.journal_app.entity.User;

public interface UserSARepository {
    List<User> getUserForSA(); 
}
