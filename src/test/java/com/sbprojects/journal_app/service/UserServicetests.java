package com.sbprojects.journal_app.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.sbprojects.journal_app.entity.User;
import com.sbprojects.journal_app.repository.UserRepository;


@SpringBootTest
public class UserServicetests {

    @Autowired
    public UserRepository myUserRepo;

    @Autowired
    public UserService myUserService;
    
    // @Disabled
    // @Test
    // public void testFindByUserName() {
    //     // assertEquals(4, 2 + 2);
    //     // assertTrue(5>3);

    //     User user = myUserRepo.findByusername("ram");
    //     // assertNotNull(myUserRepo.findByusername("ram"));
    //     assertTrue(!user.getUserEntries().isEmpty());
    // }

    // @Disabled
    // @BeforeAll
    // @BeforeEach
    // @AfterAll
    // @AfterEach
    // void setUp() {

    // }


    @Disabled
    @ParameterizedTest
    @ValueSource(strings = {
        "ram",
        "vipul",
        "sb015002",
        "lol"
    })
    public void testFindByUserName(String name) {
        assertNotNull(myUserRepo.findByUsername(name), "failed for " + name);
    }

    @Disabled
    @ParameterizedTest
    @ArgumentsSource(UserArgumentsProvider.class)
    public void testSaveNewUser(User user) {
        myUserService.saveUser(user);
    }

    @Disabled
    @ParameterizedTest
    @CsvSource({
        "1, 1, 2",
        "2, 2, 4",
        "1, 2, 2"
    })
    public void test(int a, int b, int expected) {
        assertEquals(expected, a + b);
    }
}



