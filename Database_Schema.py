__author__ = 'tbowling3'

import os
import sqlite3
import sys

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


conn_tracks = sqlite3.connect(os.path.join(msd_subset_addf_path,
                                    'subset_track_metadata.db'))

conn_artist = sqlite3.connect(os.path.join(msd_subset_addf_path,
                                    'subset_artist_term.db'))

conn_similar = sqlite3.connect(os.path.join(msd_subset_addf_path,
                                    'subset_artist_similarity.db'))

conn_dummy = sqlite3.connect(os.path.join(msd_subset_addf_path,
                                    'subset_dummy.db'))

print("****************** subset_track_metadata.db ******************")


q = "SELECT name FROM sqlite_master WHERE type='table' ORDER BY name"
res = conn_tracks.execute(q)
print("* tables contained in DB File:")
print(res.fetchall())
print(" ")

q = "SELECT name FROM sqlite_master WHERE tbl_name='songs' ORDER BY name"
res = conn_tracks.execute(q)
print("* Columns in 'songs' Table:")
print(res.fetchall())
print(" ")

print("****************** subset_artist_term.db ******************")


q = "SELECT name FROM sqlite_master WHERE type='table' ORDER BY name"
res = conn_artist.execute(q)
print("* tables contained in DB File:")
print(res.fetchall())
print(" ")

q = "SELECT name FROM sqlite_master WHERE tbl_name='artist_mbtag' ORDER BY name"
res = conn_artist.execute(q)
print("* Columns in 'artist_mbtag' Table:")
print(res.fetchall())
print(" ")

q = "SELECT name FROM sqlite_master WHERE tbl_name='artist_term' ORDER BY name"
res = conn_artist.execute(q)
print("* Columns in 'artist_term' Table:")
print(res.fetchall())
print(" ")

q = "SELECT name FROM sqlite_master WHERE tbl_name='artists' ORDER BY name"
res = conn_artist.execute(q)
print("* Columns in 'artists' Table:")
print(res.fetchall())
print(" ")

q = "SELECT name FROM sqlite_master WHERE tbl_name='mbtags' ORDER BY name"
res = conn_artist.execute(q)
print("* Columns in 'mbtags' Table:")
print(res.fetchall())
print(" ")

q = "SELECT name FROM sqlite_master WHERE tbl_name='terms' ORDER BY name"
res = conn_artist.execute(q)
print("* Columns in 'terms' Table:")
print(res.fetchall())
print(" ")

print("****************** subset_artist_similarity.db ******************")


q = "SELECT name FROM sqlite_master WHERE type='table' ORDER BY name"
res = conn_similar.execute(q)
print("* tables contained in DB File:")
print(res.fetchall())
print(" ")

q = "SELECT name FROM sqlite_master WHERE tbl_name='artists' ORDER BY name"
res = conn_similar.execute(q)
print("* Columns in 'artists' Table:")
print(res.fetchall())
print(" ")

q = "SELECT name FROM sqlite_master WHERE tbl_name='similarity' ORDER BY name"
res = conn_similar.execute(q)
print("* Columns in 'similarity' Table:")
print(res.fetchall())
print(" ")


print("****************** subset_dummy.db ******************")

q = "SELECT name FROM sqlite_master WHERE type='table' ORDER BY name"
res = conn_dummy.execute(q)
print("* tables contained in DB File:")
print(res.fetchall())
print(" ")


conn_tracks.close()
conn_artist.close()
conn_similar.close()
conn_dummy.close()