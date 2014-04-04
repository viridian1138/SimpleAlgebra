



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





package simplealgebra.qtrnn;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.TreeSet;

import simplealgebra.Elem;
import simplealgebra.ElemFactory;
import simplealgebra.MutableElem;
import simplealgebra.Mutator;
import simplealgebra.NotInvertibleException;
import simplealgebra.NumDimensions;
import simplealgebra.SquareMatrixElem;
import simplealgebra.et.EinsteinTensorElem;
import simplealgebra.ga.GeometricAlgebraMultivectorElem;

public class QuaternionElem<U extends NumDimensions, R extends Elem<R,?>, S extends ElemFactory<R,S>> 
	extends MutableElem<R, QuaternionElem<U,R,S>, QuaternionElemFactory<U,R,S>>  {

	
	public QuaternionElem( S _fac , U _dim )
	{
		fac = _fac;
		dim = _dim;
	}
	
	
	@Override
	public QuaternionElem<U, R, S> add(QuaternionElem<U, R, S> b) {
		QuaternionElem<U,R,S> ret = new QuaternionElem<U,R,S>(fac,dim);
		Iterator<HashSet<BigInteger>> it = map.keySet().iterator();
		while( it.hasNext() )
		{
			HashSet<BigInteger> el = it.next();
			R vali = map.get( el );
			ret.setVal(el, vali );
		}
		
		it = b.map.keySet().iterator();
		while( it.hasNext() )
		{
			HashSet<BigInteger> el = it.next();
			R vali = b.map.get( el );
			R vv = ret.get( el );
			if( vv != null )
			{
				ret.setVal(el, vv.add(vali) );
			}
			else
			{
				ret.setVal(el, vali );
			}
		}
		
		return( ret );
	}

	
	@Override
	public QuaternionElem<U, R, S> mult(QuaternionElem<U, R, S> b) {
		QuaternionElem<U,R,S> ret = new QuaternionElem<U,R,S>(fac,dim);
		
		Iterator<HashSet<BigInteger>> ita = map.keySet().iterator();
		while( ita.hasNext() )
		{
			HashSet<BigInteger> ka = ita.next();
			R va = map.get( ka );
			Iterator<HashSet<BigInteger>> itb = b.map.keySet().iterator();
			while( itb.hasNext() )
			{
				HashSet<BigInteger> kb = itb.next();
				R vb = map.get( kb );
				R vmul = va.mult( vb );
				HashSet<BigInteger> el = new HashSet<BigInteger>();
				final boolean negate = calcOrd( ka , kb , el );
				if( negate )
				{
					vmul = vmul.negate();
				}
				R vv = ret.get( el );
				if( vv != null )
				{
					ret.setVal(el, vv.add(vmul) );
				}
				else
				{
					ret.setVal(el, vmul );
				}
			}
		}
		
		return( ret );
	}
	
	
	
	private boolean calcOrd( HashSet<BigInteger> ka , HashSet<BigInteger> kb , HashSet<BigInteger> el )
	{
		boolean negate = false;
		
		final TreeSet<BigInteger> kaa = new TreeSet<BigInteger>( ka );
		final TreeSet<BigInteger> kbb = new TreeSet<BigInteger>( kb );
		
		
		final int sz = kaa.size() + kbb.size();
		
		
		final BigInteger[] arr = new BigInteger[ sz ];
		
		
		int cnt = 0;
		Iterator<BigInteger> it = kaa.iterator();
		while( it.hasNext() )
		{
			arr[ cnt ] = it.next();
			cnt++;
		}
		it = kbb.iterator();
		while( it.hasNext() )
		{
			arr[ cnt ] = it.next();
			cnt++;
		}
		
		
		boolean chg = true;
		while( chg )
		{
			chg = false;
			for( cnt = 0 ; cnt < ( sz - 1 ) ; cnt++ )
			{
				final BigInteger a0 = arr[ cnt ];
				final BigInteger a1 = arr[ cnt + 1 ];
				if( ( a0 == null ) && ( a1 != null ) )
				{
					arr[ cnt ] = a1;
					arr[ cnt + 1 ] = a0;
					chg = true;
				}
				else
				{
					if( ( a0 != null ) && ( a1 != null ) )
					{
						final int cmp = a0.compareTo( a1 );
						if( cmp == 0 )
						{
							arr[ cnt ] = null;
							arr[ cnt + 1 ] = null;
							chg = true;
							negate = !negate;
						}
						else
						{
							if( cmp > 0 )
							{
								arr[ cnt ] = a1;
								arr[ cnt + 1 ] = a0;
								chg = true;
								negate = !negate;
							}
						}
					}
				}
			}
		}
		
		
		for( cnt = 0 ; cnt < sz ; cnt++ )
		{
			if( arr[ cnt ] != null )
			{
				el.add( arr[ cnt ] );
			}
		}
		
		
		//
		// Equivalent to ijk = -1.
		//
		if( dim.equals( BigInteger.valueOf( el.size() ) ) )
		{
			el.clear();
			negate = !negate;
		}
		
		
		return( negate );
	}
	

	@Override
	public QuaternionElem<U, R, S> negate() {
		QuaternionElem<U,R,S> ret = new QuaternionElem<U,R,S>(fac,dim);
		Iterator<HashSet<BigInteger>> it = map.keySet().iterator();
		while( it.hasNext() )
		{
			HashSet<BigInteger> el = it.next();
			R vali = map.get( el );
			ret.setVal(el, vali.negate() );
		}
		return( ret );
	}
	
	
	@Override
	public QuaternionElem<U, R, S> mutate( Mutator<R> mutr ) {
		QuaternionElem<U,R,S> ret = new QuaternionElem<U,R,S>(fac,dim);
		Iterator<HashSet<BigInteger>> it = map.keySet().iterator();
		while( it.hasNext() )
		{
			HashSet<BigInteger> el = it.next();
			R vali = map.get( el );
			ret.setVal(el, mutr.mutate( vali ) );
		}
		return( ret );
	}

	
	@Override
	public QuaternionElem<U, R, S> invertLeft() throws NotInvertibleException {
		return( null ); // !!!!!!!!!!!!!!!!!!!!!!!!!! TBD !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	}
	
	
	
	@Override
	public QuaternionElem<U, R, S> invertRight() throws NotInvertibleException {
		return( null ); // !!!!!!!!!!!!!!!!!!!!!!!!!! TBD !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	}
	


	@Override
	public QuaternionElem<U, R, S> divideBy(int val) {
		QuaternionElem<U,R,S> ret = new QuaternionElem<U,R,S>(fac,dim);
		Iterator<HashSet<BigInteger>> it = map.keySet().iterator();
		while( it.hasNext() )
		{
			HashSet<BigInteger> el = it.next();
			R vali = map.get( el );
			ret.setVal(el, vali.divideBy(val) );
		}
		return( ret );
	}
	
	public void toGeometricAlgebra( GeometricAlgebraMultivectorElem<U, R, ?> out )
	{
		Iterator<HashSet<BigInteger>> it = map.keySet().iterator();
		while( it.hasNext() )
		{
			HashSet<BigInteger> key = it.next();
			R val = map.get(key);
			out.setVal(key, val);
		}
	}
	
	
	public QuaternionElem<U, R, S> getGradedPart( BigInteger grade )
	{
		QuaternionElem<U, R, S> ret = new QuaternionElem<U, R, S>( fac , dim );
		Iterator<HashSet<BigInteger>> it = map.keySet().iterator();
		while( it.hasNext() )
		{
			HashSet<BigInteger> key = it.next();
			if( grade.equals( BigInteger.valueOf( key.size() ) ) )
			{
				ret.setVal(key, map.get(key) );
			}
		}
		return( ret );
	}
	
	
	public void vectorPartToRowVector( BigInteger row , SquareMatrixElem<U, R, ?> out )
	{
		QuaternionElem<U, R, S> grd = getGradedPart( BigInteger.ONE );
		Iterator<HashSet<BigInteger>> it = grd.map.keySet().iterator();
		while( it.hasNext() )
		{
			HashSet<BigInteger> key = it.next();
			BigInteger column = key.iterator().next();
			out.setVal(row, column, grd.map.get(key) );
		}
	}
	
	
	public void vectorPartToColumnVector( BigInteger column , SquareMatrixElem<U, R, ?> out )
	{
		QuaternionElem<U, R, S> grd = getGradedPart( BigInteger.ONE );
		Iterator<HashSet<BigInteger>> it = grd.map.keySet().iterator();
		while( it.hasNext() )
		{
			HashSet<BigInteger> key = it.next();
			BigInteger row = key.iterator().next();
			out.setVal(row, column, grd.map.get(key) );
		}
	}
	
	
	public void vectorPartToRankOneTensor( EinsteinTensorElem<?,R,?> out )
	{
		if( !( out.getTensorRank().equals( BigInteger.ONE ) ) )
		{
			throw( new RuntimeException( "Not a Rank One Tensor." ) );
		}
		
		QuaternionElem<U, R, S> grd = getGradedPart( BigInteger.ONE );
		Iterator<HashSet<BigInteger>> it = grd.map.keySet().iterator();
		while( it.hasNext() )
		{
			HashSet<BigInteger> key = it.next();
			BigInteger indx = key.iterator().next();
			ArrayList<BigInteger> okey = new ArrayList<BigInteger>();
			okey.add( indx );
			out.setVal(okey, grd.map.get(key));
		}
	}
	
	
	public void rowVectorMult( SquareMatrixElem<U, R, ?> in , 
			QuaternionElem<U, R, S> rowVectorOut )
	{
		final QuaternionElem<U, R, S> rowVectIn = this.getGradedPart( BigInteger.ONE );
		final Iterator<HashSet<BigInteger>> it = rowVectIn.map.keySet().iterator();
		while( it.hasNext() )
		{
			final HashSet<BigInteger> keyK = it.next();
			final R rowVectInVal = rowVectIn.get( keyK );
			final BigInteger k = keyK.iterator().next();
			final QuaternionElem<U, R, S> rowVectMat = new QuaternionElem<U, R, S>(fac, dim);
			in.rowVectorToQuaternion(k, rowVectMat);
			final Iterator<HashSet<BigInteger>> ita = rowVectMat.map.keySet().iterator();
			while( ita.hasNext() )
			{
				final HashSet<BigInteger> keyJ = ita.next();
				final R rowVectMatVal = rowVectMat.get( keyJ );
				final R val = rowVectInVal.mult( rowVectMatVal );
				final R addVal = rowVectorOut.get(keyJ);
				if( addVal != null )
				{
					rowVectorOut.setVal(keyJ, val.add( addVal ) );
				}
				else
				{
					rowVectorOut.setVal(keyJ, val );
				}
			}
		}
	}
	
	
	public void colVectorMult( SquareMatrixElem<U, R, ?> in , 
			QuaternionElem<U, R, S> colVectorOut )
	{
		final QuaternionElem<U, R, S> colVectIn = this.getGradedPart( BigInteger.ONE );
		final Iterator<HashSet<BigInteger>> it = colVectIn.map.keySet().iterator();
		while( it.hasNext() )
		{
			final HashSet<BigInteger> keyK = it.next();
			final R colVectInVal = colVectIn.get( keyK );
			final BigInteger k = keyK.iterator().next();
			final QuaternionElem<U, R, S> colVectMat = new QuaternionElem<U, R, S>(fac, dim);
			in.columnVectorToQuaternion(k, colVectMat);
			final Iterator<HashSet<BigInteger>> ita = colVectMat.map.keySet().iterator();
			while( ita.hasNext() )
			{
				final HashSet<BigInteger> keyI = ita.next();
				final R colVectMatVal = colVectMat.get( keyI );
				final R val = colVectMatVal.mult( colVectInVal );
				final R addVal = colVectorOut.get(keyI);
				if( addVal != null )
				{
					colVectorOut.setVal(keyI, val.add( addVal ) );
				}
				else
				{
					colVectorOut.setVal(keyI, val );
				}
			}
		}
	}

	
	@Override
	public QuaternionElemFactory<U, R, S> getFac() {
		return( new QuaternionElemFactory<U,R,S>( fac , dim ) );
	}
	
	
	private R get( HashSet<BigInteger> el )
	{
		return( map.get( el ) );
	}
	
	
	public R getVal( HashSet<BigInteger> el )
	{
		R val = map.get( el );
		return( val != null ? val : fac.zero() );
	}
	
	public void setVal( HashSet<BigInteger> el , R val )
	{
		map.put(el, val);
	}
	
	
	
	private final HashMap<HashSet<BigInteger>,R> map = new HashMap<HashSet<BigInteger>,R>();
	
	
	private S fac;
	private U dim;
	

}
