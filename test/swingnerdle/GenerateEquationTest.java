package swingnerdle;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.Test;

public class GenerateEquationTest {
	private Equation equation= new Equation();
	private String generatedEquation= equation.generateEquation();
    private final char[] operators = {'+', '-', '*', '/'};
    private final char[] numbers = {'1', '2', '3', '4', '5', '6', '7', '8', '9', '0'};

	@BeforeEach
	void setUp(){
	}

	@RepeatedTest(10000)
	@DisplayName("Length Between Bounds Test")
	void testLength(){
		int min = 7,max=9;
		int len = generatedEquation.length();
		assertTrue((min <= len && max >=len));
	}
	
	@RepeatedTest(10000)
	@DisplayName("Equation Contains '=' Character Test")
	void testEquationContainsEqCharacter() {
		assertTrue(generatedEquation.contains("="));
	}
	
	@RepeatedTest(10000)
	@DisplayName("Equation Contains  Only One '=' Character Test")
	void testEquationContainsMoreThanOneEqCharacter() {
		assertTrue(generatedEquation.split("=").length == 2);
	}
	
	@Test
	@DisplayName("Validation Method - Valid Equation")
	public void testEquationValidationMethod() {
		assertTrue(equation.isEquationValid("4+5*3=19"));
	}
	
	@Test
	@DisplayName("Validation Method - Invalid Equation")
	public void testEquationValidationMethodInvalid() {
		assertFalse(equation.isEquationValid("4+5/15="));
	}
	
	@RepeatedTest(10000)
	@DisplayName("Equation Contains At Least One Number")
	void testEquationContainsAtLeastOneOperand() {
		assertTrue(equation.isStringContains(numbers,generatedEquation));
	}
	
	@RepeatedTest(10000)
	@DisplayName("Equation Contains At Least One Operator")
	void testEquationContainsAtLeastOneOperator() {
		assertTrue(equation.isStringContains(operators,generatedEquation));
	}
	
	@RepeatedTest(10000)
	@DisplayName("Equation Validation Test")
	void testIsEquationValid() {
		assertTrue(equation.isEquationValid(generatedEquation));
	}
        
        

}
