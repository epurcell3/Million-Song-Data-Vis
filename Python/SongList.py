__author__ = 'tbowling3'

class SongList:

    def __init__(self, song_list):
        self.song_list = song_list

    def getNext(self):
        return self.song_list.pop()