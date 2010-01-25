package com.moseph.gis.raster;


import java.io.*;
import java.text.NumberFormat;
import java.util.regex.*;

public class RasterWriter
{
	NumberFormat cellFormat = null;
	String nodataString = Raster.DEFAULT_NODATA;
	
	public static void main( String[] args ) throws IOException
	{
		RasterWriter rt = new RasterWriter();
	}
	
	public void writeRaster( String filename, Raster r ) throws IOException
	{
		File f = new File( filename );
		if( f.exists() ) f.delete();
		if( ! f.createNewFile() ) System.err.println( "Could not create file for some reason!");
		PrintStream o = new PrintStream( f );
		//o = System.out;
		System.out.println( "writing header");
		o.println( "ncols " + r.getCols() );
		o.println( "nrows " + r.getRows() );
		o.println( "xllcorner " + r.getXll() );
		o.println( "yllcorner " + r.getYll());
		o.println( "cellsize " + r.getCellsize() );
		o.println( "NODATA_value " + r.getNDATA()  );
		System.out.println( "writing rows");
		
		for( double[] row : r.getData() )
		{
			StringBuffer b = new StringBuffer();
			for( int i = 0; i < row.length; i++ )
			{
				if( Double.isNaN( row[i] )  ) b.append( r.getNDATA() );
				else if( cellFormat != null ) b.append( cellFormat.format( row[i] ));
				else b.append( row[i] );
				if( i < row.length-1 ) b.append(  " "  );
			}
			o.println( b );
		}
		o.close();
	}
	
	public void writeRaster( String filename, double[][] data, double xll, double yll, double size, String ndata ) throws IOException
	{
		writeRaster( filename, Raster.getTempRaster( data, xll, yll, size, ndata ) );
	}
	
	public void setCellFormat( NumberFormat format )
	{
		cellFormat = format;
	}

}
