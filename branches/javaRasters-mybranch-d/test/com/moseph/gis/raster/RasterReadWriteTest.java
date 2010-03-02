package com.moseph.gis.raster;

import java.io.IOException;

import org.junit.Test;

public class RasterReadWriteTest extends RasterTestRunner
{
	RasterReader reader = new RasterReader();
	RasterWriter writer = new RasterWriter();

	@Test
	public void testRoundtrip() throws IOException
	{
		Raster r1 = new Raster();
		r1.setSize( 3, 3 );
		r1.setXll( 0 );
		r1.setYll( 0 );
		r1.setCellsize( 100 );
		r1.setNDATA( "-9999" );
		
		r1.setValue( 0, 0, 1 );
		r1.setValue( 1, 1, 2 );
		r1.setValue( 2, 2, 3 );
		
		writer.writeRaster( "testData/rt1.asc", r1 );
		r1.print();
		Raster r2 = reader.readRaster( "testData/rt1.asc" );
		r2.print();
		assertRastersEqual( r1, r2 );
	}
	
	
	
}
