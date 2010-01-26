package com.moseph.gis.raster;

import java.io.IOException;

import org.junit.*;
import static org.junit.Assert.*;

public class ReadingTest
{
	RasterReader reader = new RasterReader();

	@Test
	public void testReading() throws IOException
	{
		Raster r1 = reader.readRaster( "data/a.txt" );
		assertEquals( 136, r1.getCols() );
		assertEquals( 81, r1.getRows() );
		assertEquals( -10.05, r1.getXll(), 0.00001 );
		assertEquals( 35.85, r1.getYll(), 0.00001 );
		assertEquals( 0.1, r1.getCellsize(), 0.00001 );
		assertEquals( "-9999", r1.getNDATA() );
		assertTrue( Double.isNaN( r1.getValue( 0, 0 ) ));
		assertEquals( 0.1403, r1.getValue( 9, 7 ), 0.0000001 );
		
		Raster r2 = reader.readRaster( "data/b.ASC" );
		assertEquals( 0, r2.getValue( 0,0 ), 0.0000000000001 );
	}
}
