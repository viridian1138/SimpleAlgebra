



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





package simplealgebra;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Iterator;

public class SquareMatrixElem<U extends NumDimensions, R extends Elem<R,?>, S extends ElemFactory<R,S>> extends 
	MutableElem<R,SquareMatrixElem<U,R,S>, SquareMatrixElemFactory<U,R,S>> {

	
	public SquareMatrixElem( S _fac , U _dim )
	{
		fac = _fac;
		dim = _dim;
	}
	
	
	@Override
	public SquareMatrixElem<U, R, S> add(SquareMatrixElem<U, R, S> b) {
		SquareMatrixElem<U,R,S> ret = new SquareMatrixElem<U,R,S>(fac,dim);
		
		{
			Iterator<BigInteger> rowi = rowMap.keySet().iterator();
			while( rowi.hasNext() )
			{
				BigInteger row = rowi.next();
				HashMap<BigInteger,R> subMap = rowMap.get( row );
				Iterator<BigInteger> coli = subMap.keySet().iterator();
				while( coli.hasNext() )
				{
					BigInteger col = coli.next();
					R vali = subMap.get( col );
					ret.setVal(row, col, vali );
				}
			}
		}
		
		
		{
			Iterator<BigInteger> rowi = b.rowMap.keySet().iterator();
			while( rowi.hasNext() )
			{
				BigInteger row = rowi.next();
				HashMap<BigInteger,R> subMap = b.rowMap.get( row );
				Iterator<BigInteger> coli = subMap.keySet().iterator();
				while( coli.hasNext() )
				{
					BigInteger col = coli.next();
					R vali = subMap.get( col );
					R valp = ret.get( row , col );
					if( valp == null  )
					{
						ret.setVal(row, col, vali);
					}
					else
					{
						ret.setVal(row, col, valp.add( vali ) );
					}
				}
			}
		}
		
		return( ret );
	}

	
	@Override
	public SquareMatrixElem<U, R, S> mult(SquareMatrixElem<U, R, S> b) {
		SquareMatrixElem<U,R,S> ret = new SquareMatrixElem<U,R,S>(fac,dim);
		
		Iterator<BigInteger> rowi = rowMap.keySet().iterator();
		while( rowi.hasNext() )
		{
			BigInteger row = rowi.next();
			HashMap<BigInteger,R> subMapRowa = rowMap.get( row );
			Iterator<BigInteger> colj = b.columnMap.keySet().iterator();
			while( colj.hasNext() )
			{
				BigInteger col = colj.next();
				HashMap<BigInteger,R> subMapColb = b.columnMap.get( col );
				R val = null;
				Iterator<BigInteger> vk = ( subMapRowa.size() < subMapColb.size() ) ? 
						subMapRowa.keySet().iterator() : subMapColb.keySet().iterator();
				while( vk.hasNext() )
				{
					BigInteger k = vk.next();
					R av = subMapRowa.get( k );
					R bv = subMapColb.get( k );
					if( ( av != null ) && ( bv != null ) )
					{
						R prod = av.mult( bv );
						if( val == null )
						{
							val = prod;
						}
						else
						{
							val = val.add( prod );
						}
					}
				}
				if( val != null )
				{
					ret.setVal(row, col, val);
				}
			}
		}
		return( ret );
		
	}

	@Override
	public SquareMatrixElem<U, R, S> negate() {
		SquareMatrixElem<U,R,S> ret = new SquareMatrixElem<U,R,S>(fac,dim);
		Iterator<BigInteger> rowi = rowMap.keySet().iterator();
		while( rowi.hasNext() )
		{
			BigInteger row = rowi.next();
			HashMap<BigInteger,R> subMap = rowMap.get( row );
			Iterator<BigInteger> coli = subMap.keySet().iterator();
			while( coli.hasNext() )
			{
				BigInteger col = coli.next();
				R vali = subMap.get( col );
				ret.setVal(row, col, vali.negate() );
			}
		}
		return( ret );
	}
	
	
	@Override
	public SquareMatrixElem<U, R, S> mutate( Mutator<R> mutr ) {
		SquareMatrixElem<U,R,S> ret = new SquareMatrixElem<U,R,S>(fac,dim);
		Iterator<BigInteger> rowi = rowMap.keySet().iterator();
		while( rowi.hasNext() )
		{
			BigInteger row = rowi.next();
			HashMap<BigInteger,R> subMap = rowMap.get( row );
			Iterator<BigInteger> coli = subMap.keySet().iterator();
			while( coli.hasNext() )
			{
				BigInteger col = coli.next();
				R vali = subMap.get( col );
				ret.setVal(row, col, mutr.mutate( vali ) );
			}
		}
		return( ret );
	}
	
	

	@Override
	public SquareMatrixElem<U, R, S> invert() throws NotInvertibleException {
		SquareMatrixElem<U,R,S> copy = icopy();
		return( copy.iinvert() );
	}
	
	private SquareMatrixElem<U, R, S> iinvert() throws NotInvertibleException
	{
		SquareMatrixElem<U,R,S> ret = getFac().identity();
		
		final BigInteger max = dim.getVal();
		BigInteger cnt = BigInteger.ZERO;
		while( cnt.compareTo( max ) < 0 )
		{
			final R mv = setUpRow( cnt , ret );
			
			multiplyThroughRow( cnt , mv );
			ret.multiplyThroughRow( cnt , mv);
			
			BigInteger srcRow = cnt;
			BigInteger destRow = BigInteger.ZERO;
			while( destRow.compareTo( max ) < 0 )
			{
				if( destRow.compareTo(srcRow) != 0 )
				{
					R mult = this.getVal(destRow, srcRow, fac);
					rowSubtract( srcRow , destRow , mult );
					ret.rowSubtract(srcRow, destRow, mult);
				}
				
				destRow = destRow.add( BigInteger.ONE );
			}
			
			cnt = cnt.add( BigInteger.ONE );
		}
		
		return( ret );
	}
	
	
	private void rowSubtract( BigInteger srcRow , BigInteger destRow , R mult )
	{
		HashMap<BigInteger,R> subMapSrc = rowMap.get( srcRow );
		HashMap<BigInteger,R> subMapDest = rowMap.get( destRow );
		if( subMapSrc != null )
		{
			Iterator<BigInteger> it = subMapSrc.keySet().iterator();
			while( it.hasNext() )
			{
				BigInteger col = it.next();
				R srcVal = subMapSrc.get( col );
				R srcMultNegated = srcVal.mult( mult ).negate(); // ????????????????????????????????? which side ????????????????????????????
				R dstVal = subMapDest.get( col );
				if( dstVal != null )
				{
					this.setVal(destRow, col, dstVal.add(srcMultNegated));
				}
				else
				{
					this.setVal(destRow, col, srcMultNegated);
				}
			}
		}
	}
	
	
	private void multiplyThroughRow( BigInteger row , R mv )
	{
		HashMap<BigInteger,R> subMap = rowMap.get( row );
		if( subMap != null )
		{
			Iterator<BigInteger> it = subMap.keySet().iterator();
			while( it.hasNext() )
			{
				BigInteger col = it.next();
				R val = subMap.get( col );
				if( val != null )
				{
					R pval = val.mult( mv ); // ???????????????????????????????????????????? which side ?????????????????
					this.setVal(row, col, pval);
				}
			}
		}
	}
	
	
	private R setUpRow( final BigInteger rowi , final SquareMatrixElem<U,R,S> ret ) throws NotInvertibleException
	{
		try
		{
			R tp = this.get(rowi, rowi).invert();
			return( tp );
		}
		catch( NotInvertibleException ex )
		{
			final BigInteger max = dim.getVal();
			BigInteger cnt = rowi.add( BigInteger.ONE );
			while( cnt.compareTo( max ) < 0 )
			{
				try
				{
					R tp = this.get(cnt, rowi).invert();
					exchangeRows( rowi , cnt );
					ret.exchangeRows( rowi , cnt );
					return( tp );
				}
				catch( NotInvertibleException ex2 )
				{
					cnt = cnt.add( BigInteger.ONE );
				}
			}
			throw( new NotInvertibleException() );
		}
	}
	
	
	private void exchangeRows( final BigInteger rowa , final BigInteger rowb )
	{
		HashMap<BigInteger,R> subA = rowMap.get( rowa );
		HashMap<BigInteger,R> subB = rowMap.get( rowb );
		
		HashMap<BigInteger,R> subAA = (HashMap<BigInteger, R>) (subA != null ? subA.clone() : null);
		HashMap<BigInteger,R> subBB = (HashMap<BigInteger, R>) (subB != null ? subB.clone() : null);
		
		eraseAllRowValues( rowa , subAA );
		eraseAllRowValues( rowb , subBB );
		
		setAllRowValues( rowa , subBB );
		setAllRowValues( rowb , subAA );
	}
	
	
	private void setAllRowValues( final BigInteger row , HashMap<BigInteger,R> cmap )
	{
		if( cmap != null )
		{
			Iterator<BigInteger> it = cmap.keySet().iterator();
			while( it.hasNext() )
			{
				BigInteger col = it.next();
				this.setVal(row, col, cmap.get( col ) );
			}
		}
	}
	
	
	private void eraseAllRowValues( final BigInteger row , HashMap<BigInteger,R> cmap )
	{
		if( cmap != null )
		{
			Iterator<BigInteger> it = cmap.keySet().iterator();
			while( it.hasNext() )
			{
				BigInteger col = it.next();
				eraseVal( row , col );
			}
		}
	}
	
	
	private void eraseVal( final BigInteger row , final BigInteger col )
	{
		HashMap<BigInteger,R> subMap = rowMap.get( row );
		if( subMap != null )
		{
			subMap.remove( col );
			
			if( subMap.keySet().size() == 0 )
			{
				rowMap.remove( row );
			}
		}
		
		
		subMap = columnMap.get( col );
		if( subMap != null )
		{
			subMap.remove( row );
			
			if( subMap.keySet().size() == 0 )
			{
				columnMap.remove( col );
			}
		}
	}
	
	
	private SquareMatrixElem<U, R, S> icopy()
	{
		SquareMatrixElem<U,R,S> ret = new SquareMatrixElem<U,R,S>(fac,dim);
		Iterator<BigInteger> rowi = rowMap.keySet().iterator();
		while( rowi.hasNext() )
		{
			BigInteger row = rowi.next();
			HashMap<BigInteger,R> subMap = rowMap.get( row );
			Iterator<BigInteger> coli = subMap.keySet().iterator();
			while( coli.hasNext() )
			{
				BigInteger col = coli.next();
				R vali = subMap.get( col );
				ret.setVal(row, col, vali );
			}
		}
		return( ret );
	}

	@Override
	public SquareMatrixElem<U, R, S> divideBy(int val) {
		SquareMatrixElem<U,R,S> ret = new SquareMatrixElem<U,R,S>(fac,dim);
		Iterator<BigInteger> rowi = rowMap.keySet().iterator();
		while( rowi.hasNext() )
		{
			BigInteger row = rowi.next();
			HashMap<BigInteger,R> subMap = rowMap.get( row );
			Iterator<BigInteger> coli = subMap.keySet().iterator();
			while( coli.hasNext() )
			{
				BigInteger col = coli.next();
				R vali = subMap.get( col );
				ret.setVal(row, col, vali.divideBy( val ) );
			}
		}
		return( ret );
	}

	@Override
	public SquareMatrixElemFactory<U, R, S> getFac() {
		return( new SquareMatrixElemFactory<U,R,S>( fac , dim ) );
	}
	
	
	private R get( BigInteger row , BigInteger col )
	{
		HashMap<BigInteger,R> subMap = rowMap.get( row );
		if( subMap != null )
		{
			R val = subMap.get( col );
			if( val != null )
			{
				return( val );
			}
		}
		return( null );
	}
	
	
	public R getVal( BigInteger row , BigInteger col , S fac )
	{
		HashMap<BigInteger,R> subMap = rowMap.get( row );
		if( subMap != null )
		{
			R val = subMap.get( col );
			if( val != null )
			{
				return( val );
			}
		}
		return( fac.zero() );
	}
	
	public void setVal( BigInteger row , BigInteger col , R val )
	{
		HashMap<BigInteger,R> subMap = rowMap.get( row );
		if( subMap == null )
		{
			subMap = new HashMap<BigInteger,R>();
			rowMap.put(row, subMap);
		}
		
		subMap.put(col, val);
		
		
		subMap = columnMap.get( col );
		if( subMap == null )
		{
			subMap = new HashMap<BigInteger,R>();
			columnMap.put(col, subMap);
		}
		
		subMap.put(row, val);
	}
	
	
	
	private final HashMap<BigInteger,HashMap<BigInteger,R>> columnMap = new HashMap<BigInteger,HashMap<BigInteger,R>>();
	
	private final HashMap<BigInteger,HashMap<BigInteger,R>> rowMap = new HashMap<BigInteger,HashMap<BigInteger,R>>();
	
	private S fac;
	private U dim;
	

}