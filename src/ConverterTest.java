import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class ConverterTest {

    @org.junit.jupiter.api.Test
    void dateConverter() {
        Converter converter = new Converter();
        assertEquals("Tue Feb 28th 14:33",converter.dateConverter("1.677583814E9","10800"));
    }
}