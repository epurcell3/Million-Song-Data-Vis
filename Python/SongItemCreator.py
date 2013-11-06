__author__ = 'tbowling3'

import Iterate_All_Songs
import Song
import SongList

class SongItemCreator():

    def __init__(self):
        ias = Iterate_All_Songs.IterateAllSongs()

        artistTermsTable = ias.getArtistTermsTable()
        #artistmbtagstable = ias.getArtistMBtagsTable()

        self.songList = {}
        song_count = 0
        row_count = 0
        song_id = artistTermsTable[row_count][0]
        song_title = artistTermsTable[row_count][1]
        artist_id = artistTermsTable[row_count][2]
        artist_name = artistTermsTable[row_count][3]
        term = artistTermsTable[row_count][4]

        s = Song.Song(song_id, song_title, artist_id, artist_name)
        song_count += 1
        s.addTerm(term)
        self.songList = {song_id:s}
        row_count += 1

        while(row_count < len(artistTermsTable)):
            song_id = artistTermsTable[row_count][0]
            song_title = artistTermsTable[row_count][1]
            artist_id = artistTermsTable[row_count][2]
            artist_name = artistTermsTable[row_count][3]
            term = artistTermsTable[row_count][4]

            if song_id in self.songList.keys():
                self.songList[song_id].addTerm(term)
            else:
                s = Song.Song(song_id, song_title, artist_id, artist_name)
                song_count += 1
                s.addTerm(term)
                self.songList[song_id] = s

            row_count += 1

        print("Songs: ", song_count)
        print("Rows: ", row_count)

        #self.sl = SongList.SongList(self.songList)

    def get_song_list(self):
        return self.songList

def main():
    sic = SongItemCreator()


if __name__ == "__main__":
    main()

