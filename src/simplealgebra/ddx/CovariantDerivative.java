




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





package simplealgebra.ddx;

import simplealgebra.Elem;
import simplealgebra.ElemFactory;
import simplealgebra.NotInvertibleException;
import simplealgebra.NumDimensions;
import simplealgebra.et.EinsteinTensorElem;
import simplealgebra.et.EinsteinTensorElemFactory;
import simplealgebra.symbolic.MultiplicativeDistributionRequiredException;
import simplealgebra.symbolic.SymbolicElem;
import simplealgebra.symbolic.SymbolicElemFactory;


/**
 * Implements a covariant derivative operator as used in General Relativity.
 * 
 * @author thorngreen
 *
 * @param <U>
 * @param <R>
 * @param <S>
 */
public class CovariantDerivative<U extends NumDimensions, R extends Elem<R,?>, S extends ElemFactory<R,S>, K extends Elem<?,?>> 
		extends DerivativeElem<EinsteinTensorElem<U,SymbolicElem<R,S>,SymbolicElemFactory<R,S>>,EinsteinTensorElemFactory<U,SymbolicElem<R,S>,SymbolicElemFactory<R,S>>>          // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
{

	public CovariantDerivative( EinsteinTensorElemFactory<U, SymbolicElem<R, S>, 
				SymbolicElemFactory<R, S>> _fac , 
			EinsteinTensorElem<U, SymbolicElem<R, S>, 
				SymbolicElemFactory<R, S>> _vectorWithRespectTo,
			U _dim ,
			DirectionalDerivativePartialFactory<R,S,K> _dfac )
	{
		super( _fac );
		vectorWithRespectTo = _vectorWithRespectTo;
		dim = _dim;
		dfac = _dfac;
	}
	
	@Override
	public EinsteinTensorElem<U, SymbolicElem<R, S>, SymbolicElemFactory<R, S>> evalDerivative(
			SymbolicElem<EinsteinTensorElem<U, SymbolicElem<R, S>, SymbolicElemFactory<R, S>>, EinsteinTensorElemFactory<U, SymbolicElem<R, S>, SymbolicElemFactory<R, S>>> in )
			throws NotInvertibleException, MultiplicativeDistributionRequiredException {
		
		// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! TBD !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		
		return( null );
	}
	

	@Override
	public String writeString( ) {
		return( "covariantDerivative" );
	}
	
	
	private EinsteinTensorElem<U, SymbolicElem<R, S>, 
		SymbolicElemFactory<R, S>> vectorWithRespectTo;
	private U dim;
	private DirectionalDerivativePartialFactory<R,S,K> dfac;
	
	

}

