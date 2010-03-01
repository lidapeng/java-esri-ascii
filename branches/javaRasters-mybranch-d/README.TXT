This package is intended to re-spatialise RAMAS output. It expects the following inputs:

- a RAMAS metapop output file. This will somewhere in it have a
  collection of lines which describe the population in each population
  patch over several years
- RAMAS patch rasters. These are output from the spatial tools section
  of RAMAS, and you need one for each simulation year "File->Export
  Patch Map".

When it runs, it expects:
- the name of the population file
- a prefix for the patch rasters (these are likely to have names like
  mphare10.ASC), so if they were in the "data" directory, the prefix
  would be "data/mphare".
- a prefix for the output files (if you put in "data/harepop", you'll
  get files "harepop10.ASC" etc. appearing in the "data" directory.
- the "base year". All of the runs are relative to a base year. This
  will generally depend on the input patch map file names

Example invocation:

java -jar populationAssignment.jar data/mhare_clim_hab_dyn_base.MP data/mphare data/popHare 10

