






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



public class TestInvertRightSymbolic extends TestCase 
{
	
	
	final int NUM_DIM = 2;
	
	
	private class AElem extends SymbolicElem<SquareMatrixElem<TestDimensionTwo,DoubleElem,DoubleElemFactory>,SquareMatrixElemFactory<TestDimensionTwo,DoubleElem,DoubleElemFactory>>
	{
		private BigInteger row;
		private BigInteger col;

		
		public AElem(SquareMatrixElemFactory<TestDimensionTwo,DoubleElem,DoubleElemFactory> _fac , BigInteger _row , BigInteger _col ) {
			super(_fac);
			row = _row;
			col = _col;
		}

		@Override
		public SquareMatrixElem<TestDimensionTwo,DoubleElem,DoubleElemFactory> eval() throws NotInvertibleException,
				MultiplicativeDistributionRequiredException {
			throw( new RuntimeException( "NotSupported" ) );
		}

		@Override
		public SquareMatrixElem<TestDimensionTwo,DoubleElem,DoubleElemFactory> evalPartialDerivative(ArrayList<Elem<?, ?>> withRespectTo)
				throws NotInvertibleException,
				MultiplicativeDistributionRequiredException {
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
		
		@Override
		public boolean symbolicEquals( SymbolicElem<SquareMatrixElem<TestDimensionTwo,DoubleElem,DoubleElemFactory>,SquareMatrixElemFactory<TestDimensionTwo,DoubleElem,DoubleElemFactory>> b )
		{
			if( b instanceof AElem )
			{
				return( ( row.equals( ((AElem) b).getRow() ) ) && ( col.equals( ((AElem) b).getCol() ) ) );
			}
			
			return( false );
		}
		
		@Override
		public String writeString( ) {
			return( "a( " + row + " , " + col + " )" );
		}
		
	}
	
	
	private void verifyNoInvertLeft( 
			SymbolicElem<SquareMatrixElem<TestDimensionTwo,DoubleElem,DoubleElemFactory>,SquareMatrixElemFactory<TestDimensionTwo,DoubleElem,DoubleElemFactory>> in )
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
			SymbolicAdd<SquareMatrixElem<TestDimensionTwo,DoubleElem,DoubleElemFactory>,SquareMatrixElemFactory<TestDimensionTwo,DoubleElem,DoubleElemFactory>> add = (SymbolicAdd<SquareMatrixElem<TestDimensionTwo,DoubleElem,DoubleElemFactory>,SquareMatrixElemFactory<TestDimensionTwo,DoubleElem,DoubleElemFactory>>) in;
			verifyNoInvertLeft( add.getElemA() );
			verifyNoInvertLeft( add.getElemB() );
			return;
		}
		
		
		if( in instanceof SymbolicNegate )
		{
			SymbolicNegate<SquareMatrixElem<TestDimensionTwo,DoubleElem,DoubleElemFactory>,SquareMatrixElemFactory<TestDimensionTwo,DoubleElem,DoubleElemFactory>> neg = (SymbolicNegate<SquareMatrixElem<TestDimensionTwo,DoubleElem,DoubleElemFactory>,SquareMatrixElemFactory<TestDimensionTwo,DoubleElem,DoubleElemFactory>>) in;
			verifyNoInvertLeft( neg.getElem() );
			return;
		}
		
		
		if( in instanceof SymbolicMult )
		{
			SymbolicMult<SquareMatrixElem<TestDimensionTwo,DoubleElem,DoubleElemFactory>,SquareMatrixElemFactory<TestDimensionTwo,DoubleElem,DoubleElemFactory>> add = (SymbolicMult<SquareMatrixElem<TestDimensionTwo,DoubleElem,DoubleElemFactory>,SquareMatrixElemFactory<TestDimensionTwo,DoubleElem,DoubleElemFactory>>) in;
			verifyNoInvertLeft( add.getElemA() );
			verifyNoInvertLeft( add.getElemB() );
			return;
		}
		
		
		if( in instanceof SymbolicInvertRight )
		{
			SymbolicInvertRight<SquareMatrixElem<TestDimensionTwo,DoubleElem,DoubleElemFactory>,SquareMatrixElemFactory<TestDimensionTwo,DoubleElem,DoubleElemFactory>> inv = (SymbolicInvertRight<SquareMatrixElem<TestDimensionTwo,DoubleElem,DoubleElemFactory>,SquareMatrixElemFactory<TestDimensionTwo,DoubleElem,DoubleElemFactory>>) in;
			verifyNoInvertLeft( inv.getElem() );
			return;
		}
		
		
		if( in instanceof SymbolicInvertLeft )
		{
			Assert.assertTrue( false );
		}
		
		
		throw( new RuntimeException( "Not Recognized" ) );
		
		
	}
	
	
	
	
	
	public void testInvertRightElems() throws NotInvertibleException
	{
		final TestDimensionTwo td = new TestDimensionTwo();
		
		final DoubleElemFactory dl = new DoubleElemFactory();
		
		final SquareMatrixElemFactory<TestDimensionTwo,DoubleElem,DoubleElemFactory> se = 
				new SquareMatrixElemFactory<TestDimensionTwo,DoubleElem,DoubleElemFactory>(dl, td);
		
		final SymbolicElemFactory<SquareMatrixElem<TestDimensionTwo,DoubleElem,DoubleElemFactory>,SquareMatrixElemFactory<TestDimensionTwo,DoubleElem,DoubleElemFactory>> ye = 
				new SymbolicElemFactory<SquareMatrixElem<TestDimensionTwo,DoubleElem,DoubleElemFactory>,SquareMatrixElemFactory<TestDimensionTwo,DoubleElem,DoubleElemFactory>>(se);
		
		final SquareMatrixElemFactory<TestDimensionTwo,
		SymbolicElem<SquareMatrixElem<TestDimensionTwo,DoubleElem,DoubleElemFactory>,SquareMatrixElemFactory<TestDimensionTwo,DoubleElem,DoubleElemFactory>>,SymbolicElemFactory<SquareMatrixElem<TestDimensionTwo,DoubleElem,DoubleElemFactory>,SquareMatrixElemFactory<TestDimensionTwo,DoubleElem,DoubleElemFactory>>> se2 = 
				new SquareMatrixElemFactory<TestDimensionTwo,
				SymbolicElem<SquareMatrixElem<TestDimensionTwo,DoubleElem,DoubleElemFactory>,SquareMatrixElemFactory<TestDimensionTwo,DoubleElem,DoubleElemFactory>>,SymbolicElemFactory<SquareMatrixElem<TestDimensionTwo,DoubleElem,DoubleElemFactory>,SquareMatrixElemFactory<TestDimensionTwo,DoubleElem,DoubleElemFactory>>>(ye, td);
		
		final SquareMatrixElem<TestDimensionTwo,
		SymbolicElem<SquareMatrixElem<TestDimensionTwo,DoubleElem,DoubleElemFactory>,SquareMatrixElemFactory<TestDimensionTwo,DoubleElem,DoubleElemFactory>>,SymbolicElemFactory<SquareMatrixElem<TestDimensionTwo,DoubleElem,DoubleElemFactory>,SquareMatrixElemFactory<TestDimensionTwo,DoubleElem,DoubleElemFactory>>>
			mat = se2.zero();
		
		
		
		int i;
		int j;
		
		for( i = 0 ; i < NUM_DIM ; i++ )
		{
			for( j = 0 ; j < NUM_DIM ; j++ )
			{
				AElem val = new AElem( se , BigInteger.valueOf(i) , BigInteger.valueOf(j) );
				mat.setVal( BigInteger.valueOf(i) , BigInteger.valueOf(j) , val );
			}
		}
		
		
		
		final SquareMatrixElem<TestDimensionTwo,
		SymbolicElem<SquareMatrixElem<TestDimensionTwo,DoubleElem,DoubleElemFactory>,SquareMatrixElemFactory<TestDimensionTwo,DoubleElem,DoubleElemFactory>>,SymbolicElemFactory<SquareMatrixElem<TestDimensionTwo,DoubleElem,DoubleElemFactory>,SquareMatrixElemFactory<TestDimensionTwo,DoubleElem,DoubleElemFactory>>>
			inv = mat.invertRight();
		
		
		
		
		for( i = 0 ; i < NUM_DIM ; i++ )
		{
			for( j = 0 ; j < NUM_DIM ; j++ )
			{
				SymbolicElem<SquareMatrixElem<TestDimensionTwo,DoubleElem,DoubleElemFactory>,SquareMatrixElemFactory<TestDimensionTwo,DoubleElem,DoubleElemFactory>>
					el = inv.get( BigInteger.valueOf(i) , BigInteger.valueOf(j) );
				verifyNoInvertLeft( el );
			}
		}
		
		
//		final SquareMatrixElem<TestDimensionTwo,
//			SymbolicElem<SquareMatrixElem<TestDimensionTwo,DoubleElem,DoubleElemFactory>,SquareMatrixElemFactory<TestDimensionTwo,DoubleElem,DoubleElemFactory>>,SymbolicElemFactory<SquareMatrixElem<TestDimensionTwo,DoubleElem,DoubleElemFactory>,SquareMatrixElemFactory<TestDimensionTwo,DoubleElem,DoubleElemFactory>>>
//				shouldBeIdent = mat.mult( inv );
//		for( i = 0 ; i < NUM_DIM ; i++ )
//		{
//			for( j = 0 ; j < NUM_DIM ; j++ )
//			{
//				SymbolicElem<SquareMatrixElem<TestDimensionTwo,DoubleElem,DoubleElemFactory>,SquareMatrixElemFactory<TestDimensionTwo,DoubleElem,DoubleElemFactory>>
//					el = shouldBeIdent.get( BigInteger.valueOf(i) , BigInteger.valueOf(j) );
//				el = el.handleOptionalOp( SymbolicOps.DISTRIBUTE_SIMPLIFY , null );
//				System.out.println( el.writeString() );
//			}
//		}
		
		
	}
	
	

	
}


