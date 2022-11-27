package facebook.account;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
class AccountServiceTest {

    public static final String BASE_PATH = "/api/accounts";

    @Mock
    AccountRepository accountRepository;

    @InjectMocks
    AccountService accountService;

    @Autowired
    private MockMvc mockMvc;
    private WebApplicationContext webApplicationContext;


    @Before
    public void setUp() {
        accountService.createAccount(new AccountDto("kris", "kros", "kris@wp.pl", "Zakopane", "kris", "kris123"));
        accountService.createAccount(new AccountDto("anna", "kros", "anns@wp.pl", "Gdynia", "anna", "anna123"));
    }


  /*  @AfterEach
    public void cleanUp() {

    }*/


  /*  @Test
    public void getAllAccountsTest() throws Exception {
        ResultActions perform = this.mockMvc.perform(get(BASE_PATH));
        perform.andDo(print());

    }*/

    @Test
    public void validateEmailTest() {
        String email = "kris@wp.pl";

        accountService.validateEmail(email);

    }


}