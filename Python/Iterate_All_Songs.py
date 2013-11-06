__author__ = 'mcdc09'

# usual imports
import os
import sqlite3


class IterateAllSongs():
    # CHANGE IT TO YOUR LOCAL CONFIGURATION

    def __init__(self):
        self.msd_subset_path='/Users/ducttapeboro/Documents/College/CS_4460/MillionSong/Million-Song-Data-Vis' # path to MSD Database files
        self.limit = " "

    def database_setup(self):
        assert os.path.isdir(self.msd_subset_path),'wrong path' # sanity check for path to the Million Song Dataset code

        path_dummy = os.path.join(self.msd_subset_path, 'subset_dummy.db')
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
        q = ""
        q = q + "SELECT song_id, title, 'songs'.artist_id, artist_name, term "
        q = q + "FROM (t_db.'songs' JOIN at_db.'artist_term') "
        q = q + "WHERE 'songs'.artist_id = 'artist_term'.artist_id "
        q = q + self.limit
        q = q + ";"

        print("Q: ", q)
        q_all_res = conn_dummy.execute(q)
        results_terms = q_all_res.fetchall()

        conn_dummy.close()
        return results_terms

    def getArtistMBtagsTable(self):
        conn_dummy = self.database_setup()

        # Get Artist mbtags results
        q = ""
        q = q + "SELECT song_id, title, 'songs'.artist_id, artist_name, mbtag "
        q = q + "FROM (t_db.'songs' JOIN at_db.'artist_mbtag') "
        q = q + "WHERE 'songs'.artist_id = 'artist_mbtag'.artist_id "
        q = q + self.limit
        q = q + ";"

        print("Q: ", q)
        q_all_res = conn_dummy.execute(q)
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
    print("")
    print("")
    print("song id: \t", results_terms[0][0])
    print("song title: \t", results_terms[0][1])

if __name__ == "__main__":
    main()