This is a simple library to read and write the ASCII raster files commonly used in GIS work.

It hasn't currently seen heavy use, and is in a very beta state, but it works fine for me.

Usage should be simple - create a RasterReader and call ReadRaster to get a Raster object. Use a RasterWriter to write a Raster (or array of data) to a raster file. See the test directory for examples.