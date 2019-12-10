import com.yangu.Calculator;
import com.yangu.CalculatorException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CalculatorTest {

    @Test
    public void testAritmeticOperators() throws Exception {
        Calculator calculator = new Calculator();

        // e1
        calculator.calculate("5 2");
        assertEquals(2, calculator.getNumberStack().size());
        assertEquals(5, calculator.getNumberStackItem(0), 0);
        assertEquals(2, calculator.getNumberStackItem(1), 0);

        // e2
        calculator.calculate("clear");
        calculator.calculate("2 sqrt");
        assertEquals(1, calculator.getNumberStack().size());
        assertEquals(1.4142135624, calculator.getNumberStackItem(0), 0);
        calculator.calculate("clear 9 sqrt");
        assertEquals(1, calculator.getNumberStack().size());
        assertEquals(3, calculator.getNumberStackItem(0), 0);

        // e3
        calculator.calculate("clear");
        calculator.calculate("5 2 -");
        assertEquals(1, calculator.getNumberStack().size());
        assertEquals(3, calculator.getNumberStackItem(0), 0);
        calculator.calculate("3 -");
        assertEquals(1, calculator.getNumberStack().size());
        assertEquals(0, calculator.getNumberStackItem(0), 0);
        calculator.calculate("clear");
        assertEquals(0, calculator.getNumberStack().size());

        // e5
        calculator.calculate("clear");
        calculator.calculate("7 12 2 /");
        assertEquals(2, calculator.getNumberStack().size());
        assertEquals(7, calculator.getNumberStackItem(0), 0);
        assertEquals(6, calculator.getNumberStackItem(1), 0);
        calculator.calculate("*");
        assertEquals(1, calculator.getNumberStack().size());
        assertEquals(42, calculator.getNumberStackItem(0), 0);
        calculator.calculate("4 /");
        assertEquals(1, calculator.getNumberStack().size());
        assertEquals(10.5, calculator.getNumberStackItem(0), 0);

        // e6
        calculator.calculate("clear");
        calculator.calculate("1 2 3 4 5");
        assertEquals(5, calculator.getNumberStack().size());
        assertEquals(1, calculator.getNumberStackItem(0), 0);
        assertEquals(2, calculator.getNumberStackItem(1), 0);
        assertEquals(3, calculator.getNumberStackItem(2), 0);
        assertEquals(4, calculator.getNumberStackItem(3), 0);
        assertEquals(5, calculator.getNumberStackItem(4), 0);
        calculator.calculate("*");
        assertEquals(4, calculator.getNumberStack().size());
        assertEquals(1, calculator.getNumberStackItem(0), 0);
        assertEquals(2, calculator.getNumberStackItem(1), 0);
        assertEquals(3, calculator.getNumberStackItem(2), 0);
        assertEquals(20, calculator.getNumberStackItem(3), 0);
        calculator.calculate("clear 3 4 -");
        assertEquals(1, calculator.getNumberStack().size());
        assertEquals(-1, calculator.getNumberStackItem(0), 0);

        // e7
        calculator.calculate("clear");
        calculator.calculate("1 2 3 4 5");
        assertEquals(5, calculator.getNumberStack().size());
        assertEquals(1, calculator.getNumberStackItem(0), 0);
        assertEquals(2, calculator.getNumberStackItem(1), 0);
        assertEquals(3, calculator.getNumberStackItem(2), 0);
        assertEquals(4, calculator.getNumberStackItem(3), 0);
        assertEquals(5, calculator.getNumberStackItem(4), 0);
        calculator.calculate("* * * *");
        assertEquals(1, calculator.getNumberStack().size());
        assertEquals(120, calculator.getNumberStackItem(0), 0);

    }
    @Test
    public void testInsuficientParameters() {
        // e8
        Calculator calculator = new Calculator();
        try {
            calculator.calculate("1 2 3 * 5 + * * 6 5");
        } catch (CalculatorException e) {
            assertEquals("operator * (position: 15): insucient parameters", e.getMessage());
        }
        assertEquals(1, calculator.getNumberStack().size());
        assertEquals(11, calculator.getNumberStackItem(0), 0);
    }

    @Test
    public void testUndo() throws Exception {
        // e4
        Calculator calculator = new Calculator();
        calculator.calculate("5 4 3 2");
        assertEquals(4, calculator.getNumberStack().size());
        assertEquals(5, calculator.getNumberStackItem(0), 0);
        assertEquals(4, calculator.getNumberStackItem(1), 0);
        assertEquals(3, calculator.getNumberStackItem(2), 0);
        assertEquals(2, calculator.getNumberStackItem(3), 0);
        calculator.calculate("undo undo *");
        assertEquals(1, calculator.getNumberStack().size());
        assertEquals(20, calculator.getNumberStackItem(0), 0);
        calculator.calculate("5 *");
        assertEquals(1, calculator.getNumberStack().size());
        assertEquals(100, calculator.getNumberStackItem(0), 0);
        calculator.calculate("undo");
        assertEquals(2, calculator.getNumberStack().size());
        assertEquals(20, calculator.getNumberStackItem(0), 0);
        assertEquals(5, calculator.getNumberStackItem(1), 0);

        calculator.calculate("+ undo - undo / undo * undo sqrt undo square undo");
        assertEquals(2, calculator.getNumberStack().size());
        assertEquals(20, calculator.getNumberStackItem(0), 0.0000000001);
        assertEquals(5, calculator.getNumberStackItem(1), 0.0000000001);
    }

    @Test(expected = CalculatorException.class)
    public void testOnlyOperators() throws Exception {
        Calculator calculator = new Calculator();
        calculator.calculate("+ +");
    }

    @Test(expected = CalculatorException.class)
    public void testInvalidCharacters() throws Exception {
        Calculator calculator = new Calculator();
        calculator.calculate("2 a +");
    }

    @Test(expected = CalculatorException.class)
    public void testNoSpaces() throws Exception {
        Calculator calculator = new Calculator();
        calculator.calculate("22+");
    }

    @Test(expected = CalculatorException.class)
    public void testNoSpaces2() throws Exception {
        Calculator calculator = new Calculator();
        calculator.calculate("2 2+ 3");
    }

    @Test(expected = CalculatorException.class)
    public void testDivideByZero() throws Exception {
        Calculator calculator = new Calculator();
        calculator.calculate("1 0 /");
    }

    @Test(expected = CalculatorException.class)
    public void testNullInput() throws Exception {
        Calculator calculator = new Calculator();
        calculator.calculate(null);
    }
}
