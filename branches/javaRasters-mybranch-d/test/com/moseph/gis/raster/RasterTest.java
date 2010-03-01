package com.moseph.gis.raster;

import static org.junit.Assert.*;

public class RasterTest
{
	
	public static void assertRasterDataEqual( Raster r1, Raster r2 )
	{
		assertEquals( "Rasters have unequal numbers of rows: " + r1.rows + " vs " + r2.rows, r1.rows, r2.rows );
		assertEquals( "Rasters have unequal numbers of cols: " + r1.cols + " vs " + r2.cols, r1.cols, r2.cols );
		for( int i = 0; i < r1.rows; i++ )
		{
			for( int j = 0; j < r1.cols; j++ )
			{
				double v1 = r1.data[i][j];
				double v2 = r2.data[i][j];
				assertEquals( "Different values (" + r1 + "," + r2 + ") at " + i + ", " + j, v1, v2, 0.00000000001 );
			}
		}
	}
	
	public static void assertRastersEqual( Raster r1, Raster r2 )
	{
		assertEquals( "Rasters have different xll (" + r1.xll + ", " + r2.xll + ")", r1.xll, r2.xll, 0.000000000001 );
		assertEquals( "Rasters have different yll (" + r1.yll + ", " + r2.yll + ")", r1.yll, r2.yll, 0.000000000001 );
		assertEquals( "Rasters have different cellsize (" + r1.cellsize + ", " + r2.cellsize + ")", r1.cellsize, r2.cellsize, 0.000000000001 );
		assertEquals( "Rasters have different NDATA (" + r1.NDATA + ", " + r2.NDATA + ")", r1.NDATA, r2.NDATA );
		assertRasterDataEqual( r1, r2 );
	}
	
	

}
