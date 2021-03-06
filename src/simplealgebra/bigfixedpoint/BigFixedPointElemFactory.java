





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






package simplealgebra.bigfixedpoint;

import java.math.BigInteger;
import java.util.ArrayList;

import simplealgebra.AbsoluteValue;
import simplealgebra.ElemFactory;
import simplealgebra.NotInvertibleException;
import simplealgebra.symbolic.SymbolicAbsoluteValue;
import simplealgebra.symbolic.SymbolicElem;

public class BigFixedPointElemFactory<T extends Precision> extends ElemFactory<BigFixedPointElem<T>, BigFixedPointElemFactory<T>> {
	
	public BigFixedPointElemFactory( T _prec )
	{
		prec = _prec;
	}

	
	private T prec;



	@Override
	public BigFixedPointElem<T> identity() {
		return( new BigFixedPointElem<T>( prec.getVal() , prec ) );
	}



	@Override
	public BigFixedPointElem<T> zero() {
		return( new BigFixedPointElem<T>( BigInteger.ZERO , prec ) );
	}
	
	
	@Override
	public boolean isMultCommutative()
	{
		return( true );
	}
	
	
	@Override
	public SymbolicElem<BigFixedPointElem<T>, BigFixedPointElemFactory<T>> handleSymbolicOptionalOp( Object id , 
			ArrayList<SymbolicElem<BigFixedPointElem<T>, BigFixedPointElemFactory<T>>> args )  throws NotInvertibleException
	{
		if( id instanceof AbsoluteValue )
		{
			switch( (AbsoluteValue) id )
			{
				case ABSOLUTE_VALUE:
				{
					SymbolicElem<BigFixedPointElem<T>, BigFixedPointElemFactory<T>> arg
						= args.get( 0 );
					return( new SymbolicAbsoluteValue<BigFixedPointElem<T>, BigFixedPointElemFactory<T>>( arg , arg.getFac().getFac() ) );
				}
				// break;
				
			}
		}
		
		return( super.handleSymbolicOptionalOp(id, args) );
	}

	
}

