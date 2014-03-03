




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

public class SquareMatrixElemFactory<U extends NumDimensions, R extends Elem<R,?>, S extends ElemFactory<R,S>> extends ElemFactory<SquareMatrixElem<U,R,S>, SquareMatrixElemFactory<U,R,S>> {

	
	public SquareMatrixElemFactory( S _fac , U _dim )
	{
		fac = _fac;
		dim = _dim;
	}
	
	
	@Override
	public SquareMatrixElem<U, R, S> identity() {
		final BigInteger max = dim.getVal();
		SquareMatrixElem<U, R, S> ret = new SquareMatrixElem<U, R, S>( fac , dim );
		BigInteger cnt = BigInteger.ZERO;
		for( cnt = BigInteger.ZERO ; cnt.compareTo(max) < 0 ; cnt = cnt.add( BigInteger.ONE ) )
		{
			ret.setVal(cnt, cnt, fac.identity());
		}
		return( ret );
	}

	@Override
	public SquareMatrixElem<U, R, S> zero() {
		return( new SquareMatrixElem<U, R, S>( fac , dim ) );
	}
	
	
	private S fac;
	private U dim;

}



