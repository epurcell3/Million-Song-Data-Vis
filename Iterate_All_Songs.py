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
#import hdf5_getters as GETTERS

path_tracks = os.path.join(msd_subset_addf_path, 'subset_track_metadata.db')
path_artist = os.path.join(msd_subset_addf_path, 'subset_artist_term.db')
path_similar = os.path.join(msd_subset_addf_path, 'subset_artist_similarity.db')
path_dummy = os.path.join(msd_subset_addf_path, 'subset_dummy.db')

conn_tracks = sqlite3.connect(path_tracks)
conn_artist = sqlite3.connect(path_artist)
conn_similar = sqlite3.connect(path_similar)
conn_dummy = sqlite3.connect(path_dummy)


# create cursor for dummy database
dummy_c = conn_dummy.cursor()
#dummy_c.execute("ATTACH database 'subset_track_metadata.db' AS 'track_db';")
#dummy_c.execute("ATTACH database 'subset_artist_term.db' AS 'artist_term_db';")

#q = "SELECT name FROM sqlite_master WHERE type='table' ORDER BY name"
#res = conn_dummy.execute(q)
#print("* tables contained in DB File:")
#print(res.fetchall())
#print(" ")

#q_all = "SELECT 'song_id', 'title' FROM track_db.songs JOIN 'artist_term', 'artist_id' FROM artist_db.artist_term WHERE songs.artist_id = artist_term.artist_id;"
q_all = dummy_c.execute("SELECT 'song_id', 'title' FROM track_db.'songs';")

res_all = conn_dummy.execute(q_all)




#q_tracks = "SELECT song_id, artist_id FROM songs"
#res_tracks = conn_tracks.execute(q_tracks)
#
#q_artist = "SELECT artist_id, term FROM artist_term"
#res_artist = conn_artist.execute(q_artist)
#
#
#
#all_artist_ids = map(lambda x: x[:], res_artist.fetchall())


conn_tracks.close()
conn_artist.close()
conn_similar.close()
conn_dummy.close()

# The Echo Nest artist id look like:
#for k in range(4):
#    print(all_artist_ids[k])
