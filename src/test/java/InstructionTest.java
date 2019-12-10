import com.yangu.CalculatorException;
import com.yangu.Instruction;
import com.yangu.Operator;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class InstructionTest {

    @Test
    public void testReverseOneOperandInstruction() throws CalculatorException {

        Operator mockOperator = Mockito.mock(Operator.class);
        when(mockOperator.getOperandsNumber()).thenReturn(1);
        when(mockOperator.getReverseOp()).thenReturn("sqrt");

        Random r = new Random();
        Instruction instruction = new Instruction(mockOperator, r.nextDouble());

        assertEquals(String.format("%s", mockOperator.getReverseOp()), instruction.getReverseInstruction());
    }

    @Test
    public void testReverseTwoOperandInstruction() throws CalculatorException {

        Operator mockOperator = Mockito.mock(Operator.class);
        when(mockOperator.getOperandsNumber()).thenReturn(2);
        when(mockOperator.getReverseOp()).thenReturn("-");

        Random r = new Random();
        double value = r.nextDouble();
        Instruction instruction = new Instruction(mockOperator, value);

        assertEquals(
                String.format("%f %s %f", value, mockOperator.getReverseOp(), value),
                instruction.getReverseInstruction()
        );
    }

    @Test(expected = CalculatorException.class)
    public void testInvalidOperandsNumber() throws CalculatorException {
        Operator mockOperator = Mockito.mock(Operator.class);
        when(mockOperator.getOperandsNumber()).thenReturn(0);

        Random r = new Random();
        Instruction instruction = new Instruction(mockOperator, r.nextDouble());
        instruction.getReverseInstruction();
    }
}
