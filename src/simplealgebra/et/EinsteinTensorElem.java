



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





package simplealgebra.et;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import simplealgebra.Elem;
import simplealgebra.ElemFactory;
import simplealgebra.MutableElem;
import simplealgebra.Mutator;
import simplealgebra.NotInvertibleException;

public class EinsteinTensorElem<Z extends Object, R extends Elem<R,?>, S extends ElemFactory<R,S>> 
	extends MutableElem<R, EinsteinTensorElem<Z,R,S>, EinsteinTensorElemFactory<Z,R,S>>  {

	
	public EinsteinTensorElem( S _fac , ArrayList<Z> _contravariantIndices ,
			ArrayList<Z> _covariantIndices )
	{
		fac = _fac;
		contravariantIndices = _contravariantIndices;
		covariantIndices = _covariantIndices;
	}
	
	
	@Override
	public EinsteinTensorElem<Z, R, S> add(EinsteinTensorElem<Z, R, S> b) {
		EinsteinTensorElem<Z,R,S> ret = new EinsteinTensorElem<Z,R,S>(fac, covariantIndices , contravariantIndices);
		Iterator<ArrayList<BigInteger>> it = map.keySet().iterator();
		while( it.hasNext() )
		{
			ArrayList<BigInteger> el = it.next();
			R vali = map.get( el );
			ret.setVal(el, vali );
		}
		
		it = b.map.keySet().iterator();
		while( it.hasNext() )
		{
			ArrayList<BigInteger> el = it.next();
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
	public EinsteinTensorElem<Z, R, S> mult(EinsteinTensorElem<Z, R, S> b) {
		
		ArrayList<Integer> matchIndicesA = null; // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! TBD !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		ArrayList<Integer> matchIndicesB = null; // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! TBD !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		ArrayList<Integer> nonMatchIndices = null; // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! TBD !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		
		HashMap<ArrayList<BigInteger>,ArrayList<ArrayList<BigInteger>>> matchMap = buildSummationIndexMap( b , matchIndicesB );
		
		EinsteinTensorElem<Z,R,S> ret = null;
		
		Iterator<ArrayList<BigInteger>> it = map.keySet().iterator();
		
		while( it.hasNext() )
		{
			ArrayList<BigInteger> key = it.next();
			final R val = map.get(key);
			ArrayList<BigInteger> matchMapKey = buildSummationIndex( key , matchIndicesA );
			Iterator<ArrayList<BigInteger>> ita = matchMap.get(matchMapKey).iterator();
			while( ita.hasNext() )
			{
				ArrayList<BigInteger> bkey = ita.next();
				final R bval = b.map.get( bkey );
				final R muval = val.mult( bval );
				final ArrayList<BigInteger> combinedAB = buildCombinedAB( key , bkey );
				final ArrayList<BigInteger> placeMapKey = buildSummationIndex( combinedAB , nonMatchIndices );
				final R nval = ret.map.get( placeMapKey );
				if( nval != null )
				{
					ret.map.put(placeMapKey, nval.add( muval ) );
				}
				else
				{
					ret.map.put(placeMapKey, muval );
				}
			}
		}
		
		return( ret );
	}
	
	
	
	protected HashMap<ArrayList<BigInteger>,ArrayList<ArrayList<BigInteger>>> buildSummationIndexMap( EinsteinTensorElem<Z, R, S> b , ArrayList<Integer> matchIndices )
	{
		HashMap<ArrayList<BigInteger>,ArrayList<ArrayList<BigInteger>>> matchMap = new HashMap<ArrayList<BigInteger>,ArrayList<ArrayList<BigInteger>>>();
		
		Iterator<ArrayList<BigInteger>> it = b.map.keySet().iterator();
		
		while( it.hasNext() )
		{
			buildSummationIndexMap( it.next() , matchIndices , matchMap );
		}
		
		return( matchMap );
	}
	
	
	
	protected void buildSummationIndexMap( ArrayList<BigInteger> combinedCovariantContravariant , ArrayList<Integer> matchIndices , 
			HashMap<ArrayList<BigInteger>,ArrayList<ArrayList<BigInteger>>> matchMap )
	{
		ArrayList<BigInteger> summationIndex = buildSummationIndex( combinedCovariantContravariant , matchIndices );
		ArrayList<ArrayList<BigInteger>> match = matchMap.get( summationIndex );
		if( match == null )
		{
			match = new ArrayList<ArrayList<BigInteger>>();
			matchMap.put( summationIndex , match );
		}
		match.add( combinedCovariantContravariant );
	}
	
	
	protected ArrayList<BigInteger> buildSummationIndex( ArrayList<BigInteger> combinedIndices , ArrayList<Integer> matchIndices )
	{
		ArrayList<BigInteger> ret = new ArrayList<BigInteger>();
		
		Iterator<Integer> it = matchIndices.iterator();
		while( it.hasNext() )
		{
			ret.add( combinedIndices.get( it.next() ) );
		}
		
		return( ret );
	}
	
	
	protected ArrayList<Z> buildCombinedCovariantContravariantIn( ArrayList<Z> covariantIndices , ArrayList<Z> contravariantIndices )
	{
		ArrayList<Z> ret = new ArrayList<Z>();
		
		Iterator<Z> it = covariantIndices.iterator();
		while( it.hasNext() )
		{
			ret.add( it.next() );
		}
		
		it = contravariantIndices.iterator();
		while( it.hasNext() )
		{
			ret.add( it.next() );
		}
		
		return( ret );
	}
	
	
	protected ArrayList<BigInteger> buildCombinedCovariantContravariantVl( ArrayList<BigInteger> covariantIndices , ArrayList<BigInteger> contravariantIndices )
	{
		ArrayList<BigInteger> ret = new ArrayList<BigInteger>();
		
		Iterator<BigInteger> it = covariantIndices.iterator();
		while( it.hasNext() )
		{
			ret.add( it.next() );
		}
		
		it = contravariantIndices.iterator();
		while( it.hasNext() )
		{
			ret.add( it.next() );
		}
		
		return( ret );
	}
	
	
	protected ArrayList<BigInteger> buildCombinedAB( ArrayList<BigInteger> akey , ArrayList<BigInteger> bkey )
	{
		ArrayList<BigInteger> ret = new ArrayList<BigInteger>();
		
		Iterator<BigInteger> it = akey.iterator();
		while( it.hasNext() )
		{
			ret.add( it.next() );
		}
		
		it = bkey.iterator();
		while( it.hasNext() )
		{
			ret.add( it.next() );
		}
		
		return( ret );
	}
	

	@Override
	public EinsteinTensorElem<Z, R, S> negate() {
		EinsteinTensorElem<Z,R,S> ret = new EinsteinTensorElem<Z,R,S>(fac, covariantIndices , contravariantIndices);
		Iterator<ArrayList<BigInteger>> it = map.keySet().iterator();
		while( it.hasNext() )
		{
			ArrayList<BigInteger> el = it.next();
			R vali = map.get( el );
			ret.setVal(el, vali.negate() );
		}
		return( ret );
	}
	
	
	@Override
	public EinsteinTensorElem<Z, R, S> mutate( Mutator<R> mutr ) {
		EinsteinTensorElem<Z,R,S> ret = new EinsteinTensorElem<Z,R,S>(fac, covariantIndices , contravariantIndices);
		Iterator<ArrayList<BigInteger>> it = map.keySet().iterator();
		while( it.hasNext() )
		{
			ArrayList<BigInteger> el = it.next();
			R vali = map.get( el );
			ret.setVal(el, mutr.mutate( vali ) );
		}
		return( ret );
	}

	
	@Override
	public EinsteinTensorElem<Z, R, S> invert() throws NotInvertibleException {
		return( null ); // !!!!!!!!!!!!!!!!!!!!!!!!!! TBD !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	}
	


	@Override
	public EinsteinTensorElem<Z, R, S> divideBy(int val) {
		EinsteinTensorElem<Z,R,S> ret = new EinsteinTensorElem<Z,R,S>(fac, covariantIndices , contravariantIndices);
		Iterator<ArrayList<BigInteger>> it = map.keySet().iterator();
		while( it.hasNext() )
		{
			ArrayList<BigInteger> el = it.next();
			R vali = map.get( el );
			ret.setVal(el, vali.divideBy(val) );
		}
		return( ret );
	}

	@Override
	public EinsteinTensorElemFactory<Z, R, S> getFac() {
		return( new EinsteinTensorElemFactory<Z,R,S>( fac , covariantIndices , contravariantIndices ) );
	}
	
	
	private R get( ArrayList<BigInteger> el )
	{
		return( map.get( el ) );
	}
	
	
	public R getVal( ArrayList<BigInteger> el , S fac )
	{
		R val = map.get( el );
		return( val != null ? val : fac.zero() );
	}
	
	public void setVal( ArrayList<BigInteger> el , R val )
	{
		map.put(el, val);
	}
	
	
	
	private final HashMap<ArrayList<BigInteger>,R> map = new HashMap<ArrayList<BigInteger>,R>();
	
	
	private S fac;
	private ArrayList<Z> contravariantIndices;
	private ArrayList<Z> covariantIndices;
	

}
