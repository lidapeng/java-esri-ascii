package com.moseph.gis.raster;

import java.util.Arrays;

/**
 * Represents my best guess at the ESRI ASCII raster format. I couldn't find
 * any sensible documentation, so it supports the following features:
 * <ul>
 * <li>cellsize, xll and xll are stored as doubles, and largely ignored
 * <li>NDATA has a string representation (as it is typically read in from an
 * ascii string) and is internally represented as Double.NaN, as this is safer and
 * easier to deal with than the -9999 found in most rasters.
 * </ul>
 * @author dmrust
 *
 */
public class Raster
{
	protected double[][] data;
	protected double xll;
	protected double yll;
	protected double cellsize;
	protected int cols;
	protected int rows;
	protected String NDATA;
	public static final String DEFAULT_NODATA = "-9999";

	public void print()
	{
		System.out.println( "Rows: " + rows + " cols: " + cols + " cellsize " + cellsize );
		for( double[] row : data )
		{
			for( double val : row )
				System.out.print( val + " " );
			System.out.println( "" );
		}

	}

	/**
	 * Creates an empty raster
	 */
	public Raster()
	{

	}

	/**
	 * Creates a raster from the given data
	 * @param cellsize
	 * @param xll
	 * @param yll
	 */
	public Raster( double cellsize, double xll, double yll )
	{
		this();
		setCellsize( cellsize );
		setXll( xll );
		setYll( yll );
	}

	/**
	 * Creates a raster from the given data
	 * @param cellsize
	 * @param xll
	 * @param yll
	 */
	public Raster( double cellsize, double xll, double yll, int numRows, int numCols )
	{
		this();
		setCellsize( cellsize );
		setXll( xll );
		setYll( yll );
		setSize( numRows, numCols );
	}
	
	public Raster( int minX, int minY, int maxX, int maxY )
	{
		this();
		setCellsize(1);
		setXll( minX );
		setYll( minY );
		setSize( maxY - minY + 1, maxX - minX + 1 );
	}

	/**
	 * Creates a raster from the given data
	 * @param data
	 * @param cellsize
	 * @param xll
	 * @param yll
	 */
	public Raster( double[][] data, double cellsize, double xll, double yll )
	{
		this(cellsize, xll, yll);
		setData( data );
	}

	/**
	 * Creates a raster from the given data
	 * @param data
	 * @param cellsize
	 * @param xll
	 * @param yll
	 */
	public Raster( int[][] data, double cellsize, double xll, double yll )
	{
		this(cellsize, xll, yll);
		setData( data );
	}

	public static Raster getTempRaster( double[][] data, double xll, double yll, double size )
	{
		return getTempRaster( data, xll, yll, size, DEFAULT_NODATA );
	}

	public static Raster getTempRaster( double[][] data, double xll, double yll, double size, String ndata )
	{
		Raster a = new Raster();
		a.data = data;
		a.xll = xll;
		a.yll = yll;
		a.cellsize = size;
		a.NDATA = ndata;
		a.rows = data.length;
		a.cols = data[0].length;
		return a;
	}


	/**
	 * Sets the parameters of this raster (rows, columns, corner, cellsize, NDATA etc)
	 * to be the same as the other raster. This includes initialising the data array
	 * with NDATAs
	 * @param other
	 */
	public void init( Raster other )
	{
		xll = other.xll;
		yll = other.yll;
		cellsize = other.cellsize;
		NDATA = other.NDATA;
		setSize( other.getRows(), other.getCols() );
	}

	/**
	 * Initialises the Raster to Double.NaN (i.e. NDATA)
	 */
	public void initData()
	{
		initData( Double.NaN );
	}

	/**
	 * Initialises the raster so the entire data array contains 'value'
	 * @param value
	 */
	public void initData( double value )
	{
		data = new double[rows][];
		for( int i = 0; i < rows; i++ )
		{
			data[i] = new double[cols];

			Arrays.fill( data[i], value );
		}
	}

	/**
	 * Returns the underlying data array - NOTE: this is *NOT* a copy, if you
	 * change it, you change the data
	 * @return
	 */
	public double[][] getData()
	{
		return data;
	}

	public void setValue( int row, int column, double value )
	{
		if( row < rows && column < cols )
			data[row][column] = value;
	}

	public double getValue( int row, int column )
	{
		if( row < rows && column < cols && row >= 0 && column >= 0)
			return data[row][column];
		return Double.NaN;
	}
	
	/**
	 * Returns the value at the given XY coordinate, assuming that x is
	 * relative to Xll and y is relative to Yll
	 * @param x
	 * @param y
	 * @return
	 */
	public double getXYValue( int x, int y )
	{
		return getValue( yToRow(y),xToCol(x));
	}
	
	public void setXYValue( int x, int y, double val )
	{
		setValue( yToRow( y ), xToCol( x ), val );
	}
	
	public int xToCol( int x ) { return x - (int)xll; }
	public int yToRow( int y ) { return (rows-(y-(int)yll))-1; }
	public int colToX( int col ) { return col + (int)xll; }
	public int rowToY( int row ) { return (int)yll-(row+1-rows); }

	/**
	 * Copies the given data into the underlying data array. Also updates the number of rows and columns.
	 * @param data
	 */
	public void setData( double[][] data )
	{
		rows = data.length;
		cols = data[0].length;
		initData();
		for( int i = 0; i < rows; i++ )
			for( int j = 0; j < cols; j++ )
				this.data[i][j] = data[i][j];
	}

	/**
	 * Copies the given data into the underlying data array. Also updates the number of rows and columns.
	 * @param data
	 */
	public void setData( int[][] data )
	{
		rows = data.length;
		cols = data[0].length;
		initData();
		for( int i = 0; i < rows; i++ )
			for( int j = 0; j < cols; j++ )
				this.data[i][j] = data[i][j];
	}



	public double getXll()
	{
		return xll;
	}

	public void setXll( double xll )
	{
		this.xll = xll;
	}

	public double getYll()
	{
		return yll;
	}

	public void setYll( double yll )
	{
		this.yll = yll;
	}

	public double getCellsize()
	{
		return cellsize;
	}

	public void setCellsize( double cellsize )
	{
		this.cellsize = cellsize;
	}

	public int getCols()
	{
		return cols;
	}

	public int getRows()
	{
		return rows;
	}

	/**
	 * Sets the size of the raster, and also initialises the array
	 * with NDATA
	 * @param nrows
	 * @param columns
	 */
	public void setSize( int nrows, int columns )
	{
		this.rows = nrows;
		this.cols = columns;
		initData();
	}

	public String getNDATA()
	{
		return NDATA;
	}

	public void setNDATA( String nDATA )
	{
		NDATA = nDATA;
	}
}
