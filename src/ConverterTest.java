import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class ConverterTest {

    @org.junit.jupiter.api.Test
    void dateConverter() {
        Converter converter = new Converter();
        //assertEquals("Tue Feb 28 14:30",converter.dateConverter("10800"));
    }


    @Test
    void capitalizeDescription() {
        Converter converter = new Converter();
        assertEquals("Overcast Weather", converter.capitalizeDescription("overcast weather"));
        assertEquals("Weather", converter.capitalizeDescription("weather"));
    }
}