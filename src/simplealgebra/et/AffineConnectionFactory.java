





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

import simplealgebra.Elem;
import simplealgebra.ElemFactory;
import simplealgebra.symbolic.SymbolicElem;

/**
 * Factory for affine connections (also known as Christoffel symbol and/or "Gamma symbol").
 * 
 * @author thorngreen
 *
 * @param <Z>
 * @param <R>
 * @param <S>
 */
public class AffineConnectionFactory<Z extends Object, R extends Elem<R,?>, S extends ElemFactory<R,S>> {
	
	MetricTensorFactory<Z,R,S> metric;
	TemporaryIndexFactory<Z> temp;
	OrdinaryDerivativeFactory<Z,R,S> deriv;
	
	
	public AffineConnectionFactory( MetricTensorFactory<Z,R,S> _metric , 
			TemporaryIndexFactory<Z> _temp , OrdinaryDerivativeFactory<Z,R,S> _deriv )
	{
		metric = _metric;
		temp = _temp;
		deriv = _deriv;
	}
	
	
	public SymbolicElem<EinsteinTensorElem<Z, R, S>, EinsteinTensorElemFactory<Z, R, S>> 
		getAffineConnection( Z covar1 , Z covar2 , Z contravar1 )
	{
		final Z p = temp.getTemp();
		
		final SymbolicElem<EinsteinTensorElem<Z, R, S>, EinsteinTensorElemFactory<Z, R, S>> outerTerm = 
				metric.getMetricTensor( false , contravar1 , p ).divideBy( 2 );
		
		final SymbolicElem<EinsteinTensorElem<Z, R, S>, EinsteinTensorElemFactory<Z, R, S>> innerBeforeDeriv1 =
				metric.getMetricTensor( true , p , covar1 );
		
		final SymbolicElem<EinsteinTensorElem<Z, R, S>, EinsteinTensorElemFactory<Z, R, S>> innerBeforeDeriv2 =
				metric.getMetricTensor( true , p , covar2 );
		
		final SymbolicElem<EinsteinTensorElem<Z, R, S>, EinsteinTensorElemFactory<Z, R, S>> innerBeforeDeriv3 =
				metric.getMetricTensor( true , covar1 , covar2 );
		
		
		
		final SymbolicElem<EinsteinTensorElem<Z, R, S>, EinsteinTensorElemFactory<Z, R, S>> inner1 =
				deriv.getOrdinaryDerivative( innerBeforeDeriv1 , covar2 );
		
		final SymbolicElem<EinsteinTensorElem<Z, R, S>, EinsteinTensorElemFactory<Z, R, S>> inner2 =
				deriv.getOrdinaryDerivative( innerBeforeDeriv2 , covar1 );
		
		final SymbolicElem<EinsteinTensorElem<Z, R, S>, EinsteinTensorElemFactory<Z, R, S>> inner3 =
				deriv.getOrdinaryDerivative( innerBeforeDeriv3 , p );
		
		
		
		final SymbolicElem<EinsteinTensorElem<Z, R, S>, EinsteinTensorElemFactory<Z, R, S>> oi1 = outerTerm.mult( inner1 );
		
		final SymbolicElem<EinsteinTensorElem<Z, R, S>, EinsteinTensorElemFactory<Z, R, S>> oi2 = outerTerm.mult( inner2 );
		
		final SymbolicElem<EinsteinTensorElem<Z, R, S>, EinsteinTensorElemFactory<Z, R, S>> oi3 = outerTerm.mult( inner3 );
		
		
		final SymbolicElem<EinsteinTensorElem<Z, R, S>, EinsteinTensorElemFactory<Z, R, S>> ret =
				oi1.add( oi2 ).add( oi3 );
		
		
		return( ret );
	}
	
	

}


