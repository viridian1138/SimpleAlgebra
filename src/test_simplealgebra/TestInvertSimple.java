/**
 * 
 */
package test_simplealgebra;

import java.math.BigInteger;
import java.util.Random;

import junit.framework.Assert;
import junit.framework.TestCase;
import simplealgebra.DoubleElem;
import simplealgebra.DoubleElemFactory;
import simplealgebra.NotInvertibleException;
import simplealgebra.SquareMatrixElem;
import simplealgebra.SquareMatrixElemFactory;

/**
 * @author thorngreen
 *
 */
public class TestInvertSimple extends TestCase {


	/**
	 * Test method for {@link simplealgebra.SquareMatrixElem#invertLeft()}.
	 */
	public void testInvertLeft() throws NotInvertibleException {
		
		final Random rand = new Random();
		
		rand.setSeed( 3333 );
		
		final TestDimensionFour td = new TestDimensionFour();
		
		final DoubleElemFactory dl = new DoubleElemFactory();
		
		final SquareMatrixElemFactory<TestDimensionFour,DoubleElem,DoubleElemFactory> se = 
				new SquareMatrixElemFactory<TestDimensionFour,DoubleElem,DoubleElemFactory>(dl, td);
		
		final SquareMatrixElem<TestDimensionFour,DoubleElem,DoubleElemFactory> mat = se.zero();
		
		int i;
		int j;
		
		for( i = 0 ; i < 4 ; i++ )
		{
			for( j = 0 ; j < 4 ; j++ )
			{
				DoubleElem val = new DoubleElem( rand.nextDouble() );
				mat.setVal( BigInteger.valueOf(i) , BigInteger.valueOf(j) , val );
			}
		}
		
		final SquareMatrixElem<TestDimensionFour,DoubleElem,DoubleElemFactory> inv = mat.invertLeft();
		
		final SquareMatrixElem<TestDimensionFour,DoubleElem,DoubleElemFactory> shouldBeIdentA = mat.mult( inv );
		
		final SquareMatrixElem<TestDimensionFour,DoubleElem,DoubleElemFactory> shouldBeIdentB = inv.mult( mat );
		
		for( i = 0 ; i < 4 ; i++ )
		{
			for( j = 0 ; j < 4 ; j++ )
			{
				final double matchVal = ( i == j ) ? 1.0 : 0.0;
				
				Assert.assertEquals( matchVal , 
						shouldBeIdentA.getVal(BigInteger.valueOf(i) , BigInteger.valueOf(j), dl ).getVal() , 1E-10 );
				
				Assert.assertEquals( matchVal , 
						shouldBeIdentB.getVal(BigInteger.valueOf(i) , BigInteger.valueOf(j), dl ).getVal() , 1E-10 );
				
			}
		}
		
	}

	
	/**
	 * Test method for {@link simplealgebra.SquareMatrixElem#invertRight()}.
	 */
	public void testInvertRight() throws NotInvertibleException {
		
		final Random rand = new Random();
		
		rand.setSeed( 3333 );
		
		final TestDimensionFour td = new TestDimensionFour();
		
		final DoubleElemFactory dl = new DoubleElemFactory();
		
		final SquareMatrixElemFactory<TestDimensionFour,DoubleElem,DoubleElemFactory> se = 
				new SquareMatrixElemFactory<TestDimensionFour,DoubleElem,DoubleElemFactory>(dl, td);
		
		final SquareMatrixElem<TestDimensionFour,DoubleElem,DoubleElemFactory> mat = se.zero();
		
		int i;
		int j;
		
		for( i = 0 ; i < 4 ; i++ )
		{
			for( j = 0 ; j < 4 ; j++ )
			{
				DoubleElem val = new DoubleElem( rand.nextDouble() );
				mat.setVal( BigInteger.valueOf(i) , BigInteger.valueOf(j) , val );
			}
		}
		
		final SquareMatrixElem<TestDimensionFour,DoubleElem,DoubleElemFactory> inv = mat.invertLeft();
		
		final SquareMatrixElem<TestDimensionFour,DoubleElem,DoubleElemFactory> shouldBeIdentA = mat.mult( inv );
		
		final SquareMatrixElem<TestDimensionFour,DoubleElem,DoubleElemFactory> shouldBeIdentB = inv.mult( mat );
		
		for( i = 0 ; i < 4 ; i++ )
		{
			for( j = 0 ; j < 4 ; j++ )
			{
				final double matchVal = ( i == j ) ? 1.0 : 0.0;
				
				Assert.assertEquals( matchVal , 
						shouldBeIdentA.getVal(BigInteger.valueOf(i) , BigInteger.valueOf(j), dl ).getVal() , 1E-10 );
				
				Assert.assertEquals( matchVal , 
						shouldBeIdentB.getVal(BigInteger.valueOf(i) , BigInteger.valueOf(j), dl ).getVal() , 1E-10 );
				
			}
		}
		
	}

	
}

