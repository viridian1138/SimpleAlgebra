




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





package simplealgebra.symbolic;

import simplealgebra.Elem;
import simplealgebra.ElemFactory;
import simplealgebra.Mutable;
import simplealgebra.Mutator;
import simplealgebra.NotInvertibleException;

public class SymbolicMutable<R extends Elem<R,?>, S extends ElemFactory<R,S>, T extends Elem<T,?>> extends SymbolicElem<R,S> 
{
	
	private SymbolicElem<R,S> elemA;
	private Mutator<T> elemB;

	
	public SymbolicMutable( SymbolicElem<R,S> _elemA , Mutator<T> _elemB , S _fac )
	{
		super( _fac );
		elemA = _elemA;
		elemB = _elemB;
	}
	
	@Override
	public R eval( ) throws NotInvertibleException {
		final R evl = elemA.eval();
		Mutable<?,R,T> mutr = (Mutable<?,R,T>) evl;
		return( mutr.mutate( elemB ) );
	}

	@Override
	public String writeString( ) {
		return( elemB.writeString() + "( " + elemA.writeString() + " )" );
	}

}

