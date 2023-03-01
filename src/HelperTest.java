import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class HelperTest {

    @org.junit.jupiter.api.Test
    void dateConverter() {
        Helper helper = new Helper();
        //assertEquals("Tue Feb 28 14:30",converter.dateConverter("10800"));
    }


    @Test
    void capitalizeDescription() {
        Helper helper = new Helper();
        assertEquals("Overcast Weather", helper.capitalizeDescription("overcast weather"));
        assertEquals("Weather", helper.capitalizeDescription("weather"));
    }
}