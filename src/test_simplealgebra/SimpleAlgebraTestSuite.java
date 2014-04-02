/**
 * 
 */
package test_simplealgebra;

import junit.framework.Test;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

/**
 * @author thorngreen
 *
 */
public class SimpleAlgebraTestSuite extends TestSuite {

	
	public static Test suite()
	{
		final TestSuite s = new TestSuite();
		s.addTestSuite( TestInvertSimple.class );
		return( s );
	}
	
	
	public static void main( String in )
	{
		TestRunner run = new TestRunner();
		run.doRun( new SimpleAlgebraTestSuite() );
	}


}

