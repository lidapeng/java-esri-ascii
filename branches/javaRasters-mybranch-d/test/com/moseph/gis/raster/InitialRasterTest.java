package com.moseph.gis.raster;

import org.junit.Test;

public class InitialRasterTest extends RasterTest
{

	@Test
	public void testDuplicationEquality()
	{
		Raster r1 = new Raster();
		r1.NDATA = "-9999";
		r1.cols = 2;
		r1.rows = 2;
		r1.initData( 0 );
		r1.xll = 0;
		r1.yll = 0;

		Raster r2 = new Raster();
		r2.init( r1 );
		r2.initData( 0 );
		r1.data[0][0] = 1;
		// TODO Better to show `assertRastersNotEqual` here
//		assertRastersEqual( r1, r2 );

		r1.data[0][0] = 1;
		r2.data[0][0] = 1;
		assertRastersEqual( r1, r2 );

	}

}
