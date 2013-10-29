__author__ = 'mcdc09'

# usual imports
import os
import sys
import time
import glob
import datetime
import sqlite3
#import numpy as np # get it at: http://numpy.scipy.org/
# path to the Million Song Dataset subset (uncompressed)
# CHANGE IT TO YOUR LOCAL CONFIGURATION
msd_subset_path='/Users/ducttapeboro/Documents/College/CS_4460/MillionSong/MillionSongSubset'
msd_subset_data_path=os.path.join(msd_subset_path,'data')
msd_subset_addf_path=os.path.join(msd_subset_path,'AdditionalFiles')
assert os.path.isdir(msd_subset_path),'wrong path' # sanity check
# path to the Million Song Dataset code
# CHANGE IT TO YOUR LOCAL CONFIGURATION
msd_code_path='/Users/ducttapeboro/Documents/College/CS_4460/MillionSong/MSongsDB'
assert os.path.isdir(msd_code_path),'wrong path' # sanity check
# we add some paths to python so we can import MSD code
# Ubuntu: you can change the environment variable PYTHONPATH
# in your .bashrc file so you do not have to type these lines
sys.path.append( os.path.join(msd_code_path,'PythonSrc') )

# imports specific to the MSD
import hdf5_getters as GETTERS

conn_tracks = sqlite3.connect(os.path.join(msd_subset_addf_path,
                                    'subset_track_metadata.db'))

conn_artist = sqlite3.connect(os.path.join(msd_subset_addf_path,
                                    'subset_artist_term.db'))

q_tracks = "SELECT song_id, artist_id FROM songs"
res_tracks = conn_tracks.execute(q_tracks)

q_artist = "SELECT artist_id, artist_mbtag, artist_mbtags_count, artist_terms, artist_terms_freq, artist_terms_weight FROM artists"
res_artist = conn_artist.execute(q_artist)

all_artist_ids = map(lambda x: x[1], res_tracks.fetchall())
conn_tracks.close()
conn_artist.close()

# The Echo Nest artist id look like:
for k in range(4):
    print(all_artist_ids[k])
