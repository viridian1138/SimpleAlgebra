


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

public class ComplexElem<R extends Elem<R,?>, S extends ElemFactory<R,S>> 
	extends MutableElem<R,ComplexElem<R,S>,ComplexElemFactory<R,S>>
	{

	@Override
	public ComplexElem<R, S> add(ComplexElem<R, S> b) {
		return( new ComplexElem<R,S>( re.add( b.re ) , im.add( b.im ) ) );
	}

	@Override
	public ComplexElem<R, S> mult(ComplexElem<R, S> b) {
		final R a0 = re.mult( b.re );
		final R a1 = re.mult( b.im );
		final R a2 = im.mult( b.re );
		final R a3 = im.mult( b.im ).negate();
		return( new ComplexElem<R,S>( a0.add( a3 ) , a1.add( a2 ) ) );
	}

	@Override
	public ComplexElem<R, S> negate() {
		return( new ComplexElem<R,S>( re.negate() , im.negate() ) );
	}
	
	@Override
	public ComplexElem<R, S> mutate( Mutator<R> mutr ) {
		return( new ComplexElem<R,S>( mutr.mutate( re ) , mutr.mutate( im ) ) );
	}

	@Override
	public ComplexElem<R, S> invert() throws NotInvertibleException {
		final R a = re;
		final R b = im;
		final R binv = b.invert();
		final R cnumer = binv.mult( a ).mult( binv );
		final R cdenom = ( a.getFac().identity() ).add( cnumer.mult( a ) );
		final R c = ( cdenom.invert() ).mult( cnumer );
		final R d = binv.mult( a.mult( c ).add( a.getFac().negativeIdentity() ) );
		return( new ComplexElem<R,S>( c , d ) );
	}

	@Override
	public ComplexElem<R, S> divideBy(int val) {
		return( new ComplexElem<R,S>( re.divideBy(val) , im.divideBy(val) ) );
	}

	@Override
	public ComplexElemFactory<R, S> getFac() {
		return( new ComplexElemFactory<R,S>( (S)( re.getFac() ) ) );
	}

	
	public ComplexElem( R _re , R _im )
	{
		re = _re;
		im = _im;
	}
	
	

	/**
	 * @return the re
	 */
	public R getRe() {
		return re;
	}

	/**
	 * @return the im
	 */
	public R getIm() {
		return im;
	}



	private R re;
	private R im;

}

