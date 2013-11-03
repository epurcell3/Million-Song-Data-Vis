__author__ = 'tbowling3'

import Iterate_All_Songs
import Song

class SongItemCreator():

    def __init__(self):
        ias = Iterate_All_Songs()

        artistTermsTable = ias.getArtistTermsTable()
        artistmbtagstable = ias.getArtistMBtagsTable()


