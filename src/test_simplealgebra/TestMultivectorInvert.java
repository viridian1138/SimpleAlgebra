



//$$strtCprt
/**
* Simple Algebra 
* 
* Copyright (C) 2014 Thornton Green
* 
* This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as
* published by the Free Software Foundation; either version 3 of the License, or (at your option) any later version.
* This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty 
* of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
* You should have received a copy of the GNU General Public License along with this program; if not, 
* see <http://www.gnu.org/licenses>.
* Additional permission under GNU GPL version 3 section 7
*
*/
//$$endCprt




package test_simplealgebra;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Random;

import junit.framework.Assert;
import junit.framework.TestCase;
import simplealgebra.DoubleElem;
import simplealgebra.DoubleElemFactory;
import simplealgebra.NotInvertibleException;
import simplealgebra.ga.GeometricAlgebraMultivectorElem;
import simplealgebra.ga.GeometricAlgebraMultivectorElemFactory;

/**
 * @author thorngreen
 *
 */
public class TestMultivectorInvert extends TestCase {
	
	/**
	 * Test method for {@link simplealgebra.SquareMatrixElem#invertLeft()}.
	 */
	public void testInvertLeft() throws NotInvertibleException
	{
		seedTestInvertLeft( 1111 );
		seedTestInvertLeft( 2222 );
		seedTestInvertLeft( 3333 );
		seedTestInvertLeft( 4444 );
		seedTestInvertLeft( 5555 );
		seedTestInvertLeft( 6666 );
		seedTestInvertLeft( 7777 );
		seedTestInvertLeft( 8888 );
		seedTestInvertLeft( 9999 );
	}


	/**
	 * Test method for {@link simplealgebra.SquareMatrixElem#invertLeft()}.
	 */
	private void seedTestInvertLeft( long seed ) throws NotInvertibleException {
		
		final Random rand = new Random();
		
		rand.setSeed( seed );
		
		final TestDimensionFive td = new TestDimensionFive();
		
		final DoubleElemFactory dl = new DoubleElemFactory();
		
		final GeometricAlgebraMultivectorElemFactory<TestDimensionFive,DoubleElem,DoubleElemFactory> se = 
				new GeometricAlgebraMultivectorElemFactory<TestDimensionFive,DoubleElem,DoubleElemFactory>(dl, td);
		
		final GeometricAlgebraMultivectorElem<TestDimensionFive,DoubleElem,DoubleElemFactory> mv = se.zero();
		
		final int max = 1 << 5;
		
		int i;
		int j;
		
		for( i = 0 ; i < max ; i++ )
		{
			HashSet<BigInteger> key = new HashSet<BigInteger>();
			for( j = 0 ; j < 5 ; j++ )
			{
				final int bit = 1 << j;
				final boolean bitOn = ( i & bit ) != 0;
				if( bitOn )
				{
					key.add( BigInteger.valueOf( j ) );
				}
			}
			mv.setVal(key, new DoubleElem( 2.0 * ( rand.nextDouble() ) - 1.0 ) );
		}
		
		final GeometricAlgebraMultivectorElem<TestDimensionFive,DoubleElem,DoubleElemFactory> inv = mv.invertLeft();
		
		final GeometricAlgebraMultivectorElem<TestDimensionFive,DoubleElem,DoubleElemFactory> shouldBeIdentA = mv.mult( inv );
		
		final GeometricAlgebraMultivectorElem<TestDimensionFive,DoubleElem,DoubleElemFactory> shouldBeIdentB = inv.mult( mv );
		
		
		for( i = 0 ; i < max ; i++ )
		{
			HashSet<BigInteger> key = new HashSet<BigInteger>();
			for( j = 0 ; j < 5 ; j++ )
			{
				final int bit = 1 << j;
				final boolean bitOn = ( i & bit ) != 0;
				if( bitOn )
				{
					key.add( BigInteger.valueOf( j ) );
				}
			}
			
			final double matchVal = ( key.size() == 0 ) ? 1.0 : 0.0;
			
			//if( matchVal != 1 ) Assert.assertEquals( matchVal , 
			//		shouldBeIdentA.getVal( key ).getVal() , 0.5 );
			
			if( matchVal != 1 ) Assert.assertEquals( matchVal , 
					shouldBeIdentB.getVal( key ).getVal() , 0.5 );
			
		}
		
		
	}
	
	
	/**
	 * Test method for {@link simplealgebra.SquareMatrixElem#invertRight()}.
	 */
	public void testInvertRight() throws NotInvertibleException
	{
		seedTestInvertRight( 1111 );
		seedTestInvertRight( 2222 );
		seedTestInvertRight( 3333 );
		seedTestInvertRight( 4444 );
		seedTestInvertRight( 5555 );
		seedTestInvertRight( 6666 );
		seedTestInvertRight( 7777 );
		seedTestInvertRight( 8888 );
		seedTestInvertRight( 9999 );
	}

	
	/**
	 * Test method for {@link simplealgebra.SquareMatrixElem#invertRight()}.
	 */
	private void seedTestInvertRight( long seed ) throws NotInvertibleException {
		
		final Random rand = new Random();
		
		rand.setSeed( seed );
		
		final TestDimensionFive td = new TestDimensionFive();
		
		final DoubleElemFactory dl = new DoubleElemFactory();
		
		final GeometricAlgebraMultivectorElemFactory<TestDimensionFive,DoubleElem,DoubleElemFactory> se = 
				new GeometricAlgebraMultivectorElemFactory<TestDimensionFive,DoubleElem,DoubleElemFactory>(dl, td);
		
		final GeometricAlgebraMultivectorElem<TestDimensionFive,DoubleElem,DoubleElemFactory> mv = se.zero();
		
		final int max = 1 << 5;
		
		int i;
		int j;
		
		for( i = 0 ; i < max ; i++ )
		{
			HashSet<BigInteger> key = new HashSet<BigInteger>();
			for( j = 0 ; j < 5 ; j++ )
			{
				final int bit = 1 << j;
				final boolean bitOn = ( i & bit ) != 0;
				if( bitOn )
				{
					key.add( BigInteger.valueOf( j ) );
				}
			}
			mv.setVal(key, new DoubleElem( 2.0 * ( rand.nextDouble() ) - 1.0 ) );
		}
		
		final GeometricAlgebraMultivectorElem<TestDimensionFive,DoubleElem,DoubleElemFactory> inv = mv.invertRight();
		
		final GeometricAlgebraMultivectorElem<TestDimensionFive,DoubleElem,DoubleElemFactory> shouldBeIdentA = mv.mult( inv );
		
		final GeometricAlgebraMultivectorElem<TestDimensionFive,DoubleElem,DoubleElemFactory> shouldBeIdentB = inv.mult( mv );
		
		
		for( i = 0 ; i < max ; i++ )
		{
			HashSet<BigInteger> key = new HashSet<BigInteger>();
			for( j = 0 ; j < 5 ; j++ )
			{
				final int bit = 1 << j;
				final boolean bitOn = ( i & bit ) != 0;
				if( bitOn )
				{
					key.add( BigInteger.valueOf( j ) );
				}
			}
			
			final double matchVal = ( key.size() == 0 ) ? 1.0 : 0.0;
			
			//if( matchVal != 1 ) Assert.assertEquals( matchVal , 
			//		shouldBeIdentA.getVal( key ).getVal() , 0.5 );
			
			if( matchVal != 1 ) Assert.assertEquals( matchVal , 
					shouldBeIdentB.getVal( key ).getVal() , 0.5 );
			
		}
		
		
	}
	
	
	public static void testSimpleInvert( ) throws NotInvertibleException
	{
		Random rand = new Random( 3333 );
		
		final TestDimensionFive td = new TestDimensionFive();
		
		final DoubleElemFactory dl = new DoubleElemFactory();
		
		final GeometricAlgebraMultivectorElemFactory<TestDimensionFive,DoubleElem,DoubleElemFactory> se = 
				new GeometricAlgebraMultivectorElemFactory<TestDimensionFive,DoubleElem,DoubleElemFactory>(dl, td);
		
		final GeometricAlgebraMultivectorElem<TestDimensionFive,DoubleElem,DoubleElemFactory> mv = se.zero();
		
		HashSet<BigInteger> keyA = new HashSet<BigInteger>();
		
		keyA.add( BigInteger.valueOf( 1 ) );
		keyA.add( BigInteger.valueOf( 2 ) );
		
		mv.setVal(keyA, new DoubleElem( 2.0 * ( rand.nextDouble() ) - 1.0 ) );
		
		
		
		HashSet<BigInteger> keyB = new HashSet<BigInteger>();
		
		keyB.add( BigInteger.valueOf( 2 ) );
		keyB.add( BigInteger.valueOf( 3 ) );
		
		mv.setVal(keyB, new DoubleElem( 2.0 * ( rand.nextDouble() ) - 1.0 ) );
		
		
		
		final GeometricAlgebraMultivectorElem<TestDimensionFive,DoubleElem,DoubleElemFactory> inv = mv.invertRight();
		
		final GeometricAlgebraMultivectorElem<TestDimensionFive,DoubleElem,DoubleElemFactory> shouldBeIdentA = mv.mult( inv );
		
		final GeometricAlgebraMultivectorElem<TestDimensionFive,DoubleElem,DoubleElemFactory> shouldBeIdentB = inv.mult( mv );
		
		int i;
		int j;
		
		
		HashSet<BigInteger> key = new HashSet<BigInteger>();
		
		
		final int max = 1 << 5;
		
		for( i = 0 ; i < max ; i++ )
		{
			for( j = 0 ; j < 5 ; j++ )
			{
				final int bit = 1 << j;
				final boolean bitOn = ( i & bit ) != 0;
				if( bitOn )
				{
					key.add( BigInteger.valueOf( j ) );
				}
			}
		}
		
		for( i = 0 ; i < max ; i++ )
		{
			key = new HashSet<BigInteger>();
			for( j = 0 ; j < 5 ; j++ )
			{
				final int bit = 1 << j;
				final boolean bitOn = ( i & bit ) != 0;
				if( bitOn )
				{
					key.add( BigInteger.valueOf( j ) );
				}
			}
			
			final double matchVal = ( key.size() == 0 ) ? 1.0 : 0.0;
			
			if( matchVal != 1 ) Assert.assertEquals( matchVal , 
					shouldBeIdentA.getVal( key ).getVal() , 0.5 );
			
			if( matchVal != 1 ) Assert.assertEquals( matchVal , 
					shouldBeIdentB.getVal( key ).getVal() , 0.5 );
			
		}
		
		
	}
	

	
}

