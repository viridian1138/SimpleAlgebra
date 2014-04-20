






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
import java.util.ArrayList;
import java.util.HashSet;

import junit.framework.Assert;
import junit.framework.TestCase;

import simplealgebra.DoubleElem;
import simplealgebra.DoubleElemFactory;
import simplealgebra.Elem;
import simplealgebra.NotInvertibleException;
import simplealgebra.SquareMatrixElem;
import simplealgebra.SquareMatrixElemFactory;
import simplealgebra.symbolic.*;
import simplealgebra.*;



public class TestInvertMirroringSymbolic extends TestCase 
{
	
	
	
	private class AElem extends SymbolicElem<SquareMatrixElem<TestDimensionFour,DoubleElem,DoubleElemFactory>,SquareMatrixElemFactory<TestDimensionFour,DoubleElem,DoubleElemFactory>>
	{
		private BigInteger row;
		private BigInteger col;

		
		public AElem(SquareMatrixElemFactory<TestDimensionFour,DoubleElem,DoubleElemFactory> _fac , BigInteger _row , BigInteger _col ) {
			super(_fac);
			row = _row;
			col = _col;
		}

		@Override
		public SquareMatrixElem<TestDimensionFour,DoubleElem,DoubleElemFactory> eval() throws NotInvertibleException,
				MultiplicativeDistributionRequiredException {
			throw( new RuntimeException( "NotSupported" ) );
		}

		@Override
		public SquareMatrixElem<TestDimensionFour,DoubleElem,DoubleElemFactory> evalPartialDerivative(ArrayList<Elem<?, ?>> withRespectTo)
				throws NotInvertibleException,
				MultiplicativeDistributionRequiredException {
			throw( new RuntimeException( "NotSupported" ) );
		}

		@Override
		public String writeString() {
			throw( new RuntimeException( "NotSupported" ) );
		}

		/**
		 * @return the row
		 */
		public BigInteger getRow() {
			return row;
		}

		/**
		 * @return the col
		 */
		public BigInteger getCol() {
			return col;
		}
		
	}
	
	
	private void verifyMirror( 
			SymbolicElem<SquareMatrixElem<TestDimensionFour,DoubleElem,DoubleElemFactory>,SquareMatrixElemFactory<TestDimensionFour,DoubleElem,DoubleElemFactory>> elR,
			SymbolicElem<SquareMatrixElem<TestDimensionFour,DoubleElem,DoubleElemFactory>,SquareMatrixElemFactory<TestDimensionFour,DoubleElem,DoubleElemFactory>> elL )
	{
		if( elR instanceof AElem )
		{
			if( elL instanceof AElem )
			{
				final AElem r = (AElem) elR;
				final AElem l = (AElem) elL;
				Assert.assertEquals( r.getRow() , l.getRow() );
				Assert.assertEquals( r.getCol() , l.getCol() );
				return;
			}
			else
			{
				Assert.assertTrue( false );
			}
		}
		
		if( elR instanceof SymbolicIdentity )
		{
			if( elL instanceof SymbolicIdentity )
			{
				return;
			}
			else
			{
				Assert.assertTrue( false );
			}
		}
		
		if( elR instanceof SymbolicAdd )
		{
			if( elL instanceof SymbolicAdd )
			{
				SymbolicAdd<SquareMatrixElem<TestDimensionFour,DoubleElem,DoubleElemFactory>,SquareMatrixElemFactory<TestDimensionFour,DoubleElem,DoubleElemFactory>> addR = (SymbolicAdd<SquareMatrixElem<TestDimensionFour,DoubleElem,DoubleElemFactory>,SquareMatrixElemFactory<TestDimensionFour,DoubleElem,DoubleElemFactory>>) elR;
				SymbolicAdd<SquareMatrixElem<TestDimensionFour,DoubleElem,DoubleElemFactory>,SquareMatrixElemFactory<TestDimensionFour,DoubleElem,DoubleElemFactory>> addL = (SymbolicAdd<SquareMatrixElem<TestDimensionFour,DoubleElem,DoubleElemFactory>,SquareMatrixElemFactory<TestDimensionFour,DoubleElem,DoubleElemFactory>>) elL;
				verifyMirror( addR.getElemA() , addL.getElemA() );
				verifyMirror( addR.getElemB() , addL.getElemB() );
				return;
			}
			else
			{
				Assert.assertTrue( false );
			}
		}
		
		
		if( elR instanceof SymbolicNegate )
		{
			if( elL instanceof SymbolicNegate )
			{
				SymbolicNegate<SquareMatrixElem<TestDimensionFour,DoubleElem,DoubleElemFactory>,SquareMatrixElemFactory<TestDimensionFour,DoubleElem,DoubleElemFactory>> negR = (SymbolicNegate<SquareMatrixElem<TestDimensionFour,DoubleElem,DoubleElemFactory>,SquareMatrixElemFactory<TestDimensionFour,DoubleElem,DoubleElemFactory>>) elR;
				SymbolicNegate<SquareMatrixElem<TestDimensionFour,DoubleElem,DoubleElemFactory>,SquareMatrixElemFactory<TestDimensionFour,DoubleElem,DoubleElemFactory>> negL = (SymbolicNegate<SquareMatrixElem<TestDimensionFour,DoubleElem,DoubleElemFactory>,SquareMatrixElemFactory<TestDimensionFour,DoubleElem,DoubleElemFactory>>) elL;
				verifyMirror( negR.getElem() , negL.getElem() );
				return;
			}
			else
			{
				Assert.assertTrue( false );
			}
		}
		
		
		if( elR instanceof SymbolicMult )
		{
			if( elL instanceof SymbolicMult )
			{
				SymbolicMult<SquareMatrixElem<TestDimensionFour,DoubleElem,DoubleElemFactory>,SquareMatrixElemFactory<TestDimensionFour,DoubleElem,DoubleElemFactory>> multR = (SymbolicMult<SquareMatrixElem<TestDimensionFour,DoubleElem,DoubleElemFactory>,SquareMatrixElemFactory<TestDimensionFour,DoubleElem,DoubleElemFactory>>) elR;
				SymbolicMult<SquareMatrixElem<TestDimensionFour,DoubleElem,DoubleElemFactory>,SquareMatrixElemFactory<TestDimensionFour,DoubleElem,DoubleElemFactory>> multL = (SymbolicMult<SquareMatrixElem<TestDimensionFour,DoubleElem,DoubleElemFactory>,SquareMatrixElemFactory<TestDimensionFour,DoubleElem,DoubleElemFactory>>) elL;
				verifyMirror( multR.getElemA() , multL.getElemB() );
				verifyMirror( multR.getElemB() , multL.getElemA() );
				return;
			}
			else
			{
				Assert.assertTrue( false );
			}
		}
		
		
		if( elR instanceof SymbolicInvertRight )
		{
			if( elL instanceof SymbolicInvertLeft )
			{
				SymbolicInvertRight<SquareMatrixElem<TestDimensionFour,DoubleElem,DoubleElemFactory>,SquareMatrixElemFactory<TestDimensionFour,DoubleElem,DoubleElemFactory>> invR = (SymbolicInvertRight<SquareMatrixElem<TestDimensionFour,DoubleElem,DoubleElemFactory>,SquareMatrixElemFactory<TestDimensionFour,DoubleElem,DoubleElemFactory>>) elR;
				SymbolicInvertLeft<SquareMatrixElem<TestDimensionFour,DoubleElem,DoubleElemFactory>,SquareMatrixElemFactory<TestDimensionFour,DoubleElem,DoubleElemFactory>> invL = (SymbolicInvertLeft<SquareMatrixElem<TestDimensionFour,DoubleElem,DoubleElemFactory>,SquareMatrixElemFactory<TestDimensionFour,DoubleElem,DoubleElemFactory>>) elL;
				verifyMirror( invR.getElem() , invL.getElem() );
				return;
			}
			else
			{
				Assert.assertTrue( false );
			}
		}
		
		
		if( elR instanceof SymbolicInvertLeft )
		{
			Assert.assertTrue( false );
		}
		
		
		throw( new RuntimeException( "Not Recognized" ) );
		
		
	}
	
	
	
	
	
	public void testInvertMirrorElems() throws NotInvertibleException
	{
		final TestDimensionFour td = new TestDimensionFour();
		
		final DoubleElemFactory dl = new DoubleElemFactory();
		
		final SquareMatrixElemFactory<TestDimensionFour,DoubleElem,DoubleElemFactory> se = 
				new SquareMatrixElemFactory<TestDimensionFour,DoubleElem,DoubleElemFactory>(dl, td);
		
		final SymbolicElemFactory<SquareMatrixElem<TestDimensionFour,DoubleElem,DoubleElemFactory>,SquareMatrixElemFactory<TestDimensionFour,DoubleElem,DoubleElemFactory>> ye = 
				new SymbolicElemFactory<SquareMatrixElem<TestDimensionFour,DoubleElem,DoubleElemFactory>,SquareMatrixElemFactory<TestDimensionFour,DoubleElem,DoubleElemFactory>>(se);
		
		final SquareMatrixElemFactory<TestDimensionFour,
		SymbolicElem<SquareMatrixElem<TestDimensionFour,DoubleElem,DoubleElemFactory>,SquareMatrixElemFactory<TestDimensionFour,DoubleElem,DoubleElemFactory>>,SymbolicElemFactory<SquareMatrixElem<TestDimensionFour,DoubleElem,DoubleElemFactory>,SquareMatrixElemFactory<TestDimensionFour,DoubleElem,DoubleElemFactory>>> se2 = 
				new SquareMatrixElemFactory<TestDimensionFour,
				SymbolicElem<SquareMatrixElem<TestDimensionFour,DoubleElem,DoubleElemFactory>,SquareMatrixElemFactory<TestDimensionFour,DoubleElem,DoubleElemFactory>>,SymbolicElemFactory<SquareMatrixElem<TestDimensionFour,DoubleElem,DoubleElemFactory>,SquareMatrixElemFactory<TestDimensionFour,DoubleElem,DoubleElemFactory>>>(ye, td);
		
		final SquareMatrixElem<TestDimensionFour,
		SymbolicElem<SquareMatrixElem<TestDimensionFour,DoubleElem,DoubleElemFactory>,SquareMatrixElemFactory<TestDimensionFour,DoubleElem,DoubleElemFactory>>,SymbolicElemFactory<SquareMatrixElem<TestDimensionFour,DoubleElem,DoubleElemFactory>,SquareMatrixElemFactory<TestDimensionFour,DoubleElem,DoubleElemFactory>>>
			mat = se2.zero();
		
		
		
		int i;
		int j;
		
		for( i = 0 ; i < 4 ; i++ )
		{
			for( j = 0 ; j < 4 ; j++ )
			{
				AElem val = new AElem( se , BigInteger.valueOf(i) , BigInteger.valueOf(j) );
				mat.setVal( BigInteger.valueOf(i) , BigInteger.valueOf(j) , val );
			}
		}
		
		
		
		final SquareMatrixElem<TestDimensionFour,
		SymbolicElem<SquareMatrixElem<TestDimensionFour,DoubleElem,DoubleElemFactory>,SquareMatrixElemFactory<TestDimensionFour,DoubleElem,DoubleElemFactory>>,SymbolicElemFactory<SquareMatrixElem<TestDimensionFour,DoubleElem,DoubleElemFactory>,SquareMatrixElemFactory<TestDimensionFour,DoubleElem,DoubleElemFactory>>>
			invR = mat.invertRight();
		final SquareMatrixElem<TestDimensionFour,
		SymbolicElem<SquareMatrixElem<TestDimensionFour,DoubleElem,DoubleElemFactory>,SquareMatrixElemFactory<TestDimensionFour,DoubleElem,DoubleElemFactory>>,SymbolicElemFactory<SquareMatrixElem<TestDimensionFour,DoubleElem,DoubleElemFactory>,SquareMatrixElemFactory<TestDimensionFour,DoubleElem,DoubleElemFactory>>>
			invL = mat.invertLeft();
		
		
		
		
		for( i = 0 ; i < 4 ; i++ )
		{
			for( j = 0 ; j < 4 ; j++ )
			{
				SymbolicElem<SquareMatrixElem<TestDimensionFour,DoubleElem,DoubleElemFactory>,SquareMatrixElemFactory<TestDimensionFour,DoubleElem,DoubleElemFactory>>
					elR = invR.get( BigInteger.valueOf(i) , BigInteger.valueOf(j) );
				SymbolicElem<SquareMatrixElem<TestDimensionFour,DoubleElem,DoubleElemFactory>,SquareMatrixElemFactory<TestDimensionFour,DoubleElem,DoubleElemFactory>>
					elL = invL.get( BigInteger.valueOf(i) , BigInteger.valueOf(j) );
				verifyMirror( elR , elL );
			}
		}
		
		
	}
	
	

	
}


