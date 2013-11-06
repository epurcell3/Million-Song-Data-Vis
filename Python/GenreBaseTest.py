__author__ = 'tbowling3'

import SongItemCreator
import SongList
import GenreBase


sic = SongItemCreator()
song_list = sic.get_song_list()

song_base = SongList.SongList(song_list)

gb = GenreBase.GenreBase(song_base)