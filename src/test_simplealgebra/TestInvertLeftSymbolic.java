






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



public class TestInvertLeftSymbolic extends TestCase 
{
	
	
	
	private class AElem extends SymbolicElem<SquareMatrixElem<TestDimensionFour,DoubleElem,DoubleElemFactory>,SquareMatrixElemFactory<TestDimensionFour,DoubleElem,DoubleElemFactory>>
	{
		// private HashSet<BigInteger> indx;
		// private int col;

		
		public AElem(SquareMatrixElemFactory<TestDimensionFour,DoubleElem,DoubleElemFactory> _fac /* , HashSet<BigInteger> _indx, int _col */ ) {
			super(_fac);
			//indx = _indx;
			//col = _col;
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
		
//		/**
//		 * @return the indx
//		 */
//		public HashSet<BigInteger> getIndx() {
//			return indx;
//		}
//		
//		/**
//		 * @return the col
//		 */
//		public int getCol() {
//			return col;
//		}
		
	}
	
	
	private void verifyNoInvertRight( 
			SymbolicElem<SquareMatrixElem<TestDimensionFour,DoubleElem,DoubleElemFactory>,SquareMatrixElemFactory<TestDimensionFour,DoubleElem,DoubleElemFactory>> in )
	{
		if( in instanceof AElem )
		{
			return;
		}
		
		if( in instanceof SymbolicIdentity )
		{
			return;
		}
		
		if( in instanceof SymbolicAdd )
		{
			SymbolicAdd<SquareMatrixElem<TestDimensionFour,DoubleElem,DoubleElemFactory>,SquareMatrixElemFactory<TestDimensionFour,DoubleElem,DoubleElemFactory>> add = (SymbolicAdd<SquareMatrixElem<TestDimensionFour,DoubleElem,DoubleElemFactory>,SquareMatrixElemFactory<TestDimensionFour,DoubleElem,DoubleElemFactory>>) in;
			verifyNoInvertRight( add.getElemA() );
			verifyNoInvertRight( add.getElemB() );
			return;
		}
		
		
		if( in instanceof SymbolicNegate )
		{
			SymbolicNegate<SquareMatrixElem<TestDimensionFour,DoubleElem,DoubleElemFactory>,SquareMatrixElemFactory<TestDimensionFour,DoubleElem,DoubleElemFactory>> neg = (SymbolicNegate<SquareMatrixElem<TestDimensionFour,DoubleElem,DoubleElemFactory>,SquareMatrixElemFactory<TestDimensionFour,DoubleElem,DoubleElemFactory>>) in;
			verifyNoInvertRight( neg.getElem() );
			return;
		}
		
		
		if( in instanceof SymbolicMult )
		{
			SymbolicMult<SquareMatrixElem<TestDimensionFour,DoubleElem,DoubleElemFactory>,SquareMatrixElemFactory<TestDimensionFour,DoubleElem,DoubleElemFactory>> add = (SymbolicMult<SquareMatrixElem<TestDimensionFour,DoubleElem,DoubleElemFactory>,SquareMatrixElemFactory<TestDimensionFour,DoubleElem,DoubleElemFactory>>) in;
			verifyNoInvertRight( add.getElemA() );
			verifyNoInvertRight( add.getElemB() );
			return;
		}
		
		
		if( in instanceof SymbolicInvertLeft )
		{
			SymbolicInvertLeft<SquareMatrixElem<TestDimensionFour,DoubleElem,DoubleElemFactory>,SquareMatrixElemFactory<TestDimensionFour,DoubleElem,DoubleElemFactory>> inv = (SymbolicInvertLeft<SquareMatrixElem<TestDimensionFour,DoubleElem,DoubleElemFactory>,SquareMatrixElemFactory<TestDimensionFour,DoubleElem,DoubleElemFactory>>) in;
			verifyNoInvertRight( inv.getElem() );
			return;
		}
		
		
		if( in instanceof SymbolicInvertRight )
		{
			Assert.assertTrue( false );
		}
		
		
		throw( new RuntimeException( "Not Recognized" ) );
		
		
	}
	
	
	
	
	
	public void testInvertRightElems() throws NotInvertibleException
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
				AElem val = new AElem( se );
				mat.setVal( BigInteger.valueOf(i) , BigInteger.valueOf(j) , val );
			}
		}
		
		
		
		final SquareMatrixElem<TestDimensionFour,
		SymbolicElem<SquareMatrixElem<TestDimensionFour,DoubleElem,DoubleElemFactory>,SquareMatrixElemFactory<TestDimensionFour,DoubleElem,DoubleElemFactory>>,SymbolicElemFactory<SquareMatrixElem<TestDimensionFour,DoubleElem,DoubleElemFactory>,SquareMatrixElemFactory<TestDimensionFour,DoubleElem,DoubleElemFactory>>>
			inv = mat.invertLeft();
		
		
		
		
		for( i = 0 ; i < 4 ; i++ )
		{
			for( j = 0 ; j < 4 ; j++ )
			{
				SymbolicElem<SquareMatrixElem<TestDimensionFour,DoubleElem,DoubleElemFactory>,SquareMatrixElemFactory<TestDimensionFour,DoubleElem,DoubleElemFactory>>
					el = inv.get( BigInteger.valueOf(i) , BigInteger.valueOf(j) );
				verifyNoInvertRight( el );
			}
		}
		
		
	}
	
	

	
}

