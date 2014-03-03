




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
import simplealgebra.NotInvertibleException;

public class SymbolicMult<R extends Elem<R,?>, S extends ElemFactory<R,S>> extends SymbolicElem<R,S> 
{

	public SymbolicMult( SymbolicElem<R,S> _elemA , SymbolicElem<R,S> _elemB , S _fac )
	{
		super( _fac );
		elemA = _elemA;
		elemB = _elemB;
	}
	
	@Override
	public R eval( ) throws NotInvertibleException {
		return( elemA.eval().mult( elemB.eval() ) );
	}

	@Override
	public String writeString( ) {
		return( "mult( " + ( elemA.writeString() ) + " , " + ( elemB.writeString() ) + " )" );
	}
	
	private SymbolicElem<R,S> elemA;
	private SymbolicElem<R,S> elemB;

}

