package tests.demoqaTests;
import api.ApiTests;;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import pages.ProfilePage;



@Tag("demoqa_tests")
public class AddAndDeleteBook extends TestBase {
    ApiTests apiTests = new ApiTests();
   ProfilePage profilePage=new ProfilePage();

   @Test
           @DisplayName("Проверка удаления товара из списка")
           void deleteBookFromCollectionTest () {
       apiTests.login();
       apiTests.deleteBook();
       apiTests.addBookToCollection();
       apiTests.addCookie();
 profilePage.openProfilePage()
         .checkUserName()
                .checkBookInCollection()
                .deleteOneBookInCollection()
                .checkCollectionIsEmpty();

    }
}




