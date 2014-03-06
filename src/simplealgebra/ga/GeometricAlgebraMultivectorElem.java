



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





package simplealgebra.ga;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.TreeSet;

import simplealgebra.Elem;
import simplealgebra.ElemFactory;
import simplealgebra.Mutable;
import simplealgebra.Mutator;
import simplealgebra.NotInvertibleException;
import simplealgebra.NumDimensions;

public class GeometricAlgebraMultivectorElem<U extends NumDimensions, R extends Elem<R,?>, S extends ElemFactory<R,S>> extends Elem<GeometricAlgebraMultivectorElem<U,R,S>, GeometricAlgebraMultivectorElemFactory<U,R,S>> 
	implements Mutable<GeometricAlgebraMultivectorElem<U,R,S>, GeometricAlgebraMultivectorElem<U,R,S>, R> {

	
	public GeometricAlgebraMultivectorElem( S _fac , U _dim )
	{
		fac = _fac;
		dim = _dim;
	}
	
	
	@Override
	public GeometricAlgebraMultivectorElem<U, R, S> add(GeometricAlgebraMultivectorElem<U, R, S> b) {
		GeometricAlgebraMultivectorElem<U,R,S> ret = new GeometricAlgebraMultivectorElem<U,R,S>(fac,dim);
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
	public GeometricAlgebraMultivectorElem<U, R, S> mult(GeometricAlgebraMultivectorElem<U, R, S> b) {
		GeometricAlgebraMultivectorElem<U,R,S> ret = new GeometricAlgebraMultivectorElem<U,R,S>(fac,dim);
		
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
		
		
		return( negate );
	}
	

	@Override
	public GeometricAlgebraMultivectorElem<U, R, S> negate() {
		GeometricAlgebraMultivectorElem<U,R,S> ret = new GeometricAlgebraMultivectorElem<U,R,S>(fac,dim);
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
	public GeometricAlgebraMultivectorElem<U, R, S> mutate( Mutator<R> mutr ) {
		GeometricAlgebraMultivectorElem<U,R,S> ret = new GeometricAlgebraMultivectorElem<U,R,S>(fac,dim);
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
	public GeometricAlgebraMultivectorElem<U, R, S> invert() throws NotInvertibleException {
		return( null ); // !!!!!!!!!!!!!!!!!!!!!!!!!! TBD !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	}
	


	@Override
	public GeometricAlgebraMultivectorElem<U, R, S> divideBy(int val) {
		GeometricAlgebraMultivectorElem<U,R,S> ret = new GeometricAlgebraMultivectorElem<U,R,S>(fac,dim);
		Iterator<HashSet<BigInteger>> it = map.keySet().iterator();
		while( it.hasNext() )
		{
			HashSet<BigInteger> el = it.next();
			R vali = map.get( el );
			ret.setVal(el, vali.divideBy(val) );
		}
		return( ret );
	}

	@Override
	public GeometricAlgebraMultivectorElemFactory<U, R, S> getFac() {
		return( new GeometricAlgebraMultivectorElemFactory<U,R,S>( fac , dim ) );
	}
	
	
	private R get( HashSet<BigInteger> el )
	{
		return( map.get( el ) );
	}
	
	
	public R getVal( HashSet<BigInteger> el , S fac )
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
