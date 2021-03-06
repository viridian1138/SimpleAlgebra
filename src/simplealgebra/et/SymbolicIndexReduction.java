



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

import java.util.ArrayList;
import java.util.HashSet;

import simplealgebra.Elem;
import simplealgebra.ElemFactory;
import simplealgebra.NotInvertibleException;
import simplealgebra.symbolic.MultiplicativeDistributionRequiredException;
import simplealgebra.symbolic.SymbolicElem;


public class SymbolicIndexReduction<Z extends Object, R extends Elem<R,?>, S extends ElemFactory<R,S>> extends SymbolicElem<EinsteinTensorElem<Z,R,S>,EinsteinTensorElemFactory<Z,R,S>> 
{

	public SymbolicIndexReduction( SymbolicElem<EinsteinTensorElem<Z,R,S>,EinsteinTensorElemFactory<Z,R,S>> _elem , 
			EinsteinTensorElemFactory<Z,R,S> _fac ,
			HashSet<Z> _contravariantReduce , HashSet<Z> _covariantReduce )
	{
		super( _fac );
		elem = _elem;
		contravariantReduce = _contravariantReduce;
		covariantReduce = _covariantReduce;
	}
	
	@Override
	public EinsteinTensorElem<Z,R,S> eval( ) throws NotInvertibleException, MultiplicativeDistributionRequiredException {
		return( elem.eval().indexReduction( contravariantReduce , covariantReduce ) );
	}
	
	@Override
	public EinsteinTensorElem<Z,R,S> evalPartialDerivative( ArrayList<Elem<?,?>> withRespectTo ) throws NotInvertibleException, MultiplicativeDistributionRequiredException
	{
		return( elem.evalPartialDerivative( withRespectTo ).indexReduction( contravariantReduce , covariantReduce ) );
	}

	@Override
	public String writeString( ) {
		return( "symbolicIndexReduction" );
	}
	
	private HashSet<Z> contravariantReduce;
	private HashSet<Z> covariantReduce;
	private SymbolicElem<EinsteinTensorElem<Z,R,S>,EinsteinTensorElemFactory<Z,R,S>> elem;

}

