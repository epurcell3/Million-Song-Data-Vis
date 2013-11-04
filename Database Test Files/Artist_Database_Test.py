__author__ = 'tbowling3'

import os
import sys
try:
    import sqlite3
except ImportError:
    print('you need sqlite3 installed to use this program')
    sys.exit(0)

# connect to the SQLite database
msd_subset_path='/Users/ducttapeboro/Documents/College/CS_4460/MillionSong/MillionSongSubset'
msd_subset_addf_path=os.path.join(msd_subset_path,'AdditionalFiles')
conn = sqlite3.connect(os.path.join(msd_subset_addf_path,
                                    'subset_artist_similarity.db'))



def encode_string(s):
    """
    Simple utility function to make sure a string is proper
    to be used in a SQLite query
    (different than posgtresql, no N to specify unicode)
    EXAMPLE:
      That's my boy! -> 'That''s my boy!'
    """
    return "'"+s.replace("'","''")+"'"

# from that connection, get a cursor to do queries
# NOTE: we could query directly from the connection object
c = conn.cursor()

print('*************** GENERAL SQLITE DEMO ***************************')

# list all tables in that dataset
# note that sqlite does the actual job when we call fetchall() or fetchone()
q = "SELECT name FROM sqlite_master WHERE type='table' ORDER BY name"
res = c.execute(q)
print("* tables contained in that SQLite file/database (there should be 3):")
print(res.fetchall())

# list all indices
q = "SELECT name FROM sqlite_master WHERE type='index' ORDER BY name"
res = c.execute(q)
print('* indices in the database to make reads faster:')
print(res.fetchall())

print('*************** ARTISTS TABLE DEMO ****************************')

# list all artist ID
q = "SELECT artist_id FROM artists"
res = c.execute(q)
print("* number of artist Echo Nest ID in 'artists' table:")
print(len(res.fetchall()))

print('*************** ARTIST SIMILARITY DEMO ************************')

# get a random similarity relationship
q = "SELECT target,similar FROM similarity LIMIT 1"
res = c.execute(q)
a,s = res.fetchone()
print('* one random similarity relationship (A->B means B similar to A):')
print(a,'->',s)

# count number of similar artist to a in previous call
q = "SELECT Count(similar) FROM similarity WHERE target="+encode_string(a)
res = c.execute(q)
print('* artist',a,'has that many similar artists in the dataset:')
print(res.fetchone()[0])

# count number of artist s (c queries up) is similar to
q = "SELECT Count(target) FROM similarity WHERE similar="+encode_string(s)
res = c.execute(q)
print('* artist',s,'is similar to that many artists in the dataset:')
print(res.fetchone()[0])

# DONE
# close cursor and connection
# (if for some reason you added stuff to the db or alter
#  a table, you need to also do a conn.commit())
c.close()
conn.close()