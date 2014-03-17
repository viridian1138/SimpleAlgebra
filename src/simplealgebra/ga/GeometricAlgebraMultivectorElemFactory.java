




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
import java.util.HashSet;

import simplealgebra.Elem;
import simplealgebra.ElemFactory;
import simplealgebra.NumDimensions;

public class GeometricAlgebraMultivectorElemFactory<U extends NumDimensions, R extends Elem<R,?>, S extends ElemFactory<R,S>> extends ElemFactory<GeometricAlgebraMultivectorElem<U,R,S>, GeometricAlgebraMultivectorElemFactory<U,R,S>> {

	
	public GeometricAlgebraMultivectorElemFactory( S _fac , U _dim )
	{
		fac = _fac;
		dim = _dim;
	}
	
	
	@Override
	public GeometricAlgebraMultivectorElem<U, R, S> identity() {
		GeometricAlgebraMultivectorElem<U, R, S> ret = new GeometricAlgebraMultivectorElem<U, R, S>( fac , dim );
		ret.setVal( new HashSet<BigInteger>(), fac.identity() );
		return( ret );
	}

	@Override
	public GeometricAlgebraMultivectorElem<U, R, S> zero() {
		return( new GeometricAlgebraMultivectorElem<U, R, S>( fac , dim ) );
	}
	
	
	public S getFac()
	{
		return( fac );
	}
	
	
	private S fac;
	private U dim;

}



