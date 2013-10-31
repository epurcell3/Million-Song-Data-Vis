__author__ = 'mcdc09'

# usual imports
import os
import sys
import time
import glob
import datetime
import sqlite3
import numpy as np

class IterateAllSongs():
    # CHANGE IT TO YOUR LOCAL CONFIGURATION
    msd_subset_path='/Users/ducttapeboro/Documents/College/CS_4460/MillionSong/Million-Song-Data-Vis' # path to MSD Database files

    def database_setup(self):
        assert os.path.isdir(IterateAllSongs.msd_subset_path),'wrong path' # sanity check for path to the Million Song Dataset code

        path_dummy = os.path.join(IterateAllSongs.msd_subset_path, 'subset_dummy.db')
        conn_dummy = sqlite3.connect(path_dummy)


        # create cursor for dummy database
        dummy_c = conn_dummy.cursor()
        dummy_c.execute("ATTACH database 'subset_track_metadata.db' AS t_db;")
        dummy_c.execute("ATTACH database 'subset_artist_term.db' AS at_db;")
        dummy_c.execute("ATTACH database 'subset_artist_similarity.db' AS as_db;")
        conn_dummy.commit()
        return conn_dummy

    def getArtistTermsTable(self):
        conn_dummy = self.database_setup()

        # Get Artist Terms results
        q_select    = "SELECT song_id, title, 'songs'.artist_id, artist_name, term "
        q_from      = "FROM (t_db.'songs' JOIN at_db.'artist_term') "
        q_where     = "WHERE 'songs'.artist_id = 'artist_term'.artist_id "
        q_end       = ";"

        q_all = q_select + q_from + q_where + q_end
        q_all_res = conn_dummy.execute(q_all)
        results_terms = q_all_res.fetchall()

        conn_dummy.close()
        return results_terms

    def getArtistMBtagsTable(self):
        conn_dummy = self.database_setup()

        # Get Artist mbtags results
        q_select    = "SELECT song_id, title, 'songs'.artist_id, artist_name, mbtag "
        q_from      = "FROM (t_db.'songs' JOIN at_db.'artist_mbtag') "
        q_where     = "WHERE 'songs'.artist_id = 'artist_mbtag'.artist_id "
        q_end       = ";"

        q_all = q_select + q_from + q_where + q_end
        q_all_res = conn_dummy.execute(q_all)
        results_tags = q_all_res.fetchall()

        conn_dummy.close()
        return results_tags

def main():
    ias = IterateAllSongs()
    results_terms = ias.getArtistTermsTable()
    results_tags = ias.getArtistMBtagsTable()

    print("One Row From Terms Table:")
    print(results_terms[:1])
    print("")
    print("One Row From mbtabs Table:")
    print(results_tags[:1])

if __name__ == "__main__":
    main()